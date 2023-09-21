package highlighters;

import pieces.Piece;
import utils.Vec;

import java.util.ArrayList;
import java.util.List;

public class DiagonalHL extends HighlighterBase {
    private static final DiagonalHL ourInstance = new DiagonalHL();

    public static DiagonalHL getInstance() {
        return ourInstance;
    }

    private DiagonalHL() {
    }

    @Override
    List<Vec> regularHighlight(Piece p) {
        ArrayList<Vec> points = new ArrayList<>();

        for(int i = p.getX()+1, j = p.getY()+1; i < 8 && j < 8; i++,j++){
            if(addAndBreakIfEnd(points,p,i,j))break;
        }
        for(int i = p.getX()-1, j = p.getY()-1; i >= 0 && j >= 0; i--,j--){
            if(addAndBreakIfEnd(points,p,i,j))break;
        }
        for(int i = p.getX()+1, j = p.getY()-1; i < 8 && j >= 0; i++,j--){
            if(addAndBreakIfEnd(points,p,i,j))break;
        }
        for(int i = p.getX()-1, j = p.getY()+1; i >= 0 && j < 8; i--,j++){
            if(addAndBreakIfEnd(points,p,i,j))break;
        }

        return points;
    }

    @Override
    public boolean isDiagonal() {
        return true;
    }

    @Override
    public boolean isStoppable() {
        return true;
    }

    @Override
    public boolean isStraight() {
        return false;
    }
}
