package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public abstract class DelimitedFileReader<T> extends FileReader<T> {
    boolean hasHeaders;
    List<String> headerList;
    Tokenizer tokenizer;

    public DelimitedFileReader(String fileName, boolean hasHeaders, String delimitBy) {
        super(fileName);
        this.hasHeaders = hasHeaders;

        this.tokenizer = new Tokenizer(delimitBy);
    }

    public T read() {
        String line = null;

        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(this.fileName));

            if(this.hasHeaders) {
                line = br.readLine();
                this.headerList = this.tokenizer.split(line);
                setHeaderIndices();
            }

            while((line = br.readLine()) != null) {
                List<String> dataList = this.tokenizer.split(line);

                updateDataStore(dataList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataStore;
    }

    public abstract void updateDataStore(List<String> dataArray);
    public abstract void setHeaderIndices();
}
