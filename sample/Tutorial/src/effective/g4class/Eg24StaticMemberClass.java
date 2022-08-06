package effective.g4class;

import java.util.AbstractSet;
import java.util.Iterator;

/**
 * Item 24: Favor static member classes over nonstatic
 *   - If you declare a member class that does not require access to an enclosing
 *     instance, always put the static modifier in its declaration, making it a static
 *     rather than a nonstatic member class.
 */
public class Eg24StaticMemberClass {

    // Typical use of a nonstatic member class
    public class MySet<E> extends AbstractSet<E> {
        @Override public Iterator<E> iterator() {
            return new MyIterator();
        }

        @Override public int size() { return 0; }

        private class MyIterator implements Iterator<E> {
            @Override public boolean hasNext() { return false; }
            @Override public E next() { return null; }
        }
    }

}
