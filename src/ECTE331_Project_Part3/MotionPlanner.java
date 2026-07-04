package ECTE331_Project_Part3;

/**
 * MotionPlannerThread (MEDIUM priority) - Task 3.
 * Continuous CPU-bound work, never touches the motor.
 * NO sleep() in the loop - this is what makes it genuinely compete
 * for CPU time against Logger, which is required to actually
 * demonstrate priority inversion.
 */
public class MotionPlanner extends Thread {

    private volatile boolean keepRunning = true;

    public MotionPlanner() {
        this.setName("MotionPlanner");
        this.setPriority(Thread.NORM_PRIORITY); // priority 5
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
            if (now - lastPrint >= 200) { // print occasionally without giving up CPU via sleep
                System.out.println(getName() + " (MEDIUM) running...");
                lastPrint = now;
            }
        }
        System.out.println(getName() + " (MEDIUM) stopped");
    }
}