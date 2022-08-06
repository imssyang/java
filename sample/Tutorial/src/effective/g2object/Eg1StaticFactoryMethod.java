package effective.g2object;

/**
 * Item 1: Consider static factory methods instead of constructors
 */
public class Eg1StaticFactoryMethod {

    static class A {
        private static A _instance;

        //Returns an instance that is described by its parameters.
        public static A getInstance(String options) {
            return _instance;
        }

        //each call returns a new instance
        public static A newInstance() {
            return new A();
        }

    }

    public static void main(String[] args) {
    }

}
