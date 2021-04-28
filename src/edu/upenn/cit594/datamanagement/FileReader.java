package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;

public abstract class FileReader<T> implements Reader<T> {
    private String fileName;
    protected DataStore<T> dataStore;

    public FileReader(String fileName) throws FileNotFoundException {
        this.fileName = fileName;

        File file = new File(fileName);

        if (!file.exists() || !file.canRead()) {
            throw new FileNotFoundException("File: " + this.getFileName() + " does not exist or cannot be read.");
        }
    }
    
    @Override
    public abstract DataStore<T> read();
    public abstract void initializeDataStore();
    
    public String getFileName() {
        return fileName;
    }
}