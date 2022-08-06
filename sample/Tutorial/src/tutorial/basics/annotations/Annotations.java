package tutorial.basics.annotations;

import java.lang.annotation.*;
import java.util.Arrays;
import java.util.List;


/**
 * Before the Java SE 8 release, annotations could only be applied to declarations.
 * As of the Java SE 8 release, annotations can also be applied to any <em>type use</em>.
 *
 * Type annotations were created to support improved analysis of Java programs way of ensuring stronger type checking.
 * The Java SE 8 release does not provide a type checking framework, but it allows you to write (or download)
 * a type checking framework that is implemented as one or more pluggable modules that are used in conjunction
 * with the Java compiler.
 */
public class Annotations {

    public static class PredefinedAnnotation {
        /**
         *  <b>@Deprecated</b> annotation indicates that the marked element is deprecated and should no longer be used.
         *  The compiler generates a warning whenever a program uses a method, class, or field with the @Deprecated annotation.
         *
         *  When an element is deprecated, it should also be documented using the Javadoc @deprecated tag.
         *  The use of the at sign (@) in both Javadoc comments and in annotations is not coincidental:
         *  they are related conceptually. Also, note that the Javadoc tag starts with a lowercase d and
         *  the annotation starts with an uppercase D.
         *
         * @deprecated
         * explanation of why it was deprecated
         */
        @Deprecated
        static void deprecatedMethod() { System.out.println("deprecatedMethod"); }

        /**
         * <b>@SuppressWarnings</b> annotation tells the compiler to suppress specific warnings that it would otherwise generate.
         *
         * In the following example, a deprecated method is used, and the compiler usually generates a warning.
         * In this case, however, the annotation causes the warning to be suppressed.
         *
         * Every compiler warning belongs to a category.
         * The Java Language Specification lists two categories: <code>deprecation</code> and <code>unchecked</code>.
         * The unchecked warning can occur when interfacing with legacy code written before the advent of generics.
         * To suppress multiple categories of warnings, use the following syntax:
         *   <code>@SuppressWarnings({"unchecked", "deprecation"})</code>
         */
        @SuppressWarnings("deprecation")
        static void useDeprecatedMethod() { deprecatedMethod(); }

        class SuperClass {
            void overriddenMethod() { System.out.println("SuperClass.overriddenMethod"); }
        }

        class OverriddenClass extends SuperClass {
            /**
             * <b>@Override</b> annotation informs the compiler that the element is meant to override an element declared in a superclass.
             *
             * While it is not required to use this annotation when overriding a method, it helps to prevent errors.
             * If a method marked with @Override fails to correctly override a method in one of its superclasses,
             * the compiler generates an error.
             */
            @Override
            void overriddenMethod() { System.out.println("OverriddenClass.overriddenMethod"); }
        }

        /**
         * <b>@FunctionalInterface</b> annotation, introduced in Java SE 8, indicates that the type declaration is intended
         * to be a functional interface, as defined by the Java Language Specification.
         */
        @FunctionalInterface
        interface FuncInterface { void sayMessage(String message); }

        static void useFuncInterface() {
            FuncInterface funcInterface = message -> System.out.println("FunctionalInterface: " + message);
            funcInterface.sayMessage("message");
        }

