package effective.g2object;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Item 5: Prefer dependency injection to hardwiring resources
 */
public class Eg5DependencyInjecction {

    public static class Lexicon { }

    // Inappropriate use of static utility - inflexible & untestable!
    public static class SpellChecker {
        private static final Lexicon dictionary = new Lexicon();
        private SpellChecker() {} // Noninstantiable
        public static boolean isValid(String word) { return true; }
        public static List<String> suggestions(String typo) { return new LinkedList<>(); }
    }

    // Inappropriate use of singleton - inflexible & untestable!
    public static class SpellChecker2 {
        private final Lexicon dictionary = new Lexicon();
        private SpellChecker2() {}
        public static SpellChecker2 INSTANCE = new SpellChecker2();
        public boolean isValid(String word) { return true; }
        public List<String> suggestions(String typo) { return new LinkedList<>(); }
    }

    // Dependency injection provides flexibility and testability
    public class SpellChecker3 {
        private final Lexicon dictionary;
        public SpellChecker3(Lexicon dictionary) {
            this.dictionary = Objects.requireNonNull(dictionary);
        }
        public boolean isValid(String word) { return true; }
        public List<String> suggestions(String typo) { return new LinkedList<>(); }
    }

}
