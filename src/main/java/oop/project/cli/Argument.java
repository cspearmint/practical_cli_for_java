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

    /**
     * Get argument name
     * @return String representing argument name
     */
    String getName() {
        return this.name;
    }

    /**
     * Get argument type
     * @return String representing argument type
     */
    String getType() {
        return this.type;
    }

    /**
     * Determine whether argument is a flag
     * @return Boolean being true if and only if argument is a flag
     */
    boolean isFlag() { return this.flag; }
}
