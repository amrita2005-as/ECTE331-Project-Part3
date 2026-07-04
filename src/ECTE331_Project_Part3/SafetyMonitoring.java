package ECTE331_Project_Part3;

/**
 * SafetyMonitoring - HIGH priority.
 * Detects emergency conditions and stops the arm.
 */
public class SafetyMonitoring extends Thread {

    private final MotorController motor;
    private final int cycles;

    public SafetyMonitoring(MotorController motor, int cycles) {
        this.motor = motor;
        this.cycles = cycles;
        this.setName("SafetyMonitor");
        this.setPriority(Thread.MAX_PRIORITY); // priority 10
    }

    @Override
    public void run() {
        for (int i = 1; i <= cycles; i++) {
            System.out.println(getName() + " (HIGH) checking for emergency conditions... [cycle " + i + "]");

            long requestTime = System.currentTimeMillis();
            System.out.println(getName() + " (HIGH) requesting motor at " + requestTime);

            motor.moveMotor(getName() + " (HIGH)", 500); // short critical section

            try {
                Thread.sleep(300); // time between checks
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(getName() + " (HIGH) finished all cycles.");
    }
}