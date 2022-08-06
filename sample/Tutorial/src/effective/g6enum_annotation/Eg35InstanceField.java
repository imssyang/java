package effective.g6enum_annotation;

/**
 * Item 35: Use instance fields instead of ordinals
 */
public class Eg35InstanceField {

    // Abuse of ordinal to derive an associated value - DON'T DO THIS
    public enum Ensemble {
        SOLO, DUET, TRIO, QUARTET, QUINTET,
        SEXTET, SEPTET, OCTET, NONET, DECTET;
        public int numberOfMusicians() { return ordinal() + 1; }
    }

    // Never derive a value associated with an enum from its ordinal;
    // store it in an instance field instead:
    public enum Ensemble2 {
        SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),
        SEXTET(6), SEPTET(7), OCTET(8), DOUBLE_QUARTET(8),
        NONET(9), DECTET(10), TRIPLE_QUARTET(12);
        private final int numberOfMusicians;
        Ensemble2(int size) { this.numberOfMusicians = size; }
        public int numberOfMusicians() { return numberOfMusicians; }
    }

}
