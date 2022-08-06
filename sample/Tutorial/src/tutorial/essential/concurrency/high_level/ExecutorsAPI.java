package tutorial.essential.concurrency.high_level;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * Executors:
 *   In large-scale applications, it makes sense to separate thread management and creation from the rest of the application.
 *   Objects that encapsulate these functions are known as executors.
 *
 * Executor interfaces(java.util.concurrent):
 *   1. Executor, a simple interface that supports launching new tasks.
 *   2. ExecutorService, a subinterface of Executor, which adds features that help manage the lifecycle,
 *      both of the individual tasks and of the executor itself.
 *   3. ScheduledExecutorService, a subinterface of ExecutorService, supports future and/or periodic execution of tasks.
 *
 * Thread Pools:
 *   Most of the executor implementations in java.util.concurrent use thread pools, which consist of worker threads.
 *   A simple way to create an executor that uses a fixed thread pool is to invoke the newFixedThreadPool factory method
 *   in java.util.concurrent.Executors.
 */
public class ExecutorsAPI {

    public static class ThreadTask {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        };

        Runnable runnable = () -> {
            try {
                String name = Thread.currentThread().getName();
                System.out.println("Foo " + name);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Bar " + name);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        void test() {
            task.run();

            Thread thread = new Thread(task);
            thread.start();

            Thread thread1 = new Thread(runnable);
            thread1.start();

            System.out.println("Done!");
        }

        public static void main(String[] args) {
            ThreadTask threadTask = new ThreadTask();
            threadTask.test();
        }
    }

    public static class ExecutorTask {
        Runnable taskRunnable = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);
        };

        void testRunnable() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.submit(taskRunnable);

            try {
                System.out.println("attempt to shutdown executor");
                executor.shutdown(); //waits for currently running tasks to finish
                executor.awaitTermination(5, TimeUnit.SECONDS);
            }
            catch (InterruptedException e) {
                System.err.println("tasks interrupted");
            }
            finally {
                if (!executor.isTerminated()) {
                    System.err.println("cancel non-finished tasks");
                }
                executor.shutdownNow(); //interrupts all running tasks and shut the executor down immediately.
                System.out.println("shutdown finished");
            }

            System.out.println("Done!");
        }

        Callable<Integer> taskCallable = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                return 123;
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        };

        void testSubmit() {
            ExecutorService executor = Executors.newFixedThreadPool(1);
            Future<Integer> future = executor.submit(taskCallable);

            System.out.println("future done? " + future.isDone());

            Integer result = null;
            try {
                result = future.get(10, TimeUnit.SECONDS);
                executor.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

            System.out.println("future done? " + future.isDone());
            System.out.print("result: " + result);
            System.out.println("Done!");
        }

        void testInvoke() {
            ExecutorService executor = Executors.newWorkStealingPool();

            List<Callable<String>> taskList = Arrays.asList(
                    () -> "task1",
                    () -> "task2",
                    () -> "task3");

            try {
                executor.invokeAll(taskList)
                        .stream()
                        .map(future -> {
                            try {
                                return future.get();
                            }
                            catch (Exception e) {
                                throw new IllegalStateException(e);
                            }
                        })
                        .forEach(System.out::println);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Done!");

            try {
                // invokeAny() instead of returning future objects this method blocks
                // until the first callable terminates and returns the result of that callable.
                String result = executor.invokeAny(taskList);
                System.out.println(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("Done!");
        }

        void testScheduled() {
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

            Runnable task = () -> System.out.println("Scheduling: " + System.nanoTime());
            ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);

            Runnable task1 = () -> System.out.println("Scheduling1: " + System.nanoTime());
            int initialDelay = 0;
            int period = 1;
            executor.scheduleAtFixedRate(task1, initialDelay, period, TimeUnit.SECONDS);
            //executor.shutdown();

            try {
                TimeUnit.MILLISECONDS.sleep(1337);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
            System.out.printf("Remaining Delay: %sms%n", remainingDelay);
        }

        public static void main(String[] args) {
            ExecutorTask executorTask = new ExecutorTask();
            executorTask.testRunnable();
            executorTask.testSubmit();
            executorTask.testInvoke();
            executorTask.testScheduled();
        }
    }
}
