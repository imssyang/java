package tutorial.basics.classes;

/**
 * Excepting <code>Object</code>, which has no superclass, every class has one and only one direct superclass (single inheritance).
 * In the absence of any other explicit superclass, every class is implicitly a subclass of <code>Object</code>.
 *
 * A subclass inherits all the members (fields, methods, and nested classes) from its superclass.
 * Constructors are not members, so they are not inherited by subclasses,
 * but the constructor of the superclass can be invoked from the subclass.
 */
public class Inheritance {

    /**
     * A subclass inherits all of the <i>public</i> and <i>protected</i> members of its parent, no matter what package the subclass is in.
     * If the subclass is in the same package as its parent, it also inherits the <i>package-private</i> members of the parent.
     * A subclass does not inherit the private members of its parent class.
     *
     *   -> The inherited fields can be used directly, just like any other fields.
     *   -> You can declare a field in the subclass with the same name as the one in the superclass, thus hiding it (not recommended).
     *   -> You can declare new fields in the subclass that are not in the superclass.
     *   -> The inherited methods can be used directly as they are.
     *   -> You can write a new instance method in the subclass that has the same signature as the one in the superclass, thus overriding it.
     *   -> You can write a new static method in the subclass that has the same signature as the one in the superclass, thus hiding it.
     *   -> You can declare new methods in the subclass that are not in the superclass.
     *   -> You can write a subclass constructor that invokes the constructor of the superclass, either implicitly or by using the keyword super.
     */
    public static class SingleInheritance {
        public class Point {
            public int x;
            public int y;

            public Point(int x, int y) {
                this.x = x;
                this.y = y;
            }

            public void println() {
                System.out.println("(" + x + ", " + y + ")");
            }
        }

        public class PointEx extends Point {
            public int z;

            public PointEx(int x, int y, int z) {
                super(x, y);
                this.z = z;
            }

            /**
             * <b>@Override</b> annotation that instructs the compiler that you intend to override a method in the superclass.
             * If the compiler detects that the method does not exist in one of the superclasses, then it will generate an error.
             */
            @Override
            public void println() {
                System.out.println("(" + x + ", " + y + ", " + z + ")");
            }
        }

        public void test() {
            Point point = new Point(11, 22);
            point.println();

            PointEx pointEx = new PointEx(111, 222, 333);
            pointEx.println();

            /**
             * <i>Casting</i> shows the use of an object of one type in place of another type, among the objects permitted by inheritance and implementations.
             */
            // implicit casting
            Object obj = point;
            // explicit casting, This cast inserts a runtime check,
            // If obj is not a Point at runtime, an exception will be thrown.
            Point point2 = (Point)obj;
            point2.println();

            Point point3 = pointEx;
            point3.println();

            /**
             * Here the <code>instanceof</code> operator verifies that <code>obj</code> refers to a <code>PointEx</code>
             * so that we can make the cast with knowledge that there will be no runtime exception thrown.
             */
            if (point3 instanceof PointEx) {
                PointEx pointEx2 = (PointEx)point3;
                pointEx2.println();
            }
        }
    }

    /**
     * ```Multiple Inheritance of State, Implementation, and Type```
     * 1. class vs interface
     *   One significant difference between classes and interfaces is that classes can have fields whereas interfaces cannot.
     *   In addition, you can instantiate a class to create an object, which you cannot do with interfaces.
     *
     * 2. the issues of <em>multiple inheritance of state</em>
     *   One reason why the Java programming language does not permit you to extend more than one class is to avoid the issues
     *   of <em>multiple inheritance of state</em>, which is the ability to inherit fields from multiple classes.
     *
     *   For example, suppose that you are able to define a new class that extends multiple classes.
     *   When you create an object by instantiating that class, that object will inherit fields from all of the class's superclasses.
     *       What if methods or constructors from different superclasses instantiate the same field?
     *       Which method or constructor will take precedence?
     *   Because interfaces do not contain fields, you do not have to worry about problems that result from multiple inheritance of state.
     *
     * 3. one form of <em>Multiple inheritance of implementation</em>
     *   <em>Multiple inheritance of implementation</em> is the ability to inherit method definitions from multiple classes.
     *   Problems arise with this type of multiple inheritance, such as name conflicts and ambiguity.
     *
     *   <b>Default methods</b> introduce one form of multiple inheritance of implementation.
     *   A class can implement more than one interface, which can contain default methods that have the same name.
     *   The Java compiler provides some rules to determine which default method a particular class uses.
     *
     * 4. supports <em>multiple inheritance of type</em>
     *   The Java supports <em>multiple inheritance of type</em>, which is the ability of a class to implement more than one interface.
     *   An object can have multiple types: the type of its own class and the types of all the interfaces that the class implements.
     *   This means that if a variable is declared to be the type of an interface, then its value can reference any object
     *   that is instantiated from any class that implements the interface.
     */
    public static class MultipleInheritance {
        public void test() {
        }
    }

