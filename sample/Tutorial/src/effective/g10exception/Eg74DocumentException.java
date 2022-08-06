package effective.g10exception;

/**
 * Item 74: Document all exceptions thrown by each method
 */
public class Eg74DocumentException {

    // Always declare checked exceptions individually, and document precisely
    // the conditions under which each one is thrown using the Javadoc @throws tag.

    // Use the Javadoc @throws tag to document each exception that a method
    // can throw, but do not use the throws keyword on unchecked exceptions.

    // If an exception is thrown by many methods in a class for the same reason,
    // you can document the exception in the class’s documentation comment rather
    // than documenting it individually for each method.

}
