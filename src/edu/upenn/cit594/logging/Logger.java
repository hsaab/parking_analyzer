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
            out = new PrintWriter(new FileOutputStream(filename, true));
        } catch (FileNotFoundException e) {
        
        }
    }
    
    /**
     * Get instance of Logger.
     * @return the logger
     */
    public static Logger getLogger() throws IllegalStateException {
        if (filename == null) {
            throw new IllegalStateException("must set filename first");
        }
        
        if (instance == null) {
            instance = new Logger(filename);
        }
        
        return instance;
    }
    
    
    /**
     * Sets file to log to.
     * Can only be used once.
     * @param filename filename of file to log to
     */
    public static void setFilename(String filename) throws UnsupportedOperationException {
        if (filename == null) throw new IllegalArgumentException("filename must be non-null.");
        if (Logger.filename != null) {
            throw new UnsupportedOperationException("filename can only be set once");
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
