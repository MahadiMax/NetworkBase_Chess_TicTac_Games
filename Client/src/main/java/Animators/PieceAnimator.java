package Animators;

import javafx.application.Platform;
import pieces.Piece;

public class PieceAnimator extends Animator {


    private final double xDif;
    private final double yDif;
    private final double startX;
    private final double startY;
    final double endX;
    final double endY;

    final Piece piece;

    PieceAnimator(Piece piece, int endX, int endY){
        this.piece = piece;
        this.startX = piece.getX();
        this.startY = piece.getY();
        this.endX = endX;
        this.endY = endY;
        this.xDif = endX - startX;
        this.yDif = endY - startY;
        Platform.runLater(()->piece.getNode().toFront());
    }


    @Override
    void tick(double percent){
        double x = startX + (xDif * percent);
        double y = startY + (yDif * percent);
        Platform.runLater(()->piece.moveGraphicNode(x,y));
    }

    void onEnd(){
        Platform.runLater(()->piece.moveGraphicNode(endX,endY));
        stop();
    }

    public static void startInNewThread(Piece p, int endX, int endY){
        PieceAnimator ani = new PieceAnimator(p,endX,endY);
        new Thread(ani::start).start();
    }
}
