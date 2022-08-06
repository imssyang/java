package tutorial.essential.concurrency;

/**
 * Immutable Objects:
 *   An object is considered immutable if its state cannot change after it is constructed.
 *   Maximum reliance on immutable objects is widely accepted as a sound strategy for creating simple, reliable code.
 *
 * Note:
 *   Programmers are often reluctant to employ immutable objects, because they worry about the cost of creating a new object as opposed to updating an object in place.
 *   The impact of object creation is often overestimated, and can be offset by some of the efficiencies associated with immutable objects.
 *   These include decreased overhead due to garbage collection, and the elimination of code needed to protect mutable objects from corruption.
 */
public class ImmutableObjects {

    public static class SynchronizedRGB {
        private int red;
        private int green;
        private int blue;
        private String name;

        private void check(int red, int green, int blue) {
            if (red < 0 || red > 255
                    || green < 0 || green > 255
                    || blue < 0 || blue > 255) {
                throw new IllegalArgumentException();
            }
        }

        public SynchronizedRGB(int red, int green, int blue, String name) {
            check(red, green, blue);
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.name = name;
        }

        public void set(int red, int green, int blue, String name) {
            check(red, green, blue);
            synchronized (this) {
                this.red = red;
                this.green = green;
                this.blue = blue;
                this.name = name;
            }
        }

        public synchronized int getRGB() {
            return ((red << 16) | (green << 8) | blue);
        }

        public synchronized String getName() {
            return name;
        }

        public synchronized void invert() {
            red = 255 - red;
            green = 255 - green;
            blue = 255 - blue;
            name = "Inverse of " + name;
        }

        public synchronized String print() {
            return "red: " + red + " green: " + green + " blue: " + blue + " name: " + name;
        }

        public static void main(String[] args) throws InterruptedException {
            SynchronizedRGB color = new SynchronizedRGB(0, 0, 255, "Blue");

            /**
             * SynchronizedRGB must be used carefully to avoid being seen in an inconsistent state.
             * If another thread invokes color.set after Statement 1 but before Statement 2,
             * the value of myColorInt won't match the value of myColorName.
             * To avoid this outcome, the two statements must be bound together:
             */
            new Thread(() -> {
                synchronized (color) {
                    int myColorInt = color.getRGB(); //Statement 1

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    String myColorName = color.getName(); //Statement 2
                    System.out.println(color.print() + " myColorInt: " + myColorInt + " myColorName: " + myColorName);
                }
            }).start();

            new Thread(() -> {
                color.set(255, 255, 255, "White");
                System.out.println(color.print());
            }).start();

            Thread.sleep(3000);
            color.invert();
            System.out.println(color.print());
        }
    }

    /**
     * The following rules define a simple strategy for creating immutable objects:
     *   1. Don't provide "setter" methods — methods that modify fields or objects referred to by fields.
     *   2. Make all fields final and private.
     *   3. Don't allow subclasses to override methods.
     *      The simplest way to do this is to declare the class as final.
     *      A more sophisticated approach is to make the constructor private and construct instances in factory methods.
     *   4. If the instance fields include references to mutable objects, don't allow those objects to be changed:
     *      a) Don't provide methods that modify the mutable objects.
     *      b) Don't share references to the mutable objects.
     *         Never store references to external, mutable objects passed to the constructor;
     *         if necessary, create copies, and store references to the copies.
     *         Similarly, create copies of your internal mutable objects when necessary to avoid returning the originals in your methods.
     */
    static final public class ImmutableRGB {
        final private int red;
        final private int green;
        final private int blue;
        final private String name;

        private void check(int red, int green, int blue) {
            if (red < 0 || red > 255
                    || green < 0 || green > 255
                    || blue < 0 || blue > 255) {
                throw new IllegalArgumentException();
            }
        }

        public ImmutableRGB(int red, int green, int blue, String name) {
            check(red, green, blue);
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.name = name;
        }

        public int getRGB() {
            return ((red << 16) | (green << 8) | blue);
        }

        public String getName() {
            return name;
        }

        public ImmutableRGB invert() {
            return new ImmutableRGB(255 - red,
                    255 - green,
                    255 - blue,
                    "Inverse of " + name);
        }

        public String print() {
            return "red: " + red + " green: " + green + " blue: " + blue + " name: " + name;
        }

        public static void main(String[] args) throws InterruptedException {
            ImmutableRGB color = new ImmutableRGB(0, 0, 255, "Blue");

            /**
             * SynchronizedRGB must be used carefully to avoid being seen in an inconsistent state.
             * If another thread invokes color.set after Statement 1 but before Statement 2,
             * the value of myColorInt won't match the value of myColorName.
             * To avoid this outcome, the two statements must be bound together:
             */
            new Thread(() -> {
                int myColorInt = color.getRGB(); //Statement 1

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String myColorName = color.getName(); //Statement 2
                System.out.println(color.print() + " myColorInt: " + myColorInt + " myColorName: " + myColorName);
            }).start();

            new Thread(() -> {
                ImmutableRGB invert_color = color.invert();
                System.out.println(invert_color.print());
            }).start();
        }
    }
}
