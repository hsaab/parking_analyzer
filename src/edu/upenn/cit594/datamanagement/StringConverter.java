package edu.upenn.cit594.datamanagement;

// Converter for a Reader of type String.
// Base class for text file Readers.
public abstract class StringConverter<U> extends AbstractConverter<String,U> {
    private String inlineDelimiter;
    //TODO: deal with headers being in different order
    //TODO: deal with skipping lines to find header row
    
    /**
     * Constructor
     * @param reader String reader to convert
     * @param inlineDelimiter delimiter to use when converting one line to its distinct values
     */
    public StringConverter(Reader<String> reader, String inlineDelimiter) {
        super(reader);
        this.inlineDelimiter = inlineDelimiter;
    }
    
    /**
     * Splits line into array of values based on inlineDelimiter.
     * @param line the line to split
     * @return the array of values based on delimiter
     */
    protected String[] splitLine(String line) {
        return line.split(inlineDelimiter);
    }
    
}

