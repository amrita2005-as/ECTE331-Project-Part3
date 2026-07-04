package ECTE331_Project_Part3;

public class MotionPlanner extends Thread {

    public MotionPlanner() {
        setName("Motion Planner (MEDIUM)");
        setPriority(NORM_PRIORITY);
    }

    @Override
    public void run() {

        while (true) {

            System.out.println(getName() + " running...");

            try {
                Thread.sleep(200);
            } catch (Exception e) {}

        }
    }
}