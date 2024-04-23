package oop.project.cli;

import java.util.Map;
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

        while (true) {
            System.out.print("> ");
            var input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println("Exiting the application...");
                break;
            }
            try {
                cli.executeCommand(input);

                /*
                var result = Scenarios.parse(input);
                String command = input.split(" ", 2)[0];

                cli.getCommand(command).runCommand(result);
                //System.out.println(result);
                 */
            } catch (Exception e) {
                System.out.println("Error: " + e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    private static Command createDateCommand() {
        return Command.builder("date")
                .addArgument("date", "LocalDate")
                .build();
    }

    private static Command createCaclCommand() {
        return Command.builder("calc")
                .addArgument("subcommand", "string")
                .build();
    }

    private static Command createSqrtCommand() {
        return Command.builder("sqrt")
                .addArgument("number", "int")
                .withCommandFunction((map) -> {
                    // Retrieve the values from the map entries
                    Integer number = (Integer) map.get("number");

                    double ans = Math.sqrt(number);
                    System.out.println("Result: "  + ans);
                })
                .build();
    }

    private static Command createSubCommand() {
        return Command.builder("sub")
                .addFlag("left", "double")
                .addFlag("right", "double")
                .withCommandFunction((map) -> {
                    // Retrieve the values from the map entries
                    Object leftArg = map.get("left");
                    Object rightArg = map.get("right");

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
                })
                .build();
    }

    private static Command createAddCommand() {
        return Command.builder("add")
                .addFlag("left", "int")
                .addFlag("right", "int")
                .withCommandFunction((map) -> {
                    // Retrieve the values from the map entries
                    Integer leftArg = (Integer) map.get("left");
                    Integer rightArg = (Integer) map.get("right");

                    // Perform the addition if both values are present
                    if (leftArg != null && rightArg != null) {
                        int result = leftArg + rightArg;
                        System.out.println("Result: " + result);
                    } else {
                        System.out.println("One or both keys not found in the map.");
                    }
                })
                .build();
    }
}







