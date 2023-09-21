package stackRoots;

import log.Move;
import log.MoveLog;
import log.Promotion;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import pieces.Piece;
import pieces.graphics.PieceNode;
import utils.SizeUtil;

public class PromoteRoot extends StackRoot{

    public PromoteRoot(Piece pond,int x, int y) {
        SizeUtil su = SizeUtil.getInstance();
        HBox hbox = new HBox();
        hbox.setSpacing(40);
        hbox.setAlignment(Pos.CENTER);
        setCenter(hbox);
        setStyle("-fx-background-color: #00EFFF99");
        String dis = pond.isWhite() ? "l" : "d";
        char[] pNames = {'n', 'q', 'b', 'r'};

        for(int i = 0; i < 4; i++){
            PieceNode p = new PieceNode("Chess_"+pNames[i]+dis+"t60.png");
            hbox.getChildren().add(p);
            su.sizePromoteOption(p);

            final int t = i;
            p.setOnMouseClicked(e->{
                Move move = new Promotion(pond,x,y,pNames[t]);
                MoveLog.getInstance().addMove(move,false);
                pop();
            });

            p.setOnMouseEntered(e->{

            });
            p.setOnMouseExited(e->{

            });
        }
    }
}
