package oop.project.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class CommandLine {
    private Map<String, Command> commandMap = new HashMap<>();

    /**
     * Command tokenizer for extracting list of tokens from input string.
     * A token is either a substring of non-whitespace characters or a
     * substring of the form `"[^"]*"`.
     * Here, whitespace refers to a space.
     */
    private class CommandTokenizer {
        private final String input;
        private int ptr = 0;

        public CommandTokenizer(String input) {
            this.input = input;
        }

        public List<String> readTokens() {
            List<String> tokens = new ArrayList<>();
            while (!isEof()) {
                String token = readNextToken();
                if (token != null) {
                    tokens.add(token);
                }
            }
            return tokens;
        }

        private String readNextToken() {
            readWhitespace();
            if (isEof()) {
                return null;
            }
            int tokenStart = getPos();
            if (peek() == '\"') {
                // Token is a string type so read until next double quote.
                while (!isEof() && peek() != '\"') {
                    next();
                }
                if (peek() != '\"') {
                    throw new RuntimeException("Expected '\"', got eof");
                }
                next();
            }
            else {
                while (!isEof() && !isWhitespace(peek())) {
                    next();
                }
            }
            return input.substring(tokenStart, getPos());
        }

        private int getPos() {
            return ptr;
        }

        private boolean isEof() {
            return ptr >= input.length();
        }

        private boolean isWhitespace(char c) {
            return c == ' ';
        }

        private char peek() {
            return input.charAt(ptr);
        }

        private void next() {
            ptr++;
        }

        private void readWhitespace() {
            while (!isEof() && isWhitespace(peek())) {
                next();
            }
        }
    }

    /**
     * Executes command provided input string.
     * @param input Command input string
     */
    public Map<String, Object> executeCommand(String input) {
        CommandTokenizer tokenizer = new CommandTokenizer(input);
        List<String> tokens = tokenizer.readTokens();

        if (tokens.isEmpty()) {
            throw new RuntimeException("Expected command, got nothing");
        }

        String cmdName = tokens.getFirst();
        tokens.removeFirst();

        if (cmdName.equals("help")) {
            executeHelpCommand(tokens);
            return null;
        }

        Command cmd = commandMap.get(cmdName);
        if (cmd == null) {
            throw new RuntimeException("Command '" + cmdName + "' does not exist!");
        }

        Map<String, Object> argValues = cmd.parseArguments(tokens);
        cmd.runCommand(argValues);
        return argValues;
    }

    private void executeHelpCommand(List<String> tokens) {
        if (tokens.isEmpty()) {
            System.out.println("Commands:");
            for (String cmd : commandMap.keySet()) {
                System.out.println("\t" + cmd);
            }
            return;
        }
        if (tokens.size() > 1) {
            throw new RuntimeException("Found too many arguments");
        }
        String cmdHelpName = tokens.getFirst();
        Command cmdHelp = commandMap.get(cmdHelpName);
        if (cmdHelp == null) {
            throw new RuntimeException("Command '" + cmdHelpName + "' does not exist!");
        }
        String helpMessage = cmdHelp.getHelpMessage();
        if (helpMessage == null) {
            System.out.println("Command '" + cmdHelpName + "' does not have a help message.");
        }
        else {
            System.out.println("Command '" + cmdHelpName + "': " + helpMessage);
        }
    }

    /**
     * Adds command to CLI
     * @param command Command to add
     * @return Command instance of existing command with same name if it exists or null otherwise
     */
    public Command addCommand(Command command) {
        return commandMap.put(command.getName(), command);
    }

    /**
     * Retrieves command provided name
     * @param name Name of command
     * @return Command instance associated with provided name or null otherwise
     */
    public Command getCommand(String name) {
        return this.commandMap.get(name);
    }

}