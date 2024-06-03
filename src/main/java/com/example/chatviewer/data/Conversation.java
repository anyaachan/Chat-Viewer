package com.example.chatviewer.data;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a conversation, which consists of multiple messages.
 * <p>
 * Handles operations on the list of related messages.
 */
public class Conversation {
    private ArrayList<Message> messages;
    private String fileName;

    /**
     * Sets the list of messages for this conversation.
     *
     * @param messages An ArrayList of Message objects to be stored in this conversation.
     */
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    /**
     * Retrieves the list of messages in this conversation.
     *
     * @return An ArrayList of Message objects currently stored in this conversation.
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * Retrieves the name of the file from which the messages are loaded.
     *
     * @return String representing the file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Replaces the nickname of a message with a space if it is the same as the nickname of the previous message.
     * Improves readability of the message list.
     */
    public void replaceSameNicknamesWithSpace() {
        String messageName = messages.get(0).getNickname();
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if ((Objects.equals(message.getNickname(), messageName)) && (i != 0)) {
                message.setNickname(" ");
            } else {
                messageName = message.getNickname();
            }
        }
    }

    /**
     * Extracts the name of the file from the filepath.
     *
     * @param path the path to the file.
     */
    public void retrieveFileNameFromPath(String path) {
        String[] msgFilePathArray = path.split("/");
        fileName = msgFilePathArray[msgFilePathArray.length - 1];
    }
}
