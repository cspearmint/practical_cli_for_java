package oop.project.cli;
import java.util.ArrayList;
public class Command {

    String name;
    private ArrayList<Argument> args;
    private String help_message;
    // storage for the function the user will call

    public Command(String name) {
        this.name = name;
        this.args = new ArrayList<>();
    }

    public boolean addArgument(Argument arg) {
        // using parameters yet undetermined
        // initialize an argument object
        // then add it to the args arraylist
        return this.args.add(arg);
    }
    public String getName() {
        return this.name;
    }


}
