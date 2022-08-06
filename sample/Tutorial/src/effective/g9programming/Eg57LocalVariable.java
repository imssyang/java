package effective.g9programming;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Item 57: Minimize the scope of local variables
 *   - The most powerful technique for minimizing the scope of a local variable
 *     is to declare it where it is first used.
 *   - Nearly every local variable declaration should contain an initializer.
 *   - Prefer for loops to while loops.
 *   - Keep methods small and focused.
 */
public class Eg57LocalVariable {

    void test() {
        // Preferred idiom for iterating over a collection or array
        List<String> list = Arrays.asList("1", "2", "3");
        for (String s : list) {
        }

        // Idiom for iterating when you need the iterator
        for (Iterator<String> s = list.iterator(); s.hasNext(); ) {
            String str = s.next();
        }

        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String str = iter.next();
        }
    }

}
