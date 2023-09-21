package log;

import pieces.Piece;

public class EnPassant extends Move{
    private EnPassant(int fromX, int fromY, int toX, int toY) {
        super(fromX, fromY, toX, toY);
    }

    public EnPassant(Piece piece, int toX, int toY) {
        super(piece, toX, toY);
    }

    @Override
    public char getChar() {
        return 'e';
    }

    public static Move fromPacket(String[] parts){
        int[] terms = Move.intTerms(parts,4);
        return new EnPassant(terms[0],terms[1],terms[2],terms[3]);
    }

    @Override
    public String asPacket() {
        return "03,"+super.asPacket();
    }
}
