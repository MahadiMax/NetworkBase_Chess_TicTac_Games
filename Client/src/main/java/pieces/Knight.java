package pieces;


import highlighters.KnightHL;
import pieces.graphics.PieceNode;

public class Knight extends PieceBase {
    public Knight(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if(isWhite) {
            node = new PieceNode("Chess_nlt60.png");
        }else{
            node = new PieceNode("Chess_ndt60.png");
        }
        highlighter = KnightHL.getInstance();
    }

    @Override
    public char getChar() {
        return 'n';
    }
}
