package me.kroosh;

/**
 * Created by ihorkroosh on 3/5/15.
 */
public class CPUProcess {
    private int complexity;
    private int id;
    private static int nextId = 0;

    CPUProcess(int complexity) {
        this.complexity = complexity;
        id = nextId++;
    }

    public int getCompexity() {
        return complexity;
    }

    public int getId() {
        return id;
    }
}
