package effective.g8method;

import java.util.Map;

/**
 * Item 51: Design method signatures carefully
 *   - Don’t go overboard in providing convenience methods.
 *   - Avoid long parameter lists.
 */
public class Eg51MethodSignature {

    // For parameter types, favor interfaces over classes.
    public void test(Map<String, String> map) { // not HashMap!
    }

    // Prefer two-element enum types to boolean parameters.
    public enum TemperatureScale { FAHRENHEIT, CELSIUS }

}
