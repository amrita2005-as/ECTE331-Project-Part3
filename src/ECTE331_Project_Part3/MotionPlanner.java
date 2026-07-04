package ECTE331_Project_Part3;

public class MotionPlanner extends Thread {

    private volatile boolean keepRunning = true;
    private final boolean verbose;

    public MotionPlanner(boolean verbose) {
        this.verbose = verbose;
        this.setName("MotionPlanner");
        this.setPriority(Thread.NORM_PRIORITY);
    }

    public void stopRunning() {
        keepRunning = false;
    }

    @Override
    public void run() {
        long dummy = 0;
        long lastPrint = System.currentTimeMillis();
        while (keepRunning) {
            dummy += 1; // continuous CPU-bound work, no sleep - genuinely contends for CPU
            long now = System.currentTimeMillis();
            if (verbose && now - lastPrint >= 200) {
                System.out.println(getName() + " (MEDIUM) running...");
                lastPrint = now;
            }
        }
    }
}