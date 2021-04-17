package edu.upenn.cit594.datamanagement;

import org.json.simple.*;

// Converter for a Reader of type JSONObject
public abstract class JSONConverter<U> extends AbstractConverter<JSONObject,U> {
    public JSONConverter(Reader<JSONObject> reader) {
        super(reader);
    }
}

