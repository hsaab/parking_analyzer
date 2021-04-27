package edu.upenn.cit594.processor;

public abstract class Metrics<T> {
    double sum;
    int count;
    
    Metrics() {
        this.sum = 0;
        this.count = 0;
    }

    Metrics(T firstValue) {
        this.sumAndCountMetric(firstValue);
    }

    public abstract void sumAndCountMetric(T model);

    public double average() {
        return sum / count;
    }
}
