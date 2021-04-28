package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public class MarketValueMetrics extends Metrics<Property> {
    public MarketValueMetrics() {
        super();
    }
    public MarketValueMetrics(Property firstValue) {
        super(firstValue);
    }

    public void sumAndCountMetric(Property property) {
        this.count++;
        this.sum += property.marketValue;
    }
}
