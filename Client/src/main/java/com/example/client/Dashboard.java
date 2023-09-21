package com.example.client;

import javafx.scene.input.MouseEvent;

public class Dashboard {
    public void chessLogin(MouseEvent mouseEvent) {
        App.setRoot("hello-view");

    }

    public void tictactoeLogin(MouseEvent mouseEvent) {
        App.setRoot("LoginWindow");
    }
}
