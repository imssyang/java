package effective.g6enum_annotation;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Item 39: Prefer annotations to naming patterns
 *   - There is simply no reason to use naming patterns when you can use annotations instead.
 *   - All programmers should use the predefined annotation types that Java provides.
 */
public class Eg39Annotation {

    /**
     * Indicates that the annotated method is a test method.
     * Use only on parameterless static methods.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Test {
    }

    /**
     * Indicates that the annotated method is a test method that
     * must throw the designated exception to succeed.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExceptionTest {
        Class<? extends Throwable> value();
    }

    // Annotation type with an array parameter
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExceptionTest2 {
        Class<? extends Exception>[] value();
    }

    // (JDK8) Repeatable annotation type
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @Repeatable(ExceptionTestContainer.class)
    public @interface ExceptionTestJava8 {
        Class<? extends Exception> value();
    }
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExceptionTestContainer {
        ExceptionTestJava8[] value();
    }

    // Program containing marker annotations
    public static class Sample {
        @Test public static void m1() { } // Test should pass
        public static void m2() { }
        @Test public static void m3() { // Test should fail
            throw new RuntimeException("Boom");
        }
        public static void m4() { }
        @Test public void m5() { } // INVALID USE: nonstatic method
        public static void m6() { }
        @Test public static void m7() { // Test should fail
            throw new RuntimeException("Crash");
        }
        public static void m8() { }
    }

    // Program containing annotations with a parameter
    public static class Sample2 {
        @ExceptionTest(ArithmeticException.class)
        public static void m1() { // Test should pass
            int i = 0;
            i = i / i;
        }
        @ExceptionTest(ArithmeticException.class)
        public static void m2() { // Should fail (wrong exception)
            int[] a = new int[0];
            int i = a[1];
        }
        @ExceptionTest(ArithmeticException.class)
        public static void m3() { } // Should fail (no exception)
    }

    // Code containing an annotation with an array parameter
    @ExceptionTest2({ IndexOutOfBoundsException.class, NullPointerException.class })
    public static void doublyBad() {
        List<String> list = new ArrayList<>();
        // The spec permits this method to throw either
        // IndexOutOfBoundsException or NullPointerException
        list.addAll(5, null);
    }

    // (JDK8) Code containing a repeated annotation
    @ExceptionTestJava8(IndexOutOfBoundsException.class)
    @ExceptionTestJava8(NullPointerException.class)
    public static void doublyBadJava8() {
        List<String> list = new ArrayList<>();
        // The spec permits this method to throw either
        // IndexOutOfBoundsException or NullPointerException
        list.addAll(5, null);
    }

    // Program to process marker annotations
    public static class RunTests {

        static void testSample() throws Exception {
            int tests = 0;
            int passed = 0;
            Class<?> testClass = Class.forName("effective.g6enum_annotation.Eg39Annotation$Sample");
            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(Test.class)) {
                    tests++;
                    try {
                        m.invoke(null);
                        passed++;
                    } catch (InvocationTargetException wrappedExc) {
                        Throwable exc = wrappedExc.getCause();
                        System.out.println(m + " failed: " + exc);
                    } catch (Exception exc) {
                        System.out.println("Invalid @Test: " + m);
                    }
                }
            }
            System.out.printf("Passed: %d, Failed: %d%n",
                    passed, tests - passed);
        }

        static void testSample2() throws Exception {
            int tests = 0;
            int passed = 0;
            Class<?> testClass = Class.forName("effective.g6enum_annotation.Eg39Annotation$Sample2");
            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(ExceptionTest.class)) {
                    tests++;
                    try {
                        m.invoke(null);
                        System.out.printf("Test %s failed: no exception%n", m);
                    } catch (InvocationTargetException wrappedEx) {
                        Throwable exc = wrappedEx.getCause();
                        Class<? extends Throwable> excType = m.getAnnotation(ExceptionTest.class).value();
                        if (excType.isInstance(exc)) {
                            passed++;
                        } else {
                            System.out.printf(
                                    "Test %s failed: expected %s, got %s%n",
                                    m, excType.getName(), exc);
                        }
                    } catch (Exception exc) {
                        System.out.println("Invalid @Test: " + m);
                    }
                }
            }
            System.out.printf("Passed: %d, Failed: %d%n",
                    passed, tests - passed);
        }

        static void testDoublyBad() throws Exception {
            int tests = 0;
            int passed = 0;
            Class<?> testClass = Class.forName("effective.g6enum_annotation.Eg39Annotation");
            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(ExceptionTest2.class)) {
                    tests++;
                    try {
                        m.invoke(null);
                        System.out.printf("Test %s failed: no exception%n", m);
                    } catch (Throwable wrappedEx) {
                        Throwable exc = wrappedEx.getCause();
                        int oldPassed = passed;
                        Class<? extends Exception>[] excTypes = m.getAnnotation(ExceptionTest2.class).value();
                        for (Class<? extends Exception> excType : excTypes) {
                            if (excType.isInstance(exc)) {
                                passed++;
                                break;
                            }
                        }
                        if (passed == oldPassed)
                            System.out.printf("Test %s failed: %s %n", m, exc);
                    }
                }
            }
            System.out.printf("Passed: %d, Failed: %d%n",
                    passed, tests - passed);
        }

        static void testDoublyBadJava8() throws Exception {
            int tests = 0;
            int passed = 0;
            Class<?> testClass = Class.forName("effective.g6enum_annotation.Eg39Annotation");
            for (Method m : testClass.getDeclaredMethods()) {
                // Processing repeatable annotations
                if (m.isAnnotationPresent(ExceptionTestJava8.class) || m.isAnnotationPresent(ExceptionTestContainer.class)) {
                    tests++;
                    try {
                        m.invoke(null);
                        System.out.printf("Test %s failed: no exception%n", m);
                    } catch (Throwable wrappedExc) {
                        Throwable exc = wrappedExc.getCause();
                        int oldPassed = passed;
                        ExceptionTestJava8[] excTests = m.getAnnotationsByType(ExceptionTestJava8.class);
                        for (ExceptionTestJava8 excTest : excTests) {
                            if (excTest.value().isInstance(exc)) {
                                passed++;
                                break;
                            }
                        }
                        if (passed == oldPassed)
                            System.out.printf("Test %s failed: %s %n", m, exc);
                    }
                }
            }
            System.out.printf("Passed: %d, Failed: %d%n",
                    passed, tests - passed);
        }

        public static void main(String[] args) {
            try {
                testSample();
                testSample2();
                testDoublyBad();
                testDoublyBadJava8();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
