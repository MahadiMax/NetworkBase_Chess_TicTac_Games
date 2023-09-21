package highlighters;

import pieces.Piece;
import utils.Vec;

import java.util.List;

public class KnightHL extends HighlighterBase {
    private static final KnightHL ourInstance = new KnightHL();

    public static KnightHL getInstance() {
        return ourInstance;
    }

    private static final int[][] options = {
            {1,2},{-1,-2},{-1,2},{1,-2},
            {2,1},{-2,-1},{2,-1},{-2,1}
    };

    private KnightHL() {
    }


    @Override
    public boolean canAttack(Piece p, int x, int y){
        int xDif = Math.abs(p.getX() - x);
        int yDif = Math.abs(p.getY() - y);
        return ((xDif == 1 || xDif == 2) &&
                ((yDif == 1) || yDif == 2) &&
                xDif != yDif);
    }

    @Override
    List<Vec> regularHighlight(Piece p) {
        return highlightAllOptions(p,options);
    }
    @Override
    public boolean isStraight() {
        return false;
    }
    @Override
    public boolean isDiagonal() {
        return false;
    }
    @Override
    public boolean isStoppable() {
        return false;
    }
}
