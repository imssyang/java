package effective.g4class;

import java.applet.AudioClip;
import java.util.AbstractList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Item 20: Prefer interfaces to abstract classes
 */
public class Eg20InterfaceAndAbstract {

    public interface Song {
    }

    public interface Singer {
        AudioClip sing(Song s);
    }

    public interface Songwriter {
        Song compose(int chartPosition);
    }

    public interface SingerSongwriter extends Singer, Songwriter {
        AudioClip strum();
        void actSensitive();
    }

    // Concrete implementation built atop skeletal implementation
    static List<Integer> intArrayAsList(int[] a) {
        Objects.requireNonNull(a);
        // The diamond operator is only legal here in Java 9 and later
        // If you're using an earlier release, specify <Integer>
        return new AbstractList<Integer>() {
            @Override public Integer get(int i) {
                return a[i]; // Autoboxing (Item 6)
            }
            @Override public Integer set(int i, Integer val) {
                int oldVal = a[i];
                a[i] = val; // Auto-unboxing
                return oldVal; // Autoboxing
            }
            @Override public int size() {
                return a.length;
            }
        };
    }

    // Skeletal implementation class
    public abstract class AbstractMapEntry<K,V> implements Map.Entry<K,V> {
        // Entries in a modifiable map must override this method
        @Override public V setValue(V value) {
            throw new UnsupportedOperationException();
        }
        // Implements the general contract of Map.Entry.equals
        @Override public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry) o;
            return Objects.equals(e.getKey(), getKey())
                    && Objects.equals(e.getValue(), getValue());
        }
        // Implements the general contract of Map.Entry.hashCode
        @Override public int hashCode() {
            return Objects.hashCode(getKey())
                    ^ Objects.hashCode(getValue());
        }
        @Override public String toString() {
            return getKey() + "=" + getValue();
        }
    }
}
