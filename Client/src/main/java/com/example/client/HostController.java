package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class HostController {
    @FXML
    TextField tx ;

    @FXML
    Button host , back ;

    @FXML
    void hostMatch() throws  Exception{
         try {
             FileWriter f = new FileWriter("src\\main\\java\\com\\example\\client\\Files\\MatchCode");
             BufferedWriter w = new BufferedWriter(f);
             w.write(tx.getText());
             w.newLine();

             try {
                 Main mainApp = new Main();
                 Stage chessStage = new Stage();
                 mainApp.start(chessStage);
             } catch (Exception e) {
                 e.printStackTrace();
             }

             w.close();
         }
         catch (Exception a){
             a.printStackTrace();
         }
    }

    @FXML
    void  backButton() throws  Exception{
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Stage window = (Stage)back.getScene().getWindow();
        window.setScene(new Scene(root,800,600));
    }
}
