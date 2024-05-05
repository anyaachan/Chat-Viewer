package com.example.chatviewer;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import javafx.geometry.Pos;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javafx.util.Callback;

// Class which will be connected to our graphical interface
public class ChatViewerController {
    @FXML
    private Label infoLabel;
    @FXML
    private Button openButton;
    @FXML
    private Button themeSwitchButton;
    @FXML
    private Button getHelpButton;
    @FXML
    private ListView<Message> messageListView;
    private FileImportManager fileImportManager = new FileImportManager();
    private UIUtils uiUtils = new UIUtils();

    public void initialize() {
        uiUtils.setButtonImage("theme-switch-black.png", themeSwitchButton);
        uiUtils.setButtonImage("question-mark-black.png", getHelpButton);
    }

    @FXML
    public void openHelpPopUp(ActionEvent event) throws IOException {
        uiUtils.openHelpPopUp();
    }

    @FXML
    public void handleThemeToggle(ActionEvent event) {
        uiUtils.handleThemeToggle(themeSwitchButton, getHelpButton);
    }

    public void displayErrorAlert(String alertTitle,
                                  String alertHeader,
                                  String alertText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(alertText);

        alert.showAndWait();
    }

    public void updateListCell() {
        // Create a cell factory for messageListView to display messages in a custom way
        // Callback is a method that is passed as an argument and is called when a specific event occurs
        // Input type is ListView<Message> (to explain to which list the cell belongs to) and output type is ListCell<Message>
        messageListView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            // Create listCell for each message with call method
            @Override
            public ListCell<Message> call(ListView<Message> messageListView) {
                // updateItem method overrides ListCell<Message> updateItem method, which is called when the cell needs to be updated
                return new ListCell<Message>() {
                    protected void updateItem(Message message, boolean empty) {
                        super.updateItem(message, empty); // Ensure that built-in functionality executes before adding custom functionality
                        if (empty || message == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            Text timestampText = new Text("[" + message.getTimestamp() + "]  ");

                            Text nameText = message.getNickname().equals(" ") ? null : new Text(message.getNickname());
                            TextFlow nameTextFlow = new TextFlow();

                            timestampText.getStyleClass().add("timestamp-text");

                            if (nameText != null) {
                                nameText.getStyleClass().add("name-text");
                                nameTextFlow.getChildren().add(nameText);
                            }

                            TextFlow textFlow = new TextFlow(timestampText, message.createMessageFlow());

                            VBox vBox = new VBox(nameTextFlow, textFlow);
                            vBox.setAlignment(Pos.CENTER_LEFT);  // Align children to the top-left

                            setGraphic(vBox);
                        }
                        setPrefWidth(0);
                        setMaxWidth(Double.MAX_VALUE);
                    }
                };
            }
        });
    }

    public String OpenFileDialog() {
        String msgFilePath = null;

        // Try to open the file and get its path
        try {
            msgFilePath = fileImportManager.openMsgFile();
        } catch (Exception e) {
            displayErrorAlert("Error",
                    "Error opening file",
                    "An error occurred while opening the file. Check if the file exist and in .msg format and try again. " +
                            "\n\n" + "Error: " + e.getMessage());
        }
        return msgFilePath;
    }

    @FXML
    public void openMsgFile(ActionEvent event) throws IOException {
        Conversation conversation = new Conversation();
        String msgFilePath = OpenFileDialog();

        // Get file name and set it to the label
        conversation.retrieveFileNameFromPath(msgFilePath);
        infoLabel.setText(conversation.getFileName());

        // Get messages and set them to the conversation. Even if the msgObject is  empty, it will be handled in the next step.
        ArrayList<Message> msgObjects = fileImportManager.readMsgFile(msgFilePath);

        try {
            conversation.setMessages(msgObjects);
            conversation.replaceSameNicknamesWithDots();
            // Catch any error occuring due to the msg file having incorrect messages format inside
        } catch (Exception e) {
            displayErrorAlert("Error",
                    "Error reading file",
                    "No messages found in the file or the messages are not formatted correctly. Please check the file and try again. " +
                            "You can find an example of .msg files in the corresponding GitHub repository. " +
                            "\n\n" + "Error: " + e.getMessage());
        }

        // Convert ArrayList to ObservableList, as ListView requires ObservableList. ObservableList allows listeners to track changes when they occur.
        ObservableList<Message> observableMessages = FXCollections.observableArrayList(conversation.getMessages());
        messageListView.setItems(observableMessages); // Populate the list

        updateListCell();
    }
}

