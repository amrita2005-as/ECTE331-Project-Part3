package ECTE331_Project_Part3;

public class SafetyMonitoring extends Thread {

    private final MotorController motor;
    private final Log logger; // needed only for INHERITANCE mode
    private final Mode mode;
    private final boolean verbose;
    public long waitTime = -1;

    public SafetyMonitoring(MotorController motor, Log logger, Mode mode, boolean verbose) {
        this.motor = motor;
        this.logger = logger;
        this.mode = mode;
        this.verbose = verbose;
        this.setName("SafetyMonitor");
        this.setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public void run() {
        if (verbose) System.out.println(getName() + " (HIGH) waiting for motor...");

        int originalLoggerPriority = -1;
        if (mode == Mode.INHERITANCE) {
            originalLoggerPriority = logger.getPriority();
            if (verbose) {
                System.out.println(">>> INHERITANCE APPLIED: " + logger.getName()
                        + " boosted from " + originalLoggerPriority + " to " + this.getPriority());
            }
            logger.setPriority(this.getPriority());
        }

        long requestTime = System.currentTimeMillis();
        waitTime = motor.enterQuick(this, getName() + " (HIGH)", 300, requestTime, mode, verbose);

        if (mode == Mode.INHERITANCE) {
            logger.setPriority(originalLoggerPriority);
            if (verbose) {
                System.out.println(">>> INHERITANCE RELEASED: " + logger.getName()
                        + " restored to " + originalLoggerPriority);
            }
        }

        if (verbose) {
            System.out.println(getName() + " (HIGH) finished. WAIT TIME = " + waitTime + " ms");
        }
    }
}