package ECTE331_Project_Part3;

/**
 * MotionPlanner - MEDIUM priority.
 * Sends movement commands to the motor.
 */
public class MotionPlanner extends Thread {

    private final MotorController motor;
    private final int cycles;

    public MotionPlanner(MotorController motor, int cycles) {
        this.motor = motor;
        this.cycles = cycles;
        this.setName("MotionPlanner");
        this.setPriority(Thread.NORM_PRIORITY); // priority 5
    }

    @Override
    public void run() {
        for (int i = 1; i <= cycles; i++) {
            System.out.println(getName() + " (MEDIUM) planning movement... [cycle " + i + "]");

            motor.moveMotor(getName() + " (MEDIUM)", 700);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(getName() + " (MEDIUM) finished all cycles.");
    }
}