package edu.upenn.cit594.logging;

import java.io.*;

// Logs messages to file.
// Utilizes Singleton pattern.
public class Logger {
    private static Logger instance = null;
    private static String filename = null;
    private PrintWriter out;
    
    private Logger(String filename) {
        try {
            out = new PrintWriter(new FileWriter(filename, true));
        } catch (IOException e) {
            throw new RuntimeException("IO Exception for Logger");
        }
    }
    
    
    /**
     * Get instance of Logger.
     * @return the logger
     * @throws IllegalStateException if filename hasn't been set yet using setFilename method
     */
    public static Logger getLogger() throws IllegalStateException {
        if (filename == null) {
            throw new IllegalStateException("Must set filename first");
        }
        
        if (instance == null) {
            instance = new Logger(filename);
        }
        
        return instance;
    }
    
    
    /**
     * Sets file to log to.
     * Can only be set once.
     * @param filename filename of file to log to
     * @throws IllegalStateException if this method is invoked more than once in an attempt to reset filename
     */
    public static void setFilename(String filename) throws IllegalStateException {
        if (filename == null) throw new IllegalArgumentException("Filename must be non-null.");
        if (Logger.filename != null) {
            throw new IllegalStateException("Filename is already " + filename + ". Filename can only be set once");
        }
        
        Logger.filename = filename;
    }
    
    /**
     * Log object to file
     * @param object the object to log
     */
    public void log(Object object) {
        out.println(System.currentTimeMillis() + " " + object);
        out.flush();
    }
    
    /**
     * Log array to file. Keeps array contents on one line.
     * @param array the array to file
     */
    public void log(Object[] array) {
        out.print(System.currentTimeMillis());
        for (int i = 0; i < array.length; i++) {
            out.print(" " + array[i]);
        }
        out.println();
        out.flush();
    }
    
    /**
     * Log objects to file with specified format
     * @param format format to use
     * @param args objects to log
     */
    public void log(String format, Object ...args) {
        out.print(System.currentTimeMillis() + " ");
        out.printf(format, args);
        out.flush();
    }
    
    /**
     * Log error to file
     * @param e
     */
    public void error(Exception e) {
        out.println(System.currentTimeMillis() + " " + e);
        out.flush();
    }
    
}
