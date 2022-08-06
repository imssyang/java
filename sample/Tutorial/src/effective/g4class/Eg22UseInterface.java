package effective.g4class;

/**
 * Item 22: Use interfaces only to define types
 */
public class Eg22UseInterface {

    // Constant interface antipattern - do not use!
    public interface PhysicalConstants {
        // Avogadro's number (1/mol)
        static final double AVOGADROS_NUMBER = 6.022_140_857e23;
        // Boltzmann constant (J/K)
        static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
        // Mass of the electron (kg)
        static final double ELECTRON_MASS = 9.109_383_56e-31;
    }

    // Constant utility class
    public class PhysicalConstants2 {
        private PhysicalConstants2() { } // Prevents instantiation
        public static final double AVOGADROS_NUMBER = 6.022_140_857e23;
        public static final double BOLTZMANN_CONST = 1.380_648_52e-23;
        public static final double ELECTRON_MASS = 9.109_383_56e-31;
    }

}
