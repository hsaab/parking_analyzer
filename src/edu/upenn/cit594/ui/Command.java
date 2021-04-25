package edu.upenn.cit594.ui;

public abstract class Command {
    private String name;
    
    public Command(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                '}';
    }
    
    public abstract void execute();
}
