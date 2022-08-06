package effective.g8method;

import java.math.BigInteger;
import java.util.*;

/**
 * Item 52: Use overloading judiciously
 *   - A safe, conservative policy is never to export two overloadings with
 *     the same number of parameters.
 *   - You can always give methods different names instead of overloading them.
 */
public class Eg52Overload {

    // Broken! - What does this program print?
    public static class CollectionClassifier {
        public static String classify(Set<?> s) {  // overloaded
            return "Set";
        }
        public static String classify(List<?> lst) { // overloaded
            return "List";
        }
        public static String classify(Collection<?> c) { // overloaded
            return "Unknown Collection";
        }
        public static String classify2(Collection<?> c) {
            return c instanceof Set ? "Set" :
                    c instanceof List ? "List" : "Unknown Collection";
        }
        public static void main(String[] args) {
            Collection<?>[] collections = {
                    new HashSet<String>(),
                    new ArrayList<BigInteger>(),
                    new HashMap<String, String>().values()
            };
            // the choice of which overloading to invoke is made at compile time.
            for (Collection<?> c : collections)
                System.out.println(classify(c)); //print "Unknown Collection"
            for (Collection<?> c : collections)
                System.out.println(classify2(c)); //print "Set" "List" "Unknown Collection"
        }
    }

    // Selection among overloaded methods is static, while
    // selection among overridden methods is dynamic.
    static class Wine {
        String name() { return "wine"; }
    }
    static class SparklingWine extends Wine {
        @Override String name() { return "sparkling wine"; }
    }
    static class Champagne extends SparklingWine {
        @Override String name() { return "champagne"; }
    }
    public static class Overriding {
        public static void main(String[] args) {
            List<Wine> wineList = Arrays.asList(
                    new Wine(), new SparklingWine(), new Champagne());
            for (Wine wine : wineList)
                System.out.println(wine.name());
        }
    }

    public static class SetList {
        public static void main(String[] args) {
            Set<Integer> set = new TreeSet<>();
            List<Integer> list = new ArrayList<>();
            for (int i = -3; i < 3; i++) {
                set.add(i);
                list.add(i);
            }
            for (int i = 0; i < 3; i++) {
                set.remove(i);
                //list.remove(i); // BAD!! will call remove(int i), not remove(E).
                list.remove((Integer) i); // or remove(Integer.valueOf(i))
            }
            System.out.println(set + " " + list);
        }
    }

}
