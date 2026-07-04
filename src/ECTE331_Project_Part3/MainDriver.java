package ECTE331_Project_Part3;

/**
 * MainDriver - Task 4: Priority Inheritance Demonstration.
 *
 * Same staged sequence as Task 3:
 *  1. Log (LOW) starts first, acquires motor, does busy work.
 *  2. MotionPlanner (MEDIUM) starts, competes for CPU.
 *  3. SafetyMonitoring (HIGH) starts - but THIS TIME, before it blocks,
 *     it immediately boosts Log's priority (inheritance), so Log can
 *     finish faster despite MotionPlanner's interference.
 *
 * Compare the resulting wait time against Task 3's baseline (3860ms).
 */
public class MainDriver {

    public static void main(String[] args) throws InterruptedException {
        MotorController motor = new MotorController();

        Log logger = new Log(motor);
        MotionPlanner motion = new MotionPlanner();
        SafetyMonitoring safety = new SafetyMonitoring(motor, logger); // note: logger passed in

        System.out.println("=== Task 4: Priority Inheritance Demonstration ===");
        System.out.println("Priorities -> Safety(HIGH)=" + safety.getPriority() + " Motion(MEDIUM)=" + motion.getPriority() + " Logger(LOW)=" + logger.getPriority());
        System.out.println("==================================================");

        logger.start();
        Thread.sleep(100); // head start so Logger acquires the lock first

        motion.start();
        safety.start();

        logger.join();
        safety.join();

        motion.stopRunning();
        motion.join();

        System.out.println("==================================================");
        System.out.println("=== Done. Safety Monitor wait time = "+ safety.waitTime + " ms ===");
    }
}