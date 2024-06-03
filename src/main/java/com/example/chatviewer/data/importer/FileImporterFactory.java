package com.example.chatviewer.data.importer;

/**
 * Factory class for creating FileImporter implementations.
 */
public class FileImporterFactory {
    /**
     * Returns a FileImporter implementation based on the file extension.
     * @param filePath path to the file to be imported
     * @return a FileImporter implementation
     */
    public static FileImporter getFileImporter(String filePath) {
        if (filePath.endsWith(".msg")) {
            return new MsgFileImporter();
        }
        throw new IllegalArgumentException("Unsupported file type");
    }
}
