package edu.upenn.cit594.data;

public class Property {
    String location;
    int numberRooms;
    
    public Property(String location, int numberRooms) {
        this.location = location;
        this.numberRooms = numberRooms;
    }
    
    @Override
    public String toString() {
        return "Property{" +
                "location='" + location + '\'' +
                ", numberRooms=" + numberRooms +
                '}';
    }
}
