package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public abstract class DelimitedFileReader<T> extends FileReader<T> {
    boolean hasHeaders;
    String delimitBy;

    public DelimitedFileReader(String fileName, boolean hasHeaders, String delimitBy) {
        super(fileName);
        this.hasHeaders = hasHeaders;
        this.delimitBy = delimitBy;
    }

    public T read() {
        String line = null;
        List<String> headerList = null;

        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(this.fileName));

            if(this.hasHeaders) {
                line = br.readLine();
                headerList = Arrays.asList(line.split(this.delimitBy));

                setHeaderIndices(headerList);
            }

            while((line = br.readLine()) != null) {
                List<String> dataList = Arrays.asList(line.split(this.delimitBy));

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
    public abstract void setHeaderIndices(List<String> headerList);
}
