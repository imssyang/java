package effective.g9programming;

import java.util.Random;

/**
 * Item 59: Know and use the libraries
 *   - By using a standard library, you take advantage of the knowledge of the
 *     experts who wrote it and the experience of those who used it before you.
 */
public class Eg59UseLibrary {

    // Common but deeply flawed!
    // As of Java 7, you should no longer use Random. For most uses, the random
    // number generator of choice is now ThreadLocalRandom.
    static Random rnd = new Random();
    static int random(int n) {
        return Math.abs(rnd.nextInt()) % n;
    }

    public static void main(String[] args) {
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;
        for (int i = 0; i < 1000000; i++)
            if (random(n) < n/2)
                low++;
        System.out.println(low);
    }

}
