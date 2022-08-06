package jcip.examples.g2thread_safety.atomicity.race_condition;

import jcip.annotations.*;

/**
 * LazyInitRace
 *
 * Race condition in lazy initialization
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class LazyInitRace {
    private ExpensiveObject instance = null;

    public ExpensiveObject getInstance() {
        if (instance == null)
            instance = new ExpensiveObject(); //not safe!
        return instance;
    }
}

class ExpensiveObject { }

