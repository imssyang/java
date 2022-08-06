package effective.g11concurrency;

/**
 * Item 83: Use lazy initialization judiciously
 *   - Lazy initialization is the act of delaying the initialization of a field until its value is
 *     needed. If the value is never needed, the field is never initialized.
 *   - Under most circumstances, normal initialization is preferable to lazy initialization.
 *   - If you need to use lazy initialization for performance on a static field, use
 *     the lazy initialization holder class idiom.
 *   - If you need to use lazy initialization for performance on an instance field,
 *     use the double-check idiom.
 */
public class Eg83LazyInitialization {

    private class FieldType {
    }

    private static FieldType computeFieldValue() {
        return null;
    }

    // Normal initialization of an instance field
    private final FieldType field = computeFieldValue();

    // Lazy initialization of instance field - synchronized accessor
    private FieldType field2;
    private synchronized FieldType getField() {
        if (field2 == null)
            field2 = computeFieldValue();
        return field;
    }

    // Lazy initialization holder class idiom for static fields
    private static class FieldHolder {
        static final FieldType field = computeFieldValue();
    }
    private static FieldType getField2() { return FieldHolder.field; }

    // Double-check idiom for lazy initialization of instance fields
    private volatile FieldType field3;
    private FieldType getField3() {
        FieldType result = field3;
        if (result == null) { // First check (no locking)
            synchronized(this) {
                if (field3 == null) // Second check (with locking)
                    field3 = result = computeFieldValue();
            }
        }
        return result;
    }

    // Single-check idiom - can cause repeated initialization!
    private volatile FieldType field4;
    private FieldType getField4() {
        FieldType result = field4;
        if (result == null)
            field4 = result = computeFieldValue();
        return result;
    }

}
