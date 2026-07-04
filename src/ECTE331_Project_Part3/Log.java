package ECTE331_Project_Part3;

public class Log extends Thread {

    private final MotorController motor;
    private final long iterations;
    private final Mode mode;
    private final boolean verbose;

    public Log(MotorController motor, long iterations, Mode mode, boolean verbose) {
        this.motor = motor;
        this.iterations = iterations;
        this.mode = mode;
        this.verbose = verbose;
        this.setName("Logger");
        this.setPriority(Thread.MIN_PRIORITY);
    }

    @Override
    public void run() {
        if (verbose) System.out.println(getName() + " (LOW) attempting to acquire motor");
        long requestTime = System.currentTimeMillis();
        motor.enterBusyWork(this, getName() + " (LOW)", iterations, requestTime, mode, verbose);
        if (verbose) System.out.println(getName() + " (LOW) finished");
    }
}