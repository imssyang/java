package jcip.examples.g7cancel_shutdown.task_cancel;

import java.util.concurrent.*;

/**
 * InterruptBorrowedThread
 * <p/>
 * Scheduling an interrupt on a borrowed thread
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimedRun1 {
    private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(1);

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) {
        final Thread taskThread = Thread.currentThread();
        cancelExec.schedule(new Runnable() {
            /**
             * It violates the rules: you should know a thread's interruption policy before interrupting it.
             * Since timedRun can be called from an arbitrary thread, it cannot know the calling thread's interruption
             * policy.
             */
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);
        r.run();
    }
}
