package effective.g2object;

/**
 * Item 8: Avoid finalizers and cleaners (replace to implement AutoCloseable)
 *   - Finalizers are unpredictable, often dangerous, and generally unnecessary.
 *   - Cleaners are less dangerous than finalizers, but still unpredictable, slow, and generally unnecessary.
 *   - One shortcoming of finalizers and cleaners is that there is no guarantee they’ll be executed promptly.
 *   - Don’t be seduced by the methods System.gc and System.runFinalization. They may increase the odds of
 *     finalizers or cleaners getting executed, but they don’t guarantee it.
 */
public class Eg8AvoidFinalizer {

    // Just have your class implement AutoCloseable, and require its clients to invoke the close method
    // on each instance when it is no longer needed, typically using try-with-resources to ensure termination
    // even in the face of exceptions.
    public static class Room implements AutoCloseable {
        //private static final Cleaner cleaner = Cleaner.create(); //JDK9

        // Resource that requires cleaning. Must not refer to Room!
        private static class State implements Runnable {
            int numJunkPiles; // Number of junk piles in this room

            State(int numJunkPiles) {
                this.numJunkPiles = numJunkPiles;
            }

            // Invoked by close method or cleaner
            @Override
            public void run() {
                System.out.println("Cleaning room");
                numJunkPiles = 0;
            }
        }

        // The state of this room, shared with our cleanable
        private final State state;
        // Our cleanable. Cleans the room when it’s eligible for gc
        //private final Cleaner.Cleanable cleanable;

        public Room(int numJunkPiles) {
            state = new State(numJunkPiles);
            //cleanable = cleaner.register(this, state);
        }

        @Override
        public void close() {
            //cleanable.clean();
        }

    }

    public static void main(String[] args) {
        //prints Goodbye, followed by Cleaning room.
        try (Room myRoom = new Room(7)) {
            System.out.println("Goodbye");
        }

        //You might expect it to print Peace out, followed by Cleaning room,
        //but on my machine, it never prints Cleaning room; it just exits.
        new Room(99);
        System.out.println("Peace out");
    }

}
