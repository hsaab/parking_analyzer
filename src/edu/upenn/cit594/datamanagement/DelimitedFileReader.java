package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.logging.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

// A file reader that can read delimited files. Has functionality to process a file with a header row (or not)
// and various delimiters. Base DelimitedFileReader class to extend from.
public abstract class DelimitedFileReader<T> extends FileReader<T> {
    protected List<String> headerList;
    private boolean hasHeaders;
    private Tokenizer tokenizer;
    
    /**
     * Constuctor
     * @param fileName the name of the file to read
     * @param hasHeaders if the file has a header row (must be first row)
     * @param delimitBy the delimiter that separates the values in this file
     * @throws FileNotFoundException if the file from filename cannot be read or opened
     */
    public DelimitedFileReader(String fileName, boolean hasHeaders, String delimitBy) throws FileNotFoundException {
        super(fileName);
        this.hasHeaders = hasHeaders;
        this.tokenizer = new Tokenizer(delimitBy);
    }
    
    @Override
    public DataStore<T> read() {
        String line = null;
        initializeDataStore(); // sets/resets dataStore to empty version of T so updateDataStore works
        Logger.getLogger().log(super.getFileName());
        try (BufferedReader br = new BufferedReader(new java.io.FileReader(this.getFileName()))) {
            if (this.hasHeaders) {
                line = br.readLine();
                this.headerList = this.tokenizer.split(line);
                setHeaderIndices();
            }

            while ((line = br.readLine()) != null) {
                List<String> dataList = this.tokenizer.split(line);

                updateDataStore(dataList);
            }
        } catch (IOException e) {
            throw new RuntimeException("IO Exception in DelimitedFileReader.");
        }

        return dataStore;
    }

    @Override
    public abstract void initializeDataStore();
    
    /**
     * Updates this dataStore, converting the list being passed in to a domain object
     * @param dataArray the list of values to convert to domain object.
     */
    public abstract void updateDataStore(List<String> dataArray);
    
    /**
     * Set the indices of the headers in this file.
     * Use if you want to set headers based on column name (regardless of column position).
     */
    public abstract void setHeaderIndices();
}
