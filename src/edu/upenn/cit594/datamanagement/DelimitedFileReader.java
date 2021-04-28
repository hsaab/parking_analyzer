package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.logging.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public abstract class DelimitedFileReader<T> extends FileReader<T> {
    protected List<String> headerList;
    private boolean hasHeaders;
    private Tokenizer tokenizer;

    public DelimitedFileReader(String fileName, boolean hasHeaders, String delimitBy) throws FileNotFoundException {
        super(fileName);
        this.hasHeaders = hasHeaders;

        this.tokenizer = new Tokenizer(delimitBy);
    }

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
    
    public abstract void updateDataStore(List<String> dataArray);
    public abstract void setHeaderIndices();
}
