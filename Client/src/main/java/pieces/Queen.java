package pieces;

import highlighters.QueenHL;
import pieces.graphics.PieceNode;

class Queen extends PieceBase {
    Queen(boolean isWhite, int x, int y) {
        super(isWhite, x, y);

        if(isWhite) {
            node = new PieceNode("Chess_qlt60.png");
        }else{
            node = new PieceNode("Chess_qdt60.png");
        }
        highlighter = QueenHL.getInstance();
    }

    @Override
    public char getChar() {
        return 'q';
    }
}
