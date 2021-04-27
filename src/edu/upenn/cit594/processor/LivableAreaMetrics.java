package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public class LivableAreaMetrics extends Metrics<Property> {
    static String className = "LivableAreaMetrics";

    public LivableAreaMetrics() {
        super();
    }
    
    LivableAreaMetrics(Property firstValue) {
        super(firstValue);
    }

    public void sumAndCountMetric(Property property) {
        this.count++;
        this.sum += property.totalLivableArea;
    }
}
