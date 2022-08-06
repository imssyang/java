package jcip.examples.g3sharing_object.publication_escape;

/**
 * UnsafeStates
 * <p/>
 * Allowing internal mutable state to escape
 *
 * @author Brian Goetz and Tim Peierls
 */
class UnsafeStates {
    private String[] states = new String[]{
        "AK", "AL" /*...*/
    };

    // Allowing Internal Mutable State to Escape. Don't Do this.
    public String[] getStates() {
        return states;
    }
}
