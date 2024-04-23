package com.example.chatviewer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;


// Class which will be connected to our graphical interface
public class ChatViewerController {
    @FXML
    private Label infoLabel;
    @FXML
    private Button openButton;

    private ImportUtils importUtils = new ImportUtils();

    @FXML
    public void openMsgFile(ActionEvent event) {
        String msgFilePath = importUtils.openMsgFile();
        infoLabel.setText(msgFilePath);
        importUtils.readMsgFile(msgFilePath);
    }
}

