package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    private Network network;

    @FXML
    TextField msgField;
    @FXML
    TextArea mainArea;

    @FXML
    public void initialize() {
        network = new Network();
    }

    public void sendMsgAction() {
        //отправка сообщений TextField msgField;
        network.sendMessage(msgField.getText());
        // очистка поля
        msgField.clear();
        // фокус на поле
        msgField.requestFocus();
    }
}