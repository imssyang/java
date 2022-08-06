package effective.g10exception;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Item 73: Throw exceptions appropriate to the abstraction
 *   - higher layers should catch lower-level exceptions and, in their place,
 *     throw exceptions that can be explained in terms of the higher-level abstraction.
 */
public class Eg73ThrowException<E> {

    /**
     * Returns the element at the specified position in this list.
     * @throws IndexOutOfBoundsException if the index is out of range
     * ({@code index < 0 || index >= size()}).
     */
    public E get(int index) {
        ListIterator<E> i = listIterator(index);
        try {
            return i.next();
        } catch (NoSuchElementException e) { //LowerLevelException
            throw new IndexOutOfBoundsException("Index: " + index); //HigherLevelException
        }
    }

    private ListIterator<E> listIterator(int index) {
        return null;
    }

}
