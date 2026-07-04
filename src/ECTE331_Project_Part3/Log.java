package ECTE331_Project_Part3;

public class Log extends Thread {

    private final MotorController motor;

    // TUNE THIS: run once and check "actual hold time" printed.
    // Adjust this number up/down until the UNCONTENDED baseline
    // (Task 3 without MotionPlanner running) takes roughly 3-4 seconds
    // on your machine. Faster machine = bigger number needed.
    private static final long WORK_ITERATIONS = 2_000_000_000L;

    public Log(MotorController motor) {
        this.motor = motor;
        this.setName("Logger");
        this.setPriority(Thread.MIN_PRIORITY);
    }

    @Override
    public void run() {
        System.out.println(getName() + " (LOW) attempting to acquire motor");
        motor.moveMotorBusyWork(getName() + " (LOW)", WORK_ITERATIONS);
        System.out.println(getName() + " (LOW) finished");
    }
}