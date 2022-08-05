package com.example;

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
        network = new Network((args) -> {
            // передача сообщения в поле графического интерфейса "сообщения"
            mainArea.appendText((String)args[0]);
        });
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