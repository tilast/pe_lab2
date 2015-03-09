package me.kroosh;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ihorkroosh on 3/6/15.
 */
public class OperatingSystem {
    private static Random rand = new Random(157);
    private List<Thread> threads = new ArrayList<>();
    private CPUQueue q1;
    private CPUQueue q2;
    private CPUProcessor p1;
    private CPUProcessor p2;
    private CPUProcess newProcess;
    private Object monitor = new Object();

    public static int getRandomNumber(int min, int max) {
        return min + (rand.nextInt() % (max - min + 1));
    }

    OperatingSystem() {
        p1 = new CPUProcessor("Processor 1");
        p2 = new CPUProcessor("Processor 2");
        q1 = new CPUQueue(100);
        q2 = new CPUQueue(100);

        start();
    }

    public void start() {
        p1.start();
        p2.start();

//        while(true) {
        for (int i = 0; i < 10; ++i) {
            System.out.println("attempt " + i + ", q1size:" + q1.size() + ", q2size: " + q2.size());
            newProcess = new CPUProcess(getRandomNumber(0, 10));

            if (!p1.isBusy()) {
                p1.loadProcess(newProcess);
            } else if (!p2.isBusy()) {
                p2.loadProcess(newProcess);
            } else {
                System.out.println("push to queue");
                synchronized (monitor) {
                    if (!q1.isFull() || !q2.isFull()) {
                        (q1.size() < q2.size() ? q1 : q2).push(newProcess);
                    } else if (q1.isFull()) {
                        q2.push(newProcess);
                    } else if (q2.isFull()) {
                        q1.push(newProcess);
                    }
                }
            }

            try {
                Thread.sleep(1300);
            } catch (InterruptedException e) {
            }

        }
    }

    private class TakeFromQueue implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (monitor) {
                    CPUQueue requiredQueue = (q1.size() < q2.size() ? q1 : q2);
                    if (!requiredQueue.isEmpty()) {
                        if (!p1.isBusy()) {
                            p1.loadProcess(requiredQueue.pop());
                        } else if (!p2.isBusy()) {
                            p2.loadProcess(requiredQueue.pop());
                        }
                    }
                }
            }
        }
    }
}
