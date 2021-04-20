package edu.upenn.cit594.data;

import java.util.Date;

public class ParkingViolation {
    Date date;
    Double fine;
    String plateId;
    String violation;
    String state;
    String ticketNumber;
    String zipcode;

    public ParkingViolation(Date date, Double fine, String plateId, String violation,
                            String state, String ticketNumber, String zipcode) {
        this.date = date;
        this.fine = fine;
        this.plateId = plateId;
        this.violation = violation;
        this.state = state;
        this.ticketNumber = ticketNumber;
        this.zipcode = zipcode;
    }

    public ParkingViolation(Double fine, String stateOfVehiclePlate, String zipcode) {
        this.fine = fine;
        this.state = state;
        this.zipcode = zipcode;
    }


    @Override
    public String toString() {
        return "ParkingViolation{" +
                "fine=" + fine +
                ", stateOfVehiclePlate='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}