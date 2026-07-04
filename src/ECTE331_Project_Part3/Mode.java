package ECTE331_Project_Part3;

/**
 * The three scenarios being compared in Task 6.
 */
public enum Mode {
    BASELINE,     // no protocol - demonstrates priority inversion
    INHERITANCE,  // SafetyMonitor boosts Logger's priority directly
    CEILING       // MotorController boosts ANY caller to a fixed ceiling
}