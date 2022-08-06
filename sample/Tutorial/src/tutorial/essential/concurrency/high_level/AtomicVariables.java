package tutorial.essential.concurrency.high_level;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The java.util.concurrent.atomic package defines classes that support atomic operations on single variables.
 * All classes have get and set methods that work like reads and writes on volatile variables.
 */
public class AtomicVariables {

    static class AtomicCounter {
        private AtomicInteger c = new AtomicInteger(0);

        public void increment() {
            c.incrementAndGet();
        }

        public void decrement() {
            c.decrementAndGet();
        }

        public int value() {
            return c.get();
        }
    }

}
