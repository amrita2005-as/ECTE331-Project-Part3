package ECTE331_Project_Part3;
/**
 * MotionPlannerThread (MEDIUM priority) - Task 3.
 * Deliberately runs continuous CPU-bound work (does NOT need the motor)
 * to preempt Logger and delay its release of the resource.
 * This is the mechanism that causes priority inversion.
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
        while (keepRunning) {
            dummy += 1; // CPU-bound busy work, no motor access needed
            System.out.println(getName() + " (MEDIUM) running...");
            try {
                Thread.sleep(200); // small pause just so console isn't flooded
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(getName() + " (MEDIUM) stopped");
    }
} 