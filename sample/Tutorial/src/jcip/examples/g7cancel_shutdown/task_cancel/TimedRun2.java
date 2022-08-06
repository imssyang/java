package jcip.examples.g7cancel_shutdown.task_cancel;

import java.util.concurrent.*;
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static jcip.examples.LaunderThrowable.launderThrowable;

/**
 * TimedRun2
 * <p/>
 * Interrupting a task in a dedicated thread
 *
 * @author Brian Goetz and Tim Peierls
 */
public class TimedRun2 {
    private static final ScheduledExecutorService cancelExec = newScheduledThreadPool(1);

    public static void timedRun(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;

            public void run() {
                try {
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            void rethrow() {
                if (t != null)
                    throw launderThrowable(t);
            }
        }

        RethrowableTask task = new RethrowableTask();
        final Thread taskThread = new Thread(task);
        taskThread.start();
        cancelExec.schedule(new Runnable() {
            /**
             * You should not interrupt a pool thread directly when attempting to cancel a task,
             * because you won't know what task is running when the interrupt request is delivered - do
             * this only through the task's Future.
             */
            public void run() {
                taskThread.interrupt();
            }
        }, timeout, unit);
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }
}
