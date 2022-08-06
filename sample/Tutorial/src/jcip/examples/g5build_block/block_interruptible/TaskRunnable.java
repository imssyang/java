package jcip.examples.g5build_block.block_interruptible;

import java.util.concurrent.*;

/**
 * Threads may block, or pause, for several reasons:
 *   - waiting for I/O completion
 *   - waiting to acquire a lock
 *   - waiting to wake up from Thread.sleep
 *   - waiting for the result of a computation in another thread.
 * When a thread blocks, it is usually suspended and placed in one of the blocked thread states (BLOCKED, WAITING, or TIMED_WAITING).
 *
 * Interruption is a cooperative mechanism. One thread cannot force another to stop what it is doing and do something else;
 * when thread A interrupts thread B, A is merely requesting that B stop what it is doing when it gets to a convenient
 * stopping point - if it feels like it. The most sensible use for interruption is to cancel an activity.
 */

/**
 * TaskRunnable
 * <p/>
 * Restoring the interrupted status so as not to swallow the interrupt
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TaskRunnable implements Runnable {
    BlockingQueue<Task> queue;

    public void run() {
        try {
            processTask(queue.take());
        } catch (InterruptedException e) {
            // restore interrupted status
            Thread.currentThread().interrupt();
        }
    }

    void processTask(Task task) {
        // Handle the task
    }

    interface Task {
    }
}
