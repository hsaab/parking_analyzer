package edu.upenn.cit594.datamanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Used by delimited file readers to convert a String line of values into a list of values
// that the reader can then parse into a domain object.
public class Tokenizer {
    private String inlineDelimiter;
    private Pattern matchPattern;
    
    /**
     * Constructor
     * @param inlineDelimiter the inline delimiter separating values within each line
     */
    public Tokenizer(String inlineDelimiter) {
        this.inlineDelimiter = inlineDelimiter;
        this.matchPattern = createPattern(inlineDelimiter);
    }

    /**
     * Splits line into list of string tokens based on inlineDelimiter.
     * @param line the line to split
     * @return the tokens that were found
     */
    public List<String> split(String line) {
        if (line == null) return null;
        List<String> output = new ArrayList<>();
        Matcher matcher = this.matchPattern.matcher(line);
        // find all matches in provided line that correspond to matchPattern and add to output list
        while (matcher.find()) {
            String result = matcher.group();
            // remove any leading or trailing quotes, inlineDelimiter and whitespace
            if (result.startsWith("\"")) { result = result.substring(1); }
            if (result.endsWith(this.inlineDelimiter)) { result = result.substring(0, result.length() - 1); }
            if (result.endsWith("\"")) { result = result.substring(0, result.length() - 1); }

            output.add(result.trim());
        }
        return output;
    }
    
    /**
     * Creates a Pattern that when used with a Matcher can break a String into separate values based on provided delimiter.
     * @param inlineDelimiter the delimiter separating values in lines
     * @return
     */
    private Pattern createPattern(String inlineDelimiter) {
       return Pattern.compile( // matches with...
                "\"[^\"]*\"" + inlineDelimiter // double quotes followed by the inlineDelimiter ("Shapiro, Eric", => "Shapiro, Eric",)
                        + "|" + "[^"+ inlineDelimiter + "]+" // anything that isn't the inlineDelimiter (test, => test)
                        + "|" + "^" + inlineDelimiter // a starting inlineDelimiter (, => ,) this is for when start of line has no value
                        + "|" + "(?=" + inlineDelimiter + "(" + inlineDelimiter + "|$))" // the inlineDelimiter followed by the inlineDelimiter or the end of the line (,, => "")
        );
    }
}
