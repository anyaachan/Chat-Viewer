package com.example.chatviewer;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class UIUtils {
    private boolean darkModeEnabled = false;

    public void setButtonImage(String imagePath,
                               Button button) {
        ImageView themeImageView = new ImageView(new Image(imagePath));
        themeImageView.setFitWidth(40);
        themeImageView.setFitHeight(40);

        button.setPickOnBounds(true);
        button.setGraphic(themeImageView);
    }

    public void handleThemeToggle(Button themeSwitchButton,
                                  Button getHelpButton) {
        Scene scene = themeSwitchButton.getScene();
        if (scene != null) {
            if (!darkModeEnabled) {
                scene.getStylesheets().clear();
                scene.getStylesheets().add("style-dark.css");

                setButtonImage("theme-switch-white.png", themeSwitchButton);
                setButtonImage("question-mark-white.png", getHelpButton);
                darkModeEnabled = true;
            } else {
                scene.getStylesheets().clear();
                scene.getStylesheets().add("style-light.css");

                setButtonImage("theme-switch-black.png", themeSwitchButton);
                setButtonImage("question-mark-black.png", getHelpButton);
                darkModeEnabled = false;
            }
        }
    }

    public void openHelpPopUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Chat Viewer Help");
        alert.setContentText("Welcome to Chat Viewer!" +
                "\n\n" +
                "This application allows you to view chat messages from .msg files. " +
                "To get started, click the Open button and select a .msg file. " +
                "\n\n" +
                "The messages should be separated by an empty line. The content of the messages should be formated as follows: " +
                "\n\n" +
                "Time: 12:00:00" +
                "\n" +
                "Name: John" +
                "\n" +
                "Message: Hello, how are you?" +
                "\n\n" +
                "You can switch between light and dark themes by clicking the theme switch button on the right. " +
                "\n\n" +
                "If you have any questions or need help, please contact me via GitHub: " +
                "\n" +
                "github.com/anyaachan" +
                "\n\n" +
                "Enjoy using Chat Viewer!");

        alert.showAndWait();
    }

}
