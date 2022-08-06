package tutorial.basics.variables;

import java.lang.Byte;
import java.lang.Short;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Float;
import java.lang.Double;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;

public class SimpleObjects {
    /**
     * There are three reasons that you might use a Number object rather than a primitive:
     *
     * 1) As an argument of a method that expects an object (often used when manipulating tutorial.collections of numbers).
     * 2) To use constants defined by the class, such as MIN_VALUE and MAX_VALUE,
     *    that provide the upper and lower bounds of the data type.
     * 3) To use class methods for converting values to and from other primitive types,
     *    for converting to and from strings, and for converting between number systems
     *    (decimal, octal, hexadecimal, binary).
     *
     * Note: There are four other subclasses of Number that are not discussed here.
     *       BigDecimal and BigInteger are used for high-precision calculations.
     *       AtomicInteger and AtomicLong are used for multi-threaded applications.
     */
    static class Numbers {
        static Byte staticByte = new Byte((byte)0xff);
        static Short staticShort = new Short((short)-32768);
        static Integer staticInt = new Integer(Integer.MIN_VALUE);
        static Long staticLong = new Long(Long.MIN_VALUE);
        static Float staticFloat = new Float(Float.MIN_VALUE);
        static Double staticDouble = new Double(Double.MIN_VALUE);

        void printDefaultValue() {
            System.out.println("\nByte " + staticByte
                    + "\nShort " + staticShort
                    + "\nInteger " + staticInt
                    + "\nLong " + staticLong
                    + "\nFloat " + staticFloat
                    + "\nDouble " + staticDouble
            );
        }

        void testPrimitiveData() {
            Byte primitiveByte = staticByte.byteValue();
            Short primitiveShort = staticShort.shortValue();
            Integer primitiveInteger = staticInt.intValue();
            Long primitiveLong = staticLong.longValue();
            Float primitiveFloat = staticFloat.floatValue();
            Double primitiveDouble = staticDouble.doubleValue();

            /**
             * The Number subclasses that wrap primitive numeric types ( Byte, Integer, Double, Float, Long, and Short)
             * each provide a class method named valueOf that converts a string to an object of that type.
             */
            System.out.println("\nPrimitiveByte " + primitiveByte
                    + "\nPrimitiveShort " + primitiveShort
                    + "\nPrimitiveInteger " + primitiveInteger
                    + "\nPrimitiveLong " + primitiveLong
                    + "\nPrimitiveFloat " + primitiveFloat
                    + "\nPrimitiveDouble " + primitiveDouble
            );
        }

        void testCompareTo() {
            Byte anotherByte = new Byte((byte)0xff);
            Short anotherShort = new Short((short)0);
            Integer anotherInt = new Integer((int)0);
            Long anotherLong = new Long((long)0);
            Float anotherFloat = new Float((float)0);
            Double anotherDouble = new Double((double)0);

            int retByte = staticByte.compareTo(anotherByte);
            int retShort = staticShort.compareTo(anotherShort);
            int retInt = staticInt.compareTo(anotherInt);
            int retLong = staticLong.compareTo(anotherLong);
            int retFloat = staticFloat.compareTo(anotherFloat);
            int retDouble = staticDouble.compareTo(anotherDouble);

            System.out.println("\nretByte " + retByte
                    + "\nretShort " + retShort
                    + "\nretInt " + retInt
                    + "\nretLong " + retLong
                    + "\nretFloat " + retFloat
                    + "\nretDouble " + retDouble
            );
        }

        void testEquals() {
            Byte anotherByte = new Byte((byte)0);
            Short anotherShort = new Short((short)-32768);
            Integer anotherInt = new Integer(-32768);
            Long anotherLong = new Long((long)-32768);
            Float anotherFloat = new Float(Float.MIN_VALUE);
            Double anotherDouble = new Double(Float.MIN_VALUE);

            boolean boolByte = staticByte.equals(anotherByte);
            boolean boolShort = staticShort.equals(anotherShort);
            boolean boolInt = staticInt.equals(anotherInt);
            boolean boolLong = staticLong.equals(anotherLong);
            boolean boolFloat = staticFloat.equals(anotherFloat);
            boolean boolDouble = staticDouble.equals(anotherDouble);

            System.out.println("\nboolByte " + boolByte
                    + "\nboolShort " + boolShort
                    + "\nboolInt " + boolInt
                    + "\nboolLong " + boolLong
                    + "\nboolFloat " + boolFloat
                    + "\nboolDouble " + boolDouble
            );
        }

