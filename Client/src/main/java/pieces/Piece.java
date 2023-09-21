package pieces;

import highlighters.Highlighter;
import pieces.graphics.PieceNode;

public interface Piece {

    boolean isWhite();

    //returns whether the piece has ever moved
    boolean hasMoved();

    Highlighter highlighter();

    PieceNode getNode();

    //for calculations, such as checking for checks or stalemates or where to move
    void movePiece(int x, int y);

    //more incremental steps for a smooth animation
    void moveGraphicNode(double x, double y);

    int getX();

    int getY();

    void ini();

    //get char associtated with the piece type
    //useful for packets, and for picking image
    char getChar();
}
