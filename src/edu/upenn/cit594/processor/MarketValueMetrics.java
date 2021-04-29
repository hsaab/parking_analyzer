package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public class MarketValueMetrics extends Metrics<Property> {
    /**
     * Constructor
     */
    public MarketValueMetrics() {
        super();
    }

    /**
     * Constructor
     * @param firstValue the first value to sum and count
     */
    public MarketValueMetrics(Property firstValue) {
        super(firstValue);
    }

    /**
     * Keeps a count and sum of properties with market value attribute
     * @param property
     */
    @Override
    public void sumAndCountMetric(Property property) {
        if (Double.isNaN(property.getMarketValue())) return;
        this.count++;
        this.sum += property.getMarketValue();
    }
}
