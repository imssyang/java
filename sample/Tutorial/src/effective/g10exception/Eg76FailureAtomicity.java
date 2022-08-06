package effective.g10exception;

import java.util.EmptyStackException;

/**
 * Item 76: Strive for failure atomicity
 */
public class Eg76FailureAtomicity {

    /* a failed method invocation should leave the object in the state that it was in prior to the invocation.
    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null; // Eliminate obsolete reference
        return result;
    }
    */

}