    /**
     * An instance method in a subclass with the same signature (name, plus the number and the type of its parameters)
     * and return type as an instance method in the superclass <i>overrides</i> the superclass's method.
     *
     * The overriding method has the same name, number and type of parameters, and return type as the method that it overrides.
     * An overriding method can also return a subtype of the type returned by the overridden method. This subtype is called
     * a <i>covariant return type</i>.
     *
     *            ```Defining a Method with the Same Signature as a Superclass's Method```
     *                                SuperclassInstanceMethod        SuperclassStaticMethod
     *       SubclassInstanceMethod      Overrides                       compile-time error
     *       SubclassStaticMethod        compile-time error              Hides
     */
    public static class OverridingAndHidingMethods {

        /**
         * The distinction between hiding a static method and overriding an instance method has important implications:
         *   -> The version of the overridden instance method that gets invoked is the one in the subclass.
         *   -> The version of the hidden static method that gets invoked depends on whether it is invoked from the superclass or the subclass.
         *
         * Note: Static methods in interfaces are never inherited.
         */
        public static class InstAndStaticMethod {
            public static class SuperClass {
                public static void classMethod() {
                    System.out.println("SuperClass.classMethod()");
                }
                public void instanceMethod() {
                    System.out.println("SuperClass.instanceMethod()");
                }
            }

            public static class SubClass extends SuperClass {
                public static void classMethod() {
                    System.out.println("SubClass.classMethod()");
                }
                public void instanceMethod() {
                    System.out.println("SubClass.instanceMethod()");
                }
            }

            public void test() {
                SuperClass.classMethod();
                SubClass.classMethod();

                SuperClass superClass = new SuperClass();
                SubClass subClass = new SubClass();
                superClass.instanceMethod();
                subClass.instanceMethod();

                SuperClass superClass1 = subClass;
                if (superClass1 instanceof SuperClass) {
                    superClass1.instanceMethod();
                }
                if (superClass1 instanceof SubClass) {
                    superClass1.instanceMethod();
                }
            }
        }

        /**
         * Java compiler follows inheritance rules to resolve the name conflict. These rules are driven by the following
         * two principles:
         *   -> Instance methods are preferred over interface default methods.
         *   -> Methods that are already overridden by other candidates are ignored.
         */
        public static class InterfaceMethod {
            public class Rule1BaseClass {
                public String print() { return "Rule1BaseClass"; }
            }
            public interface Rule1BaseIface1 {
                default public String print() { return "Rule1BaseIface1"; }
            }
            public interface Rule1BaseIface2 {
                default public String print() { return "Rule1BaseIface2"; }
            }
            public class Rule1ExtendClass extends Rule1BaseClass implements Rule1BaseIface1, Rule1BaseIface2 {
            }

            public interface Rule2BaseIface1 {
                default public String print() { return "Rule2BaseIface1"; }
            }
            public interface Rule2BaseIface2 extends Rule2BaseIface1 {
                default public String print() { return "Rule2BaseIface2"; }
            }
            public interface Rule2BaseIface3 extends Rule2BaseIface1 {
            }
            public class Rule2Class implements Rule2BaseIface2, Rule2BaseIface3 {
            }

            public void test() {
                Rule1ExtendClass rule1ExtendClass = new Rule1ExtendClass();
                System.out.println(rule1ExtendClass.print());

                Rule2Class rule2Class = new Rule2Class();
                System.out.println(rule2Class.print());
            }
        }

        /**
         * The access specifier for an overriding method can allow more, but not less, access than the overridden method.
         * For example, a protected instance method in the superclass can be made public, but not private, in the subclass.
         *
         * If you attempt to change an instance method in the superclass to a static method in the subclass,
         * you will get a compile-time error, and vice versa.
         */
        public static class Modifier {
            public void test() {
            }
        }

        /**
         * <code>final</code> keyword in a method declaration to indicate that the method cannot be overridden by subclasses.
         * Methods called from constructors should generally be declared final. If a constructor calls a non-final method,
         * a subclass may redefine that method with surprising or undesirable results.
         */
        public static class FinalMethod {

            static class ChessAlgorithm {
                enum ChessPlayer { WHITE, BLACK }

