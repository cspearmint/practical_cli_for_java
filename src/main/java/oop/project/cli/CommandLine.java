package oop.project.cli;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommandLine {
    private Map<String, Command> commandMap = new HashMap<>();

    private class CommandTokenizer {
        private String input;
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

    public void executeCommand(String input) {
        CommandTokenizer tokenizer = new CommandTokenizer(input);
        List<String> tokens = tokenizer.readTokens();

        if (tokens.isEmpty()) {
            throw new RuntimeException("Expected command, got nothing");
        }

        Command cmd = commandMap.get(tokens.getFirst());
        if (cmd == null) {
            System.out.println("Command '" + tokens.getFirst() + "' does not exist!");
            return;
        }

        tokens.remove(0);
        Map<String, Object> argValues = cmd.parseArguments(tokens);

        cmd.runCommand(argValues);
    }

    public boolean addCommand(Command command) {
        // add a command created by the user
        try {
            commandMap.put(command.getName(), command);
        } catch(Error e) {
            System.out.println("Error adding command: " + e.getMessage());
            return false;
        }

        return true;

        // return command_list.put(command.getName(), command) != null;
        //return true;
    }


    public Command getCommand(String name) {
        return this.commandMap.get(name);
    }


}