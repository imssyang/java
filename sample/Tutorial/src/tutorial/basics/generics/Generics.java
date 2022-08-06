package tutorial.basics.generics;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Code that uses generics has many benefits over non-generic code:
 *   -> Stronger type checks at compile time.
 *   -> Elimination of casts.
 *   -> Enabling programmers to implement generic algorithms.
 */
public class Generics {

    /**
     * By convention, type parameter names are single, uppercase letters.
     * The most commonly used type parameter names are:
     *
     * E - Element (used extensively by the Java Collections Framework)
     * K - Key
     * N - Number
     * T - Type
     * V - Value
     * S,U,V etc. - 2nd, 3rd, 4th types
     */
    public class Box<T> {
        private T t;

        public Box() { }
        public Box(T t) { this.t = t;}
        public void set(T t) { this.t = t; }
        public T get() { return t; }

        /**
         * To declare a <code>bounded type parameter</code>, list the type parameter's name, followed by the <code>extends</code> keyword, followed by its <i>upper bound</i>.
         * Note that, in this context, <code>extends</code> is used in a general sense to mean either "extends" (as in classes) or "implements" (as in interfaces).
         */
        public <U extends Number> void inspect(U u){
            System.out.println("T: " + t.getClass().getName());
            System.out.println("U: " + u.getClass().getName());
        }

        public void println() { System.out.println(t); }
    }

    public interface Pair<K, V> {
        public K getKey();
        public V getValue();
    }

    public class OrderedPair<K, V> implements Pair<K, V> {
        private K key;
        private V value;

        public OrderedPair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey()	{ return key; }
        public V getValue() { return value; }
    }

    public static class Util {
        /**
         * <em>Generic methods</em> are methods that introduce their own type parameters.
         * This is similar to declaring a generic type, but the type parameter's scope is limited to the method where it is declared.
         * Static and non-static generic methods are allowed, as well as generic class constructors.
         */
        public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
            return p1.getKey().equals(p2.getKey()) &&
                    p1.getValue().equals(p2.getValue());
        }

