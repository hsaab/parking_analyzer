package edu.upenn.cit594.data;

import java.util.Objects;

public class Property {
    private double marketValue;
    private double totalLivableArea;
    private String zipcode;

    public Property(Double marketValue, Double totalLivableArea, String zipcode) {
        this.marketValue = marketValue;
        this.totalLivableArea = totalLivableArea;
        this.zipcode = zipcode;
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
    
    public Double getMarketValue() {
        return marketValue;
    }
    
    public Double getTotalLivableArea() {
        return totalLivableArea;
    }
    
    public String getZipcode() {
        return zipcode;
    }
}