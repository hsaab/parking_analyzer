package edu.upenn.cit594.datamanagement;

import java.util.ArrayList;
import java.util.List;

// Converter that takes in a Reader of the same input type
// and converts from that type (eg JSONObject, String line) into domain type
// Base converter class to extend from.
public abstract class AbstractConverter<T,U> implements Converter<T,U> {
    private Reader<T> reader;
    
    /**
     * Constructor
     * @param reader reader to convert contents from
     */
    public AbstractConverter(Reader<T> reader) {
        this.reader = reader;
    }
    
    /**
     * Converts this converter's reader into a list of output type
     * @return a list of output type
     */
    public List<U> convert() {
        if (reader == null) throw new IllegalStateException("reader cannot be null");
        return convert(reader.readAll());
    }
    
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
            if (convert(object) != null) {
                list.add(convert(object));
            }
        }
        
        return list;
    }
    
    @Override
    public abstract U convert(T toParse);
}

