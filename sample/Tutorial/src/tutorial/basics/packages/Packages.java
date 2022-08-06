/**
 * The package statement must be the first line in the source file.
 * There can be only one package statement in each source file, and it applies to all types in the file.
 *
 * [Unnamed Package]
 * If you do not use a package statement, your type ends up in an unnamed package.
 * Generally speaking, an unnamed package is only for small or temporary applications or
 * when you are just beginning the development process.
 *
 * [Naming Conventions]
 * Package names are written in all lower case to avoid conflict with the names of classes or interfaces.
 * Packages in the Java language itself begin with java. or javax.
 * In this event, the suggested convention is to add an underscore.
 */
package tutorial.basics.packages;

//Importing a Package Member
import java.util.Arrays;
import java.util.List;

// Importing an Entire Package
//import java.io.*;

// less common form of import allows you to import the public nested classes of an enclosing class.
//import graphics.Rectangle;
//import graphics.Rectangle.*; //not import Rectangle

/**
 * For convenience, the Java compiler automatically imports two entire packages for each source file:
 *   (1) the java.lang package
 *   (2) the current package (the package for the current file).
 *
 * [Apparent Hierarchies of Packages]
 * Importing java.awt.* imports all of the types in the java.awt package,
 * but it does not import java.awt.color, java.awt.font, or any other java.awt.xxxx packages.
 *
 * [The Static Import Statement]
 * The static import statement gives you a way to import the constants and static methods that you want to use
 * so that you do not need to prefix the name of their class.
 *     import static java.lang.Math.PI;
 *     import static java.lang.Math.*;
 *
 * [Managing Source and Class Files]
 * You might have to set your CLASSPATH so that the compiler and the JVM can find the .class files for your types.
 *     <path_one>\sources\com\example\graphics\Rectangle.java
 *     <path_two>\classes\com\example\graphics\Rectangle.class
 * <path_two>\classes, is called the class path, and is set with the CLASSPATH system variable.
 * Both the compiler and the JVM construct the path to your .class files by adding the package name to the class path.
 *
 * Note: A class path may include several paths, separated by a semicolon (Windows) or colon (UNIX).
 *       By default, the compiler and the JVM search the current directory and the JAR file containing
 *       the Java platform classes so that these directories are automatically in your class path.
 */


/**
 * A <i>package</i> is a grouping of related types providing access protection and name space management.
 * Note that <i>types</i> refers to classes, interfaces, enumerations, and annotation types.
 */
class Packages {
    void test() {
        List<Integer> one = Arrays.asList(123, 456, 789);
        System.out.println(one);
    }

    public static void main(String[] args) {
        Packages packages = new Packages();
        packages.test();
    }
}
