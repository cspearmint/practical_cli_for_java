package oop.project.cli;

import java.util.Map;


public class CommandLine {
    private Map<String, Command> command_list;

    public void parseCommand(String input) {
        // use Scenarios to parse input string into arguments
        // these will be passed to  the command class and
        // checked against the arguments in the ArrayList
        // then the function will be called
        return;
    }

    public boolean addCommand(Command command) {
        // add a command created by the user
        return command_list.put(command.getName(), command) != null;
    }


}
