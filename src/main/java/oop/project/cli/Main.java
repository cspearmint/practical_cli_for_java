package oop.project.cli;

import java.util.Scanner;

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

            Integer value1 = (Integer) map.get(addCommand.getArgument(0).getName());
            Integer value2 = (Integer) map.get(addCommand.getArgument(1).getName());

            // Perform the addition if both values are present
            if (value1 != null && value2 != null) {
                int result = value1 + value2;
                System.out.println("Result: " + result);
            } else {
                System.out.println("One or both keys not found in the map.");
            }
        });

        Command subCommand = new Command("sub");
        subCommand.addArgument(new Argument("left", "double"));
        subCommand.addArgument(new Argument("right", "double"));
        cli.addCommand(subCommand);

        Command sqrtCommand = new Command("sqrt");
        sqrtCommand.addArgument(new Argument("number", "int"));
        cli.addCommand(sqrtCommand);

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
                }
                //System.out.println(result);
            } catch (Exception e) {
                System.out.println("Unexpected exception: " + e.getClass().getName() + ", " + e.getMessage());
            }
        }
    }
}



