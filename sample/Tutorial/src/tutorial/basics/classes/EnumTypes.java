package tutorial.basics.classes;

/**
 * Java programming language enum types are much more powerful than their counterparts in other languages.
 * The enum declaration defines a class (called an enum type). The enum class body can include methods and other fields.
 * The compiler automatically adds some special methods when it creates an enum.
 * For example, they have a static values method that returns an array containing all of the values of the enum
 * in the order they are declared.
 *
 * Note: All enums implicitly extend java.lang.Enum. Because a class can only extend one parent, the Java language
 *       does not support multiple inheritance of state, and therefore an enum cannot extend anything else.
 */
public class EnumTypes {

    public enum Day {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }

    public class DayTest {
        Day day;

        public DayTest(Day day) {
            this.day = day;
        }

        public void printDay() {
            switch (day) {
                case MONDAY: System.out.println("Monday"); break;
                case TUESDAY: System.out.println("Tuesday"); break;
                case WEDNESDAY: System.out.println("Wednesday"); break;
                case THURSDAY: System.out.println("Thursday"); break;
                case FRIDAY: System.out.println("Friday"); break;
                case SATURDAY: System.out.println("Saturday"); break;
                case SUNDAY: System.out.println("Sunday"); break;
                default: System.out.println("Unknown"); break;
            }
        }
    }

    public enum Planet {
        MERCURY (3.303e+23, 2.4397e6),
        VENUS   (4.869e+24, 6.0518e6),
        EARTH   (5.976e+24, 6.37814e6),
        MARS    (6.421e+23, 3.3972e6),
        NEPTUNE (1.024e+26, 2.4746e7);

        private final double mass;   // in kilograms
        private final double radius; // in meters

        /**
         * The constructor for an enum type must be package-private or private access.
         * It automatically creates the constants that are defined at the beginning of the enum body.
         * You cannot invoke an enum constructor yourself.
         */
        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
        }

        private double mass() { return mass; }
        private double radius() { return radius; }

        // universal gravitational constant  (m3 kg-1 s-2)
        public static final double G = 6.67300E-11;

        double surfaceGravity() {
            return G * mass / (radius * radius);
        }

        double surfaceWeight(double otherMass) {
            return otherMass * surfaceGravity();
        }

        public static void test(String[] args) {
            if (args.length != 1) {
                System.err.println("Usage: java Planet <earth_weight>");
                System.exit(-1);
            }

            double earthWeight = Double.parseDouble(args[0]);
            double mass = earthWeight/EARTH.surfaceGravity();
            for (Planet p : Planet.values()) {
                System.out.printf("Your weight on %s is %f (%f, %f)%n",
                        p, p.surfaceWeight(mass), p.mass(), p.radius());
            }
        }
    }

    public void test() {
        Day day = Day.FRIDAY;
        System.out.println(day);

        DayTest firstDay = new DayTest(Day.MONDAY);
        firstDay.printDay();
        DayTest sixthDay = new DayTest(Day.SATURDAY);
        sixthDay.printDay();

        for (Day d : Day.values()) {
            System.out.print(d + " ");
        }
        System.out.println();

        Planet.test(new String[]{"175"});
    }

    public static void main(String... args) {
        EnumTypes enumTypes = new EnumTypes();
        enumTypes.test();
    }
}


