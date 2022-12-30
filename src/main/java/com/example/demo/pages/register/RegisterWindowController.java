package com.example.demo.pages.register;

import com.example.demo.Launcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterWindowController implements Initializable {

    private final static String ERROR_STYLE = "-fx-border-color: red;";

    @FXML
    private TextField usernameInp;

    @FXML
    private TextField emailInp;

    @FXML
    private PasswordField passwordInp;

    @FXML
    private PasswordField passwordConfirmInp;

    @FXML
    private Text error;

    @FXML
    public void register(ActionEvent e){
        boolean hasEmptyField = false;

        if(usernameInp.getText().isEmpty()){
            usernameInp.setStyle(ERROR_STYLE);
            hasEmptyField = true;
        }
        if(emailInp.getText().isEmpty()){
            emailInp.setStyle(ERROR_STYLE);
            hasEmptyField = true;
        }
        if(passwordInp.getText().isEmpty()){
            passwordInp.setStyle(ERROR_STYLE);
            hasEmptyField = true;
        }
        if(passwordConfirmInp.getText().isEmpty()){
            passwordConfirmInp.setStyle(ERROR_STYLE);
            hasEmptyField = true;
        }

        if(hasEmptyField){
            return;
        }

        if(!passwordInp.getText().equals(passwordConfirmInp.getText())){
            error.setText("Passwords are not matched");
            return;
        }

        // TODO: Actual register

        Launcher.switchToScene("LoginView").show();
    }

    @FXML
    public void usernameClick(MouseEvent e){
        if(usernameInp.getStyle().equals(ERROR_STYLE)){
            usernameInp.setStyle("");
            error.setText("");
        }
    }

    @FXML
    public void emailClick(MouseEvent e){
        if(emailInp.getStyle().equals(ERROR_STYLE)){
            emailInp.setStyle("");
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
    public void passwordConfirmClick(MouseEvent e){
        if(passwordConfirmInp.getStyle().equals(ERROR_STYLE)){
            passwordConfirmInp.setStyle("");
            error.setText("");
        }
    }

    @FXML
    public void hyperlink(ActionEvent e) throws IOException {
        Launcher.switchToScene("LoginView").show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameInp.focusedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(!newVal && usernameInp.getText().isEmpty()){
                usernameInp.setStyle(ERROR_STYLE);
            }
        });
        emailInp.focusedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(!newVal && emailInp.getText().isEmpty()){
                emailInp.setStyle(ERROR_STYLE);
            }
        });
        passwordInp.focusedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(!newVal && passwordInp.getText().isEmpty()){
                passwordInp.setStyle(ERROR_STYLE);
            }
        });
        passwordConfirmInp.focusedProperty().addListener((observableValue, oldVal, newVal) -> {
            if(!newVal && passwordConfirmInp.getText().isEmpty()){
                passwordConfirmInp.setStyle(ERROR_STYLE);
            }
        });
    }
}
