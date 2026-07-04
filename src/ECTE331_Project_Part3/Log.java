package ECTE331_Project_Part3;
/**
 * LoggerThread (LOW priority) - Task 3.
 * Deliberately acquires the motor FIRST and does busy-work while holding it,
 * so it can be preempted by MotionPlanner (MEDIUM) on the CPU.
 */
public class Log extends Thread {

    private final MotorController motor;

    public Log(MotorController motor) {
        this.motor = motor;
        this.setName("Logger");
        this.setPriority(Thread.MIN_PRIORITY); // priority 1
    }

    @Override
    public void run() {
        System.out.println(getName() + " (LOW) attempting to acquire motor");
        // Holds the motor for 4000ms of actual CPU work -> long enough for
        // MotionPlanner to interfere and for SafetyMonitor to be seen waiting.
        motor.moveMotorBusyWork(getName() + " (LOW)", 4000);
        System.out.println(getName() + " (LOW) finished");
    }
}