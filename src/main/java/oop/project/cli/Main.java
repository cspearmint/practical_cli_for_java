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
        Command addCommand = new Command("add");
        addCommand.addArgument(new Argument("left", "int"));
        addCommand.addArgument(new Argument("right", "int"));
        cli.addCommand(addCommand);

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

        Command subCommand = new Command("sub");
        subCommand.addArgument(new Argument("left", "double"));
        subCommand.addArgument(new Argument("right", "double"));
        cli.addCommand(subCommand);

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
        Command sqrtCommand = new Command("sqrt");
        sqrtCommand.addArgument(new Argument("number", "int"));
        cli.addCommand(sqrtCommand);

        sqrtCommand.setCommandFunction((map) -> {
            // Retrieve the values from the map entries
            Integer number = (Integer) map.get(sqrtCommand.getArgument(0).getName());

            double ans = Math.sqrt(number);

            System.out.println("Result: "  + ans);
        });

        Command calcCommand = new Command("calc");
        calcCommand.addArgument(new Argument("subcommand", "string"));
        cli.addCommand(calcCommand);

        Command dateCommand = new Command("date");
        dateCommand.addArgument(new Argument("date", "LocalDate"));
        cli.addCommand(dateCommand);



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

                switch (command) {
                    case "add":
                        addCommand.runCommand(result);
                        break;
                    case "sub":
                        subCommand.runCommand(result);
                        break;
                    case "sqrt":
                        sqrtCommand.runCommand(result);
                        break;
                    default:
                        System.out.println("Invalid command");
                }
                //System.out.println(result);
            } catch (Exception e) {
                System.out.println("Unexpected exception: " + e.getClass().getName() + ", " + e.getMessage());
            }
        }
    }
}



