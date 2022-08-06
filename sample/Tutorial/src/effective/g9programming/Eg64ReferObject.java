package effective.g9programming;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Item 64: Refer to objects by their interfaces
 */
public class Eg64ReferObject {

    public static void main(String[] args) {
        // Good - uses interface as type
        Set<String> sonSet = new LinkedHashSet<>();

        // Bad - uses class as type!
        LinkedHashSet<String> sonSet2 = new LinkedHashSet<>();
    }

}