        void testIntegerToString() {
            Integer decodeInt = Integer.decode("123");
            int parseInt1 = Integer.parseInt("123456");
            int parseInt2 = Integer.parseInt("FFFF", 16);
            String toString1 = staticInt.toString();
            String toString2 = Integer.toString(123456789);
            Integer valueOf1 = Integer.valueOf(65535);
            Integer valueOf2 = Integer.valueOf("65535");
            Integer valueOf3 = Integer.valueOf("ffff", 16);

            System.out.println("\ndecodeInt " + decodeInt
                    + "\nparseInt1 " + parseInt1
                    + "\nparseInt2 " + parseInt2
                    + "\ntoString1 " + toString1
                    + "\ntoString2 " + toString2
                    + "\nvalueOf1 " + valueOf1
                    + "\nvalueOf2 " + valueOf2
                    + "\nvalueOf3 " + valueOf3
            );
        }

        void testFormatNumeric() {
            long n = 461012;
            System.out.format("%d%n", n);      //  -->  "461012"
            System.out.format("%08d%n", n);    //  -->  "00461012"
            System.out.format("%+8d%n", n);    //  -->  " +461012"
            System.out.format("%,8d%n", n);    // -->  " 461,012"
            System.out.format("%+,8d%n%n", n); //  -->  "+461,012"

            double pi = Math.PI;

            System.out.format("%f%n", pi);       // -->  "3.141593"
            System.out.format("%.3f%n", pi);     // -->  "3.142"
            System.out.format("%10.3f%n", pi);   // -->  "     3.142"
            System.out.format("%-10.3f%n", pi);  // -->  "3.142"
            System.out.format(Locale.FRANCE,
                    "%-10.4f%n%n", pi); // -->  "3,1416"

            Calendar c = Calendar.getInstance();
            System.out.format("%tB %te, %tY%n", c, c, c); // -->  "May 29, 2006"
            System.out.format("%tl:%tM %tp%n", c, c, c);  // -->  "2:34 am"
            System.out.format("%tD%n", c);    // -->  "05/29/06"
        }

        void customFormat(String pattern, double value ) {
            DecimalFormat myFormatter = new DecimalFormat(pattern);
            String output = myFormatter.format(value);
            System.out.println(value + "  " + pattern + "  " + output);
        }

        void testCustomFormat() {
            customFormat("###,###.###", 123456.789);
            customFormat("###.##", 123456.789);
            customFormat("000000.000", 123.78);
            customFormat("$###,###.###", 12345.67);
        }

        void test() {
            printDefaultValue();
            testPrimitiveData();
            testCompareTo();
            testEquals();
            testIntegerToString();
            testFormatNumeric();
            testCustomFormat();
        }
    }


    static class Characters {
        void testChar() {
            char ch = 'a';
            char uniChar = '\u03A9';
            char[] charArray = { 'a', 'b', 'c', 'd', 'e' };

            System.out.println(ch);
            System.out.println(uniChar);
            System.out.println(charArray);
        }

        /**
         * If you pass a primitive char into a method that expects an object, the compiler automatically converts the char to a Character for you.
         *
         * Note: The Character class is immutable, so that once it is created, a Character object cannot be changed.
         */
        void testCharacter() {
            Character ch = new Character('a');
            System.out.println(ch);
        }

        /**
         * A character preceded by a backslash (\) is an escape sequence and has special meaning to the compiler.
         */
        void testEscapeSequences() {
            System.out.println("She said \"Hello!\" to me.");
        }

        void test() {
            testChar();
            testCharacter();
            testEscapeSequences();
        }
    }

    /**
     * String objects are immutable, which means that once created, their values cannot be changed.
     * Since strings are immutable, what these methods really do is create and return a new string
     * that contains the result of the operation.
     */
    static class Strings {
        static String staticString = new String("hello");

         void printDefaultValue() {
            System.out.println("\nString " + staticString
                    + " length " + staticString.length()
                    + " charAt(0) " + staticString.charAt(0)
                    + " charAt(1) " + staticString.charAt(1)
            );
        }

        /**
         * To accomplish the string reversal, the program had to convert the string to an array of characters,
         * reverse the array into a second array, and then convert back to a string.
         */
         void testReversal() {
            int len = staticString.length();
            char[] tempCharArray = new char[len];
            staticString.getChars(0, len, tempCharArray, 0);

            char[] charArray = new char[len];
            for (int i = 0; i < len; i++) {
                charArray[i] = tempCharArray[len-1-i];
            }

            String reversalString = new String(charArray);
            System.out.println("\nString " + staticString
                    + " reversalString " + reversalString
            );
        }

