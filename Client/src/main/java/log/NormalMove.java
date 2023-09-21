package log;

import pieces.Piece;

public class NormalMove extends Move {
    private NormalMove(int fromX, int fromY, int toX, int toY) {
        super(fromX, fromY, toX, toY);
    }

    public NormalMove(Piece piece, int toX, int toY) {
        super(piece, toX, toY);
    }

    @Override
    public char getChar() {
        return 'n';
    }

    public static Move fromPacket(String[] parts){
        int[] terms = Move.intTerms(parts,4);
        return new NormalMove(terms[0],terms[1],terms[2],terms[3]);
    }

    @Override
    public String asPacket() {
        return "02,"+super.asPacket();
    }
}

