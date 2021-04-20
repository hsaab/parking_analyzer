package edu.upenn.cit594.datamanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class DelimitedReader<T> extends Reader<T> {
    public DelimitedReader(String fileName) {
        super(fileName);
    }

    public T read() {
        FileReader input = null;

        try {
            input = new FileReader(this.fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader br = new BufferedReader(input);

        return createDataStore(br);
    }

    public abstract T createDataStore(BufferedReader br);
}
