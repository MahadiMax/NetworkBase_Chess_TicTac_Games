package log;

import board.Board;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class MoveLog {


    private final ObservableList<Move> moves = FXCollections.observableArrayList(new ArrayList<>());

    private static final MoveLog ourInstance = new MoveLog();

    private Turn turn;

    private Board board;

    private boolean lastMoveIrr = false;

    private int numMovesBeforeACastle = 0;

    public static MoveLog getInstance(){
        return ourInstance;
    }

    private MoveLog(){
    }

    public void addMove(Move move, boolean fromServer){
        if(turn == null){
            turn = Turn.getInstance();
            board = Board.getInstance();
        }

            //from server
        if(fromServer){
            if(turn.getTurn()){
                //would be a good idea to send back an error packet later on
                return;
            }
        }else{  //from client
            if(!turn.getTurn()){
                //would be a good idea to display an error message at some point
                return;
            }
        }


        if(lastMoveIrr) moves.clear();

        if(isIrreversible(move)){
            lastMoveIrr = true;
            numMovesBeforeACastle = 0;
            moves.clear();
        }

        //after checking if it is the correct turn add move to moves
        moves.add(move);
    }

                //returns the number of moves since an irreversible move has occured (take, pond move)
    public int getNumMovesSinceIrr(){
        return moves.size() + numMovesBeforeACastle;
    }

    //castle will not be considered irreversible, but will have special logic on its own
    //irreversible moves include: pond moves, takes, promotion
    private boolean isIrreversible(Move move){
        if(move instanceof NormalMove){
            //return true if a pond moves
            if(move.getPiece().getChar() == 'p')return true;

            //return true if a piece is being taken, false if not
            return board.getPieces()[move.getToX()][move.getToY()] != null;

                    //return true if it is enpassant or promotion, not castle
        }else return !(move instanceof CastleMove);
    }

    public List<Move> getMoves(){
        return moves;
    }

    public void addListener(ListChangeListener<? super Move> listChangeListener){
        moves.addListener(listChangeListener);
    }

    public Move getLastMove(){
        if(moves.size() == 0)return null;
        return moves.get(moves.size()-1);
    }
}
