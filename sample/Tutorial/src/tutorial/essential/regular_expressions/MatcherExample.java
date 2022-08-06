package tutorial.essential.regular_expressions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherExample {

    void testMatcher() {
        String text = "This is the text to be searched for occurrences of the http:// pattern.";
        String patternString = ".*http://.*";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        /**
         * if the regular expression matches the beginning of a text but not the whole text,
         * lookingAt() will return true, whereas matches() will return false.
         */
        System.out.println("lookingAt = " + matcher.lookingAt());
        System.out.println("matches   = " + matcher.matches());
    }

    void testFind() {
        String text = "This is the text which is to be searched for occurrences of the word 'is'.";
        String patternString = "is";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        int count = 0;
        while(matcher.find()) {
            count++;
            System.out.println("found: " + count + " : "
                    + matcher.start() + " - " + matcher.end());
        }
    }

    void testGroup() {
        String text = "John writes about this, and John writes about that, and John writes about everything.";
        String patternString1 = "(John)";

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()) {
            System.out.println("found: " + matcher.group(1));
        }
    }

    void testMultipleGroup() {
        String text = "John writes about this, and John Doe writes about that,"
                + " and John Wayne writes about everything.";
        String patternString1 = "(John) (.+?) ";

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()) {
            System.out.println("found: " + matcher.group(1) + " " + matcher.group(2));
        }
    }

    void testGroupInsideGroup() {
        String text = "John writes about this, and John Doe writes about that,"
                + " and John Wayne writes about everything.";
        String patternString1 = "((John) (.+?)) ";

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()) {
            System.out.println("found: <"  + matcher.group(1)
                    + "> <" + matcher.group(2)
                    + "> <" + matcher.group(3)
                    + ">");
        }
    }

    void testReplace() {
        String text = "John writes about this, and John Doe writes about that,"
                + " and John Wayne writes about everything.";
        String patternString1 = "((John) (.+?)) ";

        Pattern pattern = Pattern.compile(patternString1);
        Matcher matcher = pattern.matcher(text);

        String replaceAll = matcher.replaceAll("Joe Blocks ");
        System.out.println("replaceAll   = " + replaceAll);

        String replaceFirst = matcher.replaceFirst("Joe Blocks ");
        System.out.println("replaceFirst = " + replaceFirst);
    }

    void testAppend() {
        String text = "John writes about this, and John Doe writes about that,"
                + " and John Wayne writes about everything.";
        String patternString1 = "((John) (.+?)) ";

        Pattern      pattern      = Pattern.compile(patternString1);
        Matcher      matcher      = pattern.matcher(text);

        StringBuffer stringBuffer = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(stringBuffer, "Joe Blocks ");
            System.out.println(stringBuffer.toString());
        }
        matcher.appendTail(stringBuffer);

        System.out.println(stringBuffer.toString());
    }

    public static void main(String[] args) {
        MatcherExample matcherExample = new MatcherExample();
        matcherExample.testMatcher();
        matcherExample.testFind();
        matcherExample.testGroup();
        matcherExample.testMultipleGroup();
        matcherExample.testGroupInsideGroup();
        matcherExample.testReplace();
        matcherExample.testAppend();
    }

}
