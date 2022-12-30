package com.example.demo.pages.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController {

    @FXML
    private ListView<String> messages;

    @FXML
    private TextArea messageArea;

    @FXML
    public void send(ActionEvent e){
        addMessage("You", messageArea.getText());
        messageArea.setText("");
    }

    public void addMessage(String username, String message){
        messages.getItems().add(username +": "+message);
        messages.scrollTo(messages.getItems().size());
    }
}
