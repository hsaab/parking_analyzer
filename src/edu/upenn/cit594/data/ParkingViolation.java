package edu.upenn.cit594.data;

import java.util.Date;

public class ParkingViolation {
    Date timestamp;
    Double fine;
    String vehicleId;
    String description;
    String stateOfVehiclePlate;
    String violationId;
    String zipcode;
    
    public ParkingViolation(Date timestamp, Double fine, String vehicleId, String description,
                            String stateOfVehiclePlate, String violationId, String zipcode) {
        this.timestamp = timestamp;
        this.fine = fine;
        this.vehicleId = vehicleId;
        this.description = description;
        this.stateOfVehiclePlate = stateOfVehiclePlate;
        this.violationId = violationId;
        this.zipcode = zipcode;
    }
    
    public ParkingViolation(Double fine, String stateOfVehiclePlate, String zipcode) {
        this.fine = fine;
        this.stateOfVehiclePlate = stateOfVehiclePlate;
        this.zipcode = zipcode;
    }
    
    public String getViolationId() {
        return violationId;
    }
    
    
    @Override
    public String toString() {
        return "ParkingViolation{" +
                "fine=" + fine +
                ", stateOfVehiclePlate='" + stateOfVehiclePlate + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
