package jcip.examples.g3sharing_object.safe_publication;

/**
 * A properly constructed object can be safely published by:
 *   - x Initializing an object reference from a static initializer;
 *   - x Storing a reference to it into a volatile field or AtomicReference;
 *   - x Storing a reference to it into a final field of a properly constructed object;
 *   - x Storing a reference to it into a field that is properly guarded by a lock.
 *
 * The threadͲsafe library collections offer the following safe publication guarantees,
 * even if the Javadoc is less than clear on the subject:
 *   - x Placing a key or value in a Hashtable, synchronizedMap, or Concurrent-Map safely publishes it to any thread
 *     that retrieves it from the Map (whether directly or via an iterator);
 *   - x Placing an element in a Vector, CopyOnWriteArrayList, CopyOnWrite-ArraySet, synchronizedList, or
 *     synchronizedSet safely publishes it to any thread that retrieves it from the collection;
 *   - x Placing an element on a BlockingQueue or a ConcurrentLinkedQueue safely publishes it to any thread that
 *     retrieves it from the queue.
 */

/**
 * Holder
 * <p/>
 * Class at risk of failure if not properly published
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Holder {
    // Because synchronization was not used to make the Holder visible to other threads,
    // we say the Holder was not properly published.
    private int n;

    public Holder(int n) {
        this.n = n;
    }

    // Other threads could see a stale value for the holder field, and thus see a null reference
    // or other older value even though a value has been placed in holder.
    public void assertSanity() {
        if (n != n)
            throw new AssertionError("This statement is false.");
    }
}
