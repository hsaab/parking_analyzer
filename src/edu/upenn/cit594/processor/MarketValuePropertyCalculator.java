package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public class MarketValuePropertyCalculator extends PropertyCalculator {
    public void sumAndCountMetric(Property property) {
        this.count++;
        this.sum += property.marketValue;
    }
}
