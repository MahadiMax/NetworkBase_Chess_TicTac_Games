package highlighters.graphics;

import log.EnPassant;
import log.Move;
import log.MoveLog;
import pieces.Piece;

public class EnPassantHighlight extends Highlight {

    public EnPassantHighlight(Piece piece, int x, int y) {
        super(piece, x, y);
        setOnMouseClicked(e -> {
            HighlightGroup.clear();
            Move move = new EnPassant(piece,x,y);
            MoveLog.getInstance().addMove(move,false);
        });


    }
}
