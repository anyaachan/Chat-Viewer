package com.example.chatviewer.data.importer;

import java.util.ArrayList;
import com.example.chatviewer.data.Message;
import java.io.IOException;

public interface FileImporter {
    String openFile();
    ArrayList<Message> readFile(String filePath) throws IOException;
}
