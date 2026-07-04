package ECTE331_Project_Part3;

public class SafetyMonitoring extends Thread {

    private MotorController motor;

    public SafetyMonitoring(MotorController motor) {
        this.motor = motor;
        setName("Safety Monitor (HIGH)");
        setPriority(MAX_PRIORITY); // highest priority
    }

    @Override
    public void run() {

        long start = System.currentTimeMillis(); // start waiting time

        System.out.println("Safety Monitor waiting for motor...");
        
        motor.useMotor(getName()); // blocked if low-priority holds lock

        long end = System.currentTimeMillis(); // end waiting time

        // Measure delay caused by inversion
        System.out.println("Safety Monitor finished");
        System.out.println("WAIT TIME = " + (end - start) + " ms");
    }
}