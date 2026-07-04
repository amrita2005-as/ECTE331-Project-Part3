package ECTE331_Project_Part3;

public class MotorController {

    public synchronized void moveMotorBusyWork(String threadName, long iterations) {
        long enterTime = System.currentTimeMillis();
        System.out.println("[" + enterTime + "] " + threadName + " ACQUIRED motor (will do "
                + iterations + " units of work)");

        long dummy = 0;
        for (long i = 0; i < iterations; i++) {
            dummy += (i % 7);
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