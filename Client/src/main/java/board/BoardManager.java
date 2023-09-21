package board;

import Animators.PieceAnimator;
import Animators.PromoteAnimator;
import Animators.TakeAnimator;
import log.*;
import highlighters.KingHL;
import javafx.application.Platform;
import pieces.Piece;
import pieces.PieceFactory;

class BoardManager {

    void updateBoard(Piece[][] board,Move m){
        if(m.getPiece().highlighter() instanceof KingHL){
            Board.getInstance().updateKing(m);
        }
        switch (m.getChar()){
            case 'n':
                normal(board,(NormalMove) m);
                break;
            case 'c':
                castle(board,(CastleMove) m);
                break;
            case 'e':
                enPassant(board, (EnPassant) m);
                break;
            case 'p':
                promotion(board, (Promotion) m);
                break;
        }
        Check.getInstance().checkCheck(m);
        Draw.checkForDraw(board);
    }

    private void normal(Piece[][] board,NormalMove move){

        //TODO: insert Gudrun woosh sound here

        PieceAnimator.startInNewThread(move.getPiece(),move.getToX(),move.getToY());

        move.getPiece().movePiece(move.getToX(),move.getToY());

        Piece take = board[move.getToX()][move.getToY()];
        if(take != null)
            TakeAnimator.startInNewThread(take);

        board[move.getFromX()][move.getFromY()] = null;

        board[move.getToX()][move.getToY()] = move.getPiece();


    }

    private void castle(Piece[][] board, CastleMove move){

        PieceAnimator.startInNewThread(move.getPiece(),move.getToX(),move.getToY());

        PieceAnimator.startInNewThread(move.getRook(),move.getrToX(),move.getrToY());

        move.getPiece().movePiece(move.getToX(),move.getToY());

        move.getRook().movePiece(move.getrToX(),move.getrToY());

        board[move.getFromX()][move.getFromY()] = null;

        board[move.getToX()][move.getToY()] = move.getPiece();

        board[move.getrFromX()][move.getrFromY()] = null;

        board[move.getrToX()][move.getrToY()] = move.getRook();

    }

    private void enPassant(Piece[][] board, EnPassant move){

        PieceAnimator.startInNewThread(move.getPiece(),move.getToX(),move.getToY());

        move.getPiece().movePiece(move.getToX(),move.getToY());

        //the x coordinate of the taken piece is always the same
        //the y coordinate is either 4 or 5 depending on which side is attacking
        int y = (move.getPiece().isWhite())? 3 : 4;
        Piece take = board[move.getToX()][y];

        TakeAnimator.startInNewThread(take);

        board[move.getFromX()][move.getFromY()] = null;

        board[move.getToX()][move.getToY()] = move.getPiece();

    }

    private synchronized void promotion(Piece[][] board, Promotion move){

        Piece newPiece = PieceFactory.create(move.getToX(),move.getToY(),move.getNewType(),move.getPiece().isWhite());

        if(newPiece == null)return;

        board[move.getFromX()][move.getFromY()] = null;


        PromoteAnimator.startInNewThread(move.getPiece(),newPiece,move.getToX(),move.getToY());

        Piece take = board[move.getToX()][move.getToY()];
        if(take != null) TakeAnimator.startInNewThread(take);

        board[move.getToX()][move.getToY()] = newPiece;

        Platform.runLater(newPiece::ini);

        //make sure after initialized it does not have a graphic node at first
        newPiece.getNode().setOpacity(0);

    }


}
