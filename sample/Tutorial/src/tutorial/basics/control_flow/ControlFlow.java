package tutorial.basics.control_flow;

public class ControlFlow {
    /**
     *  A switch works with the byte, short, char, and int primitive data types.
     *  It also works with enumerated types, the String class, and a few special classes
     *  that wrap certain primitive types: Character, Byte, Short, and Integer.
     */
    void TestSwitch() {
        String month = "OCTOBER";

        int monthNumber;
        switch (month.toLowerCase()) {
            case "january": monthNumber = 1; break;
            case "february": monthNumber = 2; break;
            case "march": monthNumber = 3; break;
            case "april": monthNumber = 4; break;
            case "may": monthNumber = 5; break;
            case "june": monthNumber = 6; break;
            case "july": monthNumber = 7; break;
            case "august": monthNumber = 8; break;
            case "september": monthNumber = 9; break;
            case "october": monthNumber = 10; break;
            case "november": monthNumber = 11; break;
            case "december": monthNumber = 12; break;
            default: monthNumber = 0; break;
        }

        System.out.println("month " + month
                + " monthNumber " + monthNumber);
    }

    void TestFor() {
        int[] numbers = {1, 2, 3, 4, 5};
        for (int item : numbers) {
            System.out.println("Count is: " + item);
        }
    }

    /**
     * The break statement has two forms: labeled and unlabeled.
     */
    void TestBreak() {
        String out = new String();
        int[] numbers = {1, 2, 3, 4, 5};
        char[] chars = {'a', 'b', 'c', 'd', 'e'};

        over:
        for (int num : numbers) {
            for (char ch : chars) {
                if ('d' == ch) {
                    break;
                }
                else if ('b' == ch && 3 == num) {
                    break over;
                }
                out += String.format("%c ", ch);
            }
            out += String.format("%d\n", num);
        }
        System.out.println(out);
    }

    /**
     * The continue statement has two forms: labeled and unlabeled.
     */
    void TestContinue() {
        String out = new String();
        int[] numbers = {1, 2, 3, 4, 5};
        char[] chars = {'a', 'b', 'c', 'd', 'e'};

        skip:
        for (int num : numbers) {
            for (char ch : chars) {
                if ('d' == ch) {
                    continue;
                }
                else if (3 == num) {
                    continue skip;
                }
                out += String.format("%c ", ch);
            }
            out += String.format("%d\n", num);
        }
        System.out.println(out);
    }

    public static void main(String[] args) {
        ControlFlow controlFlow = new ControlFlow();
        controlFlow.TestSwitch();
        controlFlow.TestFor();
        controlFlow.TestBreak();
        controlFlow.TestContinue();
    }
}

