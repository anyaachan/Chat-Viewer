package com.example.chatviewer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.Objects;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


// Class which will be connected to our graphical interface
public class ChatViewerController {
    @FXML
    private Label infoLabel;
    @FXML
    private Button openButton;
    @FXML
    private ListView<Message> messageListView;
    private ImportUtils importUtils = new ImportUtils();

    public ArrayList<Message> replaceSameNicknamesWithDots(ArrayList<Message> messages){
        String messageName = messages.get(0).getNickname();
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if ((Objects.equals(message.getNickname(), messageName)) && (i != 0)) {
                message.setNickname("...");
            } else {
                messageName = message.getNickname();
            }
        }
        return messages;
    }

    public
    @FXML
    public void openMsgFile(ActionEvent event) {
        String msgFilePath = importUtils.openMsgFile();
        infoLabel.setText(msgFilePath);
        ArrayList<Message> messages = importUtils.readMsgFile(msgFilePath);
        messages = replaceSameNicknamesWithDots(messages);
        // Convert ArrayList to ObservableList, as ListView requires ObservableList. ObservableList allows listeners to track changes when they occur.
        ObservableList<Message> observableMessages = FXCollections.observableArrayList(messages);
        messageListView.setItems(observableMessages); // Populate the list

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
                            Text timestampText = new Text("[" + message.getTimestamp() + "] ");
                            Text nameText = new Text(message.getNickname() + ": ");
                            Text messageText = new Text(message.getContent());

                            // Apply styling to each part of the message
                            timestampText.setStyle("-fx-fill: black;");
                            nameText.setStyle("-fx-fill: blue;");
                            messageText.setStyle("-fx-fill: black; -fx-font-weight: bold;");

                            TextFlow textFlow = new TextFlow(timestampText, nameText, messageText);
                            setGraphic(textFlow);  // Set the graphic of the cell to the styled TextFlow
                        }
                    }

                };
            }
        });
    }
}

