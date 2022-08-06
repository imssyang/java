package jcip.examples.g3sharing_object.visibility.volatile_variable;

/**
 * use volatile variables only when all the following criteria are met:
 *   - x Writes to the variable do not depend on its current value, or you can ensure that only
 *     a single thread ever updates the value;
 *   - x The variable does not participate in invariants with other state variables;
 *   - x Locking is not required for any other reason while the variable is being accessed.
 */

/**
 * CountingSheep
 *
 * @author Brian Goetz and Tim Peierls
 */
public class CountingSheep {
    // a typical use of volatile variables: checking a status flag to determine when to exit a loop.
    volatile boolean asleep;

    void tryToSleep() {
        while (!asleep)
            countSomeSheep();
    }

    void countSomeSheep() {
        // One, two, three...
    }
}








