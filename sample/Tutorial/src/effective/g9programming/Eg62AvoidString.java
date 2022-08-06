package effective.g9programming;

/**
 * Item 62: Avoid strings where other types are more appropriate
 *   - Strings are poor substitutes for other value types.
 *   - Strings are poor substitutes for enum types.
 *   - Strings are poor substitutes for aggregate types.
 *   - Strings are poor substitutes for capabilities.
 */
public class Eg62AvoidString {

    // Broken - inappropriate use of string as capability!
    public static class ThreadLocal {
        private ThreadLocal() { } // Noninstantiable

        // Sets the current thread's value for the named variable.
        public static void set(String key, Object value) { }

        // Returns the current thread's value for the named variable.
        public static Object get(String key) { return null; }
    }

    public static class ThreadLocal2 {
        private ThreadLocal2() { } // Noninstantiable
        public static class Key { // (Capability)
            Key() { }
        }
        // Generates a unique, unforgeable key
        public static Key getKey() {
            return new Key();
        }
        public static void set(Key key, Object value) { }
        public static Object get(Key key) { return null; }
    }

    public final class ThreadLocal3<T> {
        public ThreadLocal3() { }
        public void set(T value) { }
        public T get() { return null; }
    }

    public static void main(String[] args) {
        // Inappropriate use of string as aggregate type
        String compoundKey = Class.class + "#" + 3;
        System.out.println(compoundKey);
    }

}
