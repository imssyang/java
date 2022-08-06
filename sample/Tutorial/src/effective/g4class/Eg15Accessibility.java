package effective.g4class;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Item 15: Minimize the accessibility of classes and members
 *   - Make each class or member as inaccessible as possible.
 *   - Instance fields of public classes should rarely be public,
 *     classes with public mutable fields are not generally thread-safe.
 */
public class Eg15Accessibility {

    // Potential security hole!
    public static final String[] VALUES = { "aaa", "bbb", "ccc" };

    // Make the public array private
    private static final String[] PRIVATE_VALUES2 = { "aaa", "bbb", "ccc" };

    // Add a public immutable list
    public static final List<String> VALUES2 =
        Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES2));

    // Add a public method that returns a copy of a private array
    public static final String[] values() {
        return PRIVATE_VALUES2.clone();
    }

}
