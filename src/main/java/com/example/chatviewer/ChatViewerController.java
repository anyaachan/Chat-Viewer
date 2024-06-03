package com.example.chatviewer;

import java.io.File;
import java.io.IOException;

import com.example.chatviewer.data.Conversation;
import com.example.chatviewer.data.Message;
import com.example.chatviewer.data.importer.FileImporter;
import com.example.chatviewer.data.importer.FileImporterFactory;
import com.example.chatviewer.ui.MessageBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Controller class for the Chat Viewer application.
 * Handles the interaction between the user interface and the data model.
 */

public class ChatViewerController {
    @FXML
    private Label infoLabel;
    @FXML
    private Button themeSwitchButton;
    @FXML
    private Button getHelpButton;
    @FXML
    private ListView<Message> messageListView; // List of messages
    private boolean darkModeEnabled = false;

    /**
     * Sets the image as the icon of the button.
     *
     * @param imagePath path to the image file
     * @param button    button to set the image to
     */
    public void setButtonImage(String imagePath,
                               Button button) {
        ImageView themeImageView = new ImageView(new Image(imagePath));
        themeImageView.setFitWidth(40);
        themeImageView.setFitHeight(40);

        button.setPickOnBounds(true);
        button.setGraphic(themeImageView);
    }

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * Sets the appearance of the buttons and the cells of ListView.
     */
    public void initialize() {
        setButtonImage("theme-switch-black.png", themeSwitchButton);
        setButtonImage("question-mark-black.png", getHelpButton);

        // Customize the appearance of the cells in ListView with cell factory
        messageListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Message> call(ListView<Message> messageListView) {
                // updateItem method overrides ListCell<Message> updateItem method, which is called when the cell needs to be updated
                return new ListCell<Message>() {
                    protected void updateItem(Message message, boolean empty) {
                        super.updateItem(message, empty);

                        if (empty || message == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            VBox vBox = new MessageBox(message);
                            setGraphic(vBox);
                        }

                        // Stop text from overflowing the application window
                        setPrefWidth(0);
                        setMaxWidth(Double.MAX_VALUE);
                    }
                };
            }
        });
    }

    /**
     * Displays a help dialog with instructions on how to use the application. Displayed when the help button is clicked.
     *
     * @throws IOException
     */
    @FXML
    public void openHelpPopUp() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Chat Viewer Help");
        alert.setContentText("Welcome to Chat Viewer!" +
                "\n\nThis application allows you to view chat messages from .msg files. " +
                "To get started, click the Open button and select a .msg file. " +
                "\n\nThe messages should be separated by an empty line. The content of the messages should be formatted as follows: " +
                "\n\nTime: 12:00:00\nName: John\nMessage: Hello, how are you?" +
                "\n\nYou can switch between light and dark themes by clicking the theme switch button on the right. " +
                "\n\nIf you have any questions or need help, please contact me via GitHub: " +
                "\ngithub.com/anyaachan\n\nEnjoy using Chat Viewer!");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStyleClass().add("dialog-pane");
        if (darkModeEnabled) {
            dialogPane.getStylesheets().add("style-dark.css");
        } else {
            dialogPane.getStylesheets().add("style-light.css");
        }
        alert.showAndWait();
    }

    /**
     * Switches between light and dark themes when the theme switch button is clicked.
     */
    @FXML
    public void handleThemeToggle() {
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

    /**
     * Displays a custom error alert with the specified title, header, and text.
     *
     * @param alertTitle  the title of the alert
     * @param alertHeader the header of the alert
     * @param alertText   the text of the alert
     */
    public void displayErrorAlert(String alertTitle,
                                  String alertHeader,
                                  String alertText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(alertText);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStyleClass().add("dialog-pane");
        if (darkModeEnabled) {
            dialogPane.getStylesheets().add("style-dark.css");
        } else {
            dialogPane.getStylesheets().add("style-light.css");
        }

        alert.showAndWait();
    }

    /**
     * Opens a file selection dialog and returns the path to the selected file.
     *
     * @return the path to the selected file
     */
    public String OpenFileDialog() {
        String msgFilePath = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Please Open .msg File");

            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Message Files", "*.msg"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));

            Stage mainStage = new Stage();
            File msg = fileChooser.showOpenDialog(mainStage);

            if (msg != null) {
                msgFilePath = msg.getAbsolutePath();
            } else {
                return null;
            }
        } catch (Exception e) {
            displayErrorAlert("Error",
                    "Error opening file",
                    "An error occurred while opening the file. Check if the file exist and in .msg format and try again. " +
                            "\n\n" + "Error: " + e.getMessage());
        }
        return msgFilePath;
    }

    // Handler for the Open button, loads messages into ListView

    /**
     * Loads messages from a file into the ListView. Handles file selection, message parsing, and message display in the ListView.
     * Called when the "Open File" button is clicked.
     */
    @FXML
    public void loadMsgFile() throws IOException {
        Conversation conversation = new Conversation();
        String msgFilePath = OpenFileDialog();

        if (msgFilePath == null) {
            return;
        }

        // Get file name and set it to the label
        conversation.retrieveFileNameFromPath(msgFilePath);

        // Get messages into array list
        // Empty or null msgObject will be handled when populating them into conversation.
        FileImporter fileImporter = FileImporterFactory.getFileImporter(msgFilePath);
        ArrayList<Message> msgObjects = fileImporter.readFile(msgFilePath);

        try {
            conversation.setMessages(msgObjects);
            conversation.replaceSameNicknamesWithSpace();
            infoLabel.setText(conversation.getFileName());
            // Catch any error occuring due to the msg file having incorrect messages format inside
        } catch (Exception e) {
            displayErrorAlert("Error",
                    "Error reading file",
                    "No messages found in the file or the messages are not formatted correctly. Please check the file and try again. " +
                            "You can find an example of .msg files in the corresponding GitHub repository. " +
                            "\n\n" + "Error: " + e.getMessage());
            return;
        }

        // ListView requires ObservableLis, which allows listeners to track changes when they occur.
        ObservableList<Message> observableMessages = FXCollections.observableArrayList(conversation.getMessages());
        messageListView.setItems(observableMessages); // Populate the ListView
    }
}

