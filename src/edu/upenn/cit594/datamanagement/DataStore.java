package edu.upenn.cit594.datamanagement;


// Wrapper class to store data of type T.
public class DataStore<T> {
    private T data;
    
    /**
     * Constructor
     * @param data the data to store
     */
    public DataStore(T data) {
        this.data = data;
    }
    
    /**
     * Get the data in this data store
     * @return the data in this data store
     */
    public T getData() {
        return data;
    }
}
