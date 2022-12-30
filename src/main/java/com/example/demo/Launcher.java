package com.example.demo;

import com.example.demo.network.internal.NetworkHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
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
    }

    public void run(){
        NetworkHandler.connect("localhost", 8080);
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
}
