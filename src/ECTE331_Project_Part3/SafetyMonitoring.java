package ECTE331_Project_Part3;

public class SafetyMonitoring extends Thread {

    private final MotorController motor;
    public long waitTime = -1;

    public SafetyMonitoring(MotorController motor) {
        this.motor = motor;
        this.setName("SafetyMonitor");
        this.setPriority(Thread.MAX_PRIORITY);
    }

    @Override
    public void run() {
        System.out.println(getName() + " (HIGH) attempting to enter motor");
        long requestTime = System.currentTimeMillis();
        waitTime = motor.enterMotorQuick(this, getName() + " (HIGH)", 300, requestTime);
        System.out.println(getName() + " (HIGH) finished");
        System.out.println(">>> WAIT TIME = " + waitTime + " ms <<<");
    }
}