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

    public void readMsgFile(String msgFilePath) {
        try {
            File msgFile = new File(msgFilePath);
            ArrayList<Message> msgObjects = new ArrayList<Message>();
            String strCurrentLine;

            BufferedReader objReader = new BufferedReader(new FileReader(msgFile));

            while ((strCurrentLine = objReader.readLine()) != null) {
                if (strCurrentLine.startsWith("Time")) {
                    Message message = new Message();
                    message.setTimestamp(strCurrentLine.split("Time:")[1].trim());

                    strCurrentLine = objReader.readLine();
                    if (strCurrentLine != null && strCurrentLine.startsWith("Name")) {
                        message.setNickname(strCurrentLine.split("Name:")[1].trim());
                    }

                    strCurrentLine = objReader.readLine();
                    if (strCurrentLine != null && strCurrentLine.startsWith("Message")) {
                        message.setContent(strCurrentLine.split("Message:")[1].trim());
                    }

                    msgObjects.add(message);
                }
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

    }
}

