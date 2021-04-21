package edu.upenn.cit594;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.datamanagement.*;

import java.util.List;
import java.util.Map;

public class Tester {
    public static void main(String[] args) {
        DelimitedFileReader tester = new DelimitedFileReader("comma_quote.csv", 0);
        TestConverter testerConvert = new TestConverter(tester,",");
        List<String> lines = tester.readAll();
        for (String line : lines) {
            List<String> split = testerConvert.splitLine(line);
            System.out.println(split.size() + ": " + testerConvert.splitLine(line));
        }
    
        DelimitedFileReader areaReader = new DelimitedFileReader("population.txt", 0);
        AreaDelimitedConverter areaConverter = new AreaDelimitedConverter(areaReader);
        List<Area> areas = areaConverter.convertToList();
        Map<String, Area> map = areaConverter.convertToMap();
        System.out.println(areas.size());
        System.out.println(areas);
        
        DelimitedFileReader reader = new DelimitedFileReader("properties.csv", 1);
        PropertyCsvConverter converter = new PropertyCsvConverter(reader);
        printEntry(converter, 32);
        
        DelimitedFileReader reader_edit = new DelimitedFileReader("edit_properties.csv", 1);
        PropertyCsvConverter converter_edit = new PropertyCsvConverter(reader_edit);
        printEntry(converter_edit, 32);
        
        DelimitedFileReader violationReader = new DelimitedFileReader("parking.csv", 0);
        ParkingViolationCsvConverter violationConverter = new ParkingViolationCsvConverter(violationReader);
        printEntry(violationConverter, 0);
    }
    
    public static void printEntry(AbstractConverter<?, ?> converter, int entryNum) {
        List<?> entries = converter.convertToList();
        System.out.println(entries.get(entryNum));
    }
}
