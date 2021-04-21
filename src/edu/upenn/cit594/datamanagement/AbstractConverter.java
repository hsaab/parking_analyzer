package edu.upenn.cit594.datamanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Converter that takes in a Reader of the same input type
// and converts from that type (eg JSONObject, String line) into domain type
// Base converter class to extend from.
public abstract class AbstractConverter<T,U> implements Converter<T,U> {
    private Reader<T> reader;
    private List<U> convertedContents;
    
    /**
     * Constructor
     * @param reader reader to convert contents from
     */
    public AbstractConverter(Reader<T> reader) {
        this.reader = reader;
    }
    
    public Reader<T> getReader() {
        return reader;
    }
    
    /**
     * Converts this converter's reader into a list of objects of output type
     * @return a list of output type
     */
    public List<U> convertToList() {
        if (convertedContents == null) {
            if (reader == null) throw new IllegalStateException("reader cannot be null");
            convertedContents = convert(reader.readAll());
        }
        
        return convertedContents;
    }
    
    /**
     * Converts this converter's reader into a map of objects to objects of output type
     * @return a map of object to output type
     */
    public abstract Map<?,U> convertToMap();
    
    /**
     * Converts a provided reader of input type into a list of output type.
     * @param toParse the reader of input type to parse
     * @return a list of output type
     */
    public List<U> convert(Reader<T> toParse) {
        return convert(toParse.readAll());
    }
    
    /**
     * Converts a provided list of input type into a list of output type.
     * Does not include any objects of input type that are converted to null.
     * @param toParse a list of input type to parse
     * @return a list of output type
     */
    public List<U> convert(List<T> toParse) {
        List<U> list = new ArrayList<>();
        for (T object : toParse) {
            U convertedObject = convert(object);
            if (convertedObject != null) {
                list.add(convertedObject);
            }
        }
        
        return list;
    }
    
    @Override
    public abstract U convert(T toParse);
}

