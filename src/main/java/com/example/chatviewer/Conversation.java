package com.example.chatviewer;

import java.util.ArrayList;
import java.util.Objects;

public class Conversation {
    private ArrayList<Message> messages;
    private String fileName;

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public String getFileName() {
        return fileName;
    }

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

    public void retrieveFileNameFromPath(String path) {
        String[] msgFilePathArray = path.split("/");
        fileName = msgFilePathArray[msgFilePathArray.length - 1];
    }
}
