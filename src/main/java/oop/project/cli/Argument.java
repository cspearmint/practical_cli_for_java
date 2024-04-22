package oop.project.cli;

public class Argument {
    private String name;
    private String type;
    //extra parameters

    Argument(String name, String type) {
        this.name = name;
        this.type = type;
    }

    String getName() {
        return this.name;
    }

    String getType() {
        return this.type;
    }
}
