package ECTE331_Project_Part3;

/**
 * SafetyMonitorThread (HIGH priority) - Task 3.
 * Requests the motor AFTER Logger already holds it, then measures
 * how long it is blocked waiting -> this is the inversion delay.
 */
public class SafetyMonitoring extends Thread {

    private final MotorController motor;
    public long waitTime = -1; // exposed so MainDriver/PerformanceEvaluator can read it

    public SafetyMonitoring(MotorController motor) {
        this.motor = motor;
        this.setName("SafetyMonitor");
        this.setPriority(Thread.MAX_PRIORITY); // priority 10
    }

    @Override
    public void run() {
        System.out.println(getName() + " (HIGH) waiting for motor...");
        long requestTime = System.currentTimeMillis();

        waitTime = motor.moveMotorQuick(getName() + " (HIGH)", 300, requestTime);

        System.out.println(getName() + " (HIGH) finished");
        System.out.println(">>> WAIT TIME = " + waitTime + " ms <<<");
    }
}