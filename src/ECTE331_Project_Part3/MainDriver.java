package ECTE331_Project_Part3;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Task 6: Performance Evaluation.
 * Runs BASELINE, INHERITANCE, and CEILING scenarios multiple times each,
 * collects Safety Monitor's wait time for every run, then reports
 * averages (and min/max) for comparison.
 */
public class MainDriver {
	private static final long WORK_ITERATIONS = 2_000_000_000L; // same as Task 3/4/5
	private static final int CEILING_PRIORITY = Thread.MAX_PRIORITY;
	private static final int RUNS_PER_MODE = 10;

	public static void main(String[] args) throws InterruptedException {
		Map<Mode, List<Long>> results = new EnumMap<>(Mode.class);
		for (Mode mode : Mode.values()) {
			results.put(mode, new ArrayList<>());
			}

		// Warm-up: run a few throwaway scenarios first so the JIT compiler
		// optimizes the hot loop BEFORE we start recording real measurements.
		// Without this, early runs are unfairly slower than later ones,
		// regardless of which mode they belong to.
		for (int i = 0; i < 3; i++) {
			runScenario(Mode.BASELINE, -1);
			}
		// INTERLEAVED execution: one trial of each mode per round, repeated.
		// This spreads system drift (JIT state, background load, thermal
		// effects) evenly across all three modes, instead of letting
		// whichever mode runs last unfairly absorb all the drift.
		for (int run = 1; run <= RUNS_PER_MODE; run++) {
			for (Mode mode : Mode.values()) {
				long wait = runScenario(mode, run);
				results.get(mode).add(wait);
				System.out.println("[" + mode + "] Run " + run + " -> wait time = " + wait + " ms");
				}
			}

		printSummary(results);
		}

	/**
     * Runs one instance of the staged scenario under the given mode.
     * Set verbose=false to suppress detailed per-thread logs during
     * bulk runs (set true if you want to see full detail for a single run).
     */
	private static long runScenario(Mode mode, int runNumber) throws InterruptedException {
		boolean verbose = false; // flip to true if you want full logs for debugging
		MotorController motor = new MotorController(CEILING_PRIORITY);
		Log logger = new Log(motor, WORK_ITERATIONS, mode, verbose);
		MotionPlanner motion = new MotionPlanner(verbose);
		SafetyMonitoring safety = new SafetyMonitoring(motor, logger, mode, verbose);
		logger.start();
		Thread.sleep(100); // head start so Logger acquires the lock first

		motion.start();
	    safety.start();

	    logger.join();
	    safety.join();

	    motion.stopRunning();
	    motion.join();

	    return safety.waitTime;
	}
	private static void printSummary(Map<Mode, List<Long>> results) {
		System.out.println("\n\n===================== SUMMARY =====================");
		System.out.printf("%-15s %-10s %-10s %-10s %-10s%n", "Mode", "Avg(ms)", "Min(ms)", "Max(ms)", "Runs");
		for (Mode mode : Mode.values()) {
			List<Long> waits = results.get(mode);
			double avg = waits.stream().mapToLong(Long::longValue).average().orElse(0);
			long min = waits.stream().mapToLong(Long::longValue).min().orElse(0);
			long max = waits.stream().mapToLong(Long::longValue).max().orElse(0);
			System.out.printf("%-15s %-10.1f %-10d %-10d %-10d%n", mode, avg, min, max, waits.size());
			}
		System.out.println("====================================================");
		}
	
}