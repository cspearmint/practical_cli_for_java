package oop.project.cli;

import java.util.Optional;

public class TestingSuite {
    /**
     * A default implementation that can be used to run scenarios.
     */
    public static Command createDateCommand() {
        return Command.builder("date")
                .addArgument("date", "LocalDate")
                .build();
    }

    public static Command createCalcCommand() {
        return Command.builder("calc")
                .addArgument("subcommand", "string")
                .build();
    }

    public static Command createSqrtCommand() {
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

    public static Command createSubCommand() {
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
                        throw new RuntimeException("One or both keys not found in the map.");
                    }
                })
                .build();
    }

    public static Command createAddCommand() {
        return Command.builder("add")
                .addArgument("left", "int")
                .addArgument("right", "int")
                .withCommandFunction((map) -> {
                    // Retrieve the values from the map entries
                    Integer leftArg = (Integer) map.get("left");
                    Integer rightArg = (Integer) map.get("right");

                    // Perform the addition if both values are present
                    if (leftArg != null && rightArg != null) {
                        int result = leftArg + rightArg;
                        System.out.println("Result: " + result);
                    } else {
                        throw new RuntimeException("One or both keys not found in the map.");
                    }
                })
                .build();
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
