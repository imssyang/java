package tutorial.essential.concurrency;

import java.util.List;

public class Synchronization {

    /**
     * Thread Interference:
     *   Suppose Thread A invokes increment at about the same time Thread B invokes decrement.
     *   Thread A's result is lost, overwritten by Thread B. This particular interleaving is only one possibility.
     *   Under different circumstances it might be Thread B's result that gets lost, or there could be no error at all.
     *   Because they are unpredictable, thread interference bugs can be difficult to detect and fix.
     *
     * Memory Consistency Errors:
     *   Memory consistency errors occur when different threads have inconsistent views of what should be the same data.
     *   The key to avoiding memory consistency errors is understanding the happens-before relationship.
     */
    static class Counter {
        private int c = 0;

        public void increment() {
            c++;
        }

        public void decrement() {
            c--;
        }

        public int value() {
            return c;
        }
    }

    /**
     * Synchronized Methods:
     *   1. When one thread is executing a synchronized method for an object, all other threads that invoke synchronized
     *      methods for the same object block (suspend execution) until the first thread is done with the object.
     *   2. When a synchronized method exits, it automatically establishes a happens-before relationship
     *      with any subsequent invocation of a synchronized method for the same object.
     *      This guarantees that changes to the state of the object are visible to all threads.
     *
     * Synchronized methods enable a simple strategy for preventing thread interference and memory consistency errors.
     * (An important exception):
     *     final fields, which cannot be modified after the object is constructed,
     *     can be safely read through non-synchronized methods, once the object is constructed.
     *
     * Intrinsic Locks:
     *   1. Synchronization is built around an internal entity known as the intrinsic lock or monitor lock.
     *      Intrinsic locks play a role in both aspects of synchronization:
     *         a) enforcing exclusive access to an object's state;
     *         b) establishing happens-before relationships that are tutorial.essential to visibility.
     *   2. Every object has an intrinsic lock associated with it.
     *      By convention, a thread that needs exclusive and consistent access to an object's fields has to acquire
     *      the object's intrinsic lock before accessing them, and then release the intrinsic lock when it's done with them.
     *   3. When a thread invokes a synchronized method, it automatically acquires the intrinsic lock
     *      for that method's object and releases it when the method returns.
     *      The lock release occurs even if the return was caused by an uncaught exception.
     *   4. When a static synchronized method is invoked, since a static method is associated with a class, not an object.
     *      In this case, the thread acquires the intrinsic lock for the Class object associated with the class.
     *      Thus access to class's static fields is controlled by a lock that's distinct from the lock for any instance of the class.
     *
     * Reentrant Synchronization:
     *   Allowing a thread to acquire the same lock more than once enables reentrant synchronization.
     *   Without reentrant synchronization, synchronized code would have to take many additional precautions
     *   to avoid having a thread cause itself to block.
     *   Intrinsic lock are reentrant.
     */
    public static class SynchronizedCounter {
        private int c = 0;

        public synchronized void increment() {
            c++;
        }

        public synchronized void decrement() {
            c--;
        }

        public synchronized int value() {
            return c;
        }
    }


    /**
     * Synchronized Statements:
     *   1. Unlike synchronized methods, synchronized statements must specify the object that provides the intrinsic lock.
     *   2. Synchronized statements are also useful for improving concurrency with fine-grained synchronization.
     */
    public static class SynchronizedStatement {
        private List<String> nameList;
        private int nameCount = 0;

        private long c1 = 0;
        private long c2 = 0;
        private Object lock1 = new Object();
        private Object lock2 = new Object();

        public void addName(String name) {
            synchronized(this) {
                nameList.add(name);
            }

            nameCount++;
        }

        public void inc1() {
            synchronized(lock1) {
                c1++;
            }
        }

        public void inc2() {
            synchronized(lock2) {
                c2++;
            }
        }
    }

    /**
     * Atomic Access:
     *   1. An atomic action cannot stop in the middle: it either happens completely, or it doesn't happen at all.
     *   2. No side effects of an atomic action are visible until the action is complete.
     *   3. Atomic actions cannot be interleaved, so they can be used without fear of thread interference.
     *   4. However, this does not eliminate all need to synchronize atomic actions, because memory consistency errors are still possible.
     *   5. Using volatile variables reduces the risk of memory consistency errors, because any write to a volatile variable
     *      establishes a happens-before relationship with subsequent reads of that same variable.
     *   6. Using simple atomic variable access is more efficient than accessing these variables through synchronized code.
     *
     * Default atomic actions:
     *   1. Reads and writes are atomic for reference variables and for most primitive variables (all types except long and double).
     *   2. Reads and writes are atomic for all variables declared volatile (including long and double variables).
     */

    public static void main(String args[]) {
        Counter counter = new Counter();
        SynchronizedCounter synchronizedCounter = new SynchronizedCounter();
        SynchronizedStatement synchronizedStatement = new SynchronizedStatement();
    }
}
