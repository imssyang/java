package jcip.examples.g5build_block.concurrent_collection;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.*;

/**
 * ConcurrentHashMap, a replacement for synchronized hashͲbased Map implementations, and CopyOnWriteArrayList, a
 * replacement for synchronized List implementations for cases where traversal is the dominant operation.
 *
 * Queue is intended to hold a set of elements temporarily while they await processing. Queue operations do not block;
 * if the queue is empty, the retrieval operation returns null. Several implementations:
 *   - ConcurrentLinkedQueue, a traditional FIFO queue.
 *   - PriorityQueue, a (non concurrent) priority ordered queue.
 *
 * Blocking queues provide blocking put and take methods as well as the timed equivalents offer and poll. If the queue
 * is full, put blocks until space becomes available; if the queue is empty, take blocks until an element is available.
 * Blocking queues support the producer-consumer design pattern, the class library contains several implementations:
 *   - LinkedBlockingQueue and ArrayBlockingQueue are FIFO queues, analogous to LinkedList and ArrayList but with
 *     better concurrent performance than a synchronized List.
 *   - PriorityBlockingQueue is a priority-ordered queue, which is useful when you want to process elements in an
 *     order other than FIFO. Just like other sorted collections, PriorityBlockingQueue can compare elements according to
 *     their natural order (if they implement Comparable) or using a Comparator.
 *   - SynchronousQueue is not really a queue at all, in that it maintains no storage space for queued elements.
 *     Instead, it maintains a list of queued threads waiting to enqueue or dequeue an element. since a SynchronousQueue
 *     has no storage capacity, put and take will block unless another thread is already waiting to participate in the handoff.
 *     Synchronous queues are generally suitable only when there are enough consumers that there nearly always will be
 *     one ready to take the handoff.
 *
 * Deque is a double-ended queue that allows efficient insertion and removal from both the head and the tail.
 * Implementations include:
 *   - ArrayDeque.
 *   - LinkedBlockingDeque.
 */

/**
 * ProducerConsumer
 * <p/>
 * Producer and consumer tasks in a desktop search application
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ProducerConsumer {
    static class FileCrawler implements Runnable {
        private final BlockingQueue<File> fileQueue;
        private final FileFilter fileFilter;
        private final File root;

        public FileCrawler(BlockingQueue<File> fileQueue,
                           final FileFilter fileFilter,
                           File root) {
            this.fileQueue = fileQueue;
            this.root = root;
            this.fileFilter = new FileFilter() {
                public boolean accept(File f) {
                    return f.isDirectory() || fileFilter.accept(f);
                }
            };
        }

        private boolean alreadyIndexed(File f) {
            return false;
        }

        public void run() {
            try {
                crawl(root);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void crawl(File root) throws InterruptedException {
            File[] entries = root.listFiles(fileFilter);
            if (entries != null) {
                for (File entry : entries)
                    if (entry.isDirectory())
                        crawl(entry);
                    else if (!alreadyIndexed(entry))
                        fileQueue.put(entry);
            }
        }
    }

    static class Indexer implements Runnable {
        private final BlockingQueue<File> queue;

        public Indexer(BlockingQueue<File> queue) {
            this.queue = queue;
        }

        public void run() {
            try {
                while (true)
                    indexFile(queue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void indexFile(File file) {
            // Index the file...
        };
    }

    private static final int BOUND = 10;
    private static final int N_CONSUMERS = Runtime.getRuntime().availableProcessors();

    public static void startIndexing(File[] roots) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
        FileFilter filter = new FileFilter() {
            public boolean accept(File file) {
                return true;
            }
        };

        for (File root : roots)
            new Thread(new FileCrawler(queue, filter, root)).start();

        for (int i = 0; i < N_CONSUMERS; i++)
            new Thread(new Indexer(queue)).start();
    }
}
