package effective.g8method;

import java.util.Date;

/**
 * Item 50: Make defensive copies when needed
 */
public class Eg50DefensiveCopy {

    // Broken "immutable" time period class
    public static final class Period {
        private final Date start;
        private final Date end;
        /**
         * @param start the beginning of the period
         * @param end the end of the period; must not precede start
         * @throws IllegalArgumentException if start is after end
         * @throws NullPointerException if start or end is null
         */
        public Period(Date start, Date end) {
            // Essential to make a defensive copy of each mutable parameter to the constructor
            this.start = new Date(start.getTime());
            this.end = new Date(end.getTime());
            if (this.start.compareTo(this.end) > 0)
                throw new IllegalArgumentException(
                        this.start + " after " + this.end);
        }
        public Date start() {
            // Repaired accessors - make defensive copies of internal fields
            return new Date(start.getTime());
        }
        public Date end() {
            // Repaired accessors - make defensive copies of internal fields
            return new Date(end.getTime());
        }

        public static void main(String[] args) {
            Date start = new Date();
            Date end = new Date();
            Period p = new Period(start, end);
            end.setYear(78); // Modifies internals of p!
            p.end().setYear(78); // Modifies internals of p!
        }
    }

}
