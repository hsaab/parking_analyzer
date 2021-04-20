package edu.upenn.cit594.datamanagement;

import java.io.File;

public abstract class Reader<T> {
    public String fileName;

    Reader(String fileName) {
        this.fileName = fileName;

        File file = new File(fileName);

        if(!file.exists() || !file.canRead()) {
            throw new Error("File: " + this.fileName + " does not exist or cannot be read.");
        }
    }

    public abstract T read();
}