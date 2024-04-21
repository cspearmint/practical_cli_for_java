package oop.project.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommandLine {
    private Map<String, Command> command_list = new HashMap<>();

    public void parseCommand(String input) {
        // use Scenarios to parse input string into arguments
        // these will be passed to  the command class and
        // checked against the arguments in the ArrayList
        // then the function will be called
        List<String> in = new ArrayList<>(List.of(input.split(" "))); // This is some bs magic.

        try {
            Command c = command_list.get(in.getFirst());
            in.removeFirst();
        }
        catch(Error e) {
            System.out.println("Command '" + in.getFirst() + "' does not exist!");
            return;
        }

        /*
        Once the Command.parseArguments(String[] args) is completed, this will take in
        the rest of the input from the List. If you wanted to implement differently,
        feel free to change this around.
            c.parseArguments(in);
         */

        /*
         */
        System.out.println("Command works!");
    }

    public boolean addCommand(Command command) {
        // add a command created by the user
        try {
            command_list.put(command.getName(), command);
        } catch(Error e) {
            System.out.println("Error adding command: " + e.getMessage());
            return false;
        }

        return true;

        // return command_list.put(command.getName(), command) != null;
        //return true;
    }


}
