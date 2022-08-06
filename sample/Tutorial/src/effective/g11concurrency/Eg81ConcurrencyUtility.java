package effective.g11concurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * Item 81: Prefer concurrency utilities to wait and notify
 */
public class Eg81ConcurrencyUtility {

    /**
     * Given the difficulty of using wait and notify correctly,
     * you should use the higher-level concurrency utilities instead.
     * The higher-level utilities in java.util.concurrent fall into three categories:
     *   - the Executor Framework;
     *   - concurrent tutorial.collections: ConcurrentMap | BlockingQueue.
     *   - synchronizer: enable threads to wait for one another, allowing them to coordinate their activities.
     */

    // use ConcurrentHashMap in preference to Collections.synchronizedMap.
    private static final ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

    // Concurrent canonicalizing map atop ConcurrentMap - not optimal
    public static String intern(String s) {
        String previousValue = map.putIfAbsent(s, s);
        return previousValue == null ? s : previousValue;
    }

    // Concurrent canonicalizing map atop ConcurrentMap - faster!
    public static String intern2(String s) {
        String result = map.get(s);
        if (result == null) {
            result = map.putIfAbsent(s, s);
            if (result == null)
                result = s;
        }
        return result;
    }

    // BlockingQueue (producer-consumer queues) allows blocking queues to be used for work queues.
    // most ExecutorService implementations, including ThreadPoolExecutor, use a BlockingQueue.

    // CountDownLatch and Semaphore are most commonly used synchronizers.
    // CyclicBarrier and Exchanger are less commonly used.
    // Phaser is the most powerful synchronizer.

    /**
     * (CountDownLatch) : Simple framework for timing concurrent execution
     * For interval timing, always use System.nanoTime rather than System.currentTimeMillis.
     *
     * @param executor
     * @param concurrency [thread starvation deadlock]: The executor passed to the time method must allow for
     *                    the creation of at least as many threads as the given concurrency level,
     *                    or the test will never complete.
     * @param action
     * @return
     * @throws InterruptedException
     */
    public static long time(Executor executor, int concurrency,
                            Runnable action) throws InterruptedException {
        // CountDownLatch takes an int that is the number of times the countDown method must be invoked
        // on the latch before all waiting threads are allowed to proceed.
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                ready.countDown(); // Tell timer we're ready
                try {
                    start.await(); // Wait till peers are ready
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown(); // Tell timer we're done
                }
            });
        }
        ready.await(); // Wait for all workers to be ready
        long startNanos = System.nanoTime();
        start.countDown(); // And they're off!
        done.await(); // Wait for all workers to finish
        return System.nanoTime() - startNanos;
    }

    /*
     * The standard idiom for using the wait method.
     *   - Always use the wait loop idiom to invoke the wait method; never invoke it outside of a loop.
    synchronized (obj) {
        while (<condition does not hold>)
            obj.wait(); // (Releases lock, and reacquires on wakeup)
    }
    */

}
