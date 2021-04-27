package edu.upenn.cit594.data;

public class Area {
    public final String zipcode;
    public final int population;
    public int fineCount;
    public double marketValuePerCapita;

    public Area(String zipcode, int population) {
        this.zipcode = zipcode;
        this.population = population;
    }

    public void setFineCount(int fineCount) {
        this.fineCount = fineCount;
    }

    public void setMarketValuePerCapita(double marketValuePerCapita) {
        this.marketValuePerCapita = marketValuePerCapita;
    }
}
