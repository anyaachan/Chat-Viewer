package com.example.chatviewer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.stage.Stage;

public class ImportUtils {

    public void openMsgFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Please Open .msg File");

        File msgFile = fileChooser.showOpenDialog(new Stage());
    }
}

