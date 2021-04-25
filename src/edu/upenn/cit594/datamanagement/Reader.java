package edu.upenn.cit594.datamanagement;

public interface Reader<T> {
    public DataStore<T> read();
}
