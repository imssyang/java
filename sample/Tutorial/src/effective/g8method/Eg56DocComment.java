package effective.g8method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Item 56: Write doc comments for all exposed API elements
 */
public class Eg56DocComment {

    /**
     * Returns the element at the specified position in this list.
     *
     * <p>This method is <i>not</i> guaranteed to run in constant
     * time. In some implementations it may run in time proportional
     * to the element position.
     *
     * @param index index of element to return; must be
     *              non-negative and less than the size of this list
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= this.size()})
     */
    public <E> E get(int index) {
        return null;
    }

    /**
     * Returns true if this collection is empty.
     *
     * @implSpec
     * This implementation returns {@code this.size() == 0}.
     *
     * @return true if this collection is empty
     */
    public boolean isEmpty() {
        return false;
    }

    /**
     * A college degree, such as B.S., {@literal M.S.} or Ph.D.
     */
    public class Degree { }

    /**
     * An object that maps keys to values. A map cannot contain
     * duplicate keys; each key can map to at most one value.
     *
     * (Remainder omitted)
     *
     * @param <K> the type of keys maintained by this map
     * @param <V> the type of mapped values
     */
    public interface Map<K, V> { }

    /**
     * An instrument section of a symphony orchestra.
     */
    public enum OrchestraSection {
        /** Woodwinds, such as flute, clarinet, and oboe. */
        WOODWIND,
        /** Brass instruments, such as french horn and trumpet. */
        BRASS,
        /** Percussion instruments, such as timpani and cymbals. */
        PERCUSSION,
        /** Stringed instruments, such as violin and cello. */
        STRING;
    }

    /**
     * Indicates that the annotated method is a test method that
     * must throw the designated exception to pass.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExceptionTest {
        /**
         * The exception that the annotated test method must throw
         * in order to pass. (The test is permitted to throw any
         * subtype of the type described by this class object.)
         */
        Class<? extends Throwable> value();
    }


}
