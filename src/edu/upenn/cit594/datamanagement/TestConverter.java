package edu.upenn.cit594.datamanagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestConverter extends DelimitedFileConverter<String> {
    public TestConverter(DelimitedFileReader reader, String inlineDelimiter) {
        super(reader, inlineDelimiter);
    }
    @Override
    public List<String> splitLine(String line) {
        if (line == null) return null;
        List<String> output = new ArrayList<>();
        
        if (line.startsWith(",")) {
            output.add("");
        }
        
        Pattern pattern = Pattern.compile("\\s*\"[^\"]*\"\\s*|[^,]+|(?=,(,|$))");
        pattern = Pattern.compile("\\s*\"[^\"]*\"\\s*|[^"+ "," + "]+" +
                "|" + "," + "(" + "," + "|$)");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String result = matcher.group();
            if (result.equals(",,")) {
                result = "";
            }
            output.add(result.trim());
        }
        return output;
    }
    
    @Override
    public String convert(String toParse) {
        return null;
    }
    
    @Override
    public Map<String,String> convertToMap() {
        return null;
    }
}
