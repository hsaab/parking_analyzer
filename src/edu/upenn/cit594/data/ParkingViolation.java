package edu.upenn.cit594.data;

import java.util.Date;

// Represents a parking violation, including the amount of the fine, the state of the car being issued the fine and
// and the zipcode where the fine took place, among other things.
public class ParkingViolation {
    private Date date;
    private double fine;
    private String violation;
    private String plateId;
    private String state;
    public String ticketNumber;
    public String zipcode;
    
    /**
     * Constructor
     * @param date date violation was issued
     * @param fine the amount of the fine issued in US dollars
     * @param violation the reason for the violation
     * @param plateId the plate of the car being issued the violation
     * @param state the state of the car being issued the violation
     * @param ticketNumber the unique number of the violation
     * @param zipcode the zipcode where the violation was issued
     */
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

    @Override
    public String toString() {
        return "ParkingViolation{" +
                "fine=" + getFine() +
                ", stateOfVehiclePlate='" + getState() + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
    
    /**
     * Get the date the violation was issued.
     * @return the date the violation was issued
     */
    public Date getDate() {
        return date;
    }
    
    /**
     * Get the fine issued in number of dollars.
     * @return the number of US dollars of the fine
     */
    public double getFine() {
        return fine;
    }
    
    /**
     * Get the stated reason for the violation
     * @return the reason for the violation
     */
    public String getViolation() {
        return violation;
    }
    
    /**
     * Get the plate of the car issued this violation.
     * @return the plate of the car issued this violation
     */
    public String getPlateId() {
        return plateId;
    }
    
    /**
     * Get the state of the car issued this violation
     * @return the state of the car that was issued this violation
     */
    public String getState() {
        return state;
    }
}