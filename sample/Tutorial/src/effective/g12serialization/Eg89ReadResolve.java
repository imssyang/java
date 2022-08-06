package effective.g12serialization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Item 89: For instance control, prefer enum types to readResolve
 *   - if you depend on readResolve for instance control, all instance fields with object reference types
 *     must be declared transient.
 */
public class Eg89ReadResolve {

    public static class Elvis implements Serializable {
        public static final Elvis INSTANCE = new Elvis();
        private Elvis() {  }
        public void leaveTheBuilding() {  }

        // readResolve for instance control - you can do better!
        // the readResolve method suffices to guarantee the singleton property.
        private Object readResolve() {
            // Return the one true Elvis and let the garbage collector
            // take care of the Elvis impersonator.
            return INSTANCE;
        }
    }

    // Broken singleton - has nontransient object reference field!
    public static class Elvis2 implements Serializable {
        public static final Elvis2 INSTANCE = new Elvis2();
        private Elvis2() { }
        private String[] favoriteSongs = { "Hound Dog", "Heartbreak Hotel" };
        public void printFavorites() {
            System.out.println(Arrays.toString(favoriteSongs));
        }
        private Object readResolve() {
            return INSTANCE;
        }
    }

    public static class ElvisStealer implements Serializable {
        static Elvis2 impersonator;
        private Elvis2 payload;
        private Object readResolve() {
            // Save a reference to the "unresolved" Elvis instance
            impersonator = payload;
            // Return object of correct type for favoriteSongs field
            return new String[] { "A Fool Such as I" };
        }
        private static final long serialVersionUID = 0;
    }

    public static class ElvisImpersonator {
        // Byte stream couldn't have come from a real Elvis instance!
        private static final byte[] serializedForm = {
                (byte)0xac, (byte)0xed, 0x00, 0x05, 0x73, 0x72, 0x00, 0x05,
                0x45, 0x6c, 0x76, 0x69, 0x73, (byte)0x84, (byte)0xe6,
                (byte)0x93, 0x33, (byte)0xc3, (byte)0xf4, (byte)0x8b,
                0x32, 0x02, 0x00, 0x01, 0x4c, 0x00, 0x0d, 0x66, 0x61, 0x76,
                0x6f, 0x72, 0x69, 0x74, 0x65, 0x53, 0x6f, 0x6e, 0x67, 0x73,
                0x74, 0x00, 0x12, 0x4c, 0x6a, 0x61, 0x76, 0x61, 0x2f, 0x6c,
                0x61, 0x6e, 0x67, 0x2f, 0x4f, 0x62, 0x6a, 0x65, 0x63, 0x74,
                0x3b, 0x78, 0x70, 0x73, 0x72, 0x00, 0x0c, 0x45, 0x6c, 0x76,
                0x69, 0x73, 0x53, 0x74, 0x65, 0x61, 0x6c, 0x65, 0x72, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0x00, 0x01,
                0x4c, 0x00, 0x07, 0x70, 0x61, 0x79, 0x6c, 0x6f, 0x61, 0x64,
                0x74, 0x00, 0x07, 0x4c, 0x45, 0x6c, 0x76, 0x69, 0x73, 0x3b,
                0x78, 0x70, 0x71, 0x00, 0x7e, 0x00, 0x02
        };
        public static void main(String[] args) {
            // Initializes ElvisStealer.impersonator and returns
            // the real Elvis (which is Elvis.INSTANCE)
            Elvis2 elvis = (Elvis2) deserialize(serializedForm);
            Elvis2 impersonator = ElvisStealer.impersonator;
            elvis.printFavorites();
            impersonator.printFavorites();
        }
        private static Object deserialize(byte[] sf) {
            try {
                return new ObjectInputStream(new ByteArrayInputStream(sf)).readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    // Enum singleton - the preferred approach
    public enum Elvis3 {
        INSTANCE;
        private String[] favoriteSongs = { "Hound Dog", "Heartbreak Hotel" };
        public void printFavorites() {
            System.out.println(Arrays.toString(favoriteSongs));
        }
    }

}
