package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


public class HomePageController{
    ArrayList<String> arrayList = new ArrayList<>() ;
    String friend , login_username;

    @FXML
    private  ListView<String> myListView ;
    @FXML
    Button  logout ,  button , join , host ;
    @FXML
    Pane pane;
    @FXML
    TextField tx ;

    @FXML
    TextArea area ;

    BufferedWriter writer ;
    BufferedReader reader ;


    Stage stage ;
    Stage stage1 ;
    String data ;
    boolean isConnected = false ;

    @FXML
    void joinB()throws  Exception {

        Parent root = FXMLLoader.load(getClass().getResource("Join.fxml"));
        Stage window = (Stage)join.getScene().getWindow();
        window.setScene(new Scene(root,800,600));

    }

    @FXML
     void hostB()throws  Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Host.fxml"));
        Stage window = (Stage)host.getScene().getWindow();
        window.setScene(new Scene(root,800,600));

    }





    @FXML
    void  buttonAction(){

        if (!isConnected ){
            String data = tx.getText();
            tx.clear();
            if( data == null || data.length() == 0 ){
                area.appendText("Enter a valid name\n");
                return;
            }
            try {
                Socket sc = new Socket("localhost" , 7000);


                OutputStreamWriter output = new OutputStreamWriter(sc.getOutputStream());
                writer = new BufferedWriter(output) ;

                writer.write(data+ "\n");
                writer.flush();

                InputStreamReader input = new InputStreamReader(sc.getInputStream());
                reader = new BufferedReader(input);

                Thread t = new Thread(){
                    @Override
                    public void run() {
                        while (true){
                            try {
                                String input = reader.readLine()+ "\n";
                                area.appendText(input);
                            }
                            catch(SocketException z){
                                area.appendText("Connection Lost\n");
                                break;
                            }
                            catch (Exception x){
                                x.printStackTrace();
                            }
                        }
                    }
                };
                t.start();

                area.appendText("Connection Established.\n");
                button.setText("Send");
                tx.setPromptText("Enter a message.");
                isConnected = true ;
            }
            catch (Exception a){
                a.printStackTrace();
            }




        }

        else {

            try {
                String msg = tx.getText();
                tx.clear();

                if (msg == null || msg.length() == 0) {
                    return;
                }
                writer.write(msg + "\n");
                writer.flush();
            } catch (Exception a) {
                a.printStackTrace();
            }
        }

    }


    @FXML
    private void logoutButton() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
        Stage window = (Stage)logout.getScene().getWindow();
        window.setScene(new Scene(root,800,600));
    }




}
