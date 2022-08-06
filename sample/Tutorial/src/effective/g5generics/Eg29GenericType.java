package effective.g5generics;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Item 29: Favor generic types
 *   - generic methods, like generic types, are safer and easier to use
 *     than methods requiring their clients to put explicit casts on input parameters and
 *     return values.
 */
public class Eg29GenericType {

    // Object-based collection - a prime candidate for generics
    public static class Stack {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;
        public Stack() {
            elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }
        public void push(Object e) {
            ensureCapacity();
            elements[size++] = e;
        }
        public Object pop() {
            if (size == 0)
                throw new EmptyStackException();
            Object result = elements[--size];
            elements[size] = null; // Eliminate obsolete reference
            return result;
        }
        public boolean isEmpty() {
            return size == 0;
        }
        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    // Initial attempt to generify Stack
    public static class Stack2<E> {
        private E[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;
        // The elements array will contain only E instances from push(E).
        // This is sufficient to ensure type safety, but the runtime
        // type of the array won't be E[]; it will always be Object[]!
        @SuppressWarnings("unchecked")
        public Stack2() {
            elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
        }
        public void push(E e) {
            ensureCapacity();
            elements[size++] = e;
        }
        public E pop() {
            if (size == 0)
                throw new EmptyStackException();
            E result = elements[--size];
            elements[size] = null; // Eliminate obsolete reference
            return result;
        }
        public boolean isEmpty() {
            return size == 0;
        }
        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    // Initial attempt to generify Stack
    public static class Stack3<E> {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;
        public Stack3() {
            elements = new Object[DEFAULT_INITIAL_CAPACITY];
        }
        public void push(E e) {
            ensureCapacity();
            elements[size++] = e;
        }
        // Appropriate suppression of unchecked warning
        public E pop() {
            if (size == 0)
                throw new EmptyStackException();
            // push requires elements to be of type E, so cast is correct
            @SuppressWarnings("unchecked") E result = (E) elements[--size];
            elements[size] = null; // Eliminate obsolete reference
            return result;
        }
        public boolean isEmpty() {
            return size == 0;
        }
        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    // Little program to exercise our generic Stack
    public static void main(String[] args) {
        Stack3<String> stack = new Stack3<>();
        for (String arg : args)
            stack.push(arg);
        while (!stack.isEmpty())
            System.out.println(stack.pop().toUpperCase());
    }

}
