package com.example.chatviewer.data;

import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a single message, including details such as the timestamp,
 * users nickname, and the content of the message.
 * Provides methods for message content manipulation.
 */

public class Message {
    private String timestamp;
    private String nickname;
    private String content;

    /**
     * Sets the timestamp of the message.
     *
     * @param timestamp timestamp in the format "hh:mm:ss"
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Sets the nickname of the user who sent the message.
     *
     * @param nickname nickname of the user
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Sets the content of the message.
     *
     * @param content text of the message
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Retrieves the timestamp of the message.
     *
     * @return A string representing the time the message was sent.
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Retrieves the nickname of the user who sent the message.
     *
     * @return A string representing the nickname of the user.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Retrieves the content of the message.
     *
     * @return A string representing the content of the message.
     */
    public String getContent() {
        return content;
    }

    /**
     * Splits the content of the message into parts based on emoticon symbols.
     * This allows for separate processing of each segment.
     *
     * @return An ArrayList of String objects, where each string is either an emoticon or a text segment.
     */
    public ArrayList<String> splitMessageByEmoticonSymbols() {
        ArrayList<String> parts = new ArrayList<>();

        Pattern pattern = Pattern.compile("(:\\)|:\\(|(?:(?!:\\)|:\\().)+)");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            parts.add(matcher.group());
        }

        return parts;
    }
}
