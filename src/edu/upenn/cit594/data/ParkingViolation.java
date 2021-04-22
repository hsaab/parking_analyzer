package edu.upenn.cit594.data;

import java.util.Date;

public class ParkingViolation {
    public Date date;
    public Double fine;
    public String violation;
    public String plateId;
    public String state;
    public String ticketNumber;
    public String zipcode;

    public ParkingViolation(Date date, Double fine, String violation, String plateId,
                            String state, String ticketNumber, String zipcode) {
        this.date = date;
        this.fine = fine;
        this.violation = violation;
        this.plateId = plateId;
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