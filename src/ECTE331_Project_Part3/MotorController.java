package ECTE331_Project_Part3;

public class MotorController {

    private Thread owner = null;

    // remaining "work units"
    private int remainingWork = 300;

    private boolean inherited = false;

    public synchronized void acquire(String name) {

        owner = Thread.currentThread();

        System.out.println("[" + System.currentTimeMillis() + "] "
                + name + " acquired motor");

        while (remainingWork > 0) {

            // simulate faster execution if inheritance is active
            int step = inherited ? 5 : 1;

            remainingWork -= step;

            try {
                Thread.sleep(10); // small delay to simulate execution
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("[" + System.currentTimeMillis() + "] "
                + name + " released motor");

        owner = null;
        remainingWork = 300;
        inherited = false;
    }

    // priority inheritance simulation
    public synchronized void applyPriorityInheritance(Thread requester) {

        if (owner != null &&
            owner.getPriority() < requester.getPriority()) {

            System.out.println("\n*** PRIORITY INHERITANCE ACTIVATED ***");
            System.out.println(owner.getName()
                    + " inherits priority from "
                    + requester.getName());

            owner.setPriority(requester.getPriority());

            inherited = true;

            System.out.println();
        }
    }

    public Thread getOwner() {
        return owner;
    }
}