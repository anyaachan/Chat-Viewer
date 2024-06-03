package com.example.chatviewer.ui;

import com.example.chatviewer.data.Message;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

/**
 * Constructs a VBox which represents a single message in the chat.
 */
public class MessageBox extends VBox {
    /**
     * This constructor handles the layout of the message content in VBox, including the nickname,
     * timestamp, and the text of the message itself. Emoticons in the message are converted to images.
     * Nicknames that are replaced with a space are not displayed.
     * <p>
     * Each part of the message is individually styled and added to the overall layout.
     * @param message The message to be displayed.
     */
    public MessageBox(Message message) {
        super();
        // Nickname, do not display if it is empty (when the message is from the same user as a previous one)
        Text nameText = message.getNickname().equals(" ") ? null : new Text(message.getNickname());
        TextFlow nameTextFlow = new TextFlow();
        if (nameText != null) {
            nameText.getStyleClass().add("name-text");
            nameTextFlow.getChildren().add(nameText);
        }

        TextFlow messageFlow = new TextFlow();
        // Timestamp
        Text timestampText = new Text("[" + message.getTimestamp() + "]  ");
        timestampText.getStyleClass().add("timestamp-text");
        messageFlow.getChildren().add(timestampText);
        // Content, add emoticons as images
        ArrayList<String> messageParts = message.splitMessageByEmoticonSymbols();
        for (String part : messageParts) {
            if (part.equals(":)")) {
                ImageView happyImage = new ImageView("smile_happy.gif");
                happyImage.setFitHeight(20);
                happyImage.setFitWidth(20);
                messageFlow.getChildren().add(happyImage);
            } else if (part.equals(":(")) {
                ImageView sadImage = new ImageView("smile_sad.gif");
                sadImage.setFitHeight(20);
                sadImage.setFitWidth(20);
                messageFlow.getChildren().add(sadImage);
            } else {
                Text messageTextPart = new Text(part);
                messageTextPart.getStyleClass().add("message-text");
                messageFlow.getChildren().add(messageTextPart);
            }
        }

        getChildren().addAll(nameTextFlow, messageFlow);
        setAlignment(Pos.CENTER_LEFT);
    }
}
