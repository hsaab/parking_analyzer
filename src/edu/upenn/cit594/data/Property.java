package edu.upenn.cit594.data;

import java.util.Objects;

public class Property {
    public Double marketValue;
    public Double totalLivableArea;
    public String zipcode;

    public Property(Double marketValue, Double totalLivableArea, String zipcode) {
        this.marketValue = marketValue;
        this.totalLivableArea = totalLivableArea;
        this.zipcode = zipcode;
        System.out.println("Property{" +
                "marketValue=" + marketValue +
                ", totalLivableArea=" + totalLivableArea +
                ", zipcode='" + zipcode + '\'' +
                '}');
    }

    @Override
    public String toString() {
        return "Property{" +
                "marketValue=" + marketValue +
                ", totalLivableArea=" + totalLivableArea +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Double.compare(property.marketValue, marketValue) == 0 && Double.compare(property.totalLivableArea, totalLivableArea) == 0 && Objects.equals(zipcode, property.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marketValue, totalLivableArea, zipcode);
    }
}