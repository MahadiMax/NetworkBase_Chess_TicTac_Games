package highlighters;

import highlighters.graphics.CastleHighlight;
import highlighters.graphics.Highlight;
import pieces.Knight;
import pieces.Piece;
import utils.IterationObj;
import utils.IterationObj.PieceBreak;
import utils.IterationObj.PieceReturn;
import utils.Vec;

import java.util.ArrayList;
import java.util.List;

public class KingHL extends HighlighterBase {

    private static final int[][] options = {
            {-1,-1},{0,-1},{1,-1},
            {-1,0}, /*x,y*/{1,0},
            {-1,1}, {0,1}, {1,1}
    };

    private static final KingHL ourInstance = new KingHL();

    public static KingHL getInstance() {
        return ourInstance;
    }

    private KingHL() {
    }

    List<Vec> protectKing(Piece p, List<Piece> agrs){
        return regularHighlight(p);
    }

    @Override
    List<Vec> findAggressorMoves(Piece p) {
        return null;
    }


    private List<Vec> castlingMoves(Piece p){

        ArrayList<Vec> moves = new ArrayList<>();
        if(p.hasMoved())return moves;


        Piece rook;
        for(int i = p.getX()+1; i < 8; i++){
            if((rook = pieces[i][p.getY()]) != null){
                if(rook.highlighter() instanceof StraightHL){
                    if(rook.isWhite() != p.isWhite())break;
                    if(rook.hasMoved())break;
                    moves.add(new Vec(p.getX()+2,p.getY()));
                }
                break;
            }
        }
        for(int i = p.getX()-1; i >= 0; i--){
            if((rook = pieces[i][p.getY()]) != null){
                if(rook.highlighter() instanceof StraightHL){
                    if(rook.isWhite() != p.isWhite())break;
                    if(rook.hasMoved())break;
                    moves.add(new Vec(p.getX()-2,p.getY()));
                }
                break;
            }
        }

        return moves;
    }

    boolean castlingThroughCheck(Vec v, Piece p, List<Vec> moves){
        int dif = p.getX() - v.getX();
            //not a castle move
        if(Math.abs(dif) != 2)return false;

                                //check if the moves contains a vector in between the castle and the king
                                //if it doesn't this means that it would be castling through a check
        return !moves.contains(new Vec(v.getX() + dif/2,v.getY()));
    }

    Highlight highlight(Piece p,int x, int y){
        if(Math.abs(p.getX()-x) > 1){
            if(p.getX() > x){
                return new CastleHighlight(p,x,y,pieces[0][y],x+1,y);
            }else{
                return new CastleHighlight(p,x,y,pieces[7][y],x-1,y);
            }
        }else{
            return new Highlight(p,x,y);
        }
    }

    @Override
    List<Vec> regularHighlight(Piece p) {
        //narrow it down to all the possible moves that aren't taken
        List<Vec> moves = highlightAllOptions(p,options);

        List<Vec> castling = castlingMoves(p);

        moves.addAll(castling);

        //remove if the king can not attack
        moves.removeIf(m -> !canAttack(p,m));

        //remove any castling moves that castle through a check
        moves.removeIf(v -> castlingThroughCheck(v,p,moves));

        return moves;
    }

    @Override
    public boolean canAttack(Piece p, int x, int y) {

        IterationObj obj;


        for(int[] opt : options){
            //go through each possible attacker and see if it can be attacked
            obj = IterationObj.create(x,y,x+opt[0],y+opt[1]);
            obj.iterateStartLoc();
            boolean straight = obj.isStraight();

            PieceBreak br = (xx,yy)->{
                if(pieces[xx][yy] != null){
                    return pieces[xx][yy] != p;
                }
                return false;
            };

            PieceReturn<Boolean> ret = (xx,yy)->{
                if(xx == -1)return false;
                Piece piece = pieces[xx][yy];

                if(piece.isWhite() == p.isWhite())return false;

                if(isMatchHighlighter(piece.highlighter(),straight)) return true;

                return (piece.highlighter() instanceof PawnHL && piece.highlighter().canAttack(piece,x,y));
            };

            if(obj.iterate(br,ret))return false;
        }


        //this solution is a little jenky, but it works, it just finds all the highlights as if the king was a knight
        //and sees if these highlights overlap with a knight of the opposite colour

        List<Vec> knightPlaces = KnightHL.getInstance().regularHighlight(new Knight(p.isWhite(),x,y));
        //if any of the possible places a knight could attack from contain a knight, the king cannot move there
        for(Vec kn : knightPlaces){
            Piece knight = pieces[kn.getX()][kn.getY()];
            if(knight != null &&
                knight.highlighter() instanceof KnightHL &&
                knight.isWhite() != p.isWhite())return false;
        }

        return true;
    }

    @Override
    public boolean isStraight() {
        return false;
    }

    @Override
    public boolean isDiagonal() {
        return false;
    }

    @Override
    public boolean isStoppable() {
        return false;
    }
}
