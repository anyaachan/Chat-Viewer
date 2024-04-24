package com.example.chatviewer;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ImportUtils {

    public String openMsgFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Please Open .msg File");

        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Message Files", "*.msg"));

        Stage mainStage = new Stage();
        File msg = fileChooser.showOpenDialog(mainStage);
        String msgFilePath = msg.getAbsolutePath();
        System.out.println(msg);

        return msgFilePath;
    }

    public ArrayList<Message> readMsgFile(String msgFilePath) {
        File msgFile = new File(msgFilePath);
        ArrayList<Message> msgObjects = new ArrayList<Message>();

        // Using try with BufferedReader as a resource to ensure that it will be closed if an exception occurs
        try (BufferedReader objReader = new BufferedReader(new FileReader(msgFile))) {
            String strCurrentLine;
            String[] messageContent;
            Message message = null;

            while ((strCurrentLine = objReader.readLine()) != null) {
                if (strCurrentLine.startsWith("Time")) {
                    if (message != null) {
                        msgObjects.add(message);
                    }
                    message = new Message();
                    message.setTimestamp(strCurrentLine.split("Time\\s*:")[1].trim());
                } else if (strCurrentLine.startsWith("Name")) {
                    message.setNickname(strCurrentLine.split("Name\\s*:")[1].trim());
                } else if (strCurrentLine.startsWith("Message")) {
                    messageContent = strCurrentLine.split("Message\\s*:");
                    if (messageContent.length != 0) {
                        message.setContent(messageContent[1].trim());
                    } else {
                        message.setContent(" ");
                    }
                }
            }
            // Add the last message as it won't be added in the loop
            if (message != null) {
                msgObjects.add(message);
            }

            for (Message msg : msgObjects) {
                System.out.println(
                        "Time: " + msg.getTimestamp() +
                                ", Name: " + msg.getNickname() +
                                ", Message: " + msg.getContent()
                );
            }
        } catch (IOException e) {
            System.out.println("Failed to open the file: " + e.getMessage());
        }
        return msgObjects;
    }
}

