package board;

import log.Move;
import log.MoveLog;
import pieces.Piece;

import java.util.ArrayList;

public class Draw {


    private static MoveLog log = MoveLog.getInstance();

    {
        log.addListener(l -> {
            while(l.next()){
                if(l.wasRemoved())encapsule.clear();
            }
        });
    }

    private static ArrayList<Integer> encapsule = new ArrayList<>();

    private static ArrayList<Integer> twoFoldIndexes = new ArrayList<>();

    private static int index = 0;

    public static void checkForDraw(Piece[][] board){

        if(fiftyMoveDraw()){
            System.out.println("fifty move draw");
        } else if(threeFoldRep()){
            System.out.println("three fold repetition");
        } else if(stalemate(board)){
            System.out.println("stalemate");
        }

        System.out.println("encapsules: " + encapsule);

        System.out.println("fold Index's: " + twoFoldIndexes);

    }

    private static boolean fiftyMoveDraw(){
        return log.getNumMovesSinceIrr() >= 50;
    }


    private static boolean threeFoldRep(){
        //first find if there is a pair

        Move n = log.getLastMove();
        Move in = null;

        //find the inverse of a move if it exists
        for(int i = log.getMoves().size()-1; i >= 0; i--){
            Move m = log.getMoves().get(i);
            if(inverseMove(m,n)){
                in = m;
                break;
            }
        }

        if(in == null){
            encapsule.add(index);
            index++;
            return false;
        }
        int ind = log.getMoves().indexOf(in);

        encapsule.add(encapsule.get(ind));

        if(isSequence(ind)){
            twoFoldIndexes.add(encapsule.size());

            //this is a three fold repitition!!!!
            return twoFoldIndexes.contains(ind);

        }

        return false;
    }

    private static boolean isSequence(int index){

        //now check to see if this makes a full close

        //by default will be 0
        int[] check = new int[encapsule.size()];


        for(int i = index; i < encapsule.size(); i++){
            check[encapsule.get(i)]++;
        }
        for (int i1 : check) {
            if (i1 % 2 == 1) return false;
        }
        return true;
    }

    private static boolean inverseMove(Move m, Move n){
        if(m.getPiece() != n.getPiece())return false;
        if(m.getToX() != n.getFromX() &&
            m.getFromX() != n.getToX())return false;
        return m.getToY() == n.getFromY() ||
                m.getFromY() == n.getToY();
    }

    private static void insufficientMaterial(){

    }

    private static boolean stalemate(Piece[][] board){
        if(Check.getInstance().isCheck())return false;
        for(Piece[] row : board){
            for(Piece p : row){
                if(p == null)continue;
                if(p.isWhite() == log.getLastMove().getPiece().isWhite())continue;
                if(!p.highlighter().highlights(p).isEmpty())return false;
            }
        }
        return true;
    }
}
