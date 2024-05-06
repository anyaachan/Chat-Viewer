package com.example.chatviewer.data;

import java.util.ArrayList;
import java.util.Objects;

public class Conversation {
    private ArrayList<Message> messages;
    private String fileName;

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public String getFileName() {
        return fileName;
    }

    // If there are multiple messages from the same user in a row, replace the nickname with a space
    // Better readability in the chat and design
    public void replaceSameNicknamesWithDots() {
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

    // File name will be displayed in the application
    public void retrieveFileNameFromPath(String path) {
        String[] msgFilePathArray = path.split("/");
        fileName = msgFilePathArray[msgFilePathArray.length - 1];
    }
}
