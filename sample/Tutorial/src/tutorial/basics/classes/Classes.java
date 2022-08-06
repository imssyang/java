package tutorial.basics.classes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Access level modifiers determine whether other classes can use a particular field or invoke a particular method.
 * There are two levels of access control:
 *   1. At the top level—public, or package-private (no explicit modifier).
 *   2. At the member level—public, private, protected, or package-private (no explicit modifier).
 *               Access Levels For Members
 *    Modifier     Class    Package    Subclass    World
 *    public       Y        Y          Y           Y
 *    protected    Y        Y          Y           N
 *    no modifier  Y        Y          N           N
 *    private      Y        N          N           N
 */
public class Classes {

    private long id = 1234567890;

    /**
     * The static modifier, in combination with the final modifier, is also used to define constants.
     * The final modifier indicates that the value of this field cannot change.
     */
    public static final String INTRODUCE_INFO = "learn class in java";

    public static String callerInfo = initializeClassCallerInfo();

    // package-private (no explicit modifier)
    static Date lastConstructDate;

    /**
     * A static initialization block is a normal block of code enclosed in braces, { }, and preceded by the static keyword.
     * A class can have any number of static initialization blocks, and they can appear anywhere in the class body.
     * The runtime system guarantees that static initialization blocks are called in the order that they appear in the source code.
     */
    static {
        lastConstructDate = new Date(0);
    }

    /**
     * The advantage of private static methods is that they can be reused later if you need to reinitialize the class variable.
     */
    private static String initializeClassCallerInfo() {
        return "unknown caller information";
    }

    public static String getLastConstructDate() {
        return lastConstructDate.toString();
    }


    /**
     * A nested class is a member of its enclosing class.
     * As a member of the OuterClass, a nested class can be declared private, public, protected, or package private.
     * (Recall that outer classes can only be declared public or package private.)
     *
     * Terminology: Nested classes are divided into two categories: static and non-static.
     *              Nested classes that are declared static are called static nested classes.
     *              Non-static nested classes are called inner classes.
     *
     * Note: 1. Static nested classes do not have access to Non-static members of the enclosing class.
     *       2. A static nested class interacts with the instance members of its outer class (and other classes)
     *          just like any other top-level class. In effect, a static nested class is behaviorally
     *          a top-level class that has been nested in another top-level class for packaging convenience.
     */
    static class StaticNestedClass {
        String info;

        {
            lastConstructDate = new Date();

            info = "StaticNestedClass's infomation";
        }

        public void println() {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println(info + " " + dateFormat.format(lastConstructDate));
        }
    }

    /**
     * In general, class declarations can include these components, in order:
     *
     * 1. Modifiers such as public, private, and a number of others that you will encounter later.
     * 2. The class name, with the initial letter capitalized by convention.
     * 3. The name of the class's parent (superclass), if any, preceded by the keyword extends.
     *    A class can only extend (subclass) one parent.
     * 4. A comma-separated list of interfaces implemented by the class, if any, preceded by the keyword implements.
     *    A class can implement more than one interface.
     */
    public class Point {
        private int x;
        private int y;
        private int z = initializeInstanceZ();

        /**
         * Because an inner class is associated with an instance, it cannot define any static variable members itself,
         * only allowed in static constant variable declarations.
         */
        public static final String INTRODUCE_INFO = "Point's infomation";

        /**
         * Initializer blocks for instance variables look just like static initializer blocks,
         * but without the static keyword. The Java compiler copies initializer blocks into every constructor.
         * Therefore, this approach can be used to share a block of code between multiple constructors.
         */
        {
            /**
             * Non-static nested classes (inner classes) have access to other members of the enclosing class,
             * even if they are declared private.
             */
            lastConstructDate = new Date();

            x = -1;
            y = -1;
        }

        /**
         * A final method cannot be overridden in a subclass.
         * This is especially useful if subclasses might want to reuse the initialization method.
         * The method is final because calling non-final methods during instance initialization can cause problems.
         */
        protected final int initializeInstanceZ() {
            return -1;
        }

        public Point() {
        }

