package oop.project.cli;

public class Argument {
    private String name;
    private String type;
    //extra parameters
    private boolean flag;

    Argument(String name, String type, boolean flag) {
        this.name = name;
        this.type = type;
        this.flag = flag;
    }

    String getName() {
        return this.name;
    }

    String getType() {
        return this.type;
    }

    boolean isFlag() { return this.flag; }
}
