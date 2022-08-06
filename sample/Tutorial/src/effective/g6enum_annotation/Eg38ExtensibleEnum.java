package effective.g6enum_annotation;

import java.util.Arrays;
import java.util.Collection;

/**
 * Item 38: Emulate extensible enums with interfaces
 */
public class Eg38ExtensibleEnum {

    public interface Operation {
        double apply(double x, double y);
    }

    // Emulated extensible enum using an interface
    public enum BasicOperation implements Operation {
        PLUS("+") {
            public double apply(double x, double y) { return x + y; }
        },
        MINUS("-") {
            public double apply(double x, double y) { return x - y; }
        },
        TIMES("*") {
            public double apply(double x, double y) { return x * y; }
        },
        DIVIDE("/") {
            public double apply(double x, double y) { return x / y; }
        };
        private final String symbol;
        BasicOperation(String symbol) {
            this.symbol = symbol;
        }
        @Override public String toString() {
            return symbol;
        }
    }

    // Emulated extension enum
    public enum ExtendedOperation implements Operation {
        EXP("^") {
            public double apply(double x, double y) {
                return Math.pow(x, y);
            }
        },
        REMAINDER("%") {
            public double apply(double x, double y) {
                return x % y;
            }
        };
        private final String symbol;
        ExtendedOperation(String symbol) {
            this.symbol = symbol;
        }
        @Override public String toString() {
            return symbol;
        }
    }

    private static <T extends Enum<T> & Operation> void test(Class<T> opEnumType, double x, double y) {
        for (Operation op : opEnumType.getEnumConstants())
            System.out.printf("| %f %s %f = %f%n",
                    x, op, y, op.apply(x, y));
    }

    private static void test2(Collection<? extends Operation> opSet, double x, double y) {
        for (Operation op : opSet)
            System.out.printf("~ %f %s %f = %f%n",
                    x, op, y, op.apply(x, y));
    }

    public static void main(String[] args) {
        double x = 56;//Double.parseDouble(args[0]);
        double y = 6;//Double.parseDouble(args[1]);
        test(ExtendedOperation.class, x, y);
        test2(Arrays.asList(ExtendedOperation.values()), x, y);
    }

}
