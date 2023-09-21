package log;

import board.Board;
import pieces.Piece;
import utils.StringJoin;

public class CastleMove extends Move {


    private final Piece rook;
    private final int rFromX;
    private final int rFromY;
    private final int rToX;
    private final int rToY;

    private CastleMove(int fromX, int fromY, int toX, int toY, int rFromX, int rFromY, int rToX, int rToY) {
        super(fromX, fromY, toX, toY);
        this.rFromX = rFromX;
        this.rFromY = rFromY;
        this.rToX = rToX;
        this.rToY = rToY;
        rook = Board.getInstance().getPieces()[rFromX][rFromY];
    }

    public CastleMove(Piece piece, int toX, int toY, Piece rook, int rToX, int rToY) {
        super(piece, toX, toY);
        this.rook = rook;
        this.rToX = rToX;
        this.rToY = rToY;
        this.rFromX = rook.getX();
        this.rFromY = rook.getY();
    }

    @Override
    public char getChar() {
        return 'c';
    }

    @Override
    public String asPacket() {
        return "04," + super.asPacket() + ',' + StringJoin.joinWithCommas(rFromX,rFromY,rToX,rToY);
    }

    public static Move fromPacket(String[] parts){
        int[] terms = Move.intTerms(parts,8);
        return new CastleMove(terms[0],terms[1],terms[2],terms[3],terms[4],terms[5],terms[6],terms[7]);

    }

    public Piece getRook() {
        return rook;
    }

    public int getrFromX() {
        return rFromX;
    }

    public int getrFromY() {
        return rFromY;
    }

    public int getrToX() {
        return rToX;
    }

    public int getrToY() {
        return rToY;
    }
}
