package effective.g3method;

import java.util.Objects;

/**
 * Item 11: Always override hashCode when you override equals
 *   - If you fail to do so, your class will prevent it from functioning properly in tutorial.collections such as HashMap and HashSet.
 */
public class Eg11HashCode {

    public static final class PhoneNumber {
        // hashCode method with lazily initialized cached hash code
        private int hashCode; // Automatically initialized to 0

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
            int result = hashCode;
            if (result == 0) {
                result = Short.hashCode(areaCode);
                result = 31 * result + Short.hashCode(prefix);
                result = 31 * result + Short.hashCode(lineNum);
                hashCode = result;
            }
            return result;
        }
    }

}
