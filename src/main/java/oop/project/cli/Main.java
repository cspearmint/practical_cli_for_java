package oop.project.cli;

import java.util.Scanner;
import java.util.Optional;
import java.lang.Math;

public class Main {

    /**
     * A default implementation of main that can be used to run scenarios.
     */
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.println("Welcome to the CLI application.");
        CommandLine cli = new CommandLine();

        // Adding commands
        cli.addCommand(createAddCommand());
        cli.addCommand(createSubCommand());
        cli.addCommand(createSqrtCommand());
        cli.addCommand(createCaclCommand());
        cli.addCommand(createDateCommand());
        cli.addCommand(createHelpCommand());

        while (true) {
            System.out.print("> ");
            var input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println("Exiting the application...");
                break;
            }
            try {
                var result = Scenarios.parse(input);
                String command = input.split(" ", 2)[0];

                cli.getCommand(command).runCommand(result);

                //System.out.println(result);
            } catch (Exception e) {
                System.out.println("Unexpected exception: " + e.getClass().getName() + ", " + e.getMessage());
            }
        }
    }

    private static Command createDateCommand() {
        Command dateCommand = new Command("date");
        dateCommand.addArgument(new Argument("date", "LocalDate"));
        return dateCommand;
    }

    private static Command createCaclCommand() {
        Command calcCommand = new Command("calc");
        calcCommand.addArgument(new Argument("subcommand", "string"));
        return calcCommand;
    }

    private static Command createSqrtCommand() {
        Command sqrtCommand = new Command("sqrt");
        sqrtCommand.addArgument(new Argument("number", "int"));

        sqrtCommand.setCommandFunction((map) -> {
            // Retrieve the values from the map entries
            Integer number = (Integer) map.get(sqrtCommand.getArgument(0).getName());

            double ans = Math.sqrt(number);

            System.out.println("Result: "  + ans);
        });
        return sqrtCommand;
    }

    private static Command createSubCommand() {
        Command subCommand = new Command("sub");
        subCommand.addArgument(new Argument("left", "double"));
        subCommand.addArgument(new Argument("right", "double"));

        subCommand.setCommandFunction((map) -> {
            // Retrieve the values from the map entries
            Object leftArg = map.get(subCommand.getArgument(0).getName());
            Object rightArg = map.get(subCommand.getArgument(1).getName());

            Double value1;
            Double value2;

            // Check if value1 is an Optional and set it to 0.0 if it is
            if ( leftArg  instanceof Optional<?>) {
                Optional<?> optionalValue1 = (Optional<?>)  leftArg ;
                value1 = optionalValue1.isPresent() ? (Double) optionalValue1.get() : 0.0;
            } else {
                value1 = (Double)  leftArg ;
            }

            value2 = (Double) rightArg;

            // Perform the subtraction if both values are present
            if (value1 != null && value2 != null) {
                Double result = value1 - value2;
                System.out.println("Result: " + result);
            } else {
                System.out.println("One or both keys not found in the map.");
            }
        });
        return subCommand;
    }

    private static Command createAddCommand() {
        Command addCommand = new Command("add");
        addCommand.addArgument(new Argument("left", "int"));
        addCommand.addArgument(new Argument("right", "int"));

        addCommand.setCommandFunction((map) -> {
            // Retrieve the values from the map entries

            Integer leftArg = (Integer) map.get(addCommand.getArgument(0).getName());
            Integer rightArg = (Integer) map.get(addCommand.getArgument(1).getName());

            // Perform the addition if both values are present
            if (leftArg != null && rightArg != null) {
                int result = leftArg + rightArg;
                System.out.println("Result: " + result);
            } else {
                System.out.println("One or both keys not found in the map.");
            }
        });
        return addCommand;
    }

    private static Command createHelpCommand() {
        Command helpCommand = new Command("help");
        helpCommand.addArgument(new Argument("helpWith", "String"));
        helpCommand.setCommandFunction((map) -> {
            // Retrieve the values from the map entries

            String helpWith = (String) map.get(helpCommand.getArgument(0).getName());

            switch (helpWith) {
                case "list":
                    listCommands();
                    break;
                case "add":
                    System.out.println("add: Add two numbers. Usage: add <left> <right>");
                    break;
                case "sub":
                    System.out.println("sub: Subtract two numbers. Usage: sub --left <left_value> --right <right_value>");
                    break;
                case "sqrt":
                    System.out.println("sqrt: Calculate the square root of a number. Usage: sqrt <number>");
                    break;
                case "calc":
                    System.out.println("calc: Perform a calculation. Usage: calc <subcommand> (add/sub/sqrt)");
                    break;
                case "date":
                    System.out.println("date: Display the current date. Usage: date <YYYY-MM-DD>");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid argument: " + helpWith);
            }

        });
        return helpCommand;
    }

    public static void listCommands() {
        System.out.println("Available commands:");
        System.out.println("add: Add two numbers.");
        System.out.println("sub: Subtract two numbers.");
        System.out.println("sqrt: Calculate the square root of a number.");
        System.out.println("calc: Perform a calculation.");
        System.out.println("date: Display the current date.");
        System.out.println("To get help for a specific command, use: help <commandName>");
    }
}







