package pieces;

import highlighters.KingHL;
import pieces.graphics.PieceNode;

public class King extends PieceBase {

    King(boolean isWhite, int x, int y) {
        super(isWhite, x, y);

        if(isWhite) {
            node = new PieceNode("Chess_klt60.png");
        }else{
            node = new PieceNode("Chess_kdt60.png");
        }
        highlighter = KingHL.getInstance();

    }

    @Override
    public char getChar() {
        return 'k';
    }
}
