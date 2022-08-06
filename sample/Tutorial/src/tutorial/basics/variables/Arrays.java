package tutorial.basics.variables;

/**
 * An array is a container object that holds a fixed number of values of a single type.
 * The length of an array is established when the array is created. After creation, its length is fixed.
 */
public class Arrays {
    static void TestOne() {
        int[] anArray;

        anArray = new int[3];

        anArray[0] = 100;
        anArray[1] = 200;
        anArray[2] = 300;

        System.out.println("anArray.length: "
                + anArray.length);
        System.out.println("Element at index 0: "
                + anArray[0]);
        System.out.println("Element at index 1: "
                + anArray[1]);
        System.out.println("Element at index 2: "
                + anArray[2]);
    }

    static void TestShortcutSyntax() {
        int[] anArray = {
                100, 200, 300
        };

        for (int i : anArray) {
            System.out.println(i);
        }

    }

    static void TestMultidimensionalArray() {
        String[][] names = {
                {"Mr. ", "Mrs. ", "Ms. "},
                {"Smith", "Jones"}
        };

        System.out.println(names[0][0] + names[1][0]);
        System.out.println(names[0][2] + names[1][1]);
    }

    /**
     * For your convenience, Java SE provides several methods for performing array manipulations
     * (common tasks, such as copying, sorting and searching arrays) in the java.util.Arrays class.
     */
    static void TestCopy() {
        char[] copyFrom = {
                '1', '2', '3', '4', '5', '6', '7',
                '8', '9', '0', 'a', 'b', 'c'
        };

        char[] copyTo = new char[7];
        System.arraycopy(copyFrom, 2, copyTo, 0, 7);
        System.out.println(new String(copyTo));

        char[] copyTo2 = java.util.Arrays.copyOfRange(copyFrom, 2, 9);
        System.out.println(new String(copyTo2));
    }

    public static void main(String[] args) {
        Arrays.TestOne();
        Arrays.TestShortcutSyntax();
        Arrays.TestMultidimensionalArray();
        Arrays.TestCopy();
    }
}
