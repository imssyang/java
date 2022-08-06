package tutorial.basics.variables;

import java.lang.Character;
import java.lang.Byte;
import java.lang.Short;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Float;
import java.lang.Double;
import java.nio.charset.Charset;


public class PrimitiveTypes {

    /**
     *  a single 16-bit Unicode character.
     *  It has a minimum value of '\u0000' (or 0)
     *  and a maximum value of '\uffff' (or 65,535 inclusive).
     */
    static char staticChar;

    /**
     * The boolean data type has only two possible values: true and false.
     * This data type represents one bit of information,
     * but its "size" isn't something that's precisely defined.
     */
    static boolean staticBool;

    /**
     * The byte data type is an 8-bit signed two's complement integer.
     * It has a minimum value of -128 and a maximum value of 127 (inclusive).
     * The byte data type can be useful for saving memory in large arrays.
     */
    static byte staticByte;

    /**
     * The short data type is a 16-bit signed two's complement integer.
     * It has a minimum value of -32,768 and a maximum value of 32,767 (inclusive).
     * As with byte, the same guidelines apply: you can use a short to save memory in large arrays.
     */
    static short staticShort;

    /**
     * By default, the int data type is a 32-bit signed two's complement integer,
     * which has a minimum value of -2^31 and a maximum value of 2^31-1.
     * In Java SE 8 and later, you can use the int data type to represent an unsigned 32-bit integer,
     * which has a minimum value of 0 and a maximum value of 2^32-1.
     * Use the Integer class to use int data type as an unsigned integer.
     */
    static int staticInt;

    /**
     * The long data type is a 64-bit two's complement integer.
     * The signed long has a minimum value of -2^63 and a maximum value of 2^63-1.
     * In Java SE 8 and later, you can use the long data type to represent an unsigned 64-bit long,
     * which has a minimum value of 0 and a maximum value of 2^64-1.
     * The Long class also contains methods like compareUnsigned, divideUnsigned etc
     * to support arithmetic operations for unsigned long.
     */
    static long staticLong;

    /**
     * The float data type is a single-precision 32-bit IEEE 754 floating point.
     * This data type should never be used for precise values, such as currency.
     * For that, you will need to use the java.math.BigDecimal class instead.
     * Numbers and Strings covers BigDecimal and other useful classes provided by the Java platform.
     */
    static float staticFloat;

    /**
     * The double data type is a double-precision 64-bit IEEE 754 floating point.
     * For decimal values, this data type is generally the default choice.
     * As mentioned above, this data type should never be used for precise values, such as currency.
     */
    static double staticDouble;


    static void PrintDefaultValue() {
        System.out.println("\ndefaultCharset " + Charset.defaultCharset()
                + "\nchar " + Character.BYTES + "B(" + staticChar + ") min " + Character.MIN_VALUE + " max " +  Character.MAX_VALUE
                + "\nboolean " + staticBool
                + "\nbyte " + Byte.BYTES + "B(" + staticByte + ") min " + Byte.MIN_VALUE + " max " +  Byte.MAX_VALUE
                + "\nshort " + Short.BYTES + "B(" + staticShort + ") min " + Short.MIN_VALUE + " max " +  Short.MAX_VALUE
                + "\nint " + Integer.BYTES + "B(" + staticInt + ") min " + Integer.MIN_VALUE + " max " +  Integer.MAX_VALUE
                + "\nlong " + Long.BYTES + "B(" + staticLong + ") min " + Long.MIN_VALUE + " max " +  Long.MAX_VALUE
                + "\nfloat " + Float.BYTES + "B(" + staticFloat + ") min " + Float.MIN_VALUE + " max " +  Float.MAX_VALUE
                + "\ndouble " + Double.BYTES + "B(" + staticDouble + ") min " + Double.MIN_VALUE + " max " +  Double.MAX_VALUE
        );
    }

    public void AutoBoxingTest() {
        Integer i1 = 2;
        System.out.println("Boxing " + i1);

        int i2 = i1;
        System.out.println("Unboxing " + i2);
    }

    /**
     * boolean values true and false
     * all byte values
     * short values between -128 and 127
     * int values between -128 and 127
     * char in the range \u0000 to \u007F
     */
    public  void CachePoolTest() {
        int i1 = 123;
        int i2 = 123;
        System.out.println("int (" + i1 + ") == int (" + i2 + ") ? " + (i1 == i2));

        Integer x1= new Integer(123);
        Integer x2 = new Integer(123);
        System.out.println("new (" + x1 + ") == new (" + x2 + ") ? " + (x1 == x2));

        Integer y1 = Integer.valueOf(123);
        Integer y2 = Integer.valueOf(123);
        System.out.println("valueOf (" + y1 + ") == valueOf (" + y2 + ") ? " + (y1 == y2));

        Integer int1 = 123;
        Integer int2 = 123;
        System.out.println("Integer (" + int1 + ") == Integer (" + int2 + ") ? " + (int1 == int2));
    }

    public static void main(String[] args) {
        PrimitiveTypes.PrintDefaultValue();

        PrimitiveTypes primitiveType = new PrimitiveTypes();
        primitiveType.AutoBoxingTest();
        primitiveType.CachePoolTest();
    }
}

