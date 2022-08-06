package effective.g7lambda_stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import static java.util.Comparator.comparingInt;

/**
 * Item 42: Prefer lambdas to anonymous classes
 *   - function types: interfaces (or, rarely, abstract classes) with a single abstract method.
 *   - function objects: instances of function types.
 *   - Omit the types of all lambda parameters unless their presence makes your program clearer.
 *   - Lambdas lack names and documentation; if a computation isn’t self-explanatory,
 *     or exceeds a few lines, don’t put it in a lambda.
 *   - Lambdas are limited to functional interfaces. If you want to create an instance of an
 *     abstract class, you can do it with an anonymous class, but not a lambda.
 *   - In a lambda, the this keyword refers to the enclosing instance. In an anonymous class,
 *     the this keyword refers to the anonymous class instance. If you need access to the
 *     function object from within its body, then you must use an anonymous class.
 */
public class Eg42Lambda {

    void test1() {
        List<String> words = Arrays.asList("1", "2", "3");

        // Anonymous class instance as a function object - obsolete!
        Collections.sort(words, new Comparator<String>() {
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });

        // Lambda expression as function object (replaces anonymous class)
        Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));

        // A comparator construction method is used in place of a lambda
        Collections.sort(words, comparingInt(String::length));
        words.sort(comparingInt(String::length)); // Java8
    }

    // Enum type with constant-specific class bodies & data (Item 34)
    public enum Operation {
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
        Operation(String symbol) { this.symbol = symbol; }
        @Override public String toString() { return symbol; }
        public abstract double apply(double x, double y);
    }

    // Enum with function object fields & constant-specific behavior
    public enum Operation2 {
        PLUS ("+", (x, y) -> x + y),
        MINUS ("-", (x, y) -> x - y),
        TIMES ("*", (x, y) -> x * y),
        DIVIDE("/", (x, y) -> x / y);
        private final String symbol;
        private final DoubleBinaryOperator op;
        Operation2(String symbol, DoubleBinaryOperator op) {
            this.symbol = symbol;
            this.op = op;
        }
        @Override public String toString() { return symbol; }
        public double apply(double x, double y) {
            return op.applyAsDouble(x, y);
        }
    }

}
