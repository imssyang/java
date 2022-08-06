package effective.g3method;

import java.util.Objects;

/**
 * Item 12: Always override toString
 */
public class Eg12ToString {

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
        @Override public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof PhoneNumber))
                return false;
            PhoneNumber pn = (PhoneNumber)o;
            return pn.lineNum == lineNum && pn.prefix == prefix
                    && pn.areaCode == areaCode;
        }

        // NOTE: Always override hashCode when you override equals
        @Override public int hashCode() {
            return Objects.hash(lineNum, prefix, areaCode);
        }

        /**
         * Returns the string representation of this phone number.
         * The string consists of twelve characters whose format is
         * "XXX-YYY-ZZZZ", where XXX is the area code, YYY is the
         * prefix, and ZZZZ is the line number. Each of the capital
         * letters represents a single decimal digit.
         *
         * If any of the three parts of this phone number is too small
         * to fill up its field, the field is padded with leading zeros.
         * For example, if the value of the line number is 123, the last
         * four characters of the string representation will be "0123".
         */
        @Override public String toString() {
            return String.format("%03d-%03d-%04d",
                    areaCode, prefix, lineNum);
        }

    }

}
