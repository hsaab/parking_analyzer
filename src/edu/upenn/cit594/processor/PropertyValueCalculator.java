package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public class PropertyValueCalculator extends Calculator {
    public void sumAndCountMetric(Property property) {
        this.count++;
        this.sum += property.marketValue;
    }
}
