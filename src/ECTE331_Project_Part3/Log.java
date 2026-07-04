package ECTE331_Project_Part3;

public class Log extends Thread {

    private MotorController motor;

    public Log(MotorController motor) {
        this.motor = motor;
        setName("Logger (LOW)");
        setPriority(MIN_PRIORITY); // lowest priority
    }

    @Override
    public void run() {

        try {
            Thread.sleep(200); // ensure other threads are ready
        } catch (Exception e) {}

        System.out.println("Logger attempting to acquire motor");

        motor.useMotor(getName());

        System.out.println("Logger completed");
    }
}