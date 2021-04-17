package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

// Opens text file and reads contents into list of strings, one for each line
public class DelimitedFileReader implements Reader<String> {
    private String filename;
    private Scanner in;
    
    /**
     * Constructor
     * @param filename name of file to read
     * @throws IllegalStateException
     */
    public DelimitedFileReader(String filename) throws IllegalStateException {
        this.filename = filename;
        try {
            in = new Scanner(new File(filename));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    @Override
    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        while (in.hasNextLine()) {
            lines.add(in.nextLine());
        }
        
        return lines;
    }
    
    
}
