package edu.upenn.cit594.ui;

import edu.upenn.cit594.data.Area;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.utils.Utils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CommandLineUserInterface {
    private Scanner in;
    private Processor processor;
    private Command[] commands;
    
    
    public CommandLineUserInterface(Processor processor) {
        this.in = new Scanner(System.in);
        this.processor = processor;
        this.commands = createCommands();
    }
    
    /**
     * Starts the user interface.
     */
    public void start() {
        int choice = promptUserForValidChoice();
        commands[choice].execute();
        start(); // prompt again when execution of choice is complete
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
            while (!Utils.isOnlyDigits(input)) {
                System.out.println(input + " includes non-digits. Please enter a whole number only.");
                input = in.nextLine();
                Logger.getLogger().log(input);
            }
            choice = Integer.parseInt(input);
        } while (choice < 0 || choice >= commands.length);
        
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
        } while (zip.length() != 5 || !Utils.isOnlyDigits(zip));
        
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
                        System.out.println("Running...");
                        double result = processor.calculateAverageMarketValueByZipcode(zipcode);
                        System.out.println(Utils.truncateDecimalsInValue(result, 0));
                    }
                },
                new Command("Calculate average total livable area.") {
                    @Override
                    public void execute() {
                        String zipcode = promptUserForValidZipcode();
                        System.out.println("Running...");
                        double result = processor.calculateAverageLivableAreaByZipcode(zipcode);
                        System.out.println(Utils.truncateDecimalsInValue(result, 0));
                    }
                },
                new Command("Calculate total residential market value per capita.") {
                    @Override
                    public void execute() {
                        String zipcode = promptUserForValidZipcode();
                        System.out.println("Running...");
                        double result = processor.calculateResidentialMarketValuePerCapita(zipcode);
                        System.out.println(Utils.truncateDecimalsInValue(result, 0));
                    }
                },
                new Command("Calculate fine count by zipcode sorted by highest market value per capita") {
                    @Override
                    public void execute() {
                        System.out.println("Running...");

                        DecimalFormat formatter = new DecimalFormat("$#,###.00");

                        Set<Area> areaByHighestMarketValuePerCapita = processor.calculateFineCountForHighestMarketValuePerCapitaAreas();

                        for(Area area : areaByHighestMarketValuePerCapita) {
                            System.out.println("Zipcode: " + area.zipcode + "  market value: " + formatter.format(area.marketValuePerCapita) + "  number of fines: " + area.fineCount);
                        }
                    }
                }
        };
        
        return commands;
    }
}