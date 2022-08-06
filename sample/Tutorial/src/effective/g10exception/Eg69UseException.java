package effective.g10exception;

/**
 * Item 69: Use exceptions only for exceptional conditions
 *   - Exceptions are, as their name implies, to be used only for exceptional conditions;
 *     they should never be used for ordinary control flow.
 *   - A well-designed API must not force its clients to use exceptions for ordinary control flow.
 */
public class Eg69UseException {

    public static void main(String[] args) {
        // Horrible abuse of exceptions. Don't ever do this!
        int[] range = {1, 2, 3};
        int s = 0;
        try {
            int i = 0;
            while(true)
                s += range[i++];
        } catch (ArrayIndexOutOfBoundsException e) {
        }

        // Good
        for (int i : range) {
        }
    }

}
