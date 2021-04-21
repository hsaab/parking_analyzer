package edu.upenn.cit594.data;

import java.util.Objects;

public class Area {
    private String zipcode;
    private long population;
    
    public Area(String zipcode, long population) {
        this.zipcode = zipcode;
        this.population = population;
    }
    
    public String getZipcode() {
        return zipcode;
    }
    
    public long getPopulation() {
        return population;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return population == area.population && Objects.equals(zipcode, area.zipcode);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(zipcode, population);
    }
    
    @Override
    public String toString() {
        return "Area{" +
                "zipcode='" + zipcode + '\'' +
                ", population=" + population +
                '}';
    }
    

}
