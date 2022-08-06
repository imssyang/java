package tutorial.basics.classes;

public class NestedClasses {

    /**
     * To handle user interface events, you must know how to use inner classes,
     * because the event-handling mechanism makes extensive use of them.
     */
    public static class InnerClass {
        private final static int SIZE = 15;
        private int[] arrayOfInts = new int[SIZE];

        public InnerClass() {
            for (int i = 0; i < SIZE; i++) {
                arrayOfInts[i] = i;
            }
        }

        public void printEven() {
            InnerClassIterator iterator = this.new EvenIterator();
            while (iterator.hasNext()) {
                System.out.print(iterator.next() + " ");
            }
            System.out.println();
        }

        interface InnerClassIterator extends java.util.Iterator<Integer> { }

        private class EvenIterator implements InnerClassIterator {
            private int nextIndex = 0;

            public boolean hasNext() {
                return (nextIndex <= SIZE - 1);
            }

            public Integer next() {
                Integer retValue = Integer.valueOf(arrayOfInts[nextIndex]);

                nextIndex += 2;
                return retValue;
            }
        }
    }


    /**
     * You can define a local class inside any block.
     * Local classes are similar to inner classes because they cannot define or declare any static members.
     * Local classes are non-static because they have access to instance members of the enclosing block.
     * Consequently, they cannot contain most kinds of static declarations.
     *
     * You cannot declare an interface inside a block; interfaces are inherently static.
     * You cannot declare static initializers or member interfaces in a local class.
     * A local class can have static members provided that they are constant variables.
     * (A constant variable is a variable of primitive type or type String that is declared final
     * and initialized with a compile-time constant expression. A compile-time constant expression
     * is typically a string or an arithmetic expression that can be evaluated at compile time.)
     */
    public static class LocalClass {
        /**
         * A local class has access to the members of its enclosing class.
         */
        static String regularExpression = "[^0-9]";

        public static void validatePhoneNumber(String phoneNumber1, String phoneNumber2) {
            /**
             * However, a local class can only access local variables that are declared final.
             * However, starting in Java SE 8, a local class can access local variables and parameters of the enclosing block
             * that are final or effectively final.
             */
            final int numberLength = 10;

            class PhoneNumber {
                String formattedPhoneNumber = null;

                PhoneNumber(String phoneNumber) {
                    String currentNumber = phoneNumber.replaceAll(regularExpression, "");
                    if (currentNumber.length() == numberLength)
                        formattedPhoneNumber = currentNumber;
                    else
                        formattedPhoneNumber = null;
                }

                public String getNumber() {
                    return formattedPhoneNumber;
                }

                /**
                 * Starting in Java SE 8, if you declare the local class in a method, it can access the method's parameters.
                 */
                public void printOriginalNumbers() {
                    System.out.println("Original numbers are " + phoneNumber1 +
                            " and " + phoneNumber2);
                }
            }

            PhoneNumber myNumber1 = new PhoneNumber(phoneNumber1);
            PhoneNumber myNumber2 = new PhoneNumber(phoneNumber2);

            myNumber1.printOriginalNumbers();

            if (myNumber1.getNumber() == null)
                System.out.println("First number is invalid");
            else
                System.out.println("First number is " + myNumber1.getNumber());
            if (myNumber2.getNumber() == null)
                System.out.println("Second number is invalid");
            else
                System.out.println("Second number is " + myNumber2.getNumber());

        }
    }

    /**
     * Anonymous classes enable you to make your code more concise.
     * They enable you to declare and instantiate a class at the same time.
     * While local classes are class declarations, anonymous classes are expressions.
     *
     * Like local classes, anonymous classes have the same access to local variables of the enclosing scope:
     *   1. An anonymous class has access to the members of its enclosing class.
     *   2. An anonymous class cannot access local variables in its enclosing scope
     *      that are not declared as final or effectively final.
     *   3. Like a nested class, a declaration of a type (such as a variable) in an anonymous class
     *      shadows any other declarations in the enclosing scope that have the same name.
     *
     * Anonymous classes also have the same restrictions as local classes with respect to their members:
     *   1. You cannot declare static initializers or member interfaces in an anonymous class.
     *   2. An anonymous class can have static members provided that they are constant variables.
     *
     * Note that you can declare the following in anonymous classes:
     *   1. Fields
     *   2. Extra methods (even if they do not implement any methods of the supertype)
     *   3. Instance initializers
     *   4. Local classes
     *
     * However, you cannot declare constructors in an anonymous class.
     */
    public static class AnonymousClass {
        interface HelloWorld {
            void greet();
            void greetSomeone(String someone);
        }

        public void sayHello() {
            HelloWorld englishGreeting = new HelloWorld() {
                String name = "world";
                public void greet() {
                    greetSomeone("world");
                }
                public void greetSomeone(String someone) {
                    name = someone;
                    System.out.println("Hello " + name);
                }
            };

            englishGreeting.greet();
            englishGreeting.greetSomeone("Quincy");
        }
    }

    public static void main(String... args) {
        NestedClasses.InnerClass ic = new NestedClasses.InnerClass();
        ic.printEven();

        NestedClasses.LocalClass.validatePhoneNumber("123-456-7890", "456-7890");

        NestedClasses.AnonymousClass ac = new NestedClasses.AnonymousClass();
        ac.sayHello();
    }
}
