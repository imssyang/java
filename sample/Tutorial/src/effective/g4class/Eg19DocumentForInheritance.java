package effective.g4class;

import java.time.Instant;

/**
 * Item 19: Design and document for inheritance or else prohibit it.
 *   - Class must document its self-use of overridable methods.
 *   - The only way to test a class designed for inheritance is to write subclasses.
 *   - Constructors must not invoke overridable methods.
 *   - The Cloneable and Serializable interfaces present special difficulties
 *     when designing for inheritance. Because the clone and readObject methods behave
 *     a lot like constructors, a similar restriction applies: neither clone nor readObject
 *     may invoke an overridable method, directly or indirectly.
 */
public class Eg19DocumentForInheritance {

    public static class Super {
        // Broken - constructor invokes an overridable method
        public Super() {
            overrideMe();
        }
        public void overrideMe() {
        }
    }

    public static final class Sub extends Super {
        // Blank final, set by constructor
        private final Instant instant;
        Sub() {
            instant = Instant.now();
        }
        // Overriding method invoked by superclass constructor
        @Override public void overrideMe() {
            System.out.println(instant);
        }
        public static void main(String[] args) {
            Sub sub = new Sub();
            sub.overrideMe();
        }
    }

}
