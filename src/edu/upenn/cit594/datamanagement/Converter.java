package edu.upenn.cit594.datamanagement;

public interface Converter<T,U> {
    public U convert(T toParse);
}
