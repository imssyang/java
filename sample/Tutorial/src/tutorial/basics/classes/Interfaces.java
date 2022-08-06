package tutorial.basics.classes;

import java.util.Arrays;

/**
 * In the Java programming language, an <i>interface</i> is a reference type, similar to a class,
 * that can contain <i>only</i> constants, method signatures, default methods, static methods, and nested types.
 * When you define a new interface, you are defining a new reference data type.
 * You can use interface names anywhere you can use any other data type name.
 * If you define a reference variable whose type is an interface,
 * any object you assign to it <i>must</i> be an instance of a class that implements the interface.
 *   -> Interfaces cannot be instantiated.
 *   -> The <code>public</code> access specifier indicates that the interface can be used by any class in any package.
 *      If you do not specify that the interface is public, then your interface is accessible only to classes defined
 *      in the same package as the interface.
 *   -> An interface can extend other interfaces, just as a class subclass or extend another class.
 *      However, whereas a class can extend only one other class, an interface can extend any number of interfaces.
 *   -> Interfaces can be <i>implemented</i> by classes.
 *   -> An abstract method within an interface is followed by a semicolon, but no braces
 *      (an abstract method does not contain an implementation).
 *   -> Default methods are defined with the <code>default</code> modifier, and static methods with the
 *      <code>static</code> keyword. Method bodies exist only for default methods and static methods.
 *   -> All abstract, default, and static methods in an interface are implicitly <code>public</code>,
 *      so you can omit the <code>public</code> modifier.
 *   -> All constant values defined in an interface are implicitly <code>public</code>, <code>static</code>,
 *      and <code>final</code>. Once again, you can omit these modifiers.
 */
public class Interfaces {

    public interface Drawable
    {
        int RED = 1;
        int GREEN = 2;
        int BLUE = 3;

        void draw(int color);
    }

    public interface Relatable {
        int isLargerThan(Relatable other);
    }

    public interface Shape extends Drawable, Relatable {
        static String getName() {
            return "Shape";
        }

        //Note that you must provide an implementation for default methods.
        default void print() {
            System.out.println("defaultPrint");
        }
    }

    public interface ShapeEx extends Shape {
        /**
         * Any class that implements the interface <code>ShapeEx</code> will have to implement the method <code>print</code>;
         * this method is an <code>abstract</code> method like all other nondefault (and nonstatic) methods in an interface.
         */
        void print();
    }

    public class Point implements Shape {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void draw(int color) {
            System.out.println("draw: " + color);
        }

        public int getOffset() {
            return x * y;
        }

        public int isLargerThan(Relatable other) {
            Point otherPoint = (Point)other;
            if (this.getOffset() < otherPoint.getOffset())
                return -1;
            else if (this.getOffset() > otherPoint.getOffset())
                return 1;
            else
                return 0;
        }
    }

    public class Rectangle implements ShapeEx {
        Point[] points;

        Rectangle(Point... points) {
            this.points = Arrays.copyOfRange(points, 0, points.length);
        }

        public void print() {
            for (Point p : points) {
                System.out.print("(" + p.x + ", " + p.y + ") ");
            }
            System.out.println();
        }

        public void draw(int color) {
        }

        public int isLargerThan(Relatable other) {
            return 0;
        }
    }

    public void test() {
        System.out.println(Drawable.GREEN + " " + Drawable.BLUE);

        Point point = new Point(11, 22);
        point.draw(Drawable.RED);
        point.print();

        Point point2 = new Point(33, 44);
        System.out.println("isLargerThan: " + point.isLargerThan(point2));

        System.out.println(Shape.getName());

        Point[] points = new Point[] {
                point,
                point2,
                new Point(55, 66)
        };
        Rectangle rectangle = new Rectangle(points);
        rectangle.print();
    }

    public static void main(String[] args) {
        Interfaces interfaces = new Interfaces();
        interfaces.test();
    }
}