        /**
         * Annotations that apply to other annotations are called <em>meta-annotations</em>.
         *
         * <b>@Retention</b> annotation specifies how the marked annotation is stored:
         *   1. <code>RetentionPolicy.SOURCE</code> – is retained only in the source level and is ignored by the compiler.
         *   2. <code>RetentionPolicy.CLASS</code> – is retained by the compiler at compile time, but is ignored by the Java Virtual Machine (JVM).
         *   3. <code>RetentionPolicy.RUNTIME</code> – is retained by the JVM so it can be used by the runtime environment.
         *
         * <b>@Documented</b> annotation indicates that whenever the specified annotation is used those elements
         * should be documented using the Javadoc tool. (By default, annotations are not included in Javadoc.)
         *
         * <b>@Target</b> annotation marks another annotation to restrict what kind of Java elements the annotation
         * can be applied to.
         *   1. <code>ElementType.ANNOTATION_TYPE</code> can be applied to an annotation type.
         *   2. <code>ElementType.CONSTRUCTOR</code> can be applied to a constructor.
         *   3. <code>ElementType.FIELD</code> can be applied to a field or property.
         *   4. <code>ElementType.LOCAL_VARIABLE</code> can be applied to a local variable.
         *   5. <code>ElementType.METHOD</code> can be applied to a method-level annotation.
         *   6. <code>ElementType.PACKAGE</code> can be applied to a package declaration.
         *   7. <code>ElementType.PARAMETER</code> can be applied to the parameters of a method.
         *   8. <code>ElementType.TYPE</code> can be applied to any element of a class.
         *
         * <b>@Inherited</b> annotation indicates that the annotation type can be inherited from the super class.
         * (This is not true by default.) When the user queries the annotation type and the class has no annotation
         * for this type, the class' superclass is queried for the annotation type. This annotation applies only to
         * class declarations.
         *
         * <b>@Repeatable</b> annotation, introduced in Java SE 8, indicates that the marked annotation can be
         * applied more than once to the same declaration or type use.
         */
        static class MetaAnnotations {

            /**
             * The annotation type definition looks similar to an interface definition where the keyword
             * <code>interface</code> is preceded by the at sign (@) (@ = AT, as in annotation type).
             *
             * The body of annotation definition contains <i>annotation type element</i> declarations,
             * which look a lot like methods. Note that they can define optional default values.
             */
            @Documented
            @Inherited
            @Retention(RetentionPolicy.RUNTIME)
            @Target(ElementType.METHOD)
            public @interface CompileInfo {
                String funcName();
                int[] lineRange() default { 0,0 };
            }

            /**
             * As of the Java SE 8 release, <em>repeating annotations</em> enable you to apply the same annotation
             * to a declaration or type use.
             *
             * For compatibility reasons, repeating annotations are stored in a container annotation that is automatically
             * generated by the Java compiler. In order for the compiler to do this, two declarations are required in your code.
             *
             * Step 1: Declare a Repeatable Annotation Type
             */
            @Repeatable(Schedules.class)
            public @interface Schedule {
                String dayOfMonth() default "first";
                String dayOfWeek() default "Mon";
                int hour() default 12;
            }

            /**
             * Step 2: Declare the Containing Annotation Type
             *
             * The containing annotation type must have a <code>value</code> element with an array type.
             * The component type of the array type must be the repeatable annotation type.
             */
            public @interface Schedules {
                Schedule[] value();
            }
        }

        /**
         * <b>@SafeVarargs</b> annotation, when applied to a method or constructor, asserts that the code does
         * not perform potentially unsafe operations on its varargs parameter.
         *
         * When this annotation type is used, unchecked warnings relating to varargs usage are suppressed.
         */
        @MetaAnnotations.Schedule(dayOfMonth="last")
        @MetaAnnotations.Schedule(dayOfWeek="Fri", hour=24)
        @MetaAnnotations.CompileInfo (
                funcName = "safeVarargs",
                lineRange = { 121, 126}
        )
        @SafeVarargs // Not actually safe!
        static void safeVarargs(List<String>... stringLists) {
            Object[] array = stringLists;
            List<Integer> tmpList = Arrays.asList(42);
            array[0] = tmpList; // Semantically invalid, but compiles without warnings
            String s = stringLists[0].get(0); // Oh no, ClassCastException at runtime!
        }

        public void test() {
            deprecatedMethod();
            useDeprecatedMethod();

            SuperClass superClass = new SuperClass();
            superClass.overriddenMethod();

            OverriddenClass overriddenClass = new OverriddenClass();
            overriddenClass.overriddenMethod();

            useFuncInterface();

            List<String> name = Arrays.asList("xxx","yyy","zzz");
            safeVarargs(name);
        }
    }

    public static void main(String... args) {
        Annotations.PredefinedAnnotation predefinedAnnotation = new Annotations.PredefinedAnnotation();
        predefinedAnnotation.test();
    }
}

