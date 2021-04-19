package edu.upenn.cit594.datamanagement;

import java.util.Arrays;
import java.util.List;

public abstract class DelimitedFileConverter<U> extends AbstractConverter<String,U> {
    private String inlineDelimiter;
    protected List<String> headers;
    
    /**
     * Constructor
     * @param reader String reader to convert
     * @param inlineDelimiter delimiter to use when converting one line to its distinct values
     */
    public DelimitedFileConverter(DelimitedFileReader reader, String inlineDelimiter) {
        super(reader);
        this.inlineDelimiter = inlineDelimiter;
        headers = splitLine(reader.getHeaderLine());
    }
    
    /**
     * Splits line into array of values based on inlineDelimiter.
     * @param line the line to split
     * @return the array of values based on delimiter
     */
    protected List<String> splitLine(String line) {
        if (line == null) return null;
        return Arrays.asList(line.split(inlineDelimiter));
    }
}
