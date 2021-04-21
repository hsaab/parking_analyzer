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
        matchPattern = Pattern.compile( // matches with...
                        "\"[^\"]*\"" + inlineDelimiter // double quotes followed by the inlineDelimiter ("Shapiro, Eric", => "Shapiro, Eric",)
                                + "|" + "[^"+ inlineDelimiter + "]+" // anything that isn't the inlineDelimiter (test, => test)
                                + "|" + "^" + inlineDelimiter // a starting inlineDelimiter (, => ,) this is for when start of line has no value
                                + "|" + "(?=" + inlineDelimiter + "(" + inlineDelimiter + "|$))" // the inlineDelimiter followed by the inlineDelimiter or the end of the line (,, => "")
        );
    
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
                
        Matcher matcher = matchPattern.matcher(line);
        while (matcher.find()) {
            String result = matcher.group();
            
            if (result.startsWith("\"")) {
                result = result.substring(1);
            }
            if (result.endsWith(",")) {
                result = result.substring(0, result.length() - 1);
            }
            if (result.endsWith("\"")) {
                result = result.substring(0, result.length() - 1);
            }
            output.add(result.trim());
        }
        return output;
    }
    
    /**
     * Splits line into list of string tokens based on inlineDelimiter.
     * @param line the line to split
     * @return the tokens that were found
     */
    protected List<String> tokenize(String line) {
        if (line == null) return null;
        List<String> output = new ArrayList<>();
        Matcher matcher = matchPattern.matcher(line);
        // find all matches in provided line that correspond to matchPattern and add to output list
        while (matcher.find()) {
            String result = matcher.group();
            // remove any leading or trailing quotes, inlineDelimiter and whitespace
            if (result.startsWith("\"")) { result = result.substring(1); }
            if (result.endsWith(inlineDelimiter)) { result = result.substring(0, result.length() - 1); }
            if (result.endsWith("\"")) { result = result.substring(0, result.length() - 1); }
            
            output.add(result.trim());
        }
        return output;
    }
}
