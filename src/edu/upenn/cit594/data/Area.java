package edu.upenn.cit594.data;

import java.util.Objects;

public class Area {
    private String zipcode;
    private double population;
    private int fineCount;
    private double marketValuePerCapita;

    public Area(String zipcode, double population) {
        this.zipcode = zipcode;
        this.population = population;
    }

    public void setFineCount(int fineCount) {
        this.fineCount = fineCount;
    }

    public void setMarketValuePerCapita(double marketValuePerCapita) {
        this.marketValuePerCapita = marketValuePerCapita;
    }
    
    public String getZipcode() {
        return zipcode;
    }
    
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    
    public double getPopulation() {
        return population;
    }
    
    public void setPopulation(double population) {
        this.population = population;
    }
    
    public int getFineCount() {
        return fineCount;
    }
    
    public double getMarketValuePerCapita() {
        return marketValuePerCapita;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return Double.compare(area.getPopulation(), getPopulation()) == 0 && getFineCount() == area.getFineCount() && Double.compare(area.getMarketValuePerCapita(), getMarketValuePerCapita()) == 0 && Objects.equals(getZipcode(), area.getZipcode());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getZipcode(), getPopulation(), getFineCount(), getMarketValuePerCapita());
    }
    
    @Override
    public String toString() {
        return "Area{" +
                "zipcode='" + getZipcode() + '\'' +
                ", population=" + getPopulation() +
                ", fineCount=" + getFineCount() +
                ", marketValuePerCapita=" + getMarketValuePerCapita() +
                '}';
    }
}
