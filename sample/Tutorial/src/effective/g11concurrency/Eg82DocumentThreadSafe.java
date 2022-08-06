package effective.g11concurrency;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Item 82: Document thread safety
 *   - Immutable: Instances of this class appear constant. No external synchronization is necessary.
 *                eg String, Long, and BigInteger.
 *   - Unconditionally thread-safe: Instances of this class are mutable, but the class has sufficient
 *                                  internal synchronization that its instances can be used concurrently
 *                                  without the need for any external synchronization.
 *                                  eg AtomicLong and ConcurrentHashMap.
 *   - Conditionally thread-safe: Like unconditionally thread-safe, except that some methods require
 *                                external synchronization for safe concurrent use.
 *                                eg  Collections.synchronized wrappers.
 *   - Not thread-safe: Instances of this class are mutable. each method invocation (or invocation sequence)
 *                      with external synchronization.
 *                      eg ArrayList and HashMap.
 *   - Thread-hostile: This class is unsafe for concurrent use even if every method invocation is surrounded
 *                     by external synchronization.
 */
public class Eg82DocumentThreadSafe {

    <K,V> void test() {
        Map<K, V> m = Collections.synchronizedMap(new HashMap<>());
        Set<K> s = m.keySet(); // Needn't be in synchronized block

        synchronized(m) { // Synchronizing on m, not s!
            for (K key : s) {
                //key.f();
            }
        }
    }

    static class PrivateLock {
        // Private lock object idiom - thwarts denial-of-service attack
        // Because the private lock object is inaccessible outside the class,
        // it is impossible for clients to interfere with the object’s synchronization.
        private final Object lock = new Object();
        public void foo() {
            synchronized(lock) {
            }
        }
    }

}
