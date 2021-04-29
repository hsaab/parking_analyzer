package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.io.FileNotFoundException;

// A base reader to extend from that converts a file into domain objects. Handles opening the file.
public abstract class FileReader<T> implements Reader<T> {
    private String fileName;
    protected DataStore<T> dataStore;
    
    /**
     * Constuctor
     * @param fileName the name of the file to read
     * @throws FileNotFoundException if the file at the provided name cannot be read or opened.
     */
    public FileReader(String fileName) throws FileNotFoundException {
        this.fileName = fileName;

        File file = new File(fileName);
        if (!file.exists() || !file.canRead()) {
            throw new FileNotFoundException("File: " + this.getFileName() + " does not exist or cannot be read.");
        }
    }
    
    /**
     * Reads the contents of the file associated with this reader and converts to DataStore of the same type.
     * When called again will read the file again.
     * @return the DataStore of the data in this file
     */
    @Override
    public abstract DataStore<T> read();
    
    /**
     * Initializes the data store. Must be called before calling updateDataStore.
     */
    public abstract void initializeDataStore();
    
    /**
     * Get the file name of this reader
     * @return the name of the file of this reader
     */
    public String getFileName() {
        return fileName;
    }
}