package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.Property;

public abstract class Calculator {
    double sum;
    int count;

    public abstract void sumAndCountMetric(Property property);

    public double average() {
        return sum / count;
    }
}
