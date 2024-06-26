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
        // var arguments = split.length == 2 ? split[1] : "";
        return switch (base) {
            case "add" -> add(command);
            case "sub" -> sub(command);
            case "sqrt" -> sqrt(command);
            case "calc" -> calc(command);
            case "date" -> date(command);
            // case "help" -> help(command);
            default -> throw new IllegalArgumentException("Unknown command.");
        };
    }

    /*
    private static Map<String, Object> help(String arguments) {
        CommandLine cli = new CommandLine();
        Command c = TestingSuite.createHelpCommand();
        cli.addCommand(c);
        return cli.executeCommand(arguments);
    }

    /**
     * Takes two positional arguments:
     *  - {@code left: <your integer type>}
     *  - {@code right: <your integer type>}
     */
    private static Map<String, Object> add(String arguments) {
        CommandLine cli = new CommandLine();
        Command c = TestingSuite.createAddCommand();
        cli.addCommand(c);
        return cli.executeCommand(arguments);
    }

    /**
     * Takes two <em>named</em> arguments:
     *  - {@code left: <your decimal type>} (optional)
     *     - If your project supports default arguments, you could also parse
     *       this as a non-optional decimal value using a default of 0.0.
     *  - {@code right: <your decimal type>} (required)
     */
    public static Map<String, Object> sub(String arguments) {
        CommandLine cli = new CommandLine();
        Command c = TestingSuite.createSubCommand();
        cli.addCommand(c);
        return cli.executeCommand(arguments);
    }




    /**
     * Takes one positional argument:
     *  - {@code number: <your integer type>} where {@code number >= 0}
     */
    private static Map<String, Object> sqrt(String arguments) {
        CommandLine cli = new CommandLine();
        Command c = TestingSuite.createSqrtCommand();
        cli.addCommand(c);
        return cli.executeCommand(arguments);
    }


    /**
     * Takes one positional argument:
     *  - {@code subcommand: "add" | "div" | "sqrt" }, aka one of these values.
     *     - Note: Not all projects support subcommands, but if yours does you
     *       may want to take advantage of this scenario for that.
     */
    static Map<String, Object> calc(String arguments) {
        CommandLine cli = new CommandLine();
        Command c = TestingSuite.createCalcCommand();
        cli.addCommand(c);
        return cli.executeCommand(arguments);
    }

    /**
     * Takes one positional argument:
     *  - {@code date: Date}, a custom type representing a {@code LocalDate}
     *    object (say at least yyyy-mm-dd, or whatever you prefer).
     *     - Note: Consider this a type that CANNOT be supported by your library
     *       out of the box and requires a custom type to be defined.
     */
    static Map<String, Object> date(String arguments) {
        CommandLine cli = new CommandLine();
        Command c = TestingSuite.createDateCommand();
        cli.addCommand(c);
        return cli.executeCommand(arguments);
    }

    //TODO: Add your own scenarios based on your software design writeup. You
    //should have a couple from pain points at least, and likely some others
    //for notable features. This doesn't need to be exhaustive, but this is a
    //good place to test/showcase your functionality in context.

}