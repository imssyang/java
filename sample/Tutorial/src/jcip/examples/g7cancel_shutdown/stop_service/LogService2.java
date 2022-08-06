package jcip.examples.g7cancel_shutdown.stop_service;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class LogService2 {
    private final ExecutorService exec = newSingleThreadExecutor();
    private final BlockingQueue<String> queue;
    private final PrintWriter writer;

    public LogService2(Writer writer) {
        this.queue = new LinkedBlockingQueue<String>();
        this.writer = new PrintWriter(writer);
    }

    public void start() { }
    public void stop() throws InterruptedException {
        try {
            exec.shutdown();
            exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } finally {
            writer.close();
        }
    }
    public void log(String msg) {
        try {
            exec.execute(new WriteTask(msg));
        } catch (RejectedExecutionException ignored) { }
    }

    private class WriteTask implements Runnable {
        public WriteTask(String msg) {
            try {
                queue.put(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        String msg = queue.take();
                        writer.println(msg);
                    } catch (InterruptedException e) { /* retry */
                    }
                }
            } finally {
                writer.close();
            }
        }
    }
}
