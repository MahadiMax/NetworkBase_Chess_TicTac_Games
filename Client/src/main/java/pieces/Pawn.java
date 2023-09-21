package pieces;

import highlighters.PawnHL;
import pieces.graphics.PieceNode;

class Pawn extends PieceBase {
    Pawn(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        if(isWhite) {
            node = new PieceNode("Chess_plt60.png");
        }else{
            node = new PieceNode("Chess_pdt60.png");
        }
        highlighter = PawnHL.getInstance();
    }

    @Override
    public char getChar() {
        return 'p';
    }
}
