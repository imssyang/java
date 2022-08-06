package tutorial.basics.operators;

public class Operators {

    /**
     * The instanceof operator compares an object to a specified type.
     * You can use it to test if an object is an instance of a class, an instance of a subclass,
     * or an instance of a class that implements a particular interface.
     *
     * When using the instanceof operator, keep in mind that null is not an instance of anything.
     */
    class Parent {}
    class Child extends Parent implements MyInterface {}
    interface MyInterface {}

    void TestInstanceOf() {
        Parent obj1 = new Parent();
        Parent obj2 = new Child();

        System.out.println("obj1 instanceof Parent: "
                + (obj1 instanceof Parent));
        System.out.println("obj1 instanceof Child: "
                + (obj1 instanceof Child));
        System.out.println("obj1 instanceof MyInterface: "
                + (obj1 instanceof MyInterface));
        System.out.println("obj2 instanceof Parent: "
                + (obj2 instanceof Parent));
        System.out.println("obj2 instanceof Child: "
                + (obj2 instanceof Child));
        System.out.println("obj2 instanceof MyInterface: "
                + (obj2 instanceof MyInterface));
    }

    void TestBitShift() {
        int val1 = 0x0000FFFF;
        int val2 = 0xFFFF0000;
        int val3 = 0xFFFFFFFF;
        int val4 = val1 << 8;
        int val5 = val1 >> 8;
        int val6 = val2 >> 8;
        int val7 = val3 >> 8;
        int val8 = val3 >>> 8;

        System.out.println(String.format("%08x << 8 %08x", val1, val4));
        System.out.println(String.format("%08x >> 8 %08x", val1, val5));
        System.out.println(String.format("%08x >> 8 %08x", val2, val6));
        System.out.println(String.format("%08x >> 8 %08x", val3, val7));
        System.out.println(String.format("%08x >>> 8 %08x", val3, val8));
    }

    public static void main(String[] args) {
        Operators operators = new Operators();
        operators.TestInstanceOf();
        operators.TestBitShift();
    }
}

