package ECTE331_Project_Part3;

public class MotionPlanner extends Thread {

    private volatile boolean keepRunning = true;

    public MotionPlanner() {
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
            dummy += 1; // continuous CPU-bound work, no sleep
            long now = System.currentTimeMillis();
            if (now - lastPrint >= 200) {
                System.out.println(getName() + " (MEDIUM) running...");
                lastPrint = now;
            }
        }
        System.out.println(getName() + " (MEDIUM) stopped");
    }
}