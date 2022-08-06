package effective.g3method;

import java.awt.*;
import java.util.Objects;

/**
 * Item 10: Obey the general contract when overriding equals
 *   - Reflexive: For any non-null reference value x, x.equals(x) must return true.
 *   - Symmetric: For any non-null reference values x and y, x.equals(y) must return true
 *                if and only if y.equals(x) returns true.
 *   - Transitive: For any non-null reference values x, y, z, if x.equals(y) returns true
 *                and y.equals(z) returns true, then x.equals(z) must return true.
 *   - Consistent: For any non-null reference values x and y, multiple invocations of x.equals(y)
 *                 must consistently return true or consistently return false, provided no information
 *                 used in equals comparisons is modified.
 *   - For any non-null reference value x, x.equals(null) must return false.
 */
public class Eg10Equals {

    // The class is private or package-private, and you are certain that
    // its equals method will never be invoked.
    private class NoEquals {
        @Override public boolean equals(Object o) {
            throw new AssertionError(); // Method is never called
        }
    }

    // Broken - violates symmetry!
    public static final class CaseInsensitiveString {
        private final String s;
        public CaseInsensitiveString(String s) {
            this.s = Objects.requireNonNull(s);
        }

        // Broken - violates symmetry!
        @Override public boolean equals(Object o) {
            if (o instanceof CaseInsensitiveString)
                return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
            if (o instanceof String) // One-way interoperability!
                return s.equalsIgnoreCase((String) o);
            return false;
        }

        public static void main(String[] args) {
            CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
            String s = "polish";
            System.out.println(cis.equals(s)); //true
            System.out.println(s.equals(cis)); //false
        }
    }

    public static class Point {
        private final int x;
        private final int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override public boolean equals(Object o) {
            if (!(o instanceof Point))
                return false;
            Point p = (Point)o;
            return p.x == x && p.y == y;
        }
    }

    public static class ColorPoint extends Point {
        private final Color color;
        public ColorPoint(int x, int y, Color color) {
            super(x, y);
            this.color = color;
        }

        // Broken - violates transitivity!
        @Override public boolean equals(Object o) {
            if (!(o instanceof Point))
                return false;

            // If o is a normal Point, do a color-blind comparison
            if (!(o instanceof ColorPoint))
                return o.equals(this);

            // o is a ColorPoint; do a full comparison
            return super.equals(o) && ((ColorPoint) o).color == color;
        }

        public static void main(String[] args) {
            Point p = new Point(1, 2);
            ColorPoint cp = new ColorPoint(1, 2, Color.RED);
            System.out.println(p.equals(cp)); //true
            System.out.println(cp.equals(p)); //true

            ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
            Point p2 = new Point(1, 2);
            ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);
            System.out.println(p1.equals(p2)); //true
            System.out.println(p2.equals(p3)); //true
            System.out.println(p1.equals(p3)); //false
        }
    }

    // Class with a typical equals method
    public static final class PhoneNumber {
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

        // NOTE: Don’t substitute another type for Object in the equals declaration.
        @Override public boolean equals(Object o) { // must be Object!
            // 1. Use the == operator to check if the argument is a reference to this object.
            if (o == this)
                return true;

            // 2. Use the instanceof operator to check if the argument has the correct type.
            if (!(o instanceof PhoneNumber))
                return false;

            // 3. Cast the argument to the correct type.
            PhoneNumber pn = (PhoneNumber)o;

            // 4. For each “significant” field in the class, check if that field of the argument
            //    matches the corresponding field of this object.
            return pn.lineNum == lineNum && pn.prefix == prefix
                    && pn.areaCode == areaCode;
        }

        // NOTE: Always override hashCode when you override equals
        @Override public int hashCode() {
            int result = Short.hashCode(areaCode);
            result = 31 * result + Short.hashCode(prefix);
            result = 31 * result + Short.hashCode(lineNum);
            return result;
        }
    }

}
