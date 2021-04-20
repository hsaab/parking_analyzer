package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.data.ParkingViolation;
import edu.upenn.cit594.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ParkingViolationDelimitedReader extends DelimitedReader<List> {
    public ParkingViolationDelimitedReader(String fileName) {
        super(fileName);
    }

    @Override
    public List createDataStore(BufferedReader br) {
        String header = null;
        String line = "";
        String splitBy = ","; // could add more complex regex here to catch other cases
        List<ParkingViolation> parkingViolationList = new ArrayList<>();

        try {
            header = br.readLine();

            while ((line = br.readLine()) != null) {
                ParkingViolation parkingViolation = createParkingViolation(line, splitBy);

                parkingViolationList.add(parkingViolation);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return parkingViolationList;
    }

    public ParkingViolation createParkingViolation(String line, String splitBy) {
        String[] array = line.split(splitBy);

        Date date = Utils.getDateTime(array[0]);
        Double fine = Double.parseDouble(array[1]);
        String plateId = array[3];
        String violation = array[2];
        String state = array[4];
        String ticketNumber = array[5];
        String zipcode = array[6];

        return new ParkingViolation(date, fine, plateId, violation, state, ticketNumber, zipcode);
    }
}
