package jcip.examples.g3sharing_object.visibility;

/**
 * - In the absence of synchronization, the compiler, processor, and runtime can do some downright weird things to the
 *   order in which operations appear to execute. Attempts to reason about the order in which memory actions "must"
 *   happen in insufficiently synchronized multithreaded programs will almost certainly be incorrect.
 * - When a thread reads a variable without synchronization, it may see a stale value, but at least it sees a value that was
 *   actually placed there by some thread rather than some random value. This safety guarantee is called Out-of-thin-air
 *   safety. Out-of-thin-air safety applies to all variables, with one exception: 64-bit numeric variables (double and long) that are
 *   not declared volatile.
 */

/**
 * NoVisibility
 *
 * Sharing variables without synchronization
 *
 * @author Brian Goetz and Tim Peierls
 */

public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            // NoVisibility could loop forever because the value of ready might never become visible
            // to the reader thread. Even more strangely, NoVisibility could print zero because the write
            // to ready might be made visible to the reader thread before the write to number.
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
