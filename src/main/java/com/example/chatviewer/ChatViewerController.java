package com.example.chatviewer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;


// Class which will be connected to our graphical interface
public class ChatViewerController {
    @FXML
    private Label infoLabel;
    @FXML
    private Button openButton;
    @FXML
    private ListView<Message> messageListView;
    private ImportUtils importUtils = new ImportUtils();

    @FXML
    public void openMsgFile(ActionEvent event) {
        String msgFilePath = importUtils.openMsgFile();
        infoLabel.setText(msgFilePath);
        ArrayList<Message> messages = importUtils.readMsgFile(msgFilePath);
        // Convert ArrayList to ObservableList, as ListView requires ObservableList. ObservableList allows listeners to track changes when they occur.
        ObservableList<Message> observableMessages = FXCollections.observableArrayList(messages);
        messageListView.setItems(observableMessages); // Populate the list

    }
}

