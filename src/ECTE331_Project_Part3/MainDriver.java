package ECTE331_Project_Part3;

public class MainDriver {

    private static final int CEILING_PRIORITY = Thread.MAX_PRIORITY; // 10

    public static void main(String[] args) throws InterruptedException {
        MotorController motor = new MotorController(CEILING_PRIORITY);

        Log logger = new Log(motor);
        MotionPlanner motion = new MotionPlanner();
        SafetyMonitoring safety = new SafetyMonitoring(motor);

        System.out.println("=== Priority Ceiling Demonstration ===");
        System.out.println("Ceiling priority = " + CEILING_PRIORITY);
        System.out.println("Priorities -> Safety(HIGH)=" + safety.getPriority()
                + " Motion(MEDIUM)=" + motion.getPriority()
                + " Logger(LOW)=" + logger.getPriority());
        System.out.println("==================================================");

        logger.start();
        Thread.sleep(100); // head start so Logger enters the section first

        motion.start();
        safety.start();

        logger.join();
        safety.join();

        motion.stopRunning();
        motion.join();

        System.out.println("==================================================");
        System.out.println("=== Safety Monitor wait time = "
                + safety.waitTime + " ms ===");
    }
}