package ECTE331_Project_Part3;

public class Log extends Thread {

    private final MotorController motor;

    // Use the SAME value tuned in Task 3/4, so results are comparable.
    private static final long WORK_ITERATIONS = 2_000_000_000L;

    public Log(MotorController motor) {
        this.motor = motor;
        this.setName("Logger");
        this.setPriority(Thread.MIN_PRIORITY);
    }

    @Override
    public void run() {
        System.out.println(getName() + " (LOW) attempting to enter motor");
        long requestTime = System.currentTimeMillis();
        motor.enterMotorBusyWork(this, getName() + " (LOW)", WORK_ITERATIONS, requestTime);
        System.out.println(getName() + " (LOW) finished logging");
    }
}