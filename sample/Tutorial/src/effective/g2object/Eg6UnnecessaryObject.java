package effective.g2object;

import java.util.regex.Pattern;

/**
 * Item 6: Avoid creating unnecessary objects
 */
public class Eg6UnnecessaryObject {

    String s1 = new String("bikini"); // DON'T DO THIS!
    String s2 = "bikini"; // Right!

    // Performance can be greatly improved!
    static boolean isRomanNumeral(String s) {
        return s.matches("^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }

    // Reusing expensive object for improved performance
    public static class RomanNumerals {
        private static final Pattern ROMAN = Pattern.compile(
                "^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
        static boolean isRomanNumeral(String s) {
            return ROMAN.matcher(s).matches();
        }
    }

    // Hideously slow! Can you spot the object creation?
    // The variable sum is declared as a Long instead of a long,
    // which means that the program constructs about 231 unnecessary Long instances.
    private static long sum() {
        Long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++)
            sum += i;
        return sum;
    }

}
