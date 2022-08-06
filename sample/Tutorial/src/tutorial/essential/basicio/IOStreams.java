package tutorial.essential.basicio;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class IOStreams {

    /**
     * Programs use <em>byte streams</em> to perform input and output of 8-bit bytes.
     * All byte stream classes are descended from <code>InputStream</code> and <code>OutputStream</code>.
     *
     * Byte streams should only be used for the most primitive I/O.
     * All other stream types are built on byte streams.
     */
    void copyBytes(String inFile, String outFile) throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream(inFile);
            out = new FileOutputStream(outFile);
            int c;

            while ((c = in.read()) != -1) {
                System.out.print(String.format("%c", c));
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * The Java platform stores character values using Unicode conventions.
     * Character stream I/O automatically translates this internal format to and from the local character set.
     *
     * Character streams are often "wrappers" for byte streams.
     * The character stream uses the byte stream to perform the physical I/O, while the character stream handles
     * translation between characters and bytes.
     */
    void copyCharacters(String inFile, String outFile) throws IOException {
        FileReader inputStream = null;
        FileWriter outputStream = null;

        try {
            inputStream = new FileReader(inFile);
            outputStream = new FileWriter(outFile);

            int c;
            while ((c = inputStream.read()) != -1) {
                System.out.print(String.format("%c", c));
                outputStream.write(c);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    /**
     * Invoking <code>readLine</code> returns a line of text with the line.
     * <code>CopyLines</code> outputs each line using <code>println</code>, which appends the line terminator for
     * the current operating system. This might not be the same line terminator that was used in the input file.
     *
     * Most of the examples we've seen so far use <i>unbuffered</i> I/O.
     * This means each read or write request is handled directly by the underlying OS.
     *
     * To reduce this kind of overhead, the Java platform implements <i>buffered</i> I/O streams.
     * Buffered input streams read data from a memory area known as a <i>buffer</i>, the native input API is called only when the buffer is empty.
     * Similarly, buffered output streams write data to a buffer, and the native output API is called only when the buffer is full.
     *
     * To flush a stream manually, invoke its <code>flush</code> method.
     * The <code>flush</code> method is valid on any output stream, but has no effect unless the stream is buffered.
     */
    void copyCharactersLines(String inFile, String outFile) throws IOException {
        BufferedReader inputStream = null;
        PrintWriter outputStream = null;

        try {
            inputStream = new BufferedReader(new FileReader(inFile));
            outputStream = new PrintWriter(new FileWriter(outFile));

            String l;
            while ((l = inputStream.readLine()) != null) {
                System.out.println(l);
                outputStream.println(l);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    /**
     * By default, a scanner uses white space to separate tokens. (White space characters include blanks, tabs, and line terminators.)
     */
    void scanCharacters(String inFile) throws IOException {
        Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader(inFile)));
            //s.useDelimiter(",\\s*");
            //s.useLocale(Locale.US);

            while (s.hasNext()) {
                System.out.println("-" + s.next());
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }

    /**
     * Stream objects that implement formatting are instances of either <code>PrintWriter</code>, a character stream class,
     * or <code>PrintStream</code>, a byte stream class.
     *
     * Two levels of formatting are provided:
     *   -> <code>print</code> and <code>println</code> format individual values in a standard way.
     *   -> <code>format</code> formats almost any number of values based on a format string, with many options for precise formatting.
     */
    void formatCharacters(String inFile) {
    }

    /**
     * The Java platform supports three Standard Streams:
     *   -> Standard Input, accessed through System.in;
     *   -> Standard Output, accessed through System.out;
     *   -> Standard Error, accessed through System.err.
     *
     * You might expect the Standard Streams to be character streams, but, for historical reasons, they are byte streams.
     *
     * <code>System.out</code> and <code>System.err</code> are defined as <code>PrintStream</code> objects.
     * Although it is technically a byte stream, <code>PrintStream</code> utilizes an internal character stream object to
     * emulate many of the features of character streams.
     *
     * By contrast, <code>System.in</code> is a byte stream with no character stream features.
     * To use Standard Input as a character stream, wrap <code>System.in</code> in <code>InputStreamReader</code>.
     *     <code>InputStreamReader cin = new InputStreamReader(System.in);</code>
     */
    void standardStreams() {
    }

    /**
     * The Console is particularly useful for secure password entry.
     * Before a program can use the Console, it must attempt to retrieve the Console object by invoking System.console().
     */
    class ConsoleDemo {
        // Dummy change method.
        boolean verify(String login, char[] password) {
            // This method always returns
            // true in this example.
            // Modify this method to verify
            // password according to your rules.
            return true;
        }

        // Dummy change method.
        void change(String login, char[] password) {
            // Modify this method to change
            // password according to your rules.
        }

        void login() throws IOException {
            Console c = System.console();
            if (c == null) {
                System.err.println("No console.");
                System.exit(1);
            }

            String login = c.readLine("Enter your login: ");
            char [] oldPassword = c.readPassword("Enter your old password: ");

            if (verify(login, oldPassword)) {
                boolean noMatch;
                do {
                    char [] newPassword1 = c.readPassword("Enter your new password: ");
                    char [] newPassword2 = c.readPassword("Enter new password again: ");
                    noMatch = ! Arrays.equals(newPassword1, newPassword2);
                    if (noMatch) {
                        c.format("Passwords don't match. Try again.%n");
                    } else {
                        change(login, newPassword1);
                        c.format("Password for %s changed.%n", login);
                    }
                    Arrays.fill(newPassword1, ' ');
                    Arrays.fill(newPassword2, ' ');
                } while (noMatch);
            }

            Arrays.fill(oldPassword, ' ');
        }
    }

    static void testDataStream(String outFile) throws IOException {
        final String dataFile = "invoicedata";
        final double[] prices = { 19.99, 9.99, 15.99, 3.99, 4.99 };
        final int[] units = { 12, 8, 13, 29, 50 };
        final String[] descs = {
                "Java T-shirt",
                "Java Mug",
                "Duke Juggling Dolls",
                "Java Pin",
                "Java Key Chain"
        };

        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(dataFile)));
        for (int i = 0; i < prices.length; i ++) {
            out.writeDouble(prices[i]);
            out.writeInt(units[i]);
            out.writeUTF(descs[i]);
        }
    }

    void test() throws IOException {
        copyBytes("stream_in.txt", "stream_out.txt");
        copyCharacters("stream_in.txt", "stream_out.txt");
        copyCharactersLines("stream_in.txt", "stream_out.txt");
        scanCharacters("stream_in.txt");
        formatCharacters("stream_in.txt");
        standardStreams();
        ConsoleDemo consoleDemo = new ConsoleDemo();
        consoleDemo.login();
        testDataStream("data_out.txt");
    }

    public static void main(String[] args) throws IOException {
        IOStreams ioStreams = new IOStreams();
        ioStreams.test();
    }
}

