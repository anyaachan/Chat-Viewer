package com.example.chatviewer.data.importer;

import java.util.ArrayList;

import com.example.chatviewer.data.Message;

import java.io.IOException;

/**
 * Interface for classes that import messages from a file.
 * <p>
 * Standardizes file reading operations across different file formats.
 */
public interface FileImporter {
    /**
     * Reads the file and returns a list of messages.
     *
     * @param filePath path to the file to be imported
     * @return an ArrayList of messages
     * @throws IOException if an I/O error occurs
     */
    ArrayList<Message> readFile(String filePath) throws IOException;
}
