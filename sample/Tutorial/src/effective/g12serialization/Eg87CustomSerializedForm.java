package effective.g12serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Item 87: Consider using a custom serialized form
 *   - The default serialized form is likely to be appropriate if an object’s physical
 *     representation is identical to its logical content.
 *   - Even if you decide that the default serialized form is appropriate, you
 *     often must provide a readObject method to ensure invariants and security.
 */
public class Eg87CustomSerializedForm {

    // Good candidate for default serialized form
    public class Name implements Serializable {
        /**
         * Last name. Must be non-null.
         * @serial
         */
        private final String lastName;
        /**
         * First name. Must be non-null.
         * @serial
         */
        private final String firstName;

        /**
         * Middle name, or null if there is none.
         * @serial
         */
        private final String middleName;


        public Name(String lastName, String firstName, String middleName) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
        }
    }

    // Awful candidate for default serialized form
    public static final class StringList implements Serializable {
        private int size = 0;
        private Entry head = null;
        private static class Entry implements Serializable {
            String data;
            Entry next;
            Entry previous;
        }
    }

    // StringList with a reasonable custom serialized form
    public static final class StringList2 implements Serializable {
        private transient int size = 0;
        private transient Entry head = null;
        // No longer Serializable!
        private static class Entry {
            String data;
            Entry next;
            Entry previous;
        }
        // Appends the specified string to the list
        public final void add(String s) {  }
        /**
         * Serialize this {@code StringList} instance.
         *
         * @serialData The size of the list (the number of strings
         * it contains) is emitted ({@code int}), followed by all of
         * its elements (each a {@code String}), in the proper
         * sequence.
         */
        private void writeObject(ObjectOutputStream s) throws IOException {
            s.defaultWriteObject();
            s.writeInt(size);
            // Write out all elements in the proper order.
            for (Entry e = head; e != null; e = e.next)
                s.writeObject(e.data);
        }
        private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
            s.defaultReadObject();
            int numElements = s.readInt();
            // Read in all elements and insert them in list
            for (int i = 0; i < numElements; i++)
                add((String) s.readObject());
        }
    }

}
