package oop.project.cli;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Command {

    String name;
    private ArrayList<Argument> args;

    private Map<String, Argument> flags = new HashMap<String, Argument>();
    private String help_message;
    private CommandFunction commandFunction;

    // storage for the function the user will call
    public Command(String name) {
        this.name = name;
        this.args = new ArrayList<>();
        this.commandFunction = (map) -> {};
    }

    /*
        Adds argument to map, overrides argument if one with same name already exists
        Returns whether an argument with same name already exists in map
     */
    public boolean addArgument(String name, String type) {
        var arg = new Argument(name, type, false);
        this.args.add(arg);
        return true;
    }

    public boolean addFlag(String name, String type) {
        var arg = new Argument(name, type, true);
        this.flags.put(name, arg);
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

    public Map<String, Object> parseArguments(List<String> space_parsed_args) {
        Map<String, Object> result = new HashMap<>();
        var pos_index = 0;
        while (!space_parsed_args.isEmpty()) {
            if (space_parsed_args.getFirst().charAt(0) == '"') {
                return null;
            }
            else if (space_parsed_args.getFirst().charAt(0) == '-') {
                if (space_parsed_args.getFirst().charAt(1) == '-') {
                    //long flag
                    var arg_title = space_parsed_args.removeFirst().substring(2);

                    // check if in map
                    Argument arg = this.flags.get(arg_title);
                    if (arg == null) {
                        System.out.println("Unsupported argument type.");
                    }
                    else {
                        switch (arg.getType()) {
                            case "string": {
                                String parsed_arg = space_parsed_args.removeFirst();
                                result.put(arg.getName(), parsed_arg);
                                break;
                            }
                            case "int": {
                                int parsed_arg = Integer.parseInt(space_parsed_args.removeFirst());
                                result.put(arg.getName(), parsed_arg);
                                break;
                            }
                            case "double": {
                                double parsed_arg = Double.parseDouble(space_parsed_args.removeFirst());
                                result.put(arg.getName(), parsed_arg);
                                break;
                            }
                            default:
                                System.out.println("Unsupported argument type: " + arg.getType());
                                break;
                        }
                    }


                }
                else {
                    return null;
                }
            }
            else {
                //check if type matches
                Argument arg = args.get(pos_index);

                // Check the type of the argument using a switch statement
                switch (arg.getType()) {
                    case "string": {
                        String parsed_arg = space_parsed_args.removeFirst();
                        result.put(arg.getName(), parsed_arg);
                        break;
                    }
                    case "int": {
                        int parsed_arg = Integer.parseInt(space_parsed_args.removeFirst());
                        result.put(arg.getName(), parsed_arg);
                        break;
                    }
                    case "double": {
                        double parsed_arg = Double.parseDouble(space_parsed_args.removeFirst());
                        result.put(arg.getName(), parsed_arg);
                        break;
                    }
                    default:
                        System.out.println("Unsupported argument type: " + arg.getType());
                        break;
                }
                pos_index++;
            }
        }
        return result;
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
