package com.example.chatviewer.data.importer;

// Class Factory is a method that selects a class
public class FileImporterFactory {
    public static FileImporter getFileImporter(String filePath) {
        if (filePath.endsWith(".msg")) {
            return new MsgFileImporter();
        }
        throw new IllegalArgumentException("Unsupported file type");
    }
}
