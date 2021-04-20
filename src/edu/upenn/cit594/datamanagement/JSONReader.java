package edu.upenn.cit594.datamanagement;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public abstract class JSONReader<T> extends Reader<T> {
    public JSONReader(String fileName) {
        super(fileName);
    }

    public T read() {
        Object obj = null;

        try {
            obj = new JSONParser().parse(new FileReader(this.fileName));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray JSONArray = (JSONArray) obj;

        Iterator<Map.Entry> iterator = JSONArray.iterator();

        return createDataStore(iterator);
    }

    public abstract T createDataStore(Iterator iterator);
}
