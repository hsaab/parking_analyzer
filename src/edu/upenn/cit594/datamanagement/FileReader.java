package edu.upenn.cit594.datamanagement;

import java.io.File;

public abstract class FileReader<T> implements Reader<T> {
    public String fileName;
    protected DataStore<T> dataStore;

    public FileReader(String fileName) throws Error {
        this.fileName = fileName;

        File file = new File(fileName);

        if (!file.exists() || !file.canRead()) {
            throw new Error("File: " + this.fileName + " does not exist or cannot be read.");
        }
    }
    
    public abstract DataStore<T> read();
    public abstract void initializeDataStore();
}