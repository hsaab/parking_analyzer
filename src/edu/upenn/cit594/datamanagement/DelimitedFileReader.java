package edu.upenn.cit594.datamanagement;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

// Opens text file and reads contents into list of strings, one for each line
public class DelimitedFileReader implements Reader<String> {
    private String filename;
    private String header;
    private List<String> contents;
    
    /**
     * Constructor
     * @param filename name of file to read
     * @throws IllegalStateException
     */
    public DelimitedFileReader(String filename, int headerRowNumber) throws IllegalStateException {
        this.filename = filename;
        try {
            Scanner in = new Scanner(new File(filename));
            header = readHeader(in, headerRowNumber);
            contents = readContents(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    
    private String readHeader(Scanner in, int headerRowNumber) {
        String outputLine = null;
        int currentRow = 1;
        while (in.hasNextLine() && currentRow <= headerRowNumber) {
            outputLine = in.nextLine();
            currentRow++;
        }
        
        return outputLine;
    }
    
    private List<String> readContents(Scanner in) {
        List<String> lines = new ArrayList<>();
        while (in.hasNextLine()) {
            lines.add(in.nextLine());
        }
    
        return lines;
    }
    
    @Override
    public List<String> readAll() {
        return contents;
    }
    
    public String getHeaderLine() {
        return header;
    }
    
    public boolean hasHeaderLine() {
        return header != null;
    }
}
