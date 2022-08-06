package effective.g8method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Item 54: Return empty tutorial.collections or arrays, not nulls
 */
public class Eg54ReturnEmptyObject {

    private final List<String> cheesesInStock = new LinkedList<>();

    // Returns null to indicate an empty collection. Don’t do this!
    public List<String> getCheeses1() {
        return cheesesInStock.isEmpty() ? null
                : new ArrayList<>(cheesesInStock);
    }

    // Optimization - avoids allocating empty tutorial.collections
    public List<String> getCheeses2() {
        return cheesesInStock.isEmpty() ? Collections.emptyList()
                : new ArrayList<>(cheesesInStock);
    }

    // The right way to return a possibly empty array
    public String[] getCheeses3() {
        return cheesesInStock.toArray(new String[0]);
    }

    // Optimization - avoids allocating empty arrays
    private static final String[] EMPTY_CHEESE_ARRAY = new String[0];
    public String[] getCheeses4() {
        return cheesesInStock.toArray(EMPTY_CHEESE_ARRAY);
    }

}
