package com.example.demo;

import com.example.demo.network.internal.NetworkHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class Launcher extends Application {
    public static Stage STAGE;

    @Override
    public void start(Stage stage) throws Exception {
        Launcher.STAGE = stage;

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("LoginView.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Chat App");
        stage.show();
        String host = null;
        int port = 0;

        TextInputDialog connectionCredentials = new TextInputDialog();
        connectionCredentials.setTitle("Chat App - Server Selection");
        connectionCredentials.setHeaderText("Enter the server you want to connect");
        connectionCredentials.setContentText("HOST:PORT");
        connectionCredentials.getDialogPane().getButtonTypes().remove(1);
        Optional<String> result = connectionCredentials.showAndWait();
        if (result.isPresent()) {
            String r = result.get().trim();
            if (!r.contains(":")) {
                errorPane("Please enter a valid HOST:PORT url").showAndWait();
                Platform.exit();
                return;
            }
            try {
                host = r.split(":")[0];
                port = Integer.parseInt(r.split(":")[1]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                errorPane("Please enter a valid HOST:PORT url").showAndWait();
                Platform.exit();
                return;
            }
        } else {
            errorPane("Please enter a valid HOST:PORT url").showAndWait();
            Platform.exit();
            return;
        }
        NetworkHandler.connect(host, port);
    }

    public void run(){
        launch();
    }

    public static Stage switchToScene(String name) {
        try {
            STAGE.setScene(new Scene(FXMLLoader.load(Main.class.getResource(name+".fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return STAGE;
    }

    public static Dialog<String> errorPane(String name){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Error");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(name);
        dialog.getDialogPane().getButtonTypes().add(type);
        return dialog;
    }
}
