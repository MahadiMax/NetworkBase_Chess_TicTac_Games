package com.example.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.Main;

import java.io.File;
import java.util.Scanner;

public class JoinController {
    @FXML
    TextField tx ;

    @FXML
    Button join , back ;

    @FXML
    void joinMatch() throws  Exception{
        File f = new File("src\\main\\java\\com\\example\\client\\Files\\MatchCode");
        Scanner sc = new Scanner(f);
        String s = sc.next() ;
        System.out.println(s);
        System.out.println(tx.getText());
        if (s.equals(tx.getText())){
            try {
                Main mainApp = new Main();
                Stage chessStage = new Stage();
                mainApp.start(chessStage);
                for (int i = 0 ; i < 2 ; i++ ){
                    if (i == 0){
                        Run a = new Run();
                        Stage b = new Stage();
                        double screenX = Screen.getPrimary().getVisualBounds().getMinX();
                        double screenY = Screen.getPrimary().getVisualBounds().getMinY();
                        b.setX(screenX);
                        b.setY(screenY);
                        a.start(b);
                    }

                    else {
                        Run a = new Run();
                        Stage b = new Stage();

//                        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//                        double screenWidth = primaryScreenBounds.getWidth();
//                        double screenHeight = primaryScreenBounds.getHeight();
//
//                        b.setX(screenWidth - b.getWidth());
//                        b.setY(0);
                        b.setX(Screen.getPrimary().getVisualBounds().getMaxX() - 270);
                        b.setY(Screen.getPrimary().getVisualBounds().getMinY());
                        a.start(b);
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        sc.close();
    }

    @FXML
    void  backButton() throws  Exception{
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        Stage window = (Stage)back.getScene().getWindow();
        window.setScene(new Scene(root,800,600));
    }
}
