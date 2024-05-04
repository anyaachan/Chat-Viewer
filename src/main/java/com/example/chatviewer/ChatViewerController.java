package com.example.chatviewer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.util.ArrayList;
import javafx.geometry.Pos;

import javafx.scene.layout.VBox;

import javafx.scene.image.Image;

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
    private Button themeSwitchButton;
    private boolean darkModeEnabled = false;

    @FXML
    private Button getHelpButton;
    @FXML
    private ListView<Message> messageListView;
    private FileImportManager fileImportManager = new FileImportManager();

    public void initialize() {
        openButton.setPickOnBounds(true);
        themeSwitchButton.getStyleClass().add("theme-switch-button");
        getHelpButton.getStyleClass().add("help-button");

        setButtonImage("theme-switch-black.png", "question-mark-black.png");
    }


    public void setButtonImage (String themeSwitchPath,
                                            String helpButtonPath) {
        ImageView themeImageView = new ImageView(new Image(themeSwitchPath));
        themeImageView.setFitWidth(40);
        themeImageView.setFitHeight(40);

        themeSwitchButton.setPickOnBounds(true);
        themeSwitchButton.setGraphic(themeImageView);

        ImageView helpImageView = new ImageView(new Image(helpButtonPath));
        helpImageView.setFitWidth(40);
        helpImageView.setFitHeight(40);

        getHelpButton.setPickOnBounds(true);
        getHelpButton.setGraphic(helpImageView);
    }

    public void handleThemeToggle() {
        Scene scene = themeSwitchButton.getScene();
        if (scene != null) {
            if (!darkModeEnabled) {
                scene.getStylesheets().clear();
                scene.getStylesheets().add("style-dark.css");
                setButtonImage("theme-switch-white.png", "question-mark-white.png");
                darkModeEnabled = true;
            } else {
                scene.getStylesheets().clear();
                scene.getStylesheets().add("style-light.css");
                setButtonImage("theme-switch-black.png", "question-mark-black.png");
                darkModeEnabled = false;
            }
        }
    }

    @FXML
    public void openHelpPopUp(ActionEvent event) throws IOException {
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

    @FXML
    public void openMsgFile(ActionEvent event) throws IOException {
        Conversation conversation = new Conversation();
        String msgFilePath = null;

        // Try to open the file and get its path
        try {
            msgFilePath = fileImportManager.openMsgFile();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error opening file. Please check if the file exists and is in .msg format.");
            alert.setContentText(e.toString());

            alert.showAndWait();
        }

        // Get file name and set it to the label
        conversation.retrieveFileNameFromPath(msgFilePath);
        infoLabel.setText(conversation.getFileName());

        // Get messages and set them to the conversation. Even if the msgObject is  empty, it will be handled in the next step.
        ArrayList<Message> msgObjects = fileImportManager.readMsgFile(msgFilePath);
        System.out.println(msgObjects.size());

        // Catch any error occuring due to the msg file having incorrect messages format inside
        try {
            conversation.setMessages(msgObjects);
            conversation.replaceSameNicknamesWithDots();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error reading file");
            alert.setContentText("No messages found in the file or the messages are not formatted correctly. Please check the file and try again. " +
                    "You can find an example of .msg files in the corresponding GitHub repository. " +
                    "\n\n" + "Error: " + e.getMessage());

            alert.showAndWait();
        }

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
                                nameText = null;
                            } else {
                                nameText = new Text(currentNickname);
                            }

                            VBox vBox = new VBox();
                            vBox.setAlignment(Pos.CENTER_LEFT);  // Align children to the top-left
                            TextFlow nameTextFlow = new TextFlow();

                            timestampText.getStyleClass().add("timestamp-text");

                            if (nameText != null) {
                                nameText.getStyleClass().add("name-text");
                                nameTextFlow.getChildren().add(nameText);
                            }

                            TextFlow textFlow = new TextFlow(timestampText);
                            ArrayList<String> messageParts = message.splitMessageByEmoticonSymbols();
                            for (String part : messageParts) {
                                if (part.equals(":)")) {
                                    ImageView happyImage = new ImageView("smile_happy.gif");
                                    textFlow.getChildren().add(happyImage);
                                } else if (part.equals(":(")) {
                                    ImageView sadImage = new ImageView("smile_sad.gif");
                                    textFlow.getChildren().add(sadImage);
                                } else {
                                    Text messageTextPart = new Text(part);
                                    messageTextPart.getStyleClass().add("message-text");
                                    textFlow.getChildren().add(messageTextPart);
                                }
                            }

//                            vBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");
//                            hBox.setStyle("-fx-border-color: red; -fx-border-width: 1;");
//                            textFlow.setStyle("-fx-border-color: blue; -fx-border-width: 1;");
                            vBox.getChildren().add(nameTextFlow);
                            vBox.getChildren().add(textFlow);

                            setGraphic(vBox);
                        }
                        setPrefWidth(0);
                        setMaxWidth(Double.MAX_VALUE);
                    }
                };
            }
        });
    }
}

