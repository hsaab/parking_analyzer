package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public class LivableAreaMetrics extends Metrics<Property> {
    /**
     * Constructor
     */
    public LivableAreaMetrics() {
        super();
    }

    /**
     * Constructor
     * @param firstValue the first value to sum and count
     */
    public LivableAreaMetrics(Property firstValue) {
        super(firstValue);
    }

    /**
     * Keeps a count and sum of properties with livable area attribute
     * @param property
     */
    @Override
    public void sumAndCountMetric(Property property) {
        if (Double.isNaN(property.getTotalLivableArea())) return;
        this.count++;
        this.sum += property.getTotalLivableArea();
    }
}
