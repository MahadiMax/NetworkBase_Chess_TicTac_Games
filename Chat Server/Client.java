import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Client implements  Runnable {
    String name ;
    BufferedReader reader ;
    BufferedWriter writer ;
    final static ArrayList<Client> array = new ArrayList<>();

    Client(Socket sc ){

        try {
            OutputStreamWriter output = new OutputStreamWriter(sc.getOutputStream());
            writer = new BufferedWriter(output);

            InputStreamReader input = new InputStreamReader(sc.getInputStream());
            reader = new BufferedReader(input) ;

            name = reader.readLine();
            array.add(this);
            System.out.println("Client " + name + " connected.");
        }
        catch (Exception a ){
            a.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                String data = reader.readLine();


                synchronized (array){
                    for (Client a : array){
                        a.writer.write(data+"\n");
                        a.writer.flush();
                    }
                }
            }
            catch (SocketException x ){
                break ;
            }
            catch (Exception  a){
                a.printStackTrace();
            }
        }

    }
}
