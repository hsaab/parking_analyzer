package edu.upenn.cit594.ui;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;

public class CommandLineUserInterface {
    private Scanner in;
    private Processor processor;
    private Command[] options;
    private DecimalFormat decimalFormat;
    
    
    public CommandLineUserInterface(Processor processor) {
        this.in = new Scanner(System.in);
        this.processor = processor;
        this.options = createOptions();
        this.decimalFormat = new DecimalFormat();
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
    }
    
    /**
     * Starts the user interface.
     */
    public void start() {
        int choice = promptUserForValidChoice();
        if (choice == 0) { // exit
            in.close();
            return;
        }
        options[choice - 1].execute();
        start(); // prompt again when execution is complete
    }
    
    /**
     * Prompt user for valid choice.
     * Will not return until a valid choice has been provided.
     * @return the choice of calculation as represented by an integer the user has provided
     */
    private int promptUserForValidChoice() {
        int choice;
        do {
            printChoices();
            String input = in.nextLine();
            Logger.getLogger().log(input);
            while (!isOnlyDigits(input)) {
                System.out.println(input + " includes non-digits. Please enter a whole number only.");
                input = in.nextLine();
                Logger.getLogger().log(input);
            }
            choice = Integer.parseInt(input);
        } while (!isValidChoice(choice));
        
        return choice;
    }
    
    /**
     * Prompt user for valid zipcode.
     * Will not return until a valid zipcode has been provided.
     * @return the zipcode the user has provided
     */
    private String promptUserForValidZipcode() {
        String zip;
        do {
            System.out.print("Please provide a 5 digit zipcode: ");
            zip = in.nextLine();
            Logger.getLogger().log(zip);
        } while (zip.length() != 5 || !isOnlyDigits(zip));
        
        return zip;
    }
    
    /**
     * Prints the choices on the console.
     */
    private void printChoices() {
        System.out.println();
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d.\t%s\n", i + 1,options[i].getName());
        }
        System.out.println("Enter a number from 1 - " + options.length + " for a calculation. 0 to exit.");
    }
    
    /**
     * Determines if the input is only comprised of digits.
     * Spaces are considered non-digits.
     * @param input the input to test
     * @return true if all characters are digits, false if otherwise
     */
    private boolean isOnlyDigits(String input) {
        for (int i = 0; i < input.toCharArray().length; i++) {
            char character = input.charAt(i);
            if (!Character.isDigit(character)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Determines if the choice is valid, meaning it corresponds to a calculation or exit.
     * @param choice the number to test
     * @return true if choice is in specified range, false otherwise
     */
    private boolean isValidChoice(int choice) {
        return choice >= 0 && choice <= options.length;
    }
    
    /**
     * Truncates provided value to String representation with provided number of decimalPlaces.
     * Displays trailing 0s, even if not explicitly provided in value. Omits leading 0's.
     * @param value value to truncate
     * @param decimalPlaces number of decimal places to include
     * @return the string representation of value with truncated decimals
     */
    private String truncateDecimalsInValue(double value, int decimalPlaces) {
        if (decimalPlaces < 0) throw new IllegalArgumentException("decimalPlaces must be non-negative: " + decimalPlaces);
        StringBuilder pattern = new StringBuilder("0");
        if (decimalPlaces > 0)  pattern.append(".");
        for (int i = 1; i <= decimalPlaces; i++) {
            pattern.append("0");
        }
        
        this.decimalFormat.applyPattern(pattern.toString()); // update decimalFormat to use pattern that was just built
        return  this.decimalFormat.format(value);
       
    }
    
    /**
     * Creates an array of commands, to be set as the calculation options for the user to choose.
     * @return an array of Commands
     */
    private Command[] createOptions() {
        Command[] commands = {
                new Command("Calculate total population for all zip codes.") {
                    @Override
                    public void execute() {
                        System.out.println(this);
                        int sum = processor.sumPopulations();
                        System.out.println(sum);
                    }
                },
                new Command("Calculate total fines per capita.") {
                    @Override
                    public void execute() {
                        System.out.println(this);
                        processor.calculateTotalFinesPerCapita();
                    }
                },
                new Command("Calculate average market value.") {
                    @Override
                    public void execute() {
                        System.out.println(this);
                        String zipcode = promptUserForValidZipcode();
                        System.out.println("Loading...");
                        double result = processor.calculateAverageMarketValue(zipcode);
                        System.out.println(truncateDecimalsInValue(result, 0));
                    }
                },
                new Command("Calculate average total livable area.") {
                    @Override
                    public void execute() {
                        System.out.println(this);
                    }
                },
                new Command("Calculate total residential market value per capita.") {
                    @Override
                    public void execute() {
                        System.out.println(this);
                    }
                },
                new Command("Custom calculation") {
                    @Override
                    public void execute() {
                        System.out.println(this);
                    }
                }
        };
        
        return commands;
    }
}