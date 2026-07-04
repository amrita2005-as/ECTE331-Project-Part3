package ECTE331_Project_Part3;
/**
 * Log thread - LOW priority.
 * Records system activity.
 */
public class Log extends Thread {

    private final MotorController motor;
    private final int cycles;

    public Log(MotorController motor, int cycles) {
        this.motor = motor;
        this.cycles = cycles;
        this.setName("Logger");
        this.setPriority(Thread.MIN_PRIORITY); // priority 1
    }

    @Override
    public void run() {
        for (int i = 1; i <= cycles; i++) {
            System.out.println(getName() + " (LOW) recording activity... [cycle " + i + "]");

            motor.moveMotor(getName() + " (LOW)", 1000); // longer hold, to later show inversion

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println(getName() + " (LOW) finished all cycles.");
    }
}