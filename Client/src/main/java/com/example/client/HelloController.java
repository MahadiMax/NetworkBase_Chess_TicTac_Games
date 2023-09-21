package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.*;

public class HelloController {




    @FXML
    private TextField  tx1;
    @FXML
    PasswordField tx2;
    @FXML
    ImageView image ;

    @FXML
    Button button1 , button2 ;
    BufferedReader reader;
    BufferedWriter writer ;

    @FXML
    Label label ;

    int  flag = 0 , count = 1 ;



    @FXML
   public void login() throws Exception  {

        try {
            String login_username = tx1.getText();
            FileWriter x = new FileWriter("src\\main\\java\\com\\example\\client\\Files\\login.txt");
            BufferedWriter y = new BufferedWriter(x);
            y.write(login_username );
            y.newLine();

            FileWriter x1 = new FileWriter("src\\main\\java\\com\\example\\client\\Files\\"+login_username+"login.txt");
            BufferedWriter x2 = new BufferedWriter(x1);
            x2.write(login_username);
            x2.newLine();

            x2.close();
            y.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        String username = tx1.getText();
        String password = tx2.getText() ;

        try {
            FileReader f = new FileReader("src\\main\\java\\com\\example\\client\\Files\\registration.txt");
            BufferedReader a = new BufferedReader(f);

            String line;

            while ((line = a.readLine()) != null) {
                String part[] = line.split("-");

                if (part[1].equals(username) && part[2].equals(password) ){
                       flag = 1 ;
                       break;
                }

            }

            if(flag == 0 )  label.setText("Username or Password wrong!");
            else {
                Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
                Stage window = (Stage)button1.getScene().getWindow();
                window.setScene(new Scene(root,800,600));
            }

            a.close();

        }

        catch (Exception x ){
            x.printStackTrace();
        }

    }

    @FXML
     void register() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Stage window = (Stage)button2.getScene().getWindow();
        window.setScene(new Scene(root,800,600));
    }

    public void backToHomepage(MouseEvent mouseEvent) {
        App.setRoot("dashboard");

    }
}

