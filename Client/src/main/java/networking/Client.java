package networking;

import log.*;
import javafx.application.Platform;
import main.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {

    private static final Client ourInstance = new Client();

    private boolean isWhite;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private static final int PORT = 6666;
    private static MoveLog moveLog;

    public static Client getInstance(){
        return ourInstance;
    }

    private Client(){
        moveLog = MoveLog.getInstance();
    }

    private void startConnection(String ip) throws IOException {
        this.clientSocket = new Socket(ip, PORT);
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
    }


    private void addMoveListenerToSend(){
        moveLog.addListener(l -> {
            while(l.next()) {
                if(l.getAddedSize() == 1){
                    Move m = l.getAddedSubList().get(0);
                    if (m.getPiece().isWhite() == this.isWhite) {
                        sendMove(m);
                    }
                }
            }
        });
    }

    //sends a packet to the server that the client would like to quit
    public synchronized void sendQuit(){
       String send = "01";
       out.println(send);
    }

    //sends a move packet to the server
    private synchronized void sendMove(Move m){
        String send = m.asPacket();
        out.println(send);
    }

    /***PROTOCOL FOR PACKETS SENT TO CLIENT***/
    //all packet handling from the server happens here
    @Override
    public void run() {
        try {
            startConnection("localhost");
            System.out.println("waiting for opponent...");
            addMoveListenerToSend();

            while(!isInterrupted()){
                String message = in.readLine();
                String[] parts = message.split(",");

                /*
                *   All packets are of the form
                *   packet,param1,param2,...
                *   packet is the code that it should be treated as
                *   all of the parameters needed are split by commas
                 */


                /*
                    param 1: isWhite (boolean), whether the player is white or black
                 */
                switch (parts[0]) {
                /*
                 *  reset packet - receives info the opponent wants to restart or game is ready to start
                 *  param 1: isWhite (boolean), whether the player is white or black
                 */
                    case "00":
                        isWhite = Boolean.parseBoolean(parts[1]);
                        Platform.runLater(() -> Main.ini(isWhite));
                        break;


                    /*
                     * disconnect packet - receives info that one of the players has disconnected
                     * param 1: thisPlayer (boolean) whether or not it's this player disconnecting
                     * or the opponent
                     * action:
                     * either close the program because this player wants to quit or,
                     * inform the client that the other player has left the game/let them win
                     */
                    case "01":
                        if (Boolean.parseBoolean(parts[1])) {
                            //say you win the game cuz the other player disconnected
                            System.out.println("other player is disconnecting. You won the Match.");

                            interrupt();
                        } else {
                            System.out.println("this player is disconnecting");
                            interrupt();
                        }
                        break;

                    /*
                     * Move packet - takes info from the server on where to move a piece (opponents piece)
                     * param 1: x1 (int) x pos of piece to be moved
                     * param 2: y1 (int) y pos of piece to be moved
                     * param 3: x2 (int) x pos of where piece will move to
                     * param 4: y2 (int) y pos of where piece will move to
                     * action:
                     * apply move in clients game
                     */
                    case "02":
                        //System.out.println("here");
                        Move move = NormalMove.fromPacket(parts);
                        moveLog.addMove(move,true);
                        break;

                   /*
                    * En Passant packet
                    */
                    case "03":
                        move = EnPassant.fromPacket(parts);
                        moveLog.addMove(move,true);
                        break;

                        /*
                         * Castle Packet
                         */

                    case "04":
                        move = CastleMove.fromPacket(parts);
                        moveLog.addMove(move,true);
                        break;

                    /*
                     * promote packet - receives info that an opponents piece should be promoted
                     */
                    case "05":
                        //parse values of what piece is moving where
                        move = Promotion.fromPacket(parts);
                        moveLog.addMove(move,true);
                        break;
                }

            }

            //will have a stop condition eventually
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}