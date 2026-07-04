package ECTE331_Project_Part3;

public class MainDriver {
	public static void main(String[] args) throws InterruptedException {
		MotorController motor = new MotorController();
		Log logger = new Log(motor);
		MotionPlanner motion = new MotionPlanner();
		SafetyMonitoring safety = new SafetyMonitoring(motor);
		System.out.println("=== Task 3: Priority Inversion Demonstration ===");
		System.out.println("Priorities -> Safety(HIGH)=" + safety.getPriority() + " Motion(MEDIUM)=" + motion.getPriority() + " Logger(LOW)=" + logger.getPriority());
		System.out.println("==================================================");
		// Step 1: Logger starts first and grabs the motor immediately
		logger.start();
		Thread.sleep(100); // head start so Logger acquires the lock first

		// Step 2: MotionPlanner starts, competing for CPU (not for the motor)
		motion.start();
		// Step 3: SafetyMonitor starts, will block waiting for the motor
		safety.start();
		
		logger.join();
		safety.join();
		motion.stopRunning();
		motion.join();
		System.out.println("==================================================");
		System.out.println("=== Task 3 complete. Safety Monitor wait time = " + safety.waitTime + " ms ===");; }
}