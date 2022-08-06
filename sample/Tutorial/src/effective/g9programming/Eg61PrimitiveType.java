package effective.g9programming;

import java.util.Comparator;

/**
 * Item 61: Prefer primitive types to boxed primitives
 */
public class Eg61PrimitiveType {

    public void test() {
        // Broken comparator
        // Applying the == operator to boxed primitives is almost always wrong.
        Comparator<Integer> naturalOrderError =
                (i, j) -> (i < j) ? -1 : (i == j ? 0 : 1);

        Comparator<Integer> naturalOrderRight = (iBoxed, jBoxed) -> {
            int i = iBoxed, j = jBoxed; // Auto-unboxing
            return i < j ? -1 : (i == j ? 0 : 1);
        };

        int value1 = naturalOrderError.compare(new Integer(42), new Integer(42));
        int value2 = naturalOrderRight.compare(new Integer(42), new Integer(42));
        System.out.println(value1 + " " + value2); // print 1 0
    }

    public static class Unbelievable {
        static Integer i;
        public static void main(String[] args) {
            // when you mix primitives and boxed primitives in an operation, the boxed primitive is auto-unboxed.
            if (i == 42)
                System.out.println("Unbelievable");
        }
    }

    // Hideously slow program!
    public static void main(String[] args) {
        Long sum = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;
        }
        System.out.println(sum);
    }

}
