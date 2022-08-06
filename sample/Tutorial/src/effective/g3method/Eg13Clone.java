package effective.g3method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Objects;

/**
 * Item 13: Override clone judiciously
 *  - The general intent is that, for any object x,
 *    the expression
 *        x.clone() != x will be true,
 *    and the expression
 *        x.clone().getClass() == x.getClass() will be true, but these are not absolute requirements.
 *    While it is typically the case that
 *        x.clone().equals(x) will be true, this is not an absolute requirement.
 */
public class Eg13Clone {

    public static final class PhoneNumber implements Cloneable {
        private final short areaCode, prefix, lineNum;
        public PhoneNumber(int areaCode, int prefix, int lineNum) {
            this.areaCode = rangeCheck(areaCode, 999, "area code");
            this.prefix = rangeCheck(prefix, 999, "prefix");
            this.lineNum = rangeCheck(lineNum, 9999, "line num");
        }
        private static short rangeCheck(int val, int max, String arg) {
            if (val < 0 || val > max)
                throw new IllegalArgumentException(arg + ": " + val);
            return (short) val;
        }

        // NOTE: Don’t substitute another type for Object in the equals declaration.
        @Override public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof PhoneNumber))
                return false;
            PhoneNumber pn = (PhoneNumber)o;
            return pn.lineNum == lineNum && pn.prefix == prefix
                    && pn.areaCode == areaCode;
        }

        // NOTE: Always override hashCode when you override equals
        @Override public int hashCode() {
            return Objects.hash(lineNum, prefix, areaCode);
        }

        @Override public String toString() {
            return String.format("%03d-%03d-%04d",
                    areaCode, prefix, lineNum);
        }

        // Clone method for class with no references to mutable state.
        // Though Object’s clone method returns Object, this clone method returns PhoneNumber.
        // It is legal and desirable to do this because Java supports covariant return types.
        @Override public PhoneNumber clone() {
            try {
                return (PhoneNumber) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(); // Can't happen
            }
        }
    }

    public class Stack implements Cloneable {
        private Object[] elements;
        private int size = 0;
        private static final int DEFAULT_INITIAL_CAPACITY = 16;

        public Stack() {
            this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
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

        // Ensure space for at least one more element.
        private void ensureCapacity() {
            if (elements.length == size)
                elements = Arrays.copyOf(elements, 2 * size + 1);
        }

        // Clone method for class with references to mutable state
        @Override public Stack clone() {
            try {
                Stack result = (Stack) super.clone();
                result.elements = elements.clone();
                return result;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    public static class HashTable implements Cloneable {
        private Entry[] buckets = new Entry[32];
        private static class Entry {
            final Object key;
            Object value;
            Entry next;
            Entry(Object key, Object value, Entry next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }

            // Recursively copy the linked list headed by this Entry
            Entry deepCopy() {
                return new Entry(key, value, next == null ? null : next.deepCopy());
            }

            // Iteratively copy the linked list headed by this Entry
            Entry deepCopy2() {
                Entry result = new Entry(key, value, next);
                for (Entry p = result; p.next != null; p = p.next)
                    p.next = new Entry(p.next.key, p.next.value, p.next.next);
                return result;
            }
        }

        @Override public HashTable clone() {
            try {
                HashTable result = (HashTable) super.clone();
                result.buckets = new Entry[buckets.length];
                for (int i = 0; i < buckets.length; i++)
                    if (buckets[i] != null)
                        result.buckets[i] = buckets[i].deepCopy();
                return result;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    // To prevent subclasses from implementing clone
    public static class NotSupport implements Cloneable {
        // clone method for extendable class not supporting Cloneable
        @Override
        protected final Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
        }
    }

}
