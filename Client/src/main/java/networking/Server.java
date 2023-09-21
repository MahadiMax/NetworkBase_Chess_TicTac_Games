
package networking;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Thread{

    private static final int PORT = 6666;

    @Override
    public void run() {

        try(ServerSocket listener = new ServerSocket(PORT)) {

            System.out.println("Server is Running");

            //this has a problem, the last player will be kicked if a third person tries to connect
            while (true) {
                Player white = new Player(listener.accept(), true);
                Player black = new Player(listener.accept(), false);
                white.start();
                black.start();
                white.setOpponent(black);
                black.setOpponent(white);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }





}
