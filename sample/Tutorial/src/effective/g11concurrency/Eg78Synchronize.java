package effective.g11concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Item 78: Synchronize access to shared mutable data
 *   - The synchronized keyword ensures that only a single thread can execute a method
 *     or block at one time.
 *   - The language specification guarantees that reading or writing a variable is
 *     atomic unless the variable is of type long or double.
 *   - Do not use Thread.stop.
 *   - When multiple threads share mutable data, each thread that reads or writes the data
 *     must perform synchronization.
 */
public class Eg78Synchronize {

    /*
     * Broken! - the program never terminates: the background thread loops forever!
     * In the absence of synchronization, it’s quite acceptable for the virtual machine to transform this code:
     *     while (!stopRequested)
     *         i++;
     * into this code:
     *     if (!stopRequested)
     *         while (true)
     *             i++;
     */
    public static class BrokenStopThread {
        private static boolean stopRequested;
        public static void main(String[] args) throws InterruptedException {
            Thread backgroundThread = new Thread(() -> {
                int i = 0;
                while (!stopRequested)
                    i++;
            });
            backgroundThread.start();
            TimeUnit.SECONDS.sleep(1);
            stopRequested = true;
        }
    }

    /**
     * Properly synchronized cooperative thread termination
     *   - Synchronization is not guaranteed to work unless both read and
     *     write operations are synchronized.
     */
    public static class StopThread2 {
        private static boolean stopRequested;
        private static synchronized void requestStop() { //write
            stopRequested = true;
        }
        private static synchronized boolean stopRequested() { //read
            return stopRequested;
        }
        public static void main(String[] args) throws InterruptedException {
            Thread backgroundThread = new Thread(() -> {
                int i = 0;
                while (!stopRequested())
                    i++;
            });
            backgroundThread.start();
            TimeUnit.SECONDS.sleep(1);
            requestStop();
        }
    }

    /**
     * Cooperative thread termination with a volatile field
     *   - While the volatile modifier performs no mutual exclusion, it guarantees that
     *     any thread that reads the field will see the most recently written value.
     */
    public static class StopThread3 {
        private static volatile boolean stopRequested;
        public static void main(String[] args) throws InterruptedException {
            Thread backgroundThread = new Thread(() -> {
                int i = 0;
                while (!stopRequested)
                    i++;
            });
            backgroundThread.start();
            TimeUnit.SECONDS.sleep(1);
            stopRequested = true;
        }
    }

    static class BrokeSerialNumber {
        // Broken - requires synchronization!
        private static volatile int nextSerialNumber = 0;
        public static int generateSerialNumber() {
            return nextSerialNumber++; //the increment operator (++) is not atomic.
        }
    }

    static class SerialNumber {
        // Lock-free synchronization with java.util.concurrent.atomic
        private static final AtomicLong nextSerialNum = new AtomicLong();
        public static long generateSerialNumber() {
            return nextSerialNum.getAndIncrement();
        }
    }

}
