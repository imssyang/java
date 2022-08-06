package effective.g9programming;

import java.util.Arrays;
import java.util.List;

/**
 * Item 63: Beware the performance of string concatenation
 */
public class Eg63StringConcatenation {

    private static final int LINE_WIDTH = 100;
    private List<String> list = Arrays.asList("abc", "def", "gih");

    private String lineForItem(int i) {
        return list.get(i);
    }

    private int numItems() {
        return list.size();
    }

    // Inappropriate use of string concatenation - Performs poorly!
    // Using the string concatenation operator repeatedly to concatenate n strings requires time quadratic in n.
    public String statement() {
        String result = "";
        for (int i = 0; i < numItems(); i++)
            result += lineForItem(i); // String concatenation
        return result;
    }

    // To achieve acceptable performance, use a StringBuilder in place of a String
    public String statement2() {
        StringBuilder b = new StringBuilder(numItems() * LINE_WIDTH);
        for (int i = 0; i < numItems(); i++)
            b.append(lineForItem(i));
        return b.toString();
    }

}
