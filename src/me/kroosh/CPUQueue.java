package me.kroosh;

import sun.plugin2.gluegen.runtime.CPU;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ihorkroosh on 3/5/15.
 */
public class CPUQueue {
    private List<CPUProcess> list;
    private int maxSize;

    CPUQueue(int maxSize) {
        this.maxSize = maxSize;
        list = new ArrayList<>();
    }

    int size() {
        return list.size();
    }

    boolean isEmpty() {
        return list.isEmpty();
    }

    boolean isFull() {
        return list.size() == maxSize;
    }

    void push(CPUProcess item) {
        list.add(item);
    }

    CPUProcess pop() {
        if(this.isEmpty()) {
            throw new RuntimeException("List is empty");
        }

        CPUProcess item = list.get(0);
        list.remove(0);

        return item;
    }
}
