package com.example.chatviewer.data.importer;

import com.example.chatviewer.data.Message;

import java.io.File;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class MsgFileImporter implements FileImporter {
    public ArrayList<Message> readFile(String msgFilePath) throws IOException {
        File msgFile = new File(msgFilePath);
        ArrayList<Message> msgObjects = new ArrayList<Message>();

        // Using try with BufferedReader as a resource to ensure that it will be closed if an exception occurs
        String strCurrentLine;
        String[] messageContent;
        Message message = null;

        try (BufferedReader objReader = new BufferedReader(new FileReader(msgFile))) {
            while ((strCurrentLine = objReader.readLine()) != null) {
                if (strCurrentLine.startsWith("Time")) {

                    if ((message != null) && (message.getTimestamp() != null) && (message.getNickname() != null) && (message.getContent() != null)) {
                        msgObjects.add(message);
                    }

                    message = new Message();
                    message.setTimestamp(strCurrentLine.split("Time\\s*:")[1].trim());
                } else if (strCurrentLine.startsWith("Name")) {
                    // If the app finds several Name or Message without Time specified first, the conversation will be marked as invalid
                    if (message.getNickname() == null) {
                        message.setNickname(strCurrentLine.split("Name\\s*:")[1].trim());
                    } else {
                        return null;
                    }
                } else if (strCurrentLine.startsWith("Message")) {
                    if (message.getContent() == null) {
                        messageContent = strCurrentLine.split("Message\\s*:");
                        if (messageContent.length != 0) {
                            message.setContent(messageContent[1].trim());
                        }
                    } else {
                        return null;
                    }
                }
            }

            // Add the last message as it won't be added in the loop
            if ((message != null) && (message.getTimestamp() != null) && (message.getNickname() != null) && (message.getContent() != null)) {
                msgObjects.add(message);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return msgObjects;
    }
}

