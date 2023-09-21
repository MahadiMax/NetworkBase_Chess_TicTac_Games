package log;

import pieces.Piece;

public class Promotion extends Move {

    private final char newType;

    private Promotion(int fromX, int fromY, int toX, int toY, char newType) {
        super(fromX, fromY, toX, toY);
        this.newType = newType;
    }

    public Promotion(Piece piece, int toX, int toY, char newType) {
        super(piece, toX, toY);
        this.newType = newType;
    }

    @Override
    public String asPacket() {
        return "05," + super.asPacket() + ',' + newType;
    }

    @Override
    public char getChar() {
        return 'p';
    }

    public char getNewType(){
        return newType;
    }

    public static Move fromPacket(String[] parts){
        int[] terms = Move.intTerms(parts,4);
        return new Promotion(terms[0],terms[1],terms[2],terms[3],parts[5].charAt(0));
    }
}
