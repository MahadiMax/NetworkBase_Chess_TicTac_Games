package highlighters.graphics;

import pieces.Piece;
import stackRoots.PromoteRoot;

public class PromoteHighlight extends Highlight{

    public PromoteHighlight(Piece piece, int x, int y) {
        super(piece, x, y);
        setOnMouseClicked(e -> {
            HighlightGroup.clear();
            new PromoteRoot(piece,x,y);
        });
    }
}
