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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Class which will be connected to our graphical interface
public class ChatViewerController {
    @FXML
    private Label infoLabel;
    @FXML
    private Button openButton;
    @FXML
    private ListView<Message> messageListView;
    private FileImportManager fileImportManager = new FileImportManager();


    @FXML
    public void openMsgFile(ActionEvent event) {
        Conversation conversation = new Conversation();
        String msgFilePath = fileImportManager.openMsgFile();

        // Get file name and set it to the label
        conversation.retrieveFileNameFromPath(msgFilePath);
        infoLabel.setText(conversation.getFileName());

        // Set messages to the conversation
        conversation.setMessages(fileImportManager.readMsgFile(msgFilePath));
        // Replace consecutive nicknames with dots
        conversation.replaceSameNicknamesWithDots();

        // Convert ArrayList to ObservableList, as ListView requires ObservableList. ObservableList allows listeners to track changes when they occur.
        ObservableList<Message> observableMessages = FXCollections.observableArrayList(conversation.getMessages());
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

                            // Check whether the nickname should display or not (better readability)
                            String currentNickname = message.getNickname();
                            Text nameText = new Text();
                            if (currentNickname.equals(" ")) {
                                nameText = new Text("");
                            } else {
                                nameText = new Text(currentNickname + ": ");
                            }

                            // Set styles as specified in ICA
                            timestampText.setStyle("-fx-fill: black;");
                            nameText.setStyle("-fx-fill: blue;");

                            TextFlow textFlow = new TextFlow(timestampText, nameText);

                            ArrayList<String> messageParts = message.splitMessageByEmoticonSymbols();
                            System.out.println(messageParts);
                            for (String part : messageParts) {
                                System.out.println(part);
                                if (part.equals(":)")) {
                                    ImageView happyImage = new ImageView("smile_happy.gif");
                                    textFlow.getChildren().add(happyImage);
                                } else if (part.equals(":(")) {
                                    ImageView sadImage = new ImageView("smile_sad.gif");
                                    textFlow.getChildren().add(sadImage);
                                } else {
                                    Text messageTextPart = new Text(part);
                                    messageTextPart.setStyle("-fx-fill: black; -fx-font-weight: bold;");
                                    textFlow.getChildren().add(messageTextPart);
                                }
                            }

                            setGraphic(textFlow);  // Display textFlow in the cell

                        }

                        setPrefWidth(0);
                        setMaxWidth(Double.MAX_VALUE);
                    }

                };
            }
        });
    }
}

