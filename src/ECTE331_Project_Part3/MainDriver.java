package ECTE331_Project_Part3;

public class MainDriver {

    public static void main(String[] args) {

        MotorController motor = new MotorController();

        Log logger = new Log(motor);
        SafetyMonitoring safety = new SafetyMonitoring(motor);
        MotionPlanner motion = new MotionPlanner();

        // FORCE inversion
        logger.start();

        try { Thread.sleep(100); } catch (Exception e) {}

        safety.start();
        motion.start();
    }
}