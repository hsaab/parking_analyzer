package edu.upenn.cit594.data;

import java.util.Objects;

// Represents an area in the U.S. as defined by it's zipcode and population with additional statistics.
public class Area {
    private String zipcode;
    private double population;
    private int fineCount;
    private double marketValuePerCapita;
    
    /**
     * Constructor
     * @param zipcode zipcode of this Area
     * @param population population of this Area
     */
    public Area(String zipcode, double population) {
        this.zipcode = zipcode;
        this.population = population;
    }
    
    /**
     * Set the number of fines in this area.
     * @param fineCount the number of fines
     */
    public void setFineCount(int fineCount) {
        this.fineCount = fineCount;
    }
    
    /**
     * Set the market value per capita in this area.
     * @param marketValuePerCapita the market value per person in this area
     */
    public void setMarketValuePerCapita(double marketValuePerCapita) {
        this.marketValuePerCapita = marketValuePerCapita;
    }
    
    /**
     * Get the zipcode of this area
     * @return zipcode of this area.
     */
    public String getZipcode() {
        return zipcode;
    }
    
    /**
     * Get the population of this area, as defined by number of residents.
     * @return number of residents in this area
     */
    public double getPopulation() {
        return population;
    }
    
    /**
     * Get the number of fines issued in this area.
     * @return the count of fines in this area
     */
    public int getFineCount() {
        return fineCount;
    }
    
    /**
     * Get the market value of properties per resident in this area
     * @return the market value per capita
     */
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
