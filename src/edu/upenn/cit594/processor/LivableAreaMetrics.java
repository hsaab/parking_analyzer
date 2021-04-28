package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public class LivableAreaMetrics extends Metrics<Property> {
    public LivableAreaMetrics() {
        super();
    }
    
    public LivableAreaMetrics(Property firstValue) {
        super(firstValue);
    }

    public void sumAndCountMetric(Property property) {
        if (Double.isNaN(property.getTotalLivableArea())) return;
        this.count++;
        this.sum += property.getTotalLivableArea();
    }
}
