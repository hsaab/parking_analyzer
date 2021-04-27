package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ParkingViolation;

public class ParkingViolationMetrics extends Metrics<ParkingViolation> {
    public ParkingViolationMetrics() {
        super();
    }
    
    public ParkingViolationMetrics(ParkingViolation violation) {
        super(violation);
    }

    @Override
    public void sumAndCountMetric(ParkingViolation parkingViolation) {
        this.count++;
        this.sum += parkingViolation.fine;
    }
}