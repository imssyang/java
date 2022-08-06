package effective.g8method;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * Item 55: Return optionals judiciously
 *   - Never return a null value from an Optional-returning method.
 *   - Optionals are similar in spirit to checked exceptions.
 */
public class Eg55ReturnOptional {

    // Returns maximum value in collection - throws exception if empty
    public static <E extends Comparable<E>> E max1(Collection<E> c) {
        if (c.isEmpty())
            throw new IllegalArgumentException("Empty collection");
        E result = null;
        for (E e : c)
            if (result == null || e.compareTo(result) > 0)
                result = Objects.requireNonNull(e);
        return result;
    }

    // (JDK8) Returns maximum value in collection as an Optional<E>
    public static <E extends Comparable<E>> Optional<E> max2(Collection<E> c) {
        if (c.isEmpty())
            return Optional.empty();
        E result = null;
        for (E e : c)
            if (result == null || e.compareTo(result) > 0)
                result = Objects.requireNonNull(e);
        return Optional.of(result);
    }

    // (JDK8) Returns max val in collection as Optional<E> - uses stream
    public static <E extends Comparable<E>> Optional<E> max(Collection<E> c) {
        return c.stream().max(Comparator.naturalOrder());
    }

}
