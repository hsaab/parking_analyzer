package edu.upenn.cit594.data;

public class ParkingViolation {
    int zipcode;
    String reason;
    
    public ParkingViolation(int zipcode, String reason) {
        this.zipcode = zipcode;
        this.reason = reason;
    }
    
    @Override
    public String toString() {
        return "ParkingViolation{" +
                "zipcode=" + zipcode +
                ", reason='" + reason + '\'' +
                '}';
    }
}
