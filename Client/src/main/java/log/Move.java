package log;

import board.Board;
import pieces.Piece;
import utils.StringJoin;
import utils.Vec;

public abstract class Move {
    private final Piece piece;
    private final int fromX;
    private final int fromY;
    private final int toX;
    private final int toY;

    Move(int fromX, int fromY, int toX, int toY) {
        Board board = Board.getInstance();
        this.piece = board.getPieces()[fromX][fromY];
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    Move(Piece piece, int toX, int toY) {
        this.piece = piece;
        this.fromX = piece.getX();
        this.fromY = piece.getY();
        this.toX = toX;
        this.toY = toY;
    }

    public Piece getPiece() {
        return piece;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public Vec getFromVec(){
        return new Vec(fromX,fromY);
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public Vec getToVec(){
        return new Vec(toX,toY);
    }

    public abstract char getChar();

    public String asPacket(){
        return StringJoin.joinWithCommas(fromX,fromY,toX,toY);
    }

    //a string array of asPacket, as terms without commas
    static int[] intTerms(String[] parts, int length){
        int[] terms = new int[length];
        for (int i = 0; i < length; i++) {
            terms[i] = Integer.parseInt(parts[i + 1]);
        }
        return terms;
    }
}
