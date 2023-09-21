package log;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

public class Turn {
    private static final Turn ourInstance = new Turn();

    public static Turn getInstance() {
        return ourInstance;
    }

    private final BooleanProperty turn = new SimpleBooleanProperty(false);

    private Turn() {
        MoveLog.getInstance().addListener(l -> {
            while(l.next()) {
                if (l.getAddedSize() == 1)
                    toggleTurn();
            }

        });
    }

    public void toggleTurn(){
        turn.setValue(!turn.get());
    }

    public void addListener(ChangeListener<? super Boolean> changeListener){
        turn.addListener(changeListener);
    }

    public boolean getTurn(){
        return turn.get();
    }

}
