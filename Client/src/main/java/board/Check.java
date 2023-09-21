package board;

import log.CastleMove;
import log.Move;
import log.NormalMove;
import log.Promotion;
import highlighters.HighlighterBase;
import pieces.Piece;
import utils.IterationObj;
import utils.IterationObj.PieceBreak;
import utils.IterationObj.PieceReturn;
import utils.Vec;
import java.util.ArrayList;
import java.util.List;

public class Check {
    private static final Check ourInstance = new Check();

    public static Check getInstance() {
        return ourInstance;
    }

    private boolean check;
    private boolean isWhiteCheck;

    private static Board board;

    //not always the last moved piece
    private final List<Piece> checkers = new ArrayList<>();

    private Check() {
        check = false;
        board = Board.getInstance();
    }

    void checkCheck(Move m){

        //if it's a castle move the only thing that matters is the rook, the king will never cause a check
        if(m instanceof CastleMove)
            m = new NormalMove(((CastleMove) m).getRook(),((CastleMove) m).getrToX(),((CastleMove) m).getrToY());


        isWhiteCheck = m.getPiece().isWhite();

        //clear checkers from last check
        checkers.clear();

        final Piece p;

        //if it's a promotion we want the new piece, not the old one
        if(m instanceof Promotion) {
            p = board.getPieces()[m.getToX()][m.getToY()];
            System.out.println(p.highlighter());
        }
        else p = m.getPiece();

        //get opposite king of just moved
        Vec king = (p.isWhite()) ? Board.getInstance().getB_king() : Board.getInstance().getW_king();
                                                //make an object that will iterate from the king to the piece

        check = p.highlighter().canAttack(p,king);

        //add piece to the checkers
        if(check)checkers.add(p);

        Vec place = m.getFromVec();
        if(place == null)return;


        //for iterating from the king to the previous location that the piece moved held
        //to see if it was blocking an attacking piece from hitting the king
        IterationObj obj = IterationObj.create(king.getX(),king.getY(),place.getX(),place.getY());
        obj.iterateStartLoc();
        if(obj.isNormalSlope())return;

        PieceBreak br = (x,y)->board.getPieces()[x][y] != null;

        PieceReturn<Piece> ret = (x,y)->{
            //if there is nothing along the line it is not a check
            if(x == -1)return null;

            Piece piece = board.getPieces()[x][y];

                //if the piece attacking the king is the same colour as the piece found, it is not a check
            if(piece.isWhite() != p.isWhite())return null;

                    //if the piece goes in the same direction as the path to the king, it is check
            return HighlighterBase.isMatchHighlighter(piece.highlighter(),obj.isStraight()) ? piece : null;

        };

        Piece checker = obj.iterate(br,ret);

        if(checker != null && !checkers.contains(checker)){
            checkers.add(checker);
            check = true;
        }

        if(check){

            System.out.println("it is a check rn");
            boolean checkMate = isCheckMate(p);

        }

    }

    private boolean isCheckMate(Piece lastMoved){
        for(Piece[] row : board.getPieces()){
            for(Piece p : row){
                if(p == null)continue;
                if(p.isWhite() == lastMoved.isWhite())continue;
                if(!p.highlighter().highlights(p).isEmpty())return false;
            }
        }
        return true;
    }

    public List<Piece> getCheckers(){
        return checkers;
    }

    public boolean isCheck(){
        return check;
    }

    public boolean isWhiteCheck(){
        return isWhiteCheck;
    }
}
