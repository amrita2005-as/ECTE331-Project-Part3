package ECTE331_Project_Part3;
/**
 * MainDriver - Task 1/2 baseline.
 * Starts SafetyMonitor, MotionPlanner, and Logger threads,
 * all sharing one MotorController resource.
 */
public class MainDriver {

    public static void main(String[] args) {
        MotorController motor = new MotorController();

        int cycles = 5; // how many times each thread loops

        SafetyMonitoring safety = new SafetyMonitoring(motor, cycles);
        MotionPlanner motion = new MotionPlanner(motor, cycles);
        Log logger = new Log(motor, cycles);

        System.out.println("=== Starting Real-Time Robotic Arm Controller ===");
        System.out.println("Priorities -> Safety(HIGH)=" + safety.getPriority()
                + " Motion(MEDIUM)=" + motion.getPriority()
                + " Logger(LOW)=" + logger.getPriority());
        System.out.println("===============================================================");

        safety.start();
        motion.start();
        logger.start();

        try {
            safety.join();
            motion.join();
            logger.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("=== All threads completed ===");
    }
}