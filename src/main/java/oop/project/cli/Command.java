package oop.project.cli;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Command {

    String name;
    private ArrayList<Argument> args;
    private String help_message;
    private CommandFunction commandFunction;

    // storage for the function the user will call
    public Command(String name) {
        this.name = name;
        this.args = new ArrayList<>();
        this.commandFunction = (map) -> {};
    }

    public boolean addArgument(Argument arg) {
        // using parameters yet undetermined
        // initialize an argument object
        // then add it to the args arraylist
        this.args.add(arg);
        return true;
    }

    public Argument getArgument(int i) {
        return args.get(i);
    }
    public String getName() {

        return this.name;
    }


    public void setCommandFunction(CommandFunction<Map<String, Object>> commandFunction) {
        this.commandFunction = commandFunction;
    }

    public void runCommand(Map<String, Object> map) {
        if (commandFunction != null) {
            commandFunction.execute(map);
        }
    }

    @FunctionalInterface
    public interface CommandFunction<T> {
        void execute(T parameters);
    }



}
