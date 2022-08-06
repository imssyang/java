package jcip.examples.g3sharing_object.safe_publication;

/**
 * StuffIntoPublic
 * <p/>
 * Unsafe publication
 *
 * @author Brian Goetz and Tim Peierls
 */
public class StuffIntoPublic {
    public Holder holder;

    // Publishing an Object without Adequate Synchronization. Don't Do this.
    // Because of visibility problems, the Holder could appear to another thread to be in an inconsistent state,
    // even though its invariants were properly established by its constructor!
    public void initialize() {
        holder = new Holder(42);
    }
}
