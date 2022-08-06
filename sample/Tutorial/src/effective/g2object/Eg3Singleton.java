package effective.g2object;

import java.io.Serializable;

/**
 * Item 3: Enforce the singleton property with a private constructor or an enum type
 */
public class Eg3Singleton {

    // Singleton with public final field
    public static class Elvis implements Serializable {
        public static final Elvis INSTANCE = new Elvis();
        private Elvis() {  }
        public void leaveTheBuilding() { }

        // readResolve method to preserve singleton property
        private Object readResolve() {
            // Return the one true Elvis and let the garbage collector
            // take care of the Elvis impersonator.
            return INSTANCE;
        }
    }

    // Singleton with static factory
    public static class Elvis2 implements Serializable {
        private static final Elvis2 INSTANCE = new Elvis2();
        private Elvis2() { }
        public static Elvis2 getInstance() { return INSTANCE; }
        public void leaveTheBuilding() { }

        // readResolve method to preserve singleton property
        private Object readResolve() {
            // Return the one true Elvis and let the garbage collector
            // take care of the Elvis impersonator.
            return INSTANCE;
        }
    }

    // Enum singleton - the preferred approach
    public enum Elvis3 {
        INSTANCE;
        public void leaveTheBuilding() { }
    }

}
