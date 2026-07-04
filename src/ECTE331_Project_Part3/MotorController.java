package ECTE331_Project_Part3;

public class MotorController {

    private final int ceilingPriority;

    public MotorController(int ceilingPriority) {
        this.ceilingPriority = ceilingPriority;
    }

    private void applyCeiling(Thread caller, String label, int originalPriority) {
        System.out.println("*** PRIORITY CEILING APPLIED ***");
        System.out.println(label + " priority boosted from " + originalPriority
                + " to CEILING (" + ceilingPriority + ")");
        caller.setPriority(ceilingPriority);
    }

    private void restoreOriginal(Thread caller, String label, int originalPriority) {
        caller.setPriority(originalPriority);
        System.out.println(label + " priority restored to " + originalPriority);
    }

    /**
     * Used by Log (LOW). Fixed amount of WORK, not fixed TIME - same
     * reasoning as Task 3/4: this is what allows contention to actually
     * show up as a longer wall-clock hold time.
     */
    public synchronized long enterMotorBusyWork(Thread caller, String label, long iterations, long requestTime) {
        int originalPriority = caller.getPriority();
        applyCeiling(caller, label, originalPriority);

        long enterTime = System.currentTimeMillis();
        long waited = enterTime - requestTime;
        System.out.println("[" + enterTime + "] " + label + " ENTERED motor section (waited " + waited + " ms)");

        long dummy = 0;
        for (long i = 0; i < iterations; i++) {
            dummy += (i % 7);
        }

        long exitTime = System.currentTimeMillis();
        System.out.println("[" + exitTime + "] " + label + " EXITING motor section "
                + "(actual hold time = " + (exitTime - enterTime) + " ms) [checksum=" + dummy + "]");

        restoreOriginal(caller, label, originalPriority);
        return waited;
    }

    /**
     * Used by SafetyMonitoring (HIGH). Short, simple critical section.
     */
    public synchronized long enterMotorQuick(Thread caller, String label, long workTimeMs, long requestTime) {
        int originalPriority = caller.getPriority();
        applyCeiling(caller, label, originalPriority);

        long enterTime = System.currentTimeMillis();
        long waited = enterTime - requestTime;
        System.out.println("[" + enterTime + "] " + label + " ENTERED motor section (waited " + waited + " ms)");

        try {
            Thread.sleep(workTimeMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long exitTime = System.currentTimeMillis();
        System.out.println("[" + exitTime + "] " + label + " EXITING motor section");

        restoreOriginal(caller, label, originalPriority);
        return waited;
    }
}