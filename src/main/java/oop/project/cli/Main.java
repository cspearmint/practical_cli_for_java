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
        cli.addCommand(TestingSuite.createAddCommand());
        cli.addCommand(TestingSuite.createSubCommand());
        cli.addCommand(TestingSuite.createSqrtCommand());
        cli.addCommand(TestingSuite.createCalcCommand());
        cli.addCommand(TestingSuite.createDateCommand());
        // cli.addCommand(createHelpCommand());

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
}







