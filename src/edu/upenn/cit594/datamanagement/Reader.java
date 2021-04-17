package edu.upenn.cit594.datamanagement;

import java.util.List;

public interface Reader<T> {
    public List<T> readAll();
}
