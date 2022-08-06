package effective.g10exception;

/**
 * Item 75: Include failure-capture information in detail messages
 */
public class Eg75FailureInformation {

    static class IndexOutOfBoundsException extends Exception {
        private final int lowerBound;
        private final int upperBound;
        private final int index;

        /**
         * Constructs an IndexOutOfBoundsException.
         *
         * @param lowerBound the lowest legal index value
         * @param upperBound the highest legal index value plus one
         * @param index the actual index value
         */
        public IndexOutOfBoundsException(int lowerBound, int upperBound,
                                         int index) {
            // Generate a detail message that captures the failure
            super(String.format(
                    "Lower bound: %d, Upper bound: %d, Index: %d",
                    lowerBound, upperBound, index));
            // Save failure information for programmatic access
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            this.index = index;
        }
    }

}
