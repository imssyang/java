package tutorial.essential.concurrency;

/**
 * A concurrent application's ability to execute in a timely manner is known as its liveness.
 */
public class Liveness {

    /**
     * Deadlock:
     *   Deadlock describes a situation where two or more threads are blocked forever, waiting for each other.
     */
    public static class Deadlock {
        static class Friend {
            private final String name;
            public Friend(String name) {
                this.name = name;
            }
            public String getName() {
                return this.name;
            }
            public synchronized void bow(Friend bower) {
                System.out.format("%s: %s  has bowed to me!%n",
                        this.name, bower.getName());
                bower.bowBack(this);
            }
            public synchronized void bowBack(Friend bower) {
                System.out.format("%s: %s  has bowed back to me!%n",
                        this.name, bower.getName());
            }
        }

        public static void main(String[] args) {
            final Friend alphonse = new Friend("Alphonse");
            final Friend gaston = new Friend("Gaston");
            new Thread(new Runnable() {
                public void run() { alphonse.bow(gaston); }
            }).start();
            new Thread(new Runnable() {
                public void run() { gaston.bow(alphonse); }
            }).start();
        }
    }

    /**
     * Livelock:
     *   A thread often acts in response to the action of another thread.
     *   If the other thread's action is also a response to the action of another thread,
     *   then livelock may result.
     *   As with deadlock, livelocked threads are unable to make further progress.
     *   However, the threads are not blocked — they are simply too busy responding to each other to resume work.
     *
     * Livelock Comparable Example:
     *   This is comparable to two people attempting to pass each other in a corridor:
     *   Alphonse moves to his left to let Gaston pass, while Gaston moves to his right to let Alphonse pass.
     *   Seeing that they are still blocking each other, Alphone moves to his right, while Gaston moves to his left.
     *   They're still blocking each other, so...
     */
    public static class Livelock {
    }

    /**
     * Starvation:
     *   Starvation describes a situation where a thread is unable to gain regular access to shared resources and is unable to make progress.
     *   This happens when shared resources are made unavailable for long periods by "greedy" threads.
     *   For example,
     *     1. Suppose an object provides a synchronized method that often takes a long time to return.
     *     2. If one thread invokes this method frequently, other threads that also need frequent synchronized access to the same object will often be blocked.
     */
    public static class Starvation {
    }

}
