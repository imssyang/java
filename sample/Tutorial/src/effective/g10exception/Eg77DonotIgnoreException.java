package effective.g10exception;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Item 77: Don’t ignore exceptions
 */
public class Eg77DonotIgnoreException {

    void test() {
        // Empty catch block ignores exception - Highly suspect!
        try {
        } catch (Exception e) {
        }
    }

    // If you choose to ignore an exception, the catch block should contain a comment
    // explaining why it is appropriate to do so, and the variable should be named ignored
    void test2() {
        Future<Integer> f = null;
        int numColors = 4; // Default; guaranteed sufficient for any map
        try {
            numColors = f.get(1L, TimeUnit.SECONDS);
        } catch (TimeoutException | ExecutionException | InterruptedException ignored) {
            // Use default: minimal coloring is desirable, not required
        }
    }

}
