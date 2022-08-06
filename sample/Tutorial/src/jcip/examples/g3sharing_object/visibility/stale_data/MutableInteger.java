package jcip.examples.g3sharing_object.visibility.stale_data;

import jcip.annotations.*;

/**
 * MutableInteger
 *
 * Non-thread-safe mutable integer holder
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class MutableInteger {
    // if one thread calls set, other threads calling get may or may not see that update.
    private int value;

    public int get() {
        return value;
    }

    public void set(int value) {
        this.value = value;
    }
}








