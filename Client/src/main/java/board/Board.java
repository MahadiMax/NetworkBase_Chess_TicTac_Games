package board;

import log.Move;
import log.MoveLog;
import pieces.Piece;
import utils.Vec;

public class Board {
    private static final Board ourInstance = new Board();

    public static Board getInstance() {
        return ourInstance;
    }

    private Vec b_king = new Vec(4,0);
    private Vec w_king = new Vec(4,7);

    private final Piece[][] pieces = new Piece[8][8];

    private Board(){
        BoardManager bm = new BoardManager();
        MoveLog.getInstance().addListener(l -> {
            while(l.next()) {
                if (l.wasAdded())
                    bm.updateBoard(pieces, l.getAddedSubList().get(0));
            }
        });
    }

    public void addToBoard(Piece piece, int x, int y){
        pieces[x][y] = piece;
    }

    public Piece[][] getPieces(){
        return pieces;
    }

    public Vec getB_king() {
        return b_king;
    }

    public Vec getW_king() {
        return w_king;
    }

    void updateKing(Move m){
        Vec king = m.getToVec();
        if(m.getPiece().isWhite())w_king = king;
        else b_king = king;
    }

}
