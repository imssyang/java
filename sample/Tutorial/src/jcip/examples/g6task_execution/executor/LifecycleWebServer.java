package jcip.examples.g6task_execution.executor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.logging.*;

/**
 * - newFixedThreadPool: A fixed-size thread pool creates threads as tasks are submitted, up to the maximum pool size,
 *                       and then attempts to keep the pool size constant (adding new threads if a thread dies due to
 *                       an unexpected Exception).
 * - newCachedThreadPool: A cached thread pool has more flexibility to reap idle threads when the current size of the pool
 *                        exceeds the demand for processing, and to add new threads when demand increases, but places no
 *                        bounds on the size of the pool.
 * - newSingleThreadExecutor: A single-threaded executor creates a single worker thread to process tasks, replacing it
 *                            if it dies unexpectedly. Tasks are guaranteed to be processed sequentially according to
 *                            the order imposed by the task queue (FIFO, LIFO, priority order).
 * - newScheduledThreadPool: A fixed-size thread pool that supports delayed and periodic task execution, similar to Timer.
 *                           Timer has some drawbacks, and ScheduledThreadPoolExecutor should be thought of as its replacement.
 */


/**
 * LifecycleWebServer
 * <p/>
 * Web server with shutdown support
 *
 * @author Brian Goetz and Tim Peierls
 */
public class LifecycleWebServer {
    private final ExecutorService exec = Executors.newCachedThreadPool();

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (!exec.isShutdown()) {
            try {
                final Socket conn = socket.accept();
                exec.execute(new Runnable() {
                    public void run() {
                        handleRequest(conn);
                    }
                });
            } catch (RejectedExecutionException e) {
                if (!exec.isShutdown())
                    log("task submission rejected", e);
            }
        }
    }

    public void stop() {
        exec.shutdown();
    }

    private void log(String msg, Exception e) {
        Logger.getAnonymousLogger().log(Level.WARNING, msg, e);
    }

    void handleRequest(Socket connection) {
        Request req = readRequest(connection);
        if (isShutdownRequest(req))
            stop();
        else
            dispatchRequest(req);
    }

    interface Request {
    }

    private Request readRequest(Socket s) {
        return null;
    }

    private void dispatchRequest(Request r) {
    }

    private boolean isShutdownRequest(Request r) {
        return false;
    }
}
