package tutorial.essential.concurrency;

import java.util.Random;

/**
 * Guarded Block:
 *   Threads often have to coordinate their actions. The most common coordination idiom is the guarded block.
 *   Such a block begins by polling a condition that must be true before the block can proceed.
 *   There are a number of steps to follow in order to do this correctly.
 *
 * Example:
 *   public synchronized void guardedJoy() {
 *       // This guard only loops once for each special event, which may not be the event we're waiting for.
 *       while(!joy) {
 *           try {
 *               wait();
 *           } catch (InterruptedException e) {}
 *       }
 *       System.out.println("Joy and efficiency have been achieved!");
 *   }
 *
 *   public synchronized notifyJoy() {
 *       joy = true;
 *       notifyAll();
 *   }
 *
 * Note:
 *   1. When a thread invokes d.wait, it must own the intrinsic lock for d — otherwise an error is thrown.
 *      Invoking wait inside a synchronized method is a simple way to acquire the intrinsic lock.
 *   2. When wait is invoked, the thread releases the lock and suspends execution.
 *   3. At some future time, another thread will acquire the same lock and invoke Object.notifyAll,
 *      informing all threads waiting on that lock
 */
public class GuardedBlocks {

    /**
     * Let's use guarded blocks to create a Producer-Consumer application.
     * This kind of application shares data between two threads:
     *    the producer, that creates the data;
     *    the consumer, that does something with it.
     * The two threads communicate using a shared object.
     */
    public static class Drop {
        // Message sent from producer to consumer.
        private String message;
        // True if consumer should wait for producer to send message.
        // False if producer should wait for consumer to retrieve message.
        private boolean empty = true;

        public synchronized String take() {
            // Wait until message is available.
            while (empty) {
                try {
                    wait();
                } catch (InterruptedException e) {}
            }
            // Toggle status.
            empty = true;
            // Notify producer that status has changed.
            notifyAll();
            return message;
        }

        public synchronized void put(String message) {
            // Wait until message has been retrieved.
            while (!empty) {
                try {
                    wait();
                } catch (InterruptedException e) {}
            }
            // Toggle status.
            empty = false;
            // Store message.
            this.message = message;
            // Notify consumer that status has changed.
            notifyAll();
        }
    }

    public static class Producer implements Runnable {
        private Drop drop;

        public Producer(Drop drop) {
            this.drop = drop;
        }

        public void run() {
            String importantInfo[] = {
                    "Mares eat oats",
                    "Does eat oats",
                    "Little lambs eat ivy",
                    "A kid will eat ivy too"
            };
            Random random = new Random();

            for (int i = 0; i < importantInfo.length; i++) {
                drop.put(importantInfo[i]);
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {}
            }
            drop.put("DONE");
        }
    }

    public static class Consumer implements Runnable {
        private Drop drop;

        public Consumer(Drop drop) {
            this.drop = drop;
        }

        public void run() {
            Random random = new Random();
            for (String message = drop.take();
                 ! message.equals("DONE");
                 message = drop.take()) {
                System.out.format("MESSAGE RECEIVED: %s%n", message);
                try {
                    Thread.sleep(random.nextInt(5000));
                } catch (InterruptedException e) {}
            }
        }
    }

    public static class ProducerConsumerExample {
        public static void main(String[] args) {
            Drop drop = new Drop();
            (new Thread(new Producer(drop))).start();
            (new Thread(new Consumer(drop))).start();
        }
    }
}
