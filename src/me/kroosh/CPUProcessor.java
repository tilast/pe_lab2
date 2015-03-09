package me.kroosh;

/**
 * Created by ihorkroosh on 3/5/15.
 */
public class CPUProcessor extends Thread {
    public static final int PROCESSOR_DELAY_COEF = 1000;
    public static final int ITERATIONS_DELAY_COEF = 3000;

    private boolean busy;
    private CPUProcess workingProcess;

    private String processorName;

    CPUProcessor(String name) {
        processorName = name;
    }

    public boolean isBusy() {
        return busy;
    }

    public synchronized void loadProcess(CPUProcess process) {
        workingProcess = process;
    }

    @Override
    public void run() {
        while(true) {
            if(workingProcess != null) {
                System.out.println("Start working processor " + processorName + " with process with id" + workingProcess.getId());
                busy = true;
                try {
                    sleep(PROCESSOR_DELAY_COEF * Math.abs(workingProcess.getCompexity()) + 1300);
                } catch ( InterruptedException e ) {}
                busy = false;
                System.out.println("Finished working processor " + processorName + " with process with id" + workingProcess.getId());
                workingProcess = null;
            }
            try {
                sleep(ITERATIONS_DELAY_COEF);
            } catch ( InterruptedException e ) {}
        }
    }
}
