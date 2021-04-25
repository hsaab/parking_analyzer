package edu.upenn.cit594.ui;

import java.util.Scanner;
import edu.upenn.cit594.processor.Processor;

public class CommandLineUserInterface {
    private Scanner in;
    private Processor processor;
    private Command[] options;
    
    
    public CommandLineUserInterface() {
        this.in = new Scanner(System.in);
        this.options = createOptions();
    }
    
    public CommandLineUserInterface(Processor processor) {
        this.in = new Scanner(System.in);
        this.processor = processor;
        this.options = createOptions();
    }
    
    public void start() {
        int choice = promptUserForValidChoice();
        if (choice == 0) return;
        options[choice - 1].execute();
        start();
    }
    
    /**
     * Prompt user for valid choice.
     * Will not return until a valid choice has been made.
     * @return the choice of calculation as represented by an integer
     */
    private int promptUserForValidChoice() {
        int choice;
        do {
            printChoices();
            String input = in.nextLine();
            while (!isOnlyDigits(input)) {
                System.out.println(input + " includes non-digits. Please enter a whole number only.");
                input = in.nextLine();
            }
            choice = Integer.parseInt(input);
        } while (!isValidChoice(choice));
        
        return choice;
    }
    
    private String promptUserForValidZipcode() {
        String zip;
        do {
            System.out.print("Please provide a 5 digit zipcode: ");
            zip = in.nextLine();
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
     * Creates the array of options that can be selected by user.
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