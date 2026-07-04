package ECTE331_Project_Part3;
/**
 * MotorController - shared resource for Task 3 (priority inversion demo).
 */
public class MotorController {

    /**
     * Called by Logger (LOW). Holds the lock while doing CPU-bound busy work,
     * NOT sleep, so a higher-priority thread on the CPU can genuinely delay it.
     */
    public synchronized void moveMotorBusyWork(String threadName, long workTimeMs) {
        long enterTime = System.currentTimeMillis();
        System.out.println("[" + enterTime + "] " + threadName + " ACQUIRED motor (will busy-work for " + workTimeMs + " ms)");

        long start = System.currentTimeMillis();
        long dummy = 0;
        // Busy loop instead of sleep -> this thread actually needs CPU time
        while (System.currentTimeMillis() - start < workTimeMs) {
            dummy += 1; // meaningless work, just to occupy the CPU
        }

        long exitTime = System.currentTimeMillis();
        System.out.println("[" + exitTime + "] " + threadName + " RELEASED motor " + "(actual hold time = " + (exitTime - enterTime) + " ms)");
    }

    /**
     * Called by Safety Monitor (HIGH). Short, simple critical section.
     * requestTime is passed in so we can measure the TRUE waiting time
     * (time between REQUESTING the lock and actually ACQUIRING it) -
     * not the time including how long it holds the lock afterwards.
     * @return the wait time in ms (requestTime -> acquired time)
     */
    public synchronized long moveMotorQuick(String threadName, long workTimeMs, long requestTime) {
        long enterTime = System.currentTimeMillis();
        long waited = enterTime - requestTime;
        System.out.println("[" + enterTime + "] " + threadName + " ACQUIRED motor (waited " + waited + " ms)");
        try {
            Thread.sleep(workTimeMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        long exitTime = System.currentTimeMillis();
        System.out.println("[" + exitTime + "] " + threadName + " RELEASED motor");
        return waited;
    }
}