package ECTE331_Project_Part3;

public class MotionPlanner extends Thread {

    public MotionPlanner() {
        setName("Motion Planner (MEDIUM)");
        setPriority(NORM_PRIORITY); // medium priority
    }

    @Override
    public void run() {

        // Simulates continuous system activity causing CPU interference
        while (true) {
            System.out.println("Motion Planner running...");
            try {
                Thread.sleep(300);
            } catch (Exception e) {}
        }
    }
}


