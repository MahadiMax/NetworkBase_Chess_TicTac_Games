package highlighters;

import board.Board;
import board.Check;
import log.Turn;
import highlighters.graphics.Highlight;
import highlighters.graphics.HighlightGroup;
import pieces.Piece;
import utils.IterationObj;
import utils.IterationObj.PieceBreak;
import utils.IterationObj.PieceReturn;
import utils.Vec;

import java.util.ArrayList;
import java.util.List;


public abstract class HighlighterBase implements Highlighter {


    private static HighlightGroup group;

    private static Board board;
    static Piece[][] pieces;

    /*** finds the moves that any piece can make if it is protecting the king***/

    //this returns null if there is no aggressor to protect from, and empty if there is but there are no moves
    //this method is the default but is overriden by a couple highlighters
    List<Vec> findAggressorMoves(Piece p){
        Vec king = sameKing(p);
        IterationObj obj = IterationObj.create(king.getX(),king.getY(),p.getX(),p.getY());
        if(obj.isNormalSlope())return null;

        ArrayList<Vec> moves = new ArrayList<>();

        PieceBreak br = (x,y)-> {

            moves.add(new Vec(x,y));

            //break if it's the piece we were going to
            if(x == p.getX() && y == p.getY())return true;

            //can return here if there is something inbetween the king and the piece
            return (pieces[x][y] != null);
        };
                                        //returns whether the next step should happen or not
        PieceReturn<Boolean> ret = (x,y)-> (x == p.getX() && y == p.getY());

        //iterate once so not on the king
        obj.iterateStartLoc();

        if(obj.iterate(br,ret)){

            boolean straight = obj.isStraight();

            PieceBreak br2 = (x,y)-> {
                moves.add(new Vec(x,y));
                return(pieces[x][y] != null);
            };

            PieceReturn<List<Vec>> ret2 = (x,y)-> {
                //if it makes it through the loop to the edge of the board without reaching anything
                if(x == -1)return null;

                Piece piece = pieces[x][y];
                if(piece.isWhite() == p.isWhite()){
                    return null;
                } else if(isMatchHighlighter(piece.highlighter(),straight)){

                        //it is not inefficient to check this this far in, as it is required to see if there is a piece on the same lines anyways
                        //if the piece in question can move along the same line as
                        if(isMatchHighlighter(p.highlighter(),straight)) {
                            moves.add(new Vec(x, y));
                            return moves;

                        }else if(p.highlighter() instanceof PawnHL){
                            return PawnHL.getInstance().findAggressorSpecial(p,piece,straight,obj.getSlope());
                        }

                        //returns empty list because the piece at this point cannot move
                        //whereas returning null would imply it does not have to protect
                        moves.clear();
                        return moves;
                }else {
                    return null;
                }

            };

            obj.ressetStartLoc(p.getX(),p.getY());
            obj.iterateStartLoc();
            return obj.iterate(br2,ret2);
        }
        return null;
    }

    //returns if the highlighter is same type as boolean
    //is more efficent then checking if a piece can attack, if it is already known that the piece is on the same lines
    public static boolean isMatchHighlighter(Highlighter highlighter, boolean straight){
        return (straight & highlighter.isStraight()) || (!straight && highlighter.isDiagonal());
    }



    //this method is called if the last move was a check
    //this will return all the moves that can protect the king
    //this implies, block the attackers move, or take the attacker
    List<Vec> protectKing(Piece p, List<Piece> agrs){
        ArrayList<Vec> moves = new ArrayList<>();

                    //for anybody except the king there is nothing that can be done at this point
        if(agrs.size() != 1)return moves;

        Piece agr = agrs.get(0);

        if(agr.highlighter().isStoppable()){
            Vec king = sameKing(p);
            IterationObj obj = IterationObj.create(king.getX(),king.getY(), agr.getX(), agr.getY());
            obj.iterateStartLoc();

            IterationObj.PieceBreak br = (x,y)->{
                if(x == agr.getX() && y == agr.getY()){
                    if(p.highlighter().canAttack(p,x,y)) moves.add(new Vec(x,y));
                    return true;
                }else{
                    if(p.highlighter().canMove(p,x,y)) moves.add(new Vec(x,y));
                    return false;
                }
            };

            //doesn't need to return anything, just needs to add the necessary moves
            IterationObj.PieceReturn<Boolean> ret = (x,y)->false;

            obj.iterate(br,ret);

        }else {
            //if can hit the unstoppable piece do it
            if(canAttack(p, agr.getX(), agr.getY()))moves.add(new Vec(agr.getX(), agr.getY()));
        }

        return moves;
    }

    /***Abstract or default methods that must or may be overriden***/
    //this method will return all the possible moves, assuming no check, or sacrifice
    abstract List<Vec> regularHighlight(Piece p);



    //by default just make one canAttack method and define these other ways of calling
    @Override
    public boolean canAttack(Piece p, Vec agr) {
        return canAttack(p,agr.getX(),agr.getY());
    }

    //this m
    @Override
    public boolean canMove(Piece p, int x, int y){
        return canAttack(p,x,y);
    }

    //works for any stoppable pieces, needs to be overriden for none stoppable pieces
    public boolean canAttack(Piece p, int aX, int aY){
        //make an object that will iterate from the king to the piece
        IterationObj obj = IterationObj.create(aX,aY,p.getX(),p.getY());

        if (obj.isNormalSlope()) return false;

        boolean straight = obj.isStraight();

        //if it cannot attack on the same line as the line the king sits on from it
        if(!isMatchHighlighter(p.highlighter(),straight))return false;

        //don't include the king in the iteration
        obj.iterateStartLoc();

        PieceBreak br = (x,y)->{

            if(board.getPieces()[x][y] != null)return true;

            return(x == p.getX() && y == p.getY());

        };

        PieceReturn<Boolean> ret = (x,y)->(x == p.getX() && y == p.getY());

        return obj.iterate(br,ret);


    }

    /****METHODS FOR SUBCLASSES TO USE***/

    private Vec sameKing(Piece p){
        return (p.isWhite()) ? board.getW_king() : board.getB_king();
    }


    final List<Vec> highlightAllOptions(Piece p, int[][] options) {
        List<Vec> points = new ArrayList<>();
        for (int[] opt : options) {
            int x = p.getX() + opt[0], y = p.getY() + opt[1];
            if (x < 0 || y < 0 || x >= 8 || y >= 8) continue;
            if (pieces[x][y] != null && pieces[x][y].isWhite() == p.isWhite()) continue;
            points.add(new Vec(x, y));
        }
        return points;
    }

    //this method is for methods that require a breakpoint at the enemy piece, or before a piece of the same team
    final boolean addAndBreakIfEnd(List<Vec> points, Piece p, int x, int y) {

        if (pieces[x][y] == null) {
            points.add(new Vec(x, y));
            return false;
        } else if (pieces[x][y].isWhite() != p.isWhite()) {
            points.add(new Vec(x, y));
        }
        return true;
    }


    /***DOES ALL OF THE GENERIC WORK, BASED OFF OF OVERRIDEN METHODS***/


    final public List<Vec> highlights(Piece p){
        if (pieces == null) {
            board = Board.getInstance();
            pieces = board.getPieces();
        }
        if (group == null) group = HighlightGroup.getInstance();
        //there's a sizing node in here so we must be careful to just remove the highlights and not it
        group.getChildren().removeIf(o -> o instanceof Highlight);

        List<Vec> aggressorMoves, protectKing = null, finalMoves;

        aggressorMoves = findAggressorMoves(p);

        if (Check.getInstance().isCheck() && Check.getInstance().isWhiteCheck() != p.isWhite()) {
            protectKing = protectKing(p,Check.getInstance().getCheckers());
        }

        if (aggressorMoves != null && protectKing != null) {
            return new ArrayList<>();
        } else if (aggressorMoves != null) {
            finalMoves = aggressorMoves;
        } else if (protectKing != null) {
            finalMoves = protectKing;
        } else {
            finalMoves = regularHighlight(p);
        }

        return finalMoves;
    }

    Highlight highlight(Piece p,int x, int y) {
        return new Highlight(p,x,y);
    }


    final public void highlight(Piece p) {

        if(!Turn.getInstance().getTurn())return;

        List<Vec> moves = highlights(p);
        //adds the list of final moves to the highlightGroup
        for (Vec finalMove : moves) {
            Highlight hl = highlight(p, finalMove.getX(), finalMove.getY());
            group.getChildren().add(hl);
        }

    }

}
