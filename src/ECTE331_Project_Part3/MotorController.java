package ECTE331_Project_Part3;

/**
 * Unified MotorController for Task 6.
 * The ceiling boost is ONLY applied when mode == CEILING.
 * For BASELINE and INHERITANCE, this class does nothing extra -
 * inheritance's boosting is handled by SafetyMonitorThread instead,
 * since inheritance is about one thread reacting to another,
 * not a property of the resource itself.
 */
public class MotorController {

    private final int ceilingPriority;

    public MotorController(int ceilingPriority) {
        this.ceilingPriority = ceilingPriority;
    }

    /**
     * Used by Logger (LOW). Fixed amount of WORK (not time), so contention
     * shows up as a longer wall-clock hold time.
     */
    public synchronized void enterBusyWork(Thread caller, String label, long iterations,
                                            long requestTime, Mode mode, boolean verbose) {
        int originalPriority = caller.getPriority();

        if (mode == Mode.CEILING) {
            if (verbose) {
                System.out.println("*** PRIORITY CEILING APPLIED *** " + label
                        + " boosted from " + originalPriority + " to " + ceilingPriority);
            }
            caller.setPriority(ceilingPriority);
        }

        long enterTime = System.currentTimeMillis();
        if (verbose) {
            System.out.println("[" + enterTime + "] " + label + " ACQUIRED motor (waited "
                    + (enterTime - requestTime) + " ms)");
        }

        long dummy = 0;
        for (long i = 0; i < iterations; i++) {
            dummy += (i % 7);
        }

        long exitTime = System.currentTimeMillis();
        if (verbose) {
            System.out.println("[" + exitTime + "] " + label + " RELEASED motor (hold time = "
                    + (exitTime - enterTime) + " ms) [checksum=" + dummy + "]");
        }

        if (mode == Mode.CEILING) {
            caller.setPriority(originalPriority);
            if (verbose) {
                System.out.println(label + " priority restored to " + originalPriority);
            }
        }
    }

    /**
     * Used by SafetyMonitor (HIGH). Short, simple critical section.
     * @return the wait time (requestTime -> lock acquired)
     */
    public synchronized long enterQuick(Thread caller, String label, long workTimeMs,
                                         long requestTime, Mode mode, boolean verbose) {
        int originalPriority = caller.getPriority();

        if (mode == Mode.CEILING) {
            if (verbose) {
                System.out.println("*** PRIORITY CEILING APPLIED *** " + label
                        + " boosted from " + originalPriority + " to " + ceilingPriority);
            }
            caller.setPriority(ceilingPriority);
        }

        long enterTime = System.currentTimeMillis();
        long waited = enterTime - requestTime;
        if (verbose) {
            System.out.println("[" + enterTime + "] " + label + " ACQUIRED motor (waited " + waited + " ms)");
        }

        try {
            Thread.sleep(workTimeMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long exitTime = System.currentTimeMillis();
        if (verbose) {
            System.out.println("[" + exitTime + "] " + label + " RELEASED motor");
        }

        if (mode == Mode.CEILING) {
            caller.setPriority(originalPriority);
            if (verbose) {
                System.out.println(label + " priority restored to " + originalPriority);
            }
        }

        return waited;
    }
}