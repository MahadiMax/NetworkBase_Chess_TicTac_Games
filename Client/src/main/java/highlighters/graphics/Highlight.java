package highlighters.graphics;

import board.Board;
import log.Move;
import log.MoveLog;
import log.NormalMove;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pieces.Piece;
import utils.SizeUtil;

public class Highlight extends Rectangle {

    private static SizeUtil su;

    private static Piece[][] pieces;

    private static final Color color = Color.rgb(24,200,200,0.6);

    private static final Color select = Color.rgb(0,0,140,0.6);

    public Highlight(Piece piece,int x, int y){
        if(pieces == null)pieces = Board.getInstance().getPieces();
        if(su == null)su = SizeUtil.getInstance();
        su.sizeHighlight(this,x,y);
        this.setFill(color);
        setOnMouseEntered(e -> setFill(select));
        setOnMouseExited(e -> setFill(color));
        setOnMouseClicked(e -> {
            Move move = new NormalMove(piece,x,y);
            MoveLog.getInstance().addMove(move,false);
            HighlightGroup.clear();
        });
    }
}
