package effective.g6enum_annotation;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.RandomAccess;

/**
 * Item 41: Use marker interfaces to define types
 *   - A marker interface is an interface that contains no method declarations but merely
 *     designates (or “marks”) a class that implements the interface as having some
 *     property. For example, consider the Serializable interface.
 */
public class Eg41MarkerInterface implements Serializable, Cloneable, RandomAccess, Remote {

}
