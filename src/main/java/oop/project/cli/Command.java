package oop.project.cli;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {
    public final String name;
    private final ArrayList<Argument> args;
    private final Map<String, Argument> flags;
    private final CommandFunction commandFunction;
    private String helpMessage;

    private Command(String name, ArrayList<Argument> args, Map<String, Argument> flags,
                    CommandFunction commandFunction, String helpMessage) {
        this.name = name;
        this.args = args;
        this.flags = flags;
        this.commandFunction = commandFunction;
        this.helpMessage = helpMessage;
    }

    /**
     * Builder for Command
     */
    public static class Builder {
        private String name;
        private ArrayList<Argument> args;
        private Map<String, Argument> flags;
        private CommandFunction commandFunction;
        private String helpMessage;

        public Builder(String name) {
            this.name = name;
            this.args = new ArrayList<>();
            this.flags = new HashMap<String, Argument>();
            this.commandFunction = (map) -> {};
            this.helpMessage = null;
        }

        public Builder addArgument(String name, String type) {
            var arg = new Argument(name, type, false);
            this.args.add(arg);
            return this;
        }

        public Builder addFlag(String name, String type) {
            var arg = new Argument(name, type, true);
            this.flags.put(name, arg);
            return this;
        }

        public Builder withCommandFunction(CommandFunction<Map<String, Object>> commandFunction) {
            this.commandFunction = commandFunction;
            return this;
        }

        public Builder withHelpMessage(String helpMessage) {
            this.helpMessage = helpMessage;
            return this;
        }

        public Command build() {
            return new Command(name, args, flags, commandFunction, helpMessage);
        }
    }

    /**
     * Create builder for Command instance
     * @param name Command name
     * @return
     */
    public static Builder builder(String name) {
        return new Builder(name);
    }

    /**
     * Get argument at index i
     * @param i Index of argument
     * @return Argument instance
     */
    public Argument getArgument(int i) {
        return args.get(i);
    }

    /**
     * Retrieves command name
     * @return Command name string
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves command help message
     * @return Help message string or null if no help message was set
     */
    public String getHelpMessage() {
        return this.helpMessage;
    }


    /**
     * Parse string list of argument tokens into a map
     * @param space_parsed_args List of argument tokens
     * @return Map with keys as argument names and values as corresponding argument values
     */
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
                                throw new IllegalArgumentException("Unsupported argument type: " + arg.getType());
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
                        throw new IllegalArgumentException("Unsupported argument type: " + arg.getType());
                }
                pos_index++;
            }
        }
        return result;
    }


    /**
     * Execute command
     * @param map Map of argument values with keys as argument names
     */
    public void runCommand(Map<String, Object> map) {
        boolean allKeysPresent = true;
        for (Argument key : this.args) {
            if (!map.containsKey(key.getName())) {
                allKeysPresent = false;
                break; // No need to continue checking if one key is missing
            }
        }
        for (Argument key : this.flags.values()) {
            if (!map.containsKey(key.getName())) {
                allKeysPresent = false;
                break; // No need to continue checking if one key is missing
            }
        }

        // Output the result
        if (!allKeysPresent) {
          throw new RuntimeException("Missing arguments!");
        }
        if (commandFunction != null) {
            commandFunction.execute(map);
        }
    }

    @FunctionalInterface
    public interface CommandFunction<T> {
        void execute(T parameters);
    }

}
