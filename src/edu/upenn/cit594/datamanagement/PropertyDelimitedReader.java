package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.Property;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PropertyDelimitedReader extends DelimitedReader<List> {
    public PropertyDelimitedReader(String fileName) {
        super(fileName);
    }

    @Override
    public List createDataStore(BufferedReader br) {
        String header = null;
        String line = "";
        String splitBy = ","; // could add more complex regex here to catch other cases
        List<Property> propertyList = new ArrayList<>();

        try {
            header = br.readLine();

            List<String> list = Arrays.asList(header.split(","));

            int zipcodeIndex = list.indexOf("zip_code");
            int totalLivableAreaIndex = list.indexOf("total_livable_area");
            int marketValueIndex = list.indexOf("market_value");

            while ((line = br.readLine()) != null) {
                Property property = createProperty(line, splitBy, marketValueIndex, totalLivableAreaIndex, zipcodeIndex);

                propertyList.add(property);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return propertyList;
    }

    private Property createProperty(String line, String splitBy, int marketValueIndex, int totalLivableAreaIndex, int zipcodeIndex) {
        String[] array = line.split(splitBy);

        return new Property(
                Double.parseDouble(array[marketValueIndex]),
                Double.parseDouble(array[totalLivableAreaIndex]),
                array[zipcodeIndex]
        );
    }
}
