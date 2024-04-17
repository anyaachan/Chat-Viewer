package com.example.chatviewer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;


// Class which will be connected to our graphical interface
public class ChatViewerController {
    @FXML
    private Label welcomeText;
    private Button openButton;

    private ImportUtils importUtils = new ImportUtils();

    @FXML
    public void openMsgFile(ActionEvent event) {
        try {
            importUtils.openMsgFile();
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

