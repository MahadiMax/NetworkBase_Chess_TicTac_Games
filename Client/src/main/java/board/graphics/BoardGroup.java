package board.graphics;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import utils.SizeUtil;

public class BoardGroup extends Group {
    private static final BoardGroup ourInstance = new BoardGroup();

    public static BoardGroup getInstance() {
        return ourInstance;
    }

    private BoardGroup() {
        initializeBoard();
    }

    private void initializeBoard(){
        //this needs to change with which player it is but for now constant

        SizeUtil su = SizeUtil.getInstance();

        //changes just switch i values for the reverse board


        for(int i = 1; i < 8; i+=2){
            for(int j = 0; j < 8; j+=2){
                initializeBlock(su,i,j);
            }
        }

        for(int i = 0; i < 8; i+=2){
            for(int j = 1; j < 8; j+=2){
                initializeBlock(su,i,j);
            }
        }
    }

    private void initializeBlock(SizeUtil su,int x, int y){
        Rectangle rect = new Rectangle();
        rect.setFill(Color.rgb(150,0,24));
        su.sizeRect(rect,x,y);
        getChildren().add(rect);
    }
}

