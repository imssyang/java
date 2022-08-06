package jcip.examples.g3sharing_object.immutability;

import java.util.*;

import jcip.annotations.*;

/**
 * ThreeStooges
 * <p/>
 * Immutable class built out of mutable underlying objects,
 * demonstration of candidate for lock elision
 *
 * @author Brian Goetz and Tim Peierls
 */
@Immutable
 public final class ThreeStooges {
    // Final fields can't be modified (although the objects they refer to can be modified if they are mutable),
    // but they also have special semantics under the Java Memory Model.
    // Just as it is a good practice to make all fields private unless they need greater visibility,
    // it is a good practice to make all fields final unless they need to be mutable.
    private final Set<String> stooges = new HashSet<String>();

    public ThreeStooges() {
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
    }

    public boolean isStooge(String name) {
        return stooges.contains(name);
    }

    public String getStoogeNames() {
        List<String> stooges = new Vector<String>();
        stooges.add("Moe");
        stooges.add("Larry");
        stooges.add("Curly");
        return stooges.toString();
    }
}