         void testConcatenate() {
            String string1 = "world";
            String string2 = staticString.concat(string1);
            String string3 = "Hi! ".concat(string1);
            String string4 = staticString + ", " + string1 + "!";
            String string5 = "The Java programming language does not permit literal strings "
                    + "to span lines in source files, so you must use the + concatenation operator "
                    + "at the end of each line in a multi-line string. ";
            System.out.println("\nString " + staticString
                    + "\nstring1 " + string1
                    + "\nstring2 " + string2
                    + "\nstring3 " + string3
                    + "\nstring4 " + string4
                    + "\nstring5 " + string5
            );
        }

        void testFormat() {
            Float floatVar = new Float(1.123f);
            Integer intVar = new Integer(123456);
            String stringVar = new String("abcde");

            String fs = String.format("floatVar %f, "
                            + "intVar %d, "
                            + "stringVar %s",
                    floatVar, intVar, stringVar);
            System.out.println(fs);
        }

         class Filename {
            private String fullPath;
            private char pathSeparator,
                    extensionSeparator;

            public Filename(String str, char sep, char ext) {
                fullPath = str;
                pathSeparator = sep;
                extensionSeparator = ext;
            }

            public String extension() {
                int dot = fullPath.lastIndexOf(extensionSeparator);
                return fullPath.substring(dot + 1);
            }

            public String filename() {
                int dot = fullPath.lastIndexOf(extensionSeparator);
                int sep = fullPath.lastIndexOf(pathSeparator);
                return fullPath.substring(sep + 1, dot);
            }

            public String path() {
                int sep = fullPath.lastIndexOf(pathSeparator);
                return fullPath.substring(0, sep);
            }
        }

        void testFilename() {
            final String FPATH = "/home/user/index.html";
            Filename myHomePage = new Filename(FPATH, '/', '.');
            System.out.println("Extension = " + myHomePage.extension());
            System.out.println("Filename = " + myHomePage.filename());
            System.out.println("Path = " + myHomePage.path());
        }

        void testCompare() {
            String searchMe = "Green Eggs and Ham";
            String findMe = "Eggs";
            int searchMeLength = searchMe.length();
            int findMeLength = findMe.length();
            boolean foundIt = false;
            for (int i = 0;
                 i <= (searchMeLength - findMeLength);
                 i++) {
                if (searchMe.regionMatches(i, findMe, 0, findMeLength)) {
                    foundIt = true;
                    System.out.println(searchMe.substring(i, i + findMeLength));
                    break;
                }
            }
            if (!foundIt)
                System.out.println("No match found.");
        }

        /**
         * StringBuilder objects are like String objects, except that they can be modified.
         * Internally, these objects are treated like variable-length arrays that contain a sequence of characters.
         * At any point, the length and content of the sequence can be changed through method invocations.
         *
         * Strings should always be used unless string builders offer an advantage in terms of simpler code or better performance.
         * For example, if you need to concatenate a large number of strings, appending to a <code>StringBuilder</code> object is more efficient.
         *
         * Unlike strings, every string builder also has a <i>capacity</i>, the number of character spaces that have been allocated.
         * The capacity, which is returned by the <code>capacity()</code> method, is always greater than or equal to the length
         * (usually greater than) and will automatically expand as necessary to accommodate additions to the string builder.
         */
        void testStringBuilder() {
            String palindrome = "Dot saw I was Tod";
            StringBuilder sb = new StringBuilder(palindrome);
            sb.reverse();  // reverse it
            System.out.println(sb);

            // creates empty builder, capacity 16
            StringBuilder sb2 = new StringBuilder();
            // adds 9 character string at beginning
            sb2.append("Greetings");
            System.out.println(sb2 + " capacity:" + sb2.capacity());
        }

        void test() {
            printDefaultValue();
            testReversal();
            testConcatenate();
            testFormat();
            testFilename();
            testCompare();
            testStringBuilder();
        }
    }

    public static void main(String[] args) {
        SimpleObjects.Numbers numbers = new SimpleObjects.Numbers();
        numbers.test();

        SimpleObjects.Characters characters = new SimpleObjects.Characters();
        characters.test();

        SimpleObjects.Strings strings = new SimpleObjects.Strings();
        strings.test();
    }
}

