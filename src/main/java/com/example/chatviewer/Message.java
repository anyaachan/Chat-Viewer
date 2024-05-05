package com.example.chatviewer;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
    private String timestamp;
    private String nickname;
    private String content;


    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContent() {
        return content;
    }


    public ArrayList<String> splitMessageByEmoticonSymbols() {
        ArrayList<String> parts = new ArrayList<>();

        Pattern pattern = Pattern.compile("(:\\)|:\\(|(?:(?!:\\)|:\\().)+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            parts.add(matcher.group());
        }

        return parts;
    }

    public TextFlow createMessageFlow() {
        TextFlow textFlow = new TextFlow();
        ArrayList<String> messageParts = this.splitMessageByEmoticonSymbols();
        for (String part : messageParts) {
            if (part.equals(":)")) {
                ImageView happyImage = new ImageView("smile_happy.gif");
                happyImage.setFitHeight(20);
                happyImage.setFitWidth(20);
                textFlow.getChildren().add(happyImage);
            } else if (part.equals(":(")) {
                ImageView sadImage = new ImageView("smile_sad.gif");
                sadImage.setFitHeight(20);
                sadImage.setFitWidth(20);
                textFlow.getChildren().add(sadImage);
            } else {
                Text messageTextPart = new Text(part);
                messageTextPart.getStyleClass().add("message-text");
                textFlow.getChildren().add(messageTextPart);
            }
        }
        return textFlow;
    }
}
