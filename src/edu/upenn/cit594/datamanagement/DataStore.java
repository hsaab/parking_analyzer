package edu.upenn.cit594.datamanagement;

public class DataStore<T> {
    private T data;
    
    public DataStore(T data) {
        this.data = data;
    }
    
    public T getData() {
        return data;
    }
}
