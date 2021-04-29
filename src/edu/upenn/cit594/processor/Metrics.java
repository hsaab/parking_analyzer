package edu.upenn.cit594.processor;

public abstract class Metrics<T> {
    double sum;
    int count;
    
    public Metrics() {
        this.sum = 0;
        this.count = 0;
    }
    
    public Metrics(double sum, int count) {
        this.sum = sum;
        this.count = count;
    }

    public Metrics(T firstValue) {
        this.sumAndCountMetric(firstValue);
    }

    public abstract void sumAndCountMetric(T model);

    public double average() {
        return sum / count;
    }
}
