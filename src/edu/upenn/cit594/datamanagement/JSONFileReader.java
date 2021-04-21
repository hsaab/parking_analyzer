package edu.upenn.cit594.datamanagement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public abstract class JSONFileReader<T> extends FileReader<T> {
    public JSONFileReader(String fileName) {
        super(fileName);
    }

    public T read() {
        Object obj = null;

        try {
            obj = new JSONParser().parse(new java.io.FileReader(this.fileName));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONArray JSONArray = (JSONArray) obj;

        Iterator<Map.Entry> iterator = JSONArray.iterator();

        while (iterator.hasNext()) {
            JSONObject rawParkingViolation = (JSONObject) iterator.next();

            updateDataStore(rawParkingViolation);
        }

        return dataStore;
    }

    public abstract void updateDataStore(JSONObject jsonObject);
}
