package tutorial.essential.concurrency;

public class ThreadObjects {

    //(more general)The Runnable interface defines a single method, run, meant to contain the code executed in the thread.
    public static class HelloRunnable implements Runnable {
        Object parameter;
        public HelloRunnable(Object parameter) {
            this.parameter = parameter;
        }
        public void run() {
            Thread mainThread = (Thread)parameter;
            try {
                //The join method allows one thread to wait for the completion of another.
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello from HelloRunnable!");
        }
    }

    //The Thread class itself implements Runnable, though its run method does nothing.
    public static class HelloThread extends Thread {
        public void run() {
            System.out.println("Hello from HelloThread!");
        }
    }

    //Thread.sleep causes the current thread to suspend execution for a specified period.
    public static class SleepMessages {
        public static void printWithSleep() {
            String importantInfo[] = {
                    "Mares eat oats",
                    "Does eat oats",
                    "Little lambs eat ivy",
                    "A kid will eat ivy too"
            };


            /**
             * Many methods that throw InterruptedException, such as sleep,
             * are designed to cancel their current operation and return immediately when an interrupt is received.
             */
            for (int i = 0; i < importantInfo.length; i++) {
                try {
                    if (1 == i) {
                        // Invoking Thread.interrupt sets interrupt status.
                        Thread.currentThread().interrupt();
                        throw new InterruptedException();
                    }

                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // The non-static isInterrupted method, which is used by one thread
                // to query the interrupt status of another, does not change the interrupt status flag.
                if (Thread.currentThread().isInterrupted())
                {
                    System.out.println("currentThread().isInterrupted()");
                }

                //  When a thread checks for an interrupt by invoking the static method Thread.interrupted, interrupt status is cleared.
                if (Thread.interrupted()) {
                    System.out.println("We've been interrupted: no more crunching");
                }

                System.out.println(importantInfo[i]);
            }
        }
    }

    public static void main(String args[]) {
        (new Thread(new HelloRunnable(Thread.currentThread()))).start();
        (new HelloThread()).start();

        SleepMessages.printWithSleep();
    }

}
