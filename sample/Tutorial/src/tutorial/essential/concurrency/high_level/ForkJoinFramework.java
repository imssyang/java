package tutorial.essential.concurrency.high_level;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * The fork/join framework is an implementation of the ExecutorService interface that helps you take advantage of multiple processors.
 * The fork/join framework uses a work-stealing algorithm.
 * Worker threads that run out of things to do can steal tasks from other threads that are still busy.
 */
public class ForkJoinFramework {

    public static class ForkJoinAdd extends RecursiveTask<Long> {

        private final long[] numbers;
        private final int start;
        private final int end;
        public static final long threshold = 10_000;

        public ForkJoinAdd(long[] numbers) {
            this(numbers, 0, numbers.length);
        }

        private ForkJoinAdd(long[] numbers, int start, int end) {
            this.numbers = numbers;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {

            int length = end - start;
            if (length <= threshold) {
                return add();
            }

            ForkJoinAdd firstTask = new ForkJoinAdd(numbers, start, start + length / 2);
            firstTask.fork(); //start asynchronously

            ForkJoinAdd secondTask = new ForkJoinAdd(numbers, start + length / 2, end);

            Long secondTaskResult = secondTask.compute();
            Long firstTaskResult = firstTask.join();

            return firstTaskResult + secondTaskResult;

        }

        private long add() {
            long result = 0;
            for (int i = start; i < end; i++) {
                result += numbers[i];
            }
            return result;
        }

        public static long startForkJoinSum(long n) {
            long[] numbers = LongStream.rangeClosed(1, n).toArray();
            ForkJoinTask<Long> task = new ForkJoinAdd(numbers);
            return new ForkJoinPool().invoke(task);
        }

        public static void main(String[] args) {
            System.out.println(ForkJoinAdd.startForkJoinSum(1_000_000));
        }
    }


    public static class ForkJoinFibonacci extends RecursiveAction {
        private static final long threshold = 10;
        private volatile long number;

        public ForkJoinFibonacci(long number) {
            this.number = number;
        }

        public long getNumber() {
            return number;
        }

        @Override
        protected void compute() {
            long n = number;
            if (n <= threshold) {
                number = fib(n);
            } else {
                ForkJoinFibonacci f1 = new ForkJoinFibonacci(n - 1);
                ForkJoinFibonacci f2 = new ForkJoinFibonacci(n - 2);
                ForkJoinTask.invokeAll(f1, f2);
                number = f1.number + f2.number;
            }
        }

        private static long fib(long n) {
            if (n <= 1) return n;
            else return fib(n - 1) + fib(n - 2);
        }

        public static void main(String[] args) {
            ForkJoinFibonacci task = new ForkJoinFibonacci(50);
            new ForkJoinPool().invoke(task);

            System.out.println(task.getNumber());
        }
    }

}
