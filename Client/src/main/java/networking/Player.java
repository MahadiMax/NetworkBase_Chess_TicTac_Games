
package networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static utils.StringJoin.joinWithCommas;

class Player extends Thread{

    private Player opponent;
    private final Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private final boolean isWhite;

    Player(Socket socket, boolean isWhite) {

        this.isWhite = isWhite;
        this.socket = socket;
        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            //print here what type of packet it is
            //send reset/set packet, to reset the board as white
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    //return's whether the move requested by the player is valid
    private boolean validMove(int x, int y, int newX, int newY){

        //for now no validation
        return true;
    }

    private synchronized void sendPacket(String packet){
        output.println(packet);
    }

    //sends disconnect packet to client by the protocol in client
    private synchronized void sendDis(boolean otherPlayersClient){
        String send = joinWithCommas("01",otherPlayersClient);
        output.println(send);
    }

    //sends valid ini packet to client by the protocol in client
    private synchronized void sendIniPacket(){
        String send = joinWithCommas("00",isWhite);
        output.println(send);
    }

    /***Protocol for how server handles packets from client***/
    public void run() {
        try {

            //if the player has been started, then both players are ready to play,
            //and ini packet can be sent
            sendIniPacket();

            // Repeatedly get commands from the client and process them.
            while (!Thread.interrupted()) {
                String message = input.readLine();
                String[] parts = message.split(",");

                switch (parts[0]) {

                    /*
                     * reset packet - not implemented yet
                     */

                    case "00":
                        break;

                    /*
                     * quit packet - the client would like to quit the game
                     * action:
                     * send disconnect packet to both clients
                     */
                    case "01":
                        opponent.sendDis(true);
                        sendDis(false);
                        interrupt();
                        break;

                    /*
                     * move packet - the client would like to move their player
                     * param 1: x1 (int) x pos of piece to be moved
                     * param 2: y1 (int) y pos of piece to be moved
                     * param 3: x2 (int) x pos of where piece will move to
                     * param 4: y2 (int) y pos of where piece will move to
                     * action:
                     * send a move packet to the opponent so the opponent will know our client has moved
                     */
                    case "02":

                        /*
                         * En Passant packet - the client would like to move their player
                         * param 1: x1 (int) x pos of piece to be moved
                         * param 2: y1 (int) y pos of piece to be moved
                         * param 3: x2 (int) x pos of where piece will move to
                         * param 4: y2 (int) y pos of where piece will move to
                         * action:
                         * send a move packet to the opponent so the opponent will know our client has moved
                         */


                    case "03":

                        /*
                         * Castle packet - the client would like to move their player
                         * param 1: x1 (int) x pos of piece to be moved
                         * param 2: y1 (int) y pos of piece to be moved
                         * param 3: x2 (int) x pos of where piece will move to
                         * param 4: y2 (int) y pos of where piece will move to
                         * action:
                         * send a move packet to the opponent so the opponent will know our client has moved
                         */

                    case "04":
                        /*
                         * Promotion packet - the client would like to move their player
                         * param 1: x1 (int) x pos of piece to be moved
                         * param 2: y1 (int) y pos of piece to be moved
                         * param 3: x2 (int) x pos of where piece will move to
                         * param 4: y2 (int) y pos of where piece will move to
                         * param 5: newType (char) new type of piece
                         * action:
                         * send a move packet to the opponent so the opponent will know our client has moved
                         */


                    case "05":

                        //send the packet sent back to the opponent
                        opponent.sendPacket(message);
                }
            }
        } catch (IOException e) {
            System.out.println("Player died: " + e);
        } finally {
            try {socket.close();} catch (IOException e) {}
        }
    }
}
