package com.example.demo.pages.login;

import com.example.demo.Launcher;
import com.example.demo.network.events.EventHandler;
import com.example.demo.network.internal.ConnectionState;
import com.example.demo.network.internal.NetworkHandler;
import com.example.demo.network.packets.Packet;
import com.example.demo.network.packets.impl.incoming.MessagePacket;
import com.example.demo.network.packets.impl.incoming.ServerResponse;
import com.example.demo.network.packets.impl.outgoing.AuthPacket;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable {
    private final static String ERROR_STYLE = "-fx-border-color: red;";

    @FXML
    private TextField usernameInp;

    @FXML
    private PasswordField passwordInp;

    @FXML
    private Text error;

    @FXML
    private Button button;

    @FXML
    public void login(ActionEvent e){
        boolean hasEmptyField = false;

        if(usernameInp.getText().isEmpty()) {
            usernameInp.setStyle(ERROR_STYLE);
            hasEmptyField = true;
        }
        if(passwordInp.getText().isEmpty()){
            passwordInp.setStyle(ERROR_STYLE);
            hasEmptyField = true;
        }

        if(hasEmptyField){
            return;
        }

        button.setDisable(true);
        new AuthPacket()
                .setUsername(usernameInp.getText())
                .setPassword(passwordInp.getText())
                .send();
    }

    @FXML
    public void usernameClick(MouseEvent e){
        if(usernameInp.getStyle().equals(ERROR_STYLE)){
            usernameInp.setStyle("");
            error.setText("");
        }
    }

    @FXML
    public void passwordClick(MouseEvent e){
        if(passwordInp.getStyle().equals(ERROR_STYLE)){
            passwordInp.setStyle("");
            error.setText("");
        }
    }

    @FXML
    public void hyperlink(ActionEvent e) {
        Launcher.switchToScene("RegisterView").show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameInp.focusedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(!newVal && usernameInp.getText().isEmpty()){
                usernameInp.setStyle(ERROR_STYLE);
            }
        });
        passwordInp.focusedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(!newVal && passwordInp.getText().isEmpty()){
                passwordInp.setStyle(ERROR_STYLE);
            }
        });

        EventHandler.setConnectionStateEvent(state -> {
            if(state == ConnectionState.CONNECTING){
                error.setText("Still connecting to server");
                button.setDisable(true);
            } else if(state == ConnectionState.CONNECTED){
                error.setText("");
                button.setDisable(false);
            }
        });

        EventHandler.setPacketCallback(ServerResponse.class, packet -> {
            ServerResponse response = (ServerResponse) packet;

            if(response.getCode() == ServerResponse.StatusCode.SUCCESS){
                Platform.runLater(() -> Launcher.switchToScene("deneme"));
            } else if(response.getCode() == ServerResponse.StatusCode.FAILURE) {
                error.setText(response.getText());
                button.setDisable(false);
            }
        });

    }
}
