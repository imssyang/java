package jcip.examples.g5build_block.synchronizer.latch;

import effective.g6enum_annotation.Eg39Annotation;

import java.util.concurrent.*;

/**
 * A synchronizer is any object that coordinates the control flow of threads based on its state.
 *   - Blocking queues can act as synchronizers;
 *   - Semaphore
 *   - Barrier
 *   - Latch: can delay the progress of threads until it reaches its terminal state.
 */

/**
 * TestHarness
 * <p/>
 * Using CountDownLatch for starting and stopping threads in timing tests
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TestHarness {
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        System.out.println("start:" + start);
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        System.out.println("end:" + end);
        return end - start;
    }

    public static void main(String[] args) {
        TestHarness testHarness = new TestHarness();
        try {
            testHarness.timeTasks(3, () -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("123");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
