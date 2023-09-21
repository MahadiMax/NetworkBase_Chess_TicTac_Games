package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class RegisterController {
    @FXML
    TextField tx1 , tx2  ;
    @FXML
    PasswordField  tx3 , tx4;

    @FXML
    Button back ,button2 ;
    @FXML
    Label label ;

    @FXML
    void backButton()throws  Exception{
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage window = (Stage)back.getScene().getWindow();
        window.setScene(new Scene(root,800,600));
    }



    @FXML
    void register() throws  Exception{
        String name = tx1.getText();
        String username = tx2.getText();
        String password = tx3.getText();
        String Cpassword = tx4.getText();
        int count = 0  ;

        if ( name.length() == 0 || username.length() == 0 || password.length() == 0 || Cpassword.length() == 0 ){
            label.setText("Not all the information given!");
        }
        else {

            try {
                FileReader f = new FileReader("src\\main\\java\\com\\example\\client\\Files\\registration.txt");
                BufferedReader b = new BufferedReader(f);
                String line ;

                while ( (line = b.readLine() ) != null ){
                    String[] part = line.split("-") ;
                    if (username.equals(part[1])){
                        count++ ;

                        break;
                    }

                }

                b.close();
            }
            catch (Exception z){
                z.printStackTrace();
            }

            if ( count != 0 ){
                label.setText("Username already taken.");
            }


            else   if (password.equals(Cpassword) ){
                if (password.length() < 4 ){
                    label.setText("Password is too weak.");
                }
                else {
                    label.setText("User Registered Successfully.");

                    try {
                        Thread.sleep(10);
                    }
                    catch (Exception s){
                        s.printStackTrace();
                    }

                    try{
                        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                        Stage window = (Stage)button2.getScene().getWindow();
                        window.setScene(new Scene(root,800,600));
                    }
                    catch (Exception a){
                        a.printStackTrace();
                    }

                    try {
                        FileWriter f = new FileWriter("src\\main\\java\\com\\example\\client\\Files\\registration.txt", true);
                        BufferedWriter a = new BufferedWriter(f);
                        a.write(name+"-");
                        a.write(username+"-");
                        a.write(password + "-");
                        a.write(Cpassword+ "");
                        a.newLine();
                        a.close();
                    }
                    catch (Exception a){
                        a.printStackTrace();
                    }
                }

            }

            else { label.setText("Please Confirm password Correctly");}
        }
    }
}
