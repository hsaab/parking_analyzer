package edu.upenn.cit594.processor;

public abstract class Metrics<T> {
    double sum;
    int count;

    /**
     * Constructor
     * initializes sum and count to 0
     */
    public Metrics() {
        this.sum = 0;
        this.count = 0;
    }

    /**
     * Constructor
     * takes a sum and count to set
     * @param sum
     * @param count
     */
    public Metrics(double sum, int count) {
        this.sum = sum;
        this.count = count;
    }

    /**
     * Constructor
     * Take a first value to pass in to sum and count
     * @param firstValue
     */
    public Metrics(T firstValue) {
        this.sumAndCountMetric(firstValue);
    }

    /**
     * Abstract method to implement in child classes
     * @param model
     */
    public abstract void sumAndCountMetric(T model);

    /**
     * Average method
     * @return the average as a couble
     */
    public double average() {
        return sum / count;
    }
}
