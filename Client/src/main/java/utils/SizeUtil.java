package utils;

import highlighters.graphics.Highlight;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pieces.graphics.PieceNode;

public class SizeUtil {
    private static SizeUtil ourInstance;

    private final NumberBinding size;
    private final NumberBinding minDim;
    //the board is flipped when so both players on their own screen see theirs at the bottom
    //but the indexes in the actual board are the same
    private final boolean flipped;

    private SizeUtil(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height, boolean isWhite) {
        //size of a single node

        minDim = Bindings.min(width,height);
        size = minDim.divide(8);

        flipped = !isWhite;
    }

    public void sizePromoteOption(PieceNode p){
        p.fitWidthProperty().bind(size.multiply(1.25));
        p.fitHeightProperty().bind(size.multiply(1.25));
    }

    //binds rectangle to grid x, y
    public void sizeRect(Rectangle node, int x, int y){
        node.widthProperty().bind(size);
        node.heightProperty().bind(size);
        node.xProperty().bind(size.multiply(x));
        node.yProperty().bind(size.multiply(y));
    }

    public void sizeHighlight(Highlight rect, int x, int y){
      sizeNode(rect.xProperty(),rect.yProperty(),
              rect.widthProperty(),rect.heightProperty(),
              new SimpleDoubleProperty(x),new SimpleDoubleProperty(y));
    }

    public void sizePieceNode(PieceNode node, DoubleProperty x, DoubleProperty y){
        sizeNode(node.xProperty(),node.yProperty(),
                node.fitWidthProperty(),node.fitHeightProperty(),
                x,y);
    }

    private void sizeNode(DoubleProperty xProperty, DoubleProperty yProperty,
            DoubleProperty widthProperty, DoubleProperty heightProperty,
                          DoubleProperty x, DoubleProperty y){
        widthProperty.bind(size);
        heightProperty.bind(size);
        if(flipped){
            xProperty.bind(new SimpleIntegerProperty(7).subtract(x).multiply(size));
            yProperty.bind(new SimpleIntegerProperty(7).subtract(y).multiply(size));
        }else{
            xProperty.bind(size.multiply(x));
            yProperty.bind(size.multiply(y));
        }
    }

    public void sizeSizingRect(Rectangle node){
        node.widthProperty().bind(minDim);
        node.heightProperty().bind(minDim);
        node.setX(0);
        node.setY(0);
        node.setFill(Color.rgb(0,0,0,0));
    }

    public boolean isFlipped(){
        return flipped;
    }

    /** INSTANCE MANAGEMENT **/
    public static void createInstance(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height, boolean isWhite){
        if(ourInstance == null) ourInstance = new SizeUtil(width, height,isWhite);
    }

    public static SizeUtil getInstance() {
        return ourInstance;
    }
}
