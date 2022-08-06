package effective.g10exception;

/**
 * Item 71: Avoid unnecessary use of checked exceptions
 */
public class Eg71UnnecessaryException {

    public static void main(String[] args) {

        try {
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace(); // Oh well, we lose.
            System.exit(1);
        } catch (ArrayStoreException e) {
            throw new AssertionError(); // Can't happen!
        }
    }

}
