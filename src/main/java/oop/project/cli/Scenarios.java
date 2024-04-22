package oop.project.cli;

import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;


public class Scenarios {

    /**
     * Parses and returns the arguments of a command (one of the scenarios
     * below) into a Map of names to values. This method is provided as a
     * starting point that works for most groups, but depending on your command
     * structure and requirements you may need to make changes to adapt it to
     * your needs - use whatever is convenient for your design.
     */
    public static Map<String, Object> parse(String command) {
        //This assumes commands follow a similar structure to unix commands,
        //e.g. `command [arguments...]`. If your project uses a different
        //structure, e.g. Lisp syntax like `(command [arguments...])`, you may
        //need to adjust this a bit to work as expected.
        var split = command.split(" ", 2);
        var base = split[0];
        var arguments = split.length == 2 ? split[1] : "";
        return switch (base) {
            case "add" -> add(arguments);
            case "sub" -> sub(arguments);
            case "sqrt" -> sqrt(arguments);
            case "calc" -> calc(arguments);
            case "date" -> date(arguments);
            case "help" -> help(arguments);
            default -> throw new IllegalArgumentException("Unknown command.");
        };
    }


    private static Map<String, Object> help(String arguments) {
        String[] parts = arguments.split(" ");

        if (parts.length != 1) {
            throw new IllegalArgumentException("help command requires 1 argument");
        }

        String help;

        switch(parts[0]) {
            case "list":
            case "sub":
            case "sqrt":
            case "add":
            case "calc":
                help = parts[0];
                break;
            default:
                throw new IllegalArgumentException("Invalid argument: " + parts[0]);
        }

        return Map.of("helpWith", help);
    }

    /**
     * Takes two positional arguments:
     *  - {@code left: <your integer type>}
     *  - {@code right: <your integer type>}
     */
    private static Map<String, Object> add(String arguments) {
        String[] parts = arguments.split(" ");

        if (parts.length != 2) {
            throw new IllegalArgumentException("add command requires two arguments: left and right");
        }

        int left = Integer.parseInt(parts[0]); //or BigInteger, etc.
        int right = Integer.parseInt(parts[1]);
        return Map.of("left", left, "right", right);
    }

    /**
     * Takes two <em>named</em> arguments:
     *  - {@code left: <your decimal type>} (optional)
     *     - If your project supports default arguments, you could also parse
     *       this as a non-optional decimal value using a default of 0.0.
     *  - {@code right: <your decimal type>} (required)
     */
    public static Map<String, Object> sub(String arguments) {
        Map<String, Object> result = new HashMap<>();

        String[] parts = arguments.split("\\s+");

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];

            if (part.startsWith("--")) {
                String argumentName = part.substring(2); // Remove the "--"
                if (i + 1 < parts.length) {
                    String argumentValue = parts[i + 1];

                    if (argumentName.equals("left")) {
                        try {
                            double leftValue = Double.parseDouble(argumentValue);
                            result.put("left", leftValue);
                        } catch (NumberFormatException e) {

                            throw new IllegalArgumentException("Error: Invalid value for 'left' argument");
                        }
                    } else if (argumentName.equals("right")) {
                        try {
                            double rightValue = Double.parseDouble(argumentValue);
                            result.put("right", rightValue);
                        } catch (NumberFormatException e) {

                            throw new IllegalArgumentException("Error: Invalid value for 'right' argument");
                        }
                    }
                } else {
                    // Handle missing value for the argument
                    throw new IllegalArgumentException("Error: Missing value for argument '" + argumentName + "'");
                }
                i++;
            } else throw new IllegalArgumentException("Error: Extraneous argument: " + parts[i]);
        }

        if (!result.containsKey("right")) {
            throw new IllegalArgumentException("Error: 'right' argument is required");
        }

        if(!result.containsKey("left")) {
            result.put("left", Optional.empty());
        }

        return result;
    }




    /**
     * Takes one positional argument:
     *  - {@code number: <your integer type>} where {@code number >= 0}
     */
    private static Map<String, Object> sqrt(String arguments) {

        String[] parts = arguments.split(" ");

        if(parts.length > 1) {
            throw new IllegalArgumentException("Error: Too many arguments");
        }

        int number;
        try {
            number = Integer.parseInt(parts[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Invalid value for argument");
        }

        if (number < 0) {
            throw new IllegalArgumentException("Error: Square root command requires a non-negative number");
        }

        return Map.of("number", number);
    }


    /**
     * Takes one positional argument:
     *  - {@code subcommand: "add" | "div" | "sqrt" }, aka one of these values.
     *     - Note: Not all projects support subcommands, but if yours does you
     *       may want to take advantage of this scenario for that.
     */
    static Map<String, Object> calc(String arguments) {
        //TODO: Parse arguments and extract values.
        String[] parts = arguments.split(" ");

        if(parts.length != 1)
            throw new IllegalArgumentException("Error: Invalid number of arguments");

        String subcommand = "";

        switch(parts[0]) {
            case "add":
            case "sub":
            case "sqrt":
                subcommand = parts[0];
                break;
            default:
                throw new IllegalArgumentException("Invalid subcommand: " + parts[0]);
        }

        return Map.of("subcommand", subcommand);
    }

    /**
     * Takes one positional argument:
     *  - {@code date: Date}, a custom type representing a {@code LocalDate}
     *    object (say at least yyyy-mm-dd, or whatever you prefer).
     *     - Note: Consider this a type that CANNOT be supported by your library
     *       out of the box and requires a custom type to be defined.
     */
    static Map<String, Object> date(String arguments) {
        // Split the arguments string by space
        String[] parts = arguments.split(" ");

        // Check if there is exactly one argument
        if (parts.length != 1) {
            throw new IllegalArgumentException("Invalid number of arguments for date command.");
        }

        // Parse the argument as a LocalDate
        LocalDate date;
        try {
            date = LocalDate.parse(parts[0]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + parts[0]);
        }

        // Return the date as a Map
        return Map.of("date", date);
    }

    //TODO: Add your own scenarios based on your software design writeup. You
    //should have a couple from pain points at least, and likely some others
    //for notable features. This doesn't need to be exhaustive, but this is a
    //good place to test/showcase your functionality in context.

}
