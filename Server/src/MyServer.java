import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) {
        try {
            ServerSocket socket = new ServerSocket(7000);

            while (true){
                Socket sc = socket.accept();
                Client client = new Client(sc);
                Thread t = new Thread(client);
                t.start();
            }
        }
        catch (Exception a){
            a.printStackTrace();
        }
    }
}
