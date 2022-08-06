package jcip.examples.g5build_block.synchronizer.future_task;

import jcip.examples.LaunderThrowable;

import java.util.concurrent.*;

/**
 * Preloader
 *
 * Using FutureTask to preload data that is needed later
 *
 * @author Brian Goetz and Tim Peierls
 */

public class Preloader {
    ProductInfo loadProductInfo() throws DataLoadException {
        System.out.println("load");
        return null;
    }

    private final FutureTask<ProductInfo> future =
        new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
            public ProductInfo call() throws DataLoadException {
                return loadProductInfo();
            }
        });
    private final Thread thread = new Thread(future);

    public void start() { thread.start(); }

    public ProductInfo get() throws DataLoadException, InterruptedException {
        try {
            // The behavior of Future.get depends on the state of the task.
            // If it is completed, get returns the result immediately, and
            // otherwise blocks until the task transitions to the completed state
            // and then returns the result or throws an exception.
            return future.get();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException)
                throw (DataLoadException) cause;
            else
                throw LaunderThrowable.launderThrowable(cause);
        }
    }

    interface ProductInfo {
    }

    public static void main(String[] args) {
        Preloader preloader = new Preloader();

        Thread backgroundThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                preloader.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        backgroundThread.start();

        try {
            ProductInfo productInfo = preloader.get();
            System.out.println(productInfo);
        } catch (DataLoadException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class DataLoadException extends Exception { }