                final ChessPlayer getFirstPlayer() {
                    return ChessPlayer.WHITE;
                }
            }

            public void test() {
                ChessAlgorithm chessAlgorithm = new ChessAlgorithm();
                System.out.println(chessAlgorithm.getFirstPlayer());
            }
        }

        public void test() {
            InstAndStaticMethod instAndStaticMethod = new InstAndStaticMethod();
            instAndStaticMethod.test();

            InterfaceMethod interfaceMethod = new InterfaceMethod();
            interfaceMethod.test();

            Modifier modifier = new Modifier();
            modifier.test();

            FinalMethod finalMethod = new FinalMethod();
            finalMethod.test();
        }
    }

    /**
     * Subclasses of a class can define their own unique behaviors and yet share some of the same functionality of the parent class.
     */
    public static class Polymorphism {
        public void test() {
        }
    }

    /**
     * Within a class, a field that has the same name as a field in the superclass hides the superclass's field, even if their types are different.
     * Within the subclass, the field in the superclass must be accessed through <code>super</code>.
     *
     * Generally speaking, we don't recommend hiding fields as it makes code difficult to read.
     */
    public static class HidingField {
        public void test() {
        }
    }

    /**
     * If your method overrides one of its superclass's methods, you can invoke the overridden method through the use of the keyword super.
     * If a constructor does not explicitly invoke a superclass constructor, the Java compiler automatically inserts a call to the no-argument constructor of the superclass.
     */
    public static class SuperKeyword {
        public class Superclass {
            public void printMethod() {
                System.out.println("Printed in Superclass.");
            }
        }

        public class Subclass extends Superclass {
            public void printMethod() {
                super.printMethod();
                System.out.println("Printed in Subclass");
            }
        }

        public void test() {
            Subclass s = new Subclass();
            s.printMethod();
        }
    }

    /**
     * The Object class, in the java.lang package, sits at the top of the class hierarchy tree.
     * Every class is a descendant, direct or indirect, of the Object class.
     */
    public static class ObjectAsSuperClass {
        public void test() {
        }
    }

    /**
     * Abstract classes cannot be instantiated, but they can be subclassed.
     * An abstract method is a method that is declared without an implementation.
     * If a class includes abstract methods, then the class itself must be declared abstract.
     *
     * Note: Methods in an <i>interface</i> that are not declared as default or static are <i>implicitly</i> abstract,
     *       so the <code>abstract</code> modifier is not used with interface methods.
     *       (It can be used, but it is unnecessary.)
     *
     * ```Abstract classes vs interfaces```
     * With abstract classes, you can declare fields that are not static and final, and define public, protected, and private concrete methods.
     * With interfaces, all fields are automatically public, static, and final, and all methods that you declare or define (as default methods) are public.
     * you can extend only one class, whether or not it is abstract, whereas you can implement any number of interfaces.
     */
    public static class AbstractMethodAndClass {
        abstract class GraphicObject {
            int x, y;

            void moveTo(int newX, int newY) {
            }
            abstract void draw();
            abstract void resize();
        }

        class Circle extends GraphicObject {
            void draw() {
            }
            void resize() {
            }
        }
        class Rectangle extends GraphicObject {
            void draw() {
            }
            void resize() {
            }
        }

        public void test() {
            Circle circle = new Circle();
            circle.draw();

            Rectangle rectangle = new Rectangle();
            rectangle.moveTo(111, 222);
        }
    }

    public static void main(String[] args) {
        Inheritance.SingleInheritance singleInheritance = new Inheritance.SingleInheritance();
        singleInheritance.test();

        Inheritance.MultipleInheritance multipleInheritance = new Inheritance.MultipleInheritance();
        multipleInheritance.test();

        Inheritance.OverridingAndHidingMethods overridingAndHidingMethods = new Inheritance.OverridingAndHidingMethods();
        overridingAndHidingMethods.test();

        Inheritance.Polymorphism polymorphism = new Inheritance.Polymorphism();
        polymorphism.test();

        Inheritance.HidingField hidingField = new Inheritance.HidingField();
        hidingField.test();

        Inheritance.SuperKeyword superKeyword = new Inheritance.SuperKeyword();
        superKeyword.test();

        Inheritance.ObjectAsSuperClass objectAsSuperClass = new Inheritance.ObjectAsSuperClass();
        objectAsSuperClass.test();

        Inheritance.AbstractMethodAndClass abstractMethodAndClass = new Inheritance.AbstractMethodAndClass();
        abstractMethodAndClass.test();
    }
}

