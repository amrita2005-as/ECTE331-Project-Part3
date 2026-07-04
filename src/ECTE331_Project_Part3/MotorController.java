package ECTE331_Project_Part3;

public class MotorController {
	//Thread that is currently holding the motor
	private Thread holder = null;

    // Critical section: only one thread can access at a time
    public synchronized void useMotor(String threadName) {
    	holder = Thread.currentThread();
    	// Log entry time and thread access
        System.out.println("[" + System.currentTimeMillis() + "] "
                + threadName + " acquired motor");

        try {
            // Simulate long motor operation (creates inversion window)
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Log release time
        System.out.println("[" + System.currentTimeMillis() + "] "
                + threadName + " released motor");
        holder = null;
    }
    public Thread getOwner() {
    	return holder;
    }
}