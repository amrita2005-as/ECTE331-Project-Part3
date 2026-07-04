package ECTE331_Project_Part3;

public class Log extends Thread {

    private MotorController motor;

    public Log(MotorController motor) {
        this.motor = motor;
        setName("Logger (LOW)");
        setPriority(MIN_PRIORITY);
    }

    @Override
    public void run() {

        System.out.println(getName() + " trying to acquire motor");

        motor.acquire(getName());

        System.out.println(getName() + " finished");
    }
}