package ECTE331_Project_Part3;

public class MainDriver {

    public static void main(String[] args) {

        MotorController motor = new MotorController();

        Log logger = new Log(motor);
        SafetyMonitoring safety = new SafetyMonitoring(motor);
        MotionPlanner motion = new MotionPlanner();

        // Force priority inversion scenario
        logger.start();

        try {
            Thread.sleep(100); // ensure logger acquires first
        } catch (Exception e) {}

        safety.start(); // high priority arrives and blocks
        motion.start(); // medium keeps running (interference)
    }
}