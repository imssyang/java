package effective.g5generics;

import java.util.*;

/**
 * Item 26: Don’t use raw types
 *   - Each generic type defines a raw type, which is the name of the generic
 *     type used without any accompanying type parameters. For example, the
 *     raw type corresponding to List<E> is List.
 *   - If you use raw types, you lose all the safety and expressiveness benefits of generics.
 *
 * Terms:
 *   Parameterized type       List<String>
 *   Actual type parameter    String
 *   Generic type             List<E>
 *   Formal type parameter    E
 *   Unbounded wildcard type  List<?>
 *   Raw type                 List
 *   Bounded type parameter   <E extends Number>
 *   Recursive type bound     <T extends Comparable<T>>
 *   Bounded wildcard type    List<? extends Number>
 *   Generic method           static <E> List<E> asList(E[] a)
 *   Type token               String.class
 */
public class Eg26RawType {

    static class Stamp {
    }

    public static void main(String[] args) {
        // Raw collection type - don't do this!
        // My stamp collection. Contains only Stamp instances.
        final Collection stamps = new HashSet();

        // Raw iterator type - don't do this!
        for (Iterator i = stamps.iterator(); i.hasNext(); ) {
            Stamp stamp = (Stamp) i.next(); // Throws ClassCastException
        }

        // Fails at runtime - unsafeAdd method uses a raw type (List)!
        List<String> strings = new ArrayList<>();
        unsafeAdd(strings, Integer.valueOf(42));
        String s = strings.get(0); // Has compiler-generated cast
    }

    private static void unsafeAdd(List list, Object o) {
        list.add(o);
    }

    // Use of raw type for unknown element type - don't do this!
    static int numElementsInCommon(Set s1, Set s2) {
        int result = 0;
        for (Object o1 : s1)
            if (s2.contains(o1))
                result++;
        return result;
    }

    // Uses unbounded wildcard type - typesafe and flexible
    static int numElementsInCommon2(Set<?> s1, Set<?> s2) {
        return 0;
    }

}
