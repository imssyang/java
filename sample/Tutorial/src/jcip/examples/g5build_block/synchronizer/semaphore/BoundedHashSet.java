package jcip.examples.g5build_block.synchronizer.semaphore;

import java.util.*;
import java.util.concurrent.*;

/**
 * BoundedHashSet
 * <p/>
 * Using Semaphore to bound a collection
 *
 * @author Brian Goetz and Tim Peierls
 */
public class BoundedHashSet <T> {
    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(bound);
    }

    public boolean add(T o) {
        boolean wasAdded = false;
        try {
            // If no permit is available, acquire blocks until one is (or until interrupted or the operation times out).
            sem.acquire();
            wasAdded = set.add(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (!wasAdded)
                sem.release(); // The release method returns a permit to the semaphore.
        }
        return wasAdded;
    }

    public boolean remove(Object o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved)
            sem.release();
        return wasRemoved;
    }

    public static void main(String[] args) {
        BoundedHashSet boundedHashSet = new BoundedHashSet<String>(3);

        Runnable runProvider = new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 5; i++) {
                    String p = String.format("product_%d", i);
                    boolean b = boundedHashSet.add(p);
                    System.out.println(String.format("%s: add %s", b, p));
                }
            }
        };

        Runnable runCustomer = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    for(int i = 0; i < 5; i++) {
                        String p = String.format("product_%d", i);
                        boolean b = boundedHashSet.remove(p);
                        System.out.println(String.format("%s: del %s", b, p));

                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        new Thread(runCustomer).start();
        new Thread(runProvider).start();

        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
