package effective.g5generics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Item 27: Eliminate unchecked warnings
 *   - Eliminate every unchecked warning that you can.
 *   - If you can’t eliminate a warning, but you can prove that the code that
 *     provoked the warning is typesafe, then (and only then) suppress the warning
 *     with an @SuppressWarnings("unchecked") annotation.
 *   - Always use the SuppressWarnings annotation on the smallest scope possible.
 *   - Every time you use a @SuppressWarnings("unchecked") annotation, add
 *     a comment saying why it is safe to do so.
 */
public class Eg27UncheckedWarning {

    Set<String> exaltation1 = new HashSet();   // unchecked assignment
    Set<String> exaltation2 = new HashSet<>();

    // Adding local variable to reduce scope of @SuppressWarnings
    public <T> T[] toArray(T[] a) {
        T[] elements = null;
        int size = 0;

        if (a.length < size) {
            // This cast is correct because the array we're creating
            // is of the same type as the one passed in, which is T[].
            @SuppressWarnings("unchecked") T[] result =
                    (T[]) Arrays.copyOf(elements, size, a.getClass());
            return result;
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

}
