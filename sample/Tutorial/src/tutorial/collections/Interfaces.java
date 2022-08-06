package tutorial.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A tutorial.collections framework is a unified architecture for representing and manipulating tutorial.collections.
 * All tutorial.collections frameworks contain the following:
 *   1. Interfaces: These are abstract data types that represent tutorial.collections.
 *                  Interfaces allow tutorial.collections to be manipulated independently of the details of their representation.
 *                  In object-oriented languages, interfaces generally form a hierarchy.
 *   2. Implementations: These are the concrete implementations of the collection interfaces.
 *                       In essence, they are reusable data structures.
 *   3. Algorithms: These are the methods that perform useful computations, such as searching and sorting,
 *                  on objects that implement collection interfaces.
 *                  In essence, algorithms are reusable functionality.
 *
 * The core collection interfaces(Two interface trees)：
 *   one starting with Collection and including Set, SortedSet, List, and Queue,
 *   another starting with Map and including SortedMap.
 *   1. Collection — the root of the collection hierarchy.
 *   2. Set — a collection that cannot contain duplicate elements.
 *   3. List — an ordered collection (sometimes called a sequence). Lists can contain duplicate elements.
 *   4. Queue — a collection used to hold multiple elements prior to processing.
 *               Queues typically, but do not necessarily, order elements in a FIFO (first-in, first-out) manner.
 *   5. Deque — a collection used to hold multiple elements prior to processing.
 *               Deques can be used both as FIFO (first-in, first-out) and LIFO (last-in, first-out).
 *   6. Map — an object that maps keys to values.
 *             A Map cannot contain duplicate keys; each key can map to at most one value.
 *   7. SortedSet — a Set that maintains its elements in ascending order.
 *   8. SortedMap — a Map that maintains its mappings in ascending key order.
 */
public class Interfaces {

    public static class Collections {

        void testIterating() {
            List<String> listNames = new ArrayList<>();
            listNames.add("Tom");
            listNames.add("Mary");
            listNames.add("Peter");
            listNames.add("John");
            listNames.add("Kim");

            System.out.println("The Classic For Loop:");
            for (int i = 0; i < listNames.size(); i++) {
                String name = listNames.get(i);
                System.out.println(name);
            }

            System.out.println("The Iterator Method:");
            Iterator<String> iterator = listNames.iterator();
            while (iterator.hasNext()) {
                String name = iterator.next();
                System.out.println(name);
            }

            System.out.println("The Enhanced For Loop:");
            for (String name : listNames) {
                System.out.println(name);
            }

            System.out.println("The Aggregate Operations:");
            listNames.stream().sorted().forEach(name -> System.out.println(name));

            System.out.println("The forEach Method with Lambda Expressions:");
            listNames.forEach(name -> System.out.println(name));
        }

        public static void main(String args[]) {
            Collections collections = new Collections();
            collections.testIterating();
        }
    }


}
