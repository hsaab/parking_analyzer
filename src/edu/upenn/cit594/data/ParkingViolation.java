package edu.upenn.cit594.data;

import java.util.Date;

public class ParkingViolation {
    private Date date;
    private double fine;
    private String violation;
    private String plateId;
    private String state;
    public String ticketNumber;
    public String zipcode;

    public ParkingViolation(Date date, double fine, String violation, String plateId,
                            String state, String ticketNumber, String zipcode) {
        this.date = date;
        this.fine = fine;
        this.violation = violation;
        this.plateId = plateId;
        this.state = state;
        this.ticketNumber = ticketNumber;
        this.zipcode = zipcode;
    }

    public ParkingViolation(double fine, String stateOfVehiclePlate, String zipcode) {
        this.fine = fine;
        this.state = getState();
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "ParkingViolation{" +
                "fine=" + getFine() +
                ", stateOfVehiclePlate='" + getState() + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
    
    public Date getDate() {
        return date;
    }
    
    public Double getFine() {
        return fine;
    }
    
    public String getViolation() {
        return violation;
    }
    
    public String getPlateId() {
        return plateId;
    }
    
    public String getState() {
        return state;
    }
}