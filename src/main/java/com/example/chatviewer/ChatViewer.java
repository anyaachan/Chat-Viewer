package com.example.chatviewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// Main code, the entry point of the application
public class ChatViewer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatViewer.class.getResource("ChatViewerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Chat Viewer");
        stage.setScene(scene);
        stage.show();
        stage.setMinHeight(500);
        stage.setMinWidth(410);
        scene.getStylesheets().add("style.css");
    }

    public static void main(String[] args) {
        launch();
    }
}