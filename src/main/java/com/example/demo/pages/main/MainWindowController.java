package com.example.demo.pages.main;

import com.example.demo.network.events.EventHandler;
import com.example.demo.network.internal.ConnectionState;
import com.example.demo.network.internal.User;
import com.example.demo.network.packets.impl.common.MessagePacket;
import com.example.demo.network.packets.impl.incoming.UnreadMessagePacket;
import com.example.demo.network.packets.impl.outgoing.RequestHistoryPacket;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable{

    @FXML
    private ListView<String> messages;

    @FXML
    private TextArea messageArea;

    @FXML
    private Text errorText;

    @FXML
    private Button sendButton;

    @FXML
    public void send(ActionEvent e){
        if(messageArea.getText() != null && !messageArea.getText().isEmpty()){
            new MessagePacket()
                    .setUsername(User.getUsername())
                    .setMessageContent(messageArea.getText())
                    .send();
            messageArea.setText("");
        }
    }

    @FXML
    public void keyPress(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER && messageArea.getText() != null && !messageArea.getText().isEmpty()){
            new MessagePacket()
                    .setUsername(User.getUsername())
                    .setMessageContent(messageArea.getText())
                    .send();
            messageArea.setText("");
        }
    }

    public void addMessage(String username, String message){
        messages.getItems().add(username +": "+message);
        messages.scrollTo(messages.getItems().size());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventHandler.setConnectionStateEvent(state -> {
            if(state == ConnectionState.CONNECTING){
                errorText.setText("Reconnecting to server");
                sendButton.setDisable(true);
            } else if(state == ConnectionState.CONNECTED){
                errorText.setText("");
                sendButton.setDisable(false);
            }
        });

        EventHandler.setPacketCallback(MessagePacket.class, packet -> {
            MessagePacket messagePacket = (MessagePacket) packet;

            Platform.runLater(() -> addMessage(messagePacket.getUsername(), messagePacket.getMessageContent()));
        });

        EventHandler.setPacketCallback(UnreadMessagePacket.class, packet -> {
            Platform.runLater(() -> {
                messages.getItems().add("------- UNREAD MESSAGES -------");
                messages.scrollTo(messages.getItems().size());
            });
        });

        new RequestHistoryPacket().send();
    }
}
