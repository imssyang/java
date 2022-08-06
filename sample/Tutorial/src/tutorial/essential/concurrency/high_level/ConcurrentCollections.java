package tutorial.essential.concurrency.high_level;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ConcurrentCollections {

    public static class Producer implements Runnable {
        private BlockingQueue<String> drop;

        public Producer(BlockingQueue<String> drop) {
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

            try {
                for (int i = 0; i < importantInfo.length; i++) {
                    drop.put(importantInfo[i]);
                    Thread.sleep(random.nextInt(5000));
                }
                drop.put("DONE");
            } catch (InterruptedException e) {}
        }
    }

    public static class Consumer implements Runnable {
        private BlockingQueue<String> drop;

        public Consumer(BlockingQueue<String> drop) {
            this.drop = drop;
        }

        public void run() {
            Random random = new Random();
            try {
                for (String message = drop.take();
                     ! message.equals("DONE");
                     message = drop.take()) {
                    System.out.format("MESSAGE RECEIVED: %s%n",
                            message);
                    Thread.sleep(random.nextInt(5000));
                }
            } catch (InterruptedException e) {}
        }
    }

    public static void main(String args[]) {
        BlockingQueue<String> drop = new SynchronousQueue<>();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