        public static <T extends Comparable<T>> int countGreaterThan(T[] anArray, T elem) {
            int count = 0;
            for (T e : anArray)
                if (e.compareTo(elem) > 0)
                    ++count;
            return count;
        }
    }

    /**
     * <strong>Wildcard Guidelines:</strong>
     *   -> An "in" variable is defined with an upper bounded wildcard, using the <tt>extends</tt> keyword.
     *   -> An "out" variable is defined with a lower bounded wildcard, using the <tt>super</tt> keyword.
     *   -> In the case where the "in" variable can be accessed using methods defined in the <tt>Object</tt> class, use an unbounded wildcard.
     *   -> In the case where the code needs to access the variable as both an "in" and an "out" variable, do not use a wildcard.
     */
    static class Wildcards {
        /**
         * ```Upper Bounded Wildcards```
         * To write the method that works on lists of <tt>Number</tt> and the subtypes of <tt>Number</tt>,
         * such as <tt>Integer</tt>, and <tt>Float</tt>, you would specify <tt>List&lt;? extends Number&gt;</tt>.
         * The term <tt>List&lt;Number&gt;</tt> is more restrictive than <tt>List&lt;? extends Number&gt;</tt> because
         * the former matches a list of type <tt>Number</tt> only, whereas the latter matches a list of type <tt>Number</tt>
         * or any of its subclasses.
         */
        public static double sumOfList(List<? extends Number> list) {
            double s = 0.0;
            for (Number n : list)
                s += n.doubleValue();
            return s;
        }

        /**
         * ```Lower Bounded Wildcards```
         * A <em>lower bounded</em> wildcard restricts the unknown type to be a specific type or a <em>super type</em> of that type.
         */
        public static void addNumbers(List<? super Integer> list) {
            for (int i = 1; i <= 10; i++) {
                list.add(i);
            }
        }

        /**
         * ```Unbounded Wildcards```
         * The unbounded wildcard type is specified using the wildcard character (<tt>?</tt>).
         * There are two scenarios where an unbounded wildcard is a useful approach:
         *   -> If you are writing a method that can be implemented using functionality provided in the <tt>Object</tt> class.
         *   -> When the code is using methods in the generic class that don't depend on the type parameter.
         */
        public static void printList(List<?> list) {
            for (Object elem: list)
                System.out.print(elem + " ");
            System.out.println();
        }

        /**
         * For example, a list may be defined as <tt>List&lt;?&gt;</tt> but, when evaluating an expression, the compiler infers
         * a particular type from the code. This scenario is known as <em>wildcard capture</em>.
         */
        void foo(List<?> i) {
            //i.set(0, i.get(0));
            fooHelper(i);
        }

        // Helper method created so that the wildcard can be captured through type inference.
        private <T> void fooHelper(List<T> l) {
            l.set(0, l.get(0));
        }
    }

    /**
     * During the type erasure process, the Java compiler erases all type parameters and replaces each with its first bound
     * if the type parameter is bounded, or <tt>Object</tt> if the type parameter is unbounded.
     */
    static class TypeErasure {
        public class Node<T> {
            private T data;
            private Node<T> next;

            public Node(T data, Node<T> next) {
                this.data = data;
                this.next = next;
            }

            public void setData(T data) {
                System.out.println("Node.setData");
                this.data = data;
            }
        }

        public class MyNode extends Node<Integer> {
            public MyNode(Integer data) { super(data, null); }

            public void setData(Integer data) {
                System.out.println("MyNode.setData");
                super.setData(data);
            }
        }

        // Because the type parameter T is unbounded, the Java compiler replaces it with Object:
        public class ErasureNode {
            private Object data;
            private ErasureNode next;

            public ErasureNode(Object data, ErasureNode next) {
                this.data = data;
                this.next = next;
            }

            public void setData(Object data) {
                System.out.println("ErasureNode.setData");
                this.data = data;
            }
        }

        public class MyErasureNode extends ErasureNode {
            public MyErasureNode(Integer data) { super(data, null); }

            /**
             * After type erasure, the method signatures do not match.
             * The <tt>Node</tt> method becomes <tt>setData(Object)</tt> and the <tt>MyNode</tt> method becomes <tt>setData(Integer)</tt>.
             * Therefore, the <tt>MyNode</tt> <tt>setData</tt> method does not override the <tt>Node</tt> <tt>setData</tt> method.
             *
             * To solve this problem and preserve the <code>polymorphism</code> of generic types after type erasure,
             * a Java compiler generates a bridge method to ensure that subtyping works as expected.
             * For the <tt>MyNode</tt> class, the compiler generates the following bridge method for <tt>setData</tt>:
             */
            //public void setData(Object data) {
            //    setData((Integer) data);
            //}
            public void setData(Integer data) {
                System.out.println("MyErasureNode.setData");
                super.setData(data);
            }
        }

        /**
         * A <em>reifiable</em> type is a type whose type information is fully available at runtime.
         * This includes primitives, non-generic types, raw types, and invocations of unbound wildcards.
         *
         * Non-reifiable types are types where information has been removed at compile-time by type erasure.
         * Examples of non-reifiable types are List<String> and List<Number>;
         * The runtime does not keep track of type parameters, so it cannot tell the difference between an List<Integer> and an List<String>.
         *
         * Arrays are reifiable as arrays remains as it is at runtime
         * While generic information attached with List is erased at runtime by erasures.
         *     List<String> list = new ArrayList<String>;
         * at runtime will be
         *     List list = new ArrayList();
         */
        class NonReifiableTypes {
        }

        static class Restrictions {
            /**
             * Cannot Instantiate Generic Types with Primitive Types
             */
            class Pair<K, V> {
                private K key;
                private V value;

                public Pair(K key, V value) {
                    this.key = key;
                    this.value = value;
                }

                void test() {
                    //Pair<int, char> p = new Pair<>(8, 'a');  // compile-time error
                    Pair<Integer, Character> p = new Pair<>(8, 'a');
                }
            }

            /**
             * Cannot Create Instances of Type Parameters
             */
            static class TypeParameters {
                public static <E> void append(List<E> list) {
                    //E elem = new E();  // compile-time error
                    //list.add(elem);
                }

                //As a workaround, you can create an object of a type parameter through reflection:
                public static <E> void append(List<E> list, Class<E> cls) throws Exception {
                    E elem = cls.newInstance();   // OK
                    list.add(elem);
                }
                public void test() throws Exception {
                    List<String> ls = new ArrayList<>();
                    append(ls, String.class);
                }
            }

            /**
             * Cannot Declare Static Fields Whose Types are Type Parameters
             */
            static class StaticFields<T> {
                // A class's static field is a class-level variable shared by all non-static objects of the class.
                // Hence, static fields of type parameters are not allowed.
                //private static T os;
            }

            /**
             * Cannot Use Casts or instanceof with Parameterized Types
             */
            static class CastsOrInstanceof {
                // Because the Java compiler erases all type parameters in generic code,
                // you cannot verify which parameterized type for a generic type is being used at runtime:
                public static <E> void rtti(List<E> list) {
                    //if (list instanceof ArrayList<Integer>) { }  // compile-time error
                }

                //
                public static void rtti2(List<?> list) {
                    if (list instanceof ArrayList<?>) { } // OK; instanceof requires a reifiable type
                }

                void testCast() {
                    //Typically, you cannot cast to a parameterized type unless it is parameterized by unbounded wildcards. For example:
                    List<Integer> li = new ArrayList<>();
                    //List<Number> ln = (List<Number>) li;  // compile-time error

                    //However, in some cases the compiler knows that a type parameter is always valid and allows the cast. For example:
                    List<String> l1 = new ArrayList<>();
                    ArrayList<String> l2 = (ArrayList<String>)l1;  // OK
                }
            }

            /**
             * Cannot Create Arrays of Parameterized Types
             */
            void testCreateArrays() {
                //List<Integer>[] arrayOfLists = new List<Integer>[2];  // compile-time error

                Object[] strings = new String[2];
                strings[0] = "hi";   // OK
                //strings[1] = 100;    // An ArrayStoreException is thrown.

                //Object[] stringLists = new List<String>[];  // compiler error, but pretend it's allowed
                //stringLists[0] = new ArrayList<String>();   // OK
                //stringLists[1] = new ArrayList<Integer>();  // An ArrayStoreException should be thrown, but the runtime can't detect it.
            }

            /**
             * Cannot Create, Catch, or Throw Objects of Parameterized Types
             */
            static class Exceptions {
                //A generic class cannot extend the Throwable class directly or indirectly.

                // Extends Throwable indirectly
                //class MathException<T> extends Exception { /* ... */ }    // compile-time error

                // Extends Throwable directly
                //class QueueFullException<T> extends Throwable { /* ... */ // compile-time error

                //A method cannot catch an instance of a type parameter:
                public static <T extends Exception, J> void execute(List<J> jobs) {
                    try {
                        for (J job : jobs) { }
                    }
                    //catch (T e) { }  // compile-time error
                    finally {  }
                }

                //You can, however, use a type parameter in a throws clause:
                class Parser<T extends Exception> {
                    public void parse(File file) throws T {     // OK
                        // ...
                    }
                }
            }

            /**
             * Cannot Overload a Method Where the Formal Parameter Types of Each Overload Erase to the Same Raw Type
             */
            public class Overloads {
                //A class cannot have two overloaded methods that will have the same signature after type erasure.
                public void print(Set<String> strSet) { }
                //public void print(Set<Integer> intSet) { }
            }
        }
    }

    void test() {
        /**
         * In Java SE 7 and later, you can replace the type arguments required to invoke the constructor of a generic class
         * with an empty set of type arguments (<>) as long as the compiler can determine, or infer, the type arguments from the context.
         */
        Box<Integer> integerBox = new Box<Integer>();
        integerBox.set(123);
        integerBox.println();
        integerBox.inspect(111);

        Pair<String, Integer> p1 = new OrderedPair<>("Even", 8);
        Pair<String, String>  p2 = new OrderedPair<>("hello", "world");
        OrderedPair<String, Box<Integer>> p3 = new OrderedPair<>("primes", new Box<>(456));
        System.out.println(p1.getKey() + ":" + p1.getValue());
        System.out.println(p2.getKey() + ":" + p2.getValue());
        System.out.println(p3.getKey() + ":" + p3.getValue().get());

        /**
         * A <em>raw type</em> is the name of a generic class or interface without any type arguments.
         */
        Box rawBox = integerBox;
        rawBox.set(789);// warning: unchecked invocation to set(T)
        rawBox.println();

        OrderedPair<Integer, String> op1 = new OrderedPair<>(1, "apple");
        OrderedPair<Integer, String> op2 = new OrderedPair<>(2, "pear");
        boolean same = Util.<Integer, String>compare(op1, op2);
        /**
         * <em>Type inference</em> is a Java compiler's ability to look at each method invocation and corresponding declaration
         * to determine the type argument (or arguments) that make the invocation applicable.
         *
         * This feature allows you to invoke a generic method as an ordinary method, without specifying a type between angle brackets.
         */
        boolean same2 = Util.compare(op1, op2);
        System.out.println(same + " " + same2);

        int cnt = Util.countGreaterThan(new Integer[] {
                1, 3, 5, 7, 9
        }, 6);
        System.out.println(cnt);

        /**
         * Integer and Double are subtypes of Number.
         * But Box<Integer> and Box<Double> are not subtypes of Box<Number>.
         */
        Box<Number> box = new Box<>();
        box.set(new Integer(111));
        box.set(new Double(1.11));
        //box.set(integerBox);

        List<Integer> li = Arrays.asList(1, 2, 3);
        System.out.println("sum = " + Wildcards.sumOfList(li));
        List<Double> ld = Arrays.asList(1.2, 2.3, 3.5);
        System.out.println("sum = " + Wildcards.sumOfList(ld));

        List<Integer> li3 = new ArrayList<>();
        Wildcards.addNumbers(li3);
        Wildcards.printList(li3);
        List<Number> ln3 = new ArrayList<>();
        Wildcards.addNumbers(ln3);
        Wildcards.printList(ln3);

        List<Integer> li2 = Arrays.asList(1, 2, 3);
        List<String>  ls2 = Arrays.asList("one", "two", "three");
        Wildcards.printList(li2);
        Wildcards.printList(ls2);

        Wildcards wildcardCapture = new Wildcards();
        wildcardCapture.foo(li2);
    }

    public static void main(String[] args) {
        Generics generics = new Generics();
        generics.test();
    }
}

