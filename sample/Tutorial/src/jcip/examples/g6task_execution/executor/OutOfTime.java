package jcip.examples.g6task_execution.executor;

import java.util.*;
import static java.util.concurrent.TimeUnit.SECONDS;



/**
 * OutOfTime
 * <p/>
 * Class illustrating confusing Timer behavior
 *
 * @author Brian Goetz and Tim Peierls
 */

public class OutOfTime {
    public static void main(String[] args) throws Exception {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(1);
        timer.schedule(new ThrowTask(), 1);
        SECONDS.sleep(5);
    }

    // The Timer thread doesn't catch the exception, so an unchecked exception thrown
    // from a TimerTask terminates the timer thread.
    static class ThrowTask extends TimerTask {
        public void run() {
            throw new RuntimeException();
        }
    }
}
