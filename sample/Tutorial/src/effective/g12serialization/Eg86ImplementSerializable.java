package effective.g12serialization;

/**
 * Item 86: Implement Serializable with great caution
 *   - A major cost of implementing Serializable is that it decreases the flexibility to change a class’s
 *     implementation once it has been released.
 *   - A second cost of implementing Serializable is that it increases the likelihood of bugs and security holes.
 *   - A third cost of implementing Serializable is that it increases the testing
 *     burden associated with releasing a new version of a class.
 *   - Implementing Serializable is not a decision to be undertaken lightly.
 *   - Classes designed for inheritance (Item 19) should rarely implement
 *     Serializable, and interfaces should rarely extend it.
 *   - Inner classes (Item 24) should not implement Serializable.
 */
public class Eg86ImplementSerializable {



}
