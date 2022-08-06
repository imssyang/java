package effective.g5generics;

import java.util.*;

/**
 * Item 31: Use bounded wildcards to increase API flexibility
 *   - PECS stands for producer-extends, consumer-super.
 */
public class Eg31BoundedWildcard {

    public static class Stack<E> {
        public Stack() { }
        public void push(E e) { }
        public E pop() { return null; }
        public boolean isEmpty() { return false; }
        // pushAll method without wildcard type - deficient!
        public void pushAll(Iterable<E> src) {
            for (E e : src)
                push(e);
        }
        // Wildcard type for a parameter that serves as an E producer
        public void pushAll2(Iterable<? extends E> src) {
            for (E e : src)
                push(e);
        }
        // popAll method without wildcard type - deficient!
        public void popAll(Collection<E> dst) {
            while (!isEmpty())
                dst.add(pop());
        }
        // Wildcard type for parameter that serves as an E consumer
        public void popAll2(Collection<? super E> dst) {
            while (!isEmpty())
                dst.add(pop());
        }
        public static void main(String[] args) {
            Stack<Number> numberStack = new Stack<>();
            Iterable<Integer> integers = Arrays.asList(1, 2, 3);
            //numberStack.pushAll(integers); //error!!!
            numberStack.pushAll2(integers);

            Stack<Number> numberStack2 = new Stack<Number>();
            Collection<Object> objects = new LinkedList<>();
            //numberStack.popAll(objects); //error!!!
            numberStack.popAll2(objects);

            Iterable<Double> doubles = Arrays.asList(1.0, 2.0, 3.0);
            // Explicit type parameter - required prior to Java 8
        }
    }

    public static <T extends Comparable<T>> T max1(List<T> list) { return null; }
    public static <T extends Comparable<? super T>> T max2(List<? extends T> list) { return null; }

    // Two possible declarations for the swap method
    public static <E> void swap1(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

    public static void swap2(List<?> list, int i, int j) { // better because it’s simpler
        swapHelper(list, i, j); // Can’t put any value except null into a List<?>
    }

    // Private helper method for wildcard capture
    private static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

}
