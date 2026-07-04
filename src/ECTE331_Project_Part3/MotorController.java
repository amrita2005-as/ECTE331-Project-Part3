package ECTE331_Project_Part3;
/**
 * MotorController - the shared resource (critical section).
 * Only one thread may access moveMotor() at a time.
 * synchronized keyword provides mutual exclusion (satisfies Task 2).
 */
public class MotorController {

    /**
     * Simulates moving the robotic arm motor.
     * @param threadName name of the calling thread, for logging
     * @param workTimeMs how long this thread "holds" the motor (simulated work)
     */
    public synchronized void moveMotor(String threadName, long workTimeMs) {
        long enterTime = System.currentTimeMillis();
        System.out.println("[" + enterTime + "] " + threadName + " ACQUIRED motor");

        try {
            Thread.sleep(workTimeMs); // simulate motor operation time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long exitTime = System.currentTimeMillis();
        System.out.println("[" + exitTime + "] " + threadName + " RELEASED motor "
                + "(held for " + (exitTime - enterTime) + " ms)");
    }
}