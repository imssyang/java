package effective.g3method;

import java.util.*;

import static java.util.Comparator.comparingInt;

/**
 * Item 14: Consider implementing Comparable
 *   In the following description, the notation sgn(expression) designates the mathematical signum function,
 *   which is defined to return -1, 0, or 1, according to whether the value of expression is negative, zero, or positive.
 *   - The implementor must ensure that sgn(x.compareTo(y)) == -sgn(y.compareTo(x)) for all x and y.
 *     (This implies that x.compareTo(y) must throw an exception if and only if y.compareTo(x) throws an exception.)
 *   - The implementor must also ensure that the relation is transitive: (x.compareTo(y) > 0 && y.compareTo(z) > 0)
 *     implies x.compareTo(z) > 0.
 *   - Finally, the implementor must ensure that x.compareTo(y) == 0 implies that sgn(x.compareTo(z)) == sgn(y.compareTo(z)), for all z.
 *   - It is strongly recommended, but not required, that (x.compareTo(y) == 0) == (x.equals(y)).
 *     Generally speaking, any class that implements the Comparable interface and violates this condition
 *     should clearly indicate this fact.
 * Note:
 *   You should have the class implement the Comparable interface so that its
 *   instances can be easily sorted, searched, and used in comparison-based tutorial.collections.
 */
public class Eg14Comparable {

    public static class WordList {
        public static void main(String[] args) {
            // relies on the fact that String implements Comparable
            Set<String> s = new TreeSet<>();
            Collections.addAll(s, args);
            System.out.println(s);
        }
    }

    // Single-field Comparable with object reference field
    public final class CaseInsensitiveString implements Comparable<CaseInsensitiveString> {
        private String s;
        public int compareTo(CaseInsensitiveString cis) {
            return String.CASE_INSENSITIVE_ORDER.compare(s, cis.s);
        }
    }

    public static final class PhoneNumber implements Comparable<PhoneNumber> {
        private final short areaCode, prefix, lineNum;
        public PhoneNumber(int areaCode, int prefix, int lineNum) {
            this.areaCode = rangeCheck(areaCode, 999, "area code");
            this.prefix = rangeCheck(prefix, 999, "prefix");
            this.lineNum = rangeCheck(lineNum, 9999, "line num");
        }
        private static short rangeCheck(int val, int max, String arg) {
            if (val < 0 || val > max)
                throw new IllegalArgumentException(arg + ": " + val);
            return (short) val;
        }

        // Multiple-field Comparable with primitive fields
        public int compareTo2(PhoneNumber pn) {
            int result = Short.compare(areaCode, pn.areaCode);
            if (result == 0) {
                result = Short.compare(prefix, pn.prefix);
                if (result == 0)
                    result = Short.compare(lineNum, pn.lineNum);
            }
            return result;
        }

        // (JDK8) Comparable with comparator construction methods
        private static final Comparator<PhoneNumber> COMPARATOR =
                comparingInt((PhoneNumber pn) -> pn.areaCode)
                        .thenComparingInt(pn -> pn.prefix)
                        .thenComparingInt(pn -> pn.lineNum);

        public int compareTo(PhoneNumber pn) {
            return COMPARATOR.compare(this, pn);
        }
    }

}
