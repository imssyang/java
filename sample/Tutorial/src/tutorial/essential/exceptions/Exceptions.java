package tutorial.essential.exceptions;

import java.io.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

/**
 * [exception]
 * <em>exception</em> is shorthand for the phrase "exceptional event."
 *
 * [exception object]
 * When an error occurs within a method, the method creates an object and hands it off to the runtime system.
 * The object, called an <em>exception object</em>, contains information about the error, including its type
 * and the state of the program when the error occurred.
 *
 * [throwing an exception]
 * Creating an exception object and handing it to the runtime system is called <em>throwing an exception</em>.
 *
 * [call stack]
 * After a method throws an exception, the runtime system attempts to find something to handle it.
 * The set of possible "somethings" to handle the exception is the ordered list of methods that had been called
 * to get to the method where the error occurred. The list of methods is known as the <em>call stack</em>.
 *
 * [exception handler]
 * The runtime system searches the call stack for a method that contains a block of code that can handle the exception.
 * This block of code is called an <em>exception handler</em>.
 *
 * [catch the exception]
 * The exception handler chosen is said to <em>catch the exception</em>.
 * If the runtime system exhaustively searches all the methods on the call stack without finding an appropriate
 * exception handler, the runtime system (and, consequently, the program) terminates.
 */
public class Exceptions {

    /**
     * The Three Kinds of Exceptions:
     *   1) checked exception. eg: java.io.FileNotFoundException
     *   2) error. eg: java.io.IOError
     *   3) runtime exception. eg: NullPointerException
     * Note: Errors and runtime exceptions are collectively known as <i>unchecked exceptions</i>.
     *
     * Throw checked exceptions must be enclosed by either of the following:
     *   -> A <code>try</code> statement that catches the exception, and must provide a handler for the exception.
     *   -> A method that specifies that it can throw the exception, and must provide a <code>throws</code> clause that lists the exception.
     */
    static class ListOfNumbers {

        private List<Integer> list;
        private static final int SIZE = 10;

        public ListOfNumbers () {
            list = new ArrayList<>(SIZE);
            for (int i = 0; i < SIZE; i++) {
                list.add(new Integer(i));
            }
        }

        /**
         * [try && catch]
         * To associate an exception handler with a <code>try</code> block, you must put a <code>catch</code> block after it.
         *
         * [catch's ExceptionType]
         * <code><em>ExceptionType</em></code>, declares the type of exception that the handler can handle and must be
         * the name of a class that inherits from the <code>Throwable</code> class.
         *
         * [finally]
         * The runtime system always executes the statements within the <code>finally</code> block regardless of what happens
         * within the <code>try</code> block. So it's the perfect place to perform cleanup.
         *
         * Note: If a <code>catch</code> block handles more than one exception type, then the <code>catch</code> parameter is implicitly <code>final</code>.
         * Note: If the JVM exits while the <code>try</code> or <code>catch</code> code is being executed, then the <code>finally</code> block may not execute.
         *       Likewise, if the thread executing the <code>try</code> or <code>catch</code> code is interrupted or killed,
         *       the <code>finally</code> block may not execute even though the application as a whole continues.
         */
        public void writeList() {
            PrintWriter out = null;
            try {
                System.out.println("Entered try statement");
                // The FileWriter constructor throws IOException, which must be caught.
                out = new PrintWriter(new FileWriter("OutFile.txt"));
                for (int i = 0; i < SIZE; i++) {
                    // The get(int) method throws IndexOutOfBoundsException, which must be caught.
                    out.println("Value at: " + i + " = " + list.get(i));
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println("IndexOutOfBoundsException: " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Caught IOException: " + e.getMessage());
            } finally {
                if (out != null) {
                    System.out.println("Closing PrintWriter");
                    out.close();
                } else {
                    System.out.println("PrintWriter not open");
                }
            }
        }

        /**
         * If the <code>writeList</code> method doesn't catch the checked exceptions that can occur within it,
         * the <code>writeList</code> method must specify that it can throw these exceptions.
         *
         * Remember that <code>IndexOutOfBoundsException</code> is an unchecked exception;
         * including it in the <code>throws</code> clause is not mandatory.
         */
        public void writeList2() throws IOException/*, IndexOutOfBoundsException*/ {
            PrintWriter out = new PrintWriter(new FileWriter("OutFile.txt"));
            for (int i = 0; i < SIZE; i++) {
                out.println("Value at: " + i + " = " + list.get(i));
            }
            out.close();
        }

        /**
         * [try-with-resources statement]
         * The <code>try</code>-with-resources statement ensures that each resource is closed at the end of the statement.
         * Any object that implements <code>java.lang.AutoCloseable</code>, which includes all objects which implement
         * <code>java.io.Closeable</code>, can be used as a resource.
         * The class <code>BufferedReader</code>, in Java SE 7 and later, implements the interface <code>java.lang.AutoCloseable</code>.
         * Because the <code>BufferedReader</code> instance is declared in a <code>try</code>-with-resource statement,
         * it will be closed regardless of whether the <code>try</code> statement completes normally or abruptly.
         *
         * Note: In a <code>try</code>-with-resources statement, any <code>catch</code> or <code>finally</code> block is
         * run after the resources declared have been closed.
         */
        static String readFirstLineFromFile(String path) throws IOException {
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                return br.readLine();
            }
        }

        /**
         * pop instantiates a new EmptyStackException object (a member of java.util) and throws it.
         */
        public Object pop() {
            Object obj;

            int size = list.size();
            if (size == 0) {
                throw new EmptyStackException();
            }

            obj = list.indexOf(0);
            return obj;
        }

        /**
         * A <em>stack trace</em> provides information on the execution history of the current thread and lists
         * the names of the classes and methods that were called at the point when the exception occurred.
         * A stack trace is a useful debugging tool that you'll normally take advantage of when an exception has been thrown.
         */
        static String readFirstLineFromFile2(String path) {
            String line = null;
            try {
                BufferedReader br = new BufferedReader(new FileReader(path));
                line = br.readLine();
            } catch (Exception cause) {
                StackTraceElement elements[] = cause.getStackTrace();
                for (int i = 0, n = elements.length; i < n; i++) {
                    System.err.println(elements[i].getFileName()
                            + ":" + elements[i].getLineNumber()
                            + ">> " + elements[i].getMethodName() + "()");
                }
            }
            return line;
        }
    }

    void test() throws IOException {
        ListOfNumbers listOfNumbers = new ListOfNumbers();
        listOfNumbers.writeList();
        listOfNumbers.writeList2();

        String firstLine = ListOfNumbers.readFirstLineFromFile("OutFile.txt");
        System.out.println(firstLine);
    }

    public static void main(String[] args) throws IOException {
        Exceptions exceptions = new Exceptions();
        exceptions.test();
    }
}
