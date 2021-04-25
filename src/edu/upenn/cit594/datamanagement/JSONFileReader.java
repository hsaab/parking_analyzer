package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.logging.Logger;
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

    public DataStore<T> read() {
        Object obj = null;
        initializeDataStore(); // sets/resets dataStore to empty version of T so updateDataStore works
        Logger.getLogger().log(super.fileName);
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
    
    @Override
    public abstract void initializeDataStore();
    public abstract void updateDataStore(JSONObject jsonObject);
}
