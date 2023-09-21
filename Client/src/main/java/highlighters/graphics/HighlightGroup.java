package highlighters.graphics;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import utils.SizeUtil;

public class HighlightGroup extends Group {
    private static final HighlightGroup ourInstance = new HighlightGroup();

    public static HighlightGroup getInstance() {
        return ourInstance;
    }

    private HighlightGroup() {
        Rectangle sizer = new Rectangle();
        SizeUtil.getInstance().sizeSizingRect(sizer);
        getChildren().add(sizer);
        //setPickOnBounds(false);
    }

    static void clear(){
        ourInstance.getChildren().removeIf(o -> o instanceof Highlight);
    }
}
