package ECTE331_Project_Part3;

public class SafetyMonitoring extends Thread {

    private MotorController motor;

    public SafetyMonitoring(MotorController motor) {
        this.motor = motor;
        setName("Safety Monitor (HIGH)");
        setPriority(MAX_PRIORITY);
    }

    @Override
    public void run() {

        long start = System.currentTimeMillis();

        System.out.println(getName() + " waiting for motor...");

        // trigger inheritance check
        motor.applyPriorityInheritance(this);

        motor.acquire(getName());

        long end = System.currentTimeMillis();

        System.out.println(getName() + " finished");
        System.out.println("WAIT TIME = " + (end - start) + " ms");
    }
}