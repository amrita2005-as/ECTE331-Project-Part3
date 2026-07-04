package ECTE331_Project_Part3;

/**
 * SafetyMonitoring (HIGH priority) - Task 4: Priority Inheritance.
 *
 * Since Java has no native priority inheritance, we SIMULATE it:
 * before requesting the motor, SafetyMonitoring directly boosts the
 * priority of the thread it knows is holding the resource (Log) up
 * to its own (HIGH) priority. This stops MotionPlanner (MEDIUM) from
 * preempting Log, so Log finishes its critical section faster.
 * Once SafetyMonitoring acquires the motor, it restores Log's original
 * priority.
 */
public class SafetyMonitoring extends Thread {

    private final MotorController motor;
    private final Log logger; // reference to the thread holding the resource
    public long waitTime = -1;

    public SafetyMonitoring(MotorController motor, Log logger) {
        this.motor = motor;
        this.logger = logger;
        this.setName("SafetyMonitor");
        this.setPriority(Thread.MAX_PRIORITY); // priority 10
    }

    @Override
    public void run() {
        System.out.println(getName() + " (HIGH) waiting for motor...");

        // --- PRIORITY INHERITANCE SIMULATION ---
        int originalLoggerPriority = logger.getPriority();
        System.out.println(">>> INHERITANCE APPLIED: " + logger.getName()
                + " priority boosted from " + originalLoggerPriority
                + " to " + this.getPriority() + " <<<");
        logger.setPriority(this.getPriority());
        // -----------------------------------------

        long requestTime = System.currentTimeMillis();
        waitTime = motor.moveMotorQuick(getName() + " (HIGH)", 300, requestTime);

        // --- RESTORE Logger's original priority now that it no longer holds the lock ---
        logger.setPriority(originalLoggerPriority);
        System.out.println(">>> INHERITANCE RELEASED: " + logger.getName()
                + " priority restored to " + originalLoggerPriority + " <<<");
        // ----------------------------------------------------------------------------

        System.out.println(getName() + " (HIGH) finished");
        System.out.println(">>> WAIT TIME = " + waitTime + " ms <<<");
    }
}