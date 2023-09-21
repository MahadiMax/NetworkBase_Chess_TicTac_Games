package com.example.client;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerHomeController  implements Initializable {
    @FXML
    private Label usernamefield;
    @FXML
    private Label scorefield;
    @FXML
    private Label reciption;
    @FXML
    Button chess;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String typedItems = "Welcome !, Have a nice Day!";
        TypingAnimation typingAnimation = new TypingAnimation(reciption, typedItems);
        typingAnimation.play();
        usernamefield.setText(Player.player.username);
    }
    @FXML
    private void mouseEntered(MouseEvent ae){
      //  new SoundPlayer(SoundPlayer.SOUND.TICK).play();
    }

    @FXML
    void chessButton()throws  Exception{
        Player.getOnlineList();
        App.setRoot("hello-view");
    }

    // typing animation
    public class TypingAnimation {
        private final Label label;
        private final String[] items;
        private int currentItemIndex;
        private int currentCharIndex;

        public TypingAnimation(Label label, String text) {
            this.label = label;
            this.items = text.split(",");
            this.currentItemIndex = 0;
            this.currentCharIndex = 0;
        }

        public void play() {
            Timeline timeline = new Timeline();
            KeyFrame keyFrame = new KeyFrame(Duration.millis(200), event -> {
                if (currentItemIndex < items.length) {
                    String currentItem = items[currentItemIndex];
                    if (currentCharIndex <= currentItem.length()) {
                        label.setText(currentItem.substring(0, currentCharIndex));
                        currentCharIndex++;
                    } else {
                        currentItemIndex++;
                        currentCharIndex = 0;
                    }
                } else {
                    currentItemIndex = 0;
                    currentCharIndex = 0;
                }
            });
            timeline.getKeyFrames().add(keyFrame);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }
    @FXML
    private void singlePlay(ActionEvent ae){
        Server.sendRequest(JSONRequests.playSoloGame("no").toString());


    }
    @FXML
    private void multiPlay(ActionEvent ae) throws IOException {
        Player.getOnlineList();
        App.setRoot("PlayersList");
    }
    @FXML
    private void gameHistory(ActionEvent ae) throws IOException {
        Server.sendRequest(JSONRequests.gameHistory().toString());
    }
    @FXML
    private void exit(ActionEvent ae){
        Player.logout();
        App.setRoot("dashboard");
    }
}