        /**
         * From within a constructor, you can also use the this keyword to call another constructor in the same class.
         * Doing so is called an explicit constructor invocation.
         */
        public Point(int x) {
            this(x, 0);
        }

        /**
         * Primitive arguments, such as an int or a double, are passed into methods by value.
         */
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public Point generate() {
            Point point = new Point(123, 456);
            return point;
        }

        public void print() {
            System.out.print("(" + x + ", " + y + ", " + z + ")");
        }

        public void println() {
            SimpleDateFormat dateFormat = new SimpleDateFormat();
            dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
            System.out.println("(" + x + ", " + y + ", " + z
                    + ") id(" + id + ") "
                    + dateFormat.format(lastConstructDate));
        }
    }

    public class Polygon {
        private Point[] corners;

        /**
         * You use varargs when you don't know how many of a particular type of argument will be passed to the method.
         * It's a shortcut to creating an array manually (the previous method could have used varargs rather than an array).
         */
        public Polygon(Point... corners) {
            int numberOfSides = corners.length;
            this.corners = java.util.Arrays.copyOfRange(corners, 0, numberOfSides);

            lastConstructDate = new Date();
        }

        /**
         * Reference data type parameters, such as objects, are also passed into methods by value.
         * This means that when the method returns, the passed-in reference still references the same object as before.
         * However, the values of the object's fields can be changed in the method, if they have the proper access level.
         */
        public void modifyFirst(Point point) {
            point.setX(point.getX() * 2);
            point.setY(point.getY() * 2);
            corners[0] = point;

            point = new Point(0, 0);
        }

        public void println() {
            for (Point p : corners) {
                p.print();
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public void TestPolygon() {
        Polygon polygon = new Polygon(
                new Point(1,2),
                new Point(3,4),
                new Point(5,6),
                new Point(7,8)
        );
        polygon.println();

        Point point = new Point(111, 222);
        polygon.modifyFirst(point);
        point.println();
        polygon.println();

        Point point2 = point.generate();
        point2.println();
        point.println();

        Point point3 = new Point(333);
        point3.println();

        Point point4 = new Point();
        point4.println();
    }

    public class ShadowClass {
        public int x = 11;

        class FirstLevel {

            public int x = 22;

            void println(int x) {
                System.out.println("x = " + x);
                System.out.println("this.x = " + this.x);
                System.out.println("ShadowClass.this.x = " + ShadowClass.this.x);
            }
        }

        public void test(String... args) {
            ShadowClass sc = new ShadowClass();
            ShadowClass.FirstLevel fl = sc.new FirstLevel();
            fl.println(33);
        }
    }

    public static void main(String[] args) {
        System.out.println(Classes.getLastConstructDate());
        System.out.println(Classes.callerInfo);
        Classes.callerInfo = "ClassesDemo";
        System.out.println(Classes.callerInfo);

        Classes.StaticNestedClass staticNestedClass = new Classes.StaticNestedClass();
        staticNestedClass.println();
        new Classes.StaticNestedClass().println();

        Classes classes = new Classes();
        classes.TestPolygon();

        Classes.Point point = classes.new Point();
        System.out.println(point.INTRODUCE_INFO);

        /**
         * Not all combinations of instance and class variables and methods are allowed:
         *   1. Instance methods can access instance variables and instance methods directly.
         *   2. Instance methods can access class variables and class methods directly.
         *   3. Class methods can access class variables and class methods directly.
         *   4. Class methods cannot access instance variables or instance methods directly
         *      —they must use an object reference.
         *      Also, class methods cannot use the this keyword as there is no instance for this to refer to.
         *
         * Note: You can also refer to static fields with an object reference like
         *           classes.lastConstructDate
         *       but this is discouraged because it does not make it clear that they are class variables.
         */
        System.out.println(classes.lastConstructDate.toString());
        System.out.println(Classes.lastConstructDate.toString());

        System.out.println(classes.getLastConstructDate());
        System.out.println(Classes.getLastConstructDate());

        System.out.println(Classes.INTRODUCE_INFO);

        Classes.ShadowClass sc = classes.new ShadowClass();
        sc.test();
    }
}
