package effective.g4class;

/**
 * Item 25: Limit source files to a single top-level class
 *   - While the Java compiler lets you define multiple top-level classes in a single source
 *     file, there are no benefits associated with doing so, and there are significant risks.
 */
public class Eg25TopLevelClass {

    public static void main(String[] args) {
        System.out.println(Utensil.NAME + Dessert.NAME);
    }

}

// Two classes defined in one file. Don't ever do this!
class Utensil {
    static final String NAME = "pan";
}
class Dessert {
    static final String NAME = "cake";
}

// Static member classes instead of multiple top-level classes
class Test {
    public static void main(String[] args) {
        System.out.println(Utensil.NAME + Dessert.NAME);
    }
    private static class Utensil {
        static final String NAME = "pan";
    }
    private static class Dessert {
        static final String NAME = "cake";
    }
}
