package ECTE331_Project_Part3;

public class MotorController {

    /**
     * Called by Logger (LOW). Does a FIXED AMOUNT OF WORK (iterations),
     * not a fixed amount of TIME. This is critical: if the loop exited on
     * elapsed wall-clock time, it would always finish at ~the same moment
     * regardless of CPU contention.
     */
    public synchronized void moveMotorBusyWork(String threadName, long iterations) {
        long enterTime = System.currentTimeMillis();
        System.out.println("[" + enterTime + "] " + threadName + " ACQUIRED motor (will do "
                + iterations + " units of work)");

        long dummy = 0;
        for (long i = 0; i < iterations; i++) {
            dummy += (i % 7); // meaningless work; prevents JIT from eliminating the loop
        }

        long exitTime = System.currentTimeMillis();
        System.out.println("[" + exitTime + "] " + threadName + " RELEASED motor "
                + "(actual hold time = " + (exitTime - enterTime) + " ms) [checksum=" + dummy + "]");
    }

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