package edu.upenn.cit594.datamanagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DelimitedFileConverter<U> extends AbstractConverter<String,U> {
    private String inlineDelimiter;
    protected List<String> headers;
    private Pattern matchPattern;
    
    /**
     * Constructor
     * @param reader String reader to convert
     * @param inlineDelimiter delimiter to use when converting one line to its distinct values
     */
    public DelimitedFileConverter(DelimitedFileReader reader, String inlineDelimiter) {
        super(reader);
        this.inlineDelimiter = inlineDelimiter;
        matchPattern = Pattern.compile("\"[^\"]*\"" + inlineDelimiter + "|[^"+ inlineDelimiter + "]+" +
                "|(?=" + inlineDelimiter + "(" + inlineDelimiter + "|$))");
        headers = splitLine(reader.getHeaderLine());
    }
    
    /**
     * Splits line into array of values based on inlineDelimiter.
     * @param line the line to split
     * @return the array of values based on delimiter
     */
    protected List<String> splitLine(String line) {
        if (line == null) return null;
        List<String> output = new ArrayList<>();
    
        if (line.startsWith(inlineDelimiter)) {
            output.add("");
        }
                
        Matcher matcher = matchPattern.matcher(line);
        while (matcher.find()) {
            String result = matcher.group();
            if (result.startsWith("\"")) { // removes starting quote
                result = result.substring(1);
            }
            if (result.endsWith(",")) { // removes ending comma which is captured
                result = result.substring(0, result.length() - 1);
            }
            if (result.endsWith("\"")) { // removes  ending quote which is captured
                result = result.substring(0, result.length() - 1);
            }
            output.add(result.trim());
        }
        return output;
    }
    
    /**
     * Splits line into tokens based on inlineDelimiter.
     * @param line the line to split
     * @return the tokens that are part of the line
     */
    protected List<String> tokenize(String line) {
        if (line == null) return null;
        List<String> output = new ArrayList<>();
        
        if (line.startsWith(inlineDelimiter)) {
            output.add("");
        }
        
        Matcher matcher = matchPattern.matcher(line);
        while (matcher.find()) {
            String result = matcher.group();
            if (result.startsWith("\"")) { // removes starting quote
                result = result.substring(1);
            }
            if (result.endsWith(",")) { // removes ending comma which is captured
                result = result.substring(0, result.length() - 1);
            }
            if (result.endsWith("\"")) { // removes  ending quote which is captured
                result = result.substring(0, result.length() - 1);
            }
            output.add(result.trim());
        }
        return output;
    }
}
