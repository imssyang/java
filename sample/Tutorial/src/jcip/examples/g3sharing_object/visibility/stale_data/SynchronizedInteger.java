package jcip.examples.g3sharing_object.visibility.stale_data;

import jcip.annotations.*;

/**
 * Locking is not just about mutual exclusion; it is also about memory visibility. To ensure that all threads see the most up-
 * to-date values of shared mutable variables, the reading and writing threads must synchronize on a common lock.
 */

/**
 * SynchronizedInteger
 *
 * Thread-safe mutable integer holder
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class SynchronizedInteger {
    @GuardedBy("this") private int value;

    public synchronized int get() {
        return value;
    }

    public synchronized void set(int value) {
        this.value = value;
    }
}
