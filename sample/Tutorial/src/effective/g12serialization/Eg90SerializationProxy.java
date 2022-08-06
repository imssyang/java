package effective.g12serialization;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Item 90: Consider serialization proxies instead of serialized instances
 */
public class Eg90SerializationProxy {

    public static final class Period {
        private Date start;
        private Date end;
        /**
         * @param start the beginning of the period
         * @param end   the end of the period; must not precede start
         * @throws IllegalArgumentException if start is after end
         * @throws NullPointerException     if start or end is null
         */
        public Period(Date start, Date end) {
            this.start = new Date(start.getTime());
            this.end = new Date(end.getTime());
            if (this.start.compareTo(this.end) > 0)
                throw new IllegalArgumentException(
                        start + " after " + end);
        }
        public Date start() { return new Date(start.getTime()); }
        public Date end() { return new Date(end.getTime()); }
        public String toString() { return start + " - " + end; }

        // readObject method for the serialization proxy pattern
        private void readObject(ObjectInputStream stream)
                throws InvalidObjectException {
            throw new InvalidObjectException("Proxy required");
        }

        // writeReplace method for the serialization proxy pattern
        private Object writeReplace() {
            return new SerializationProxy(this);
        }

        // Serialization proxy for Period class
        private static class SerializationProxy implements Serializable {
            private final Date start;
            private final Date end;
            SerializationProxy(Period p) {
                this.start = p.start;
                this.end = p.end;
            }
            // readResolve method for Period.SerializationProxy
            private Object readResolve() {
                return new Period(start, end); // Uses public constructor
            }
            private static final long serialVersionUID =
                    234098243823485285L; // Any number will do (Item 87)
        }
    }

}
