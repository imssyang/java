package effective.g2object;

/**
 * Item 4: Enforce noninstantiability with a private constructor.
 */
public class Eg4Noninstantiability {

    // Noninstantiable utility class
    public class UtilityClass {
        // Suppress default constructor for noninstantiability
        private UtilityClass() {
            throw new AssertionError();
        }
    }

}
