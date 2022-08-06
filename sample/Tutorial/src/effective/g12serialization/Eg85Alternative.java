package effective.g12serialization;

import java.util.HashSet;
import java.util.Set;

/**
 * Item 85: Prefer alternatives to Java serialization
 *   - The best way to avoid serialization exploits is never to deserialize anything.
 *   - There is no reason to use Java serialization in any new system you write.
 */
public class Eg85Alternative {

    // Deserialization bomb - deserializing this stream takes forever
    // The object graph consists of 201 HashSet instances, each of which contains 3
    // or fewer object references. The entire stream is 5,744 bytes long, yet the sun
    // would burn out long before you could deserialize it.
    static byte[] bomb() {
        Set<Object> root = new HashSet<>();
        Set<Object> s1 = root;
        Set<Object> s2 = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Set<Object> t1 = new HashSet<>();
            Set<Object> t2 = new HashSet<>();
            t1.add("foo"); // Make t1 unequal to t2
            s1.add(t1); s1.add(t2);
            s2.add(t1); s2.add(t2);
            s1 = t1;
            s2 = t2;
        }
        return serialize(root); // Method omitted for brevity
    }

    private static byte[] serialize(Set<Object> root) {
        return null;
    }

}
