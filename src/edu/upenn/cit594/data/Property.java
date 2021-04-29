package edu.upenn.cit594.data;

import java.util.Objects;

// Represents a property in a zipcode with a market value and livable area.
public class Property {
    private double marketValue;
    private double totalLivableArea;
    private String zipcode;
    
    /**
     * Consturctor
     * @param marketValue the market value of this property in US dollars
     * @param totalLivableArea the livable area of this property in square feet
     * @param zipcode the zipcode this property is in
     */
    public Property(Double marketValue, Double totalLivableArea, String zipcode) {
        this.marketValue = marketValue;
        this.totalLivableArea = totalLivableArea;
        this.zipcode = zipcode;
    }
    
    /**
     * Get the market value of this property in US dollars
     * @return the market value of this property in US dollars
     */
    public double getMarketValue() {
        return marketValue;
    }
    
    /**
     * Get the livable area of this property in square feet
     * @return the livable area of this property in square feet
     */
    public Double getTotalLivableArea() {
        return totalLivableArea;
    }
    
    /**
     * Get the zipcode this property is in
     * @return the zipcode this property is in
     */
    public String getZipcode() {
        return zipcode;
    }
    
    @Override
    public String toString() {
        return "Property{" +
                "marketValue=" + getMarketValue() +
                ", totalLivableArea=" + getTotalLivableArea() +
                ", zipcode='" + getZipcode() + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Double.compare(property.getMarketValue(), getMarketValue()) == 0 && Double.compare(property.getTotalLivableArea(), getTotalLivableArea()) == 0 && Objects.equals(getZipcode(), property.getZipcode());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(getMarketValue(), getTotalLivableArea(), getZipcode());
    }
}