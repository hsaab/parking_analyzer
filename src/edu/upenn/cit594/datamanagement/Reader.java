package edu.upenn.cit594.datamanagement;

// A reader of any data that will convert the contents of the source (database, file) into a DataStore.
public interface Reader<T> {
    public DataStore<T> read();
}
