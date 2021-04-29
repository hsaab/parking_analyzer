package edu.upenn.cit594.ui;

import edu.upenn.cit594.data.*;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.utils.Utils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

// User interface for command line use of Processor
public class CommandLineUserInterface implements UserInterface {
    private Scanner in;
    private Processor processor;
    private Command[] commands;
    
    /**
     * Constructor
     * @param processor the Processor handling the calculations/logic
     */
    public CommandLineUserInterface(Processor processor) {
        this.in = new Scanner(System.in);
        this.processor = processor;
        this.commands = createCommands();
    }
    
    /**
     * Starts the user interface.
     */
    public void run() {
        int choice;
        do {
            choice = promptUserForValidChoice();
            commands[choice].execute();
        } while (choice != 0);
        in.close();
    }
    
    /**
     * Prompt user for valid choice.
     * Will not return until a valid choice has been provided.
     * @return the choice of calculation as represented by an integer the user has provided
     */
    private int promptUserForValidChoice() {
        printChoices();
        String input = in.nextLine();
        Logger.getLogger().log(input);
        
        if (!Utils.isOnlyDigits(input) || !isValidCommand(Integer.parseInt(input))) {
            System.out.println(input + " is not a valid digit. Nice try. Goodbye.");
            return 0;
        }
        int choice = Integer.parseInt(input);
        
        return choice;
    }
    
    /**
     * Determines if the provided choice number corresponds to a valid command.
     * That is, choice is a valid index within commands field.
     * @param choice the number of command to run in commands
     * @return if the integer representing choice is valid
     */
    private boolean isValidCommand(int choice) {
        return choice >= 0 && choice < commands.length;
    }
    
    /**
     * Prompt user for valid zipcode.
     * Will not return until a valid zipcode has been provided.
     * @return the zipcode the user has provided
     */
    private String promptUserForValidZipcode() {
        String zip;
        System.out.print("Please provide a 5 digit zipcode: ");
        zip = in.nextLine();
        Logger.getLogger().log(zip);
        
        return zip;
    }
    
    /**
     * Prints the choices on the console.
     */
    private void printChoices() {
        System.out.println();
        for (int i = 1; i < commands.length; i++) {
            System.out.printf("%d.\t%s\n", i ,commands[i].getName());
        }
        System.out.println("Enter a number from 1 - " + (commands.length - 1) + " for a calculation. 0 to exit.");
    }
    
    /**
     * Creates an array of commands, to be set as the options for the user to choose.
     * @return an array of Commands
     */
    private Command[] createCommands() {
        Command[] commands = {
                new Command("Exit") {
                    @Override
                    public void execute() {
                        in.close();
                        System.exit(0);
                    }
                },
                new Command("Calculate total population for all zip codes.") {
                    @Override
                    public void execute() {
                        int sum = processor.sumPopulations();
                        System.out.println(sum);
                    }
                },
                new Command("Calculate total fines per capita.") {
                    @Override
                    public void execute() {
                        Map<String,Double> zipcodeToFineMap = processor.calculateTotalFinesPerCapita();
                        for (Map.Entry<String, Double> entry : zipcodeToFineMap.entrySet()) {
                            System.out.println(entry.getKey() + " " + Utils.truncateDecimalsInValue(entry.getValue(), 4));
                        }
                    }
                },
                new Command("Calculate average market value.") {
                    @Override
                    public void execute() {
                        String zipcode = promptUserForValidZipcode();

                        double result = processor.calculateAverageMarketValueByZipcode(zipcode);
                        System.out.println(Utils.truncateDecimalsInValue(result, 0));
                    }
                },
                new Command("Calculate average total livable area.") {
                    @Override
                    public void execute() {
                        String zipcode = promptUserForValidZipcode();

                        double result = processor.calculateAverageLivableAreaByZipcode(zipcode);
                        System.out.println(Utils.truncateDecimalsInValue(result, 0));
                    }
                },
                new Command("Calculate total residential market value per capita.") {
                    @Override
                    public void execute() {
                        String zipcode = promptUserForValidZipcode();

                        double result = processor.calculateResidentialMarketValuePerCapita(zipcode);
                        System.out.println(Utils.truncateDecimalsInValue(result, 0));
                    }
                },
                new Command("Calculate fine count by zipcode sorted by highest market value per capita") {
                    @Override
                    public void execute() {
                        DecimalFormat formatter = new DecimalFormat("$#,###.00");
                        Set<Area> areaByHighestMarketValuePerCapita = processor.calculateFineCountForHighestMarketValuePerCapitaAreas();

                        for(Area area : areaByHighestMarketValuePerCapita) {
                            System.out.println("Zipcode: " + area.getZipcode() + "  market value: " + formatter.format(area.getMarketValuePerCapita()) + "  number of fines: " + area.getFineCount());
                        }
                    }
                }
        };
        
        return commands;
    }
    
    /**
     * Represents a command that the user could choose on command line.
     * Must override execute method with the specified action when creating the command.
     */
    private abstract static class Command {
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
}