package effective.g4class;

/**
 * Item 17: Minimize mutability
 *   - Classes should be immutable unless there’s a very good reason to make them mutable.
 *   - If a class cannot be made immutable, limit its mutability as much as possible.
 *   - Constructors should create fully initialized objects with all of their invariants established.
 */
public class Eg17Mutability {

    /**
     * To make a class immutable, follow these five rules:
     *   - Don’t provide methods that modify the object’s state.
     *   - Ensure that the class can’t be extended.
     *   - Make all fields final.
     *   - Make all fields private.
     *   - Ensure exclusive access to any mutable components.
     */

    // Immutable complex number class (with static factories instead of constructors)
    public static final class Complex {
        public static final Complex ZERO = new Complex(0, 0);
        public static final Complex ONE = new Complex(1, 0);
        public static final Complex I = new Complex(0, 1);

        private final double re;
        private final double im;
        private Complex(double re, double im) {
            this.re = re;
            this.im = im;
        }
        public static Complex valueOf(double re, double im) {
            return new Complex(re, im);
        }
        public double realPart() { return re; }
        public double imaginaryPart() { return im; }
        public Complex plus(Complex c) {
            return new Complex(re + c.re, im + c.im);
        }
        public Complex minus(Complex c) {
            return new Complex(re - c.re, im - c.im);
        }
        public Complex times(Complex c) {
            return new Complex(re * c.re - im * c.im,
                    re * c.im + im * c.re);
        }
        public Complex dividedBy(Complex c) {
            double tmp = c.re * c.re + c.im * c.im;
            return new Complex((re * c.re + im * c.im) / tmp,
                    (im * c.re - re * c.im) / tmp);
        }
        @Override public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof Complex))
                return false;
            Complex c = (Complex) o;
            return Double.compare(c.re, re) == 0
                    && Double.compare(c.im, im) == 0;
        }
        @Override public int hashCode() {
            return 31 * Double.hashCode(re) + Double.hashCode(im);
        }
        @Override public String toString() {
            return "(" + re + " + " + im + "i)";
        }
    }

}
