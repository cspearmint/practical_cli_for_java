package oop.project.cli;
import java.util.Collections;
import java.util.Map;

public class Command {

    String name;
    private Map<String, Argument> args;
    // private String help_message; // Commented out until needed

    public Command(String name) {
        this.name = name;
        this.args = Collections.emptyMap();
    }

    /*
        Adds argument to map, overrides argument if one with same name already exists
        Returns whether an argument with same name already exists in map
     */
    public boolean addArgument(Argument arg) {
        boolean exists = args.containsKey(arg.name);
        this.args.put(arg.name, arg);
        return exists;
    }

    public String getName() {
        return this.name;
    }

}
