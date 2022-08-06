package effective.g7lambda_stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * Item 45: Use streams judiciously
 *   - the stream: which represents a finite or infinite sequence of data elements.
 *   - the stream pipeline: which represents a multistage computation on these elements.
 */
public class Eg45Stream {

    // Prints all large anagram groups in a dictionary iteratively
    public static class Anagrams {
        public static void main(String[] args) throws IOException {
            File dictionary = new File(args[0]);
            int minGroupSize = Integer.parseInt(args[1]);
            Map<String, Set<String>> groups = new HashMap<>();
            try (Scanner s = new Scanner(dictionary)) {
                while (s.hasNext()) {
                    String word = s.next();
                    groups.computeIfAbsent(alphabetize(word),
                            (unused) -> new TreeSet<>()).add(word);
                }
            }
            for (Set<String> group : groups.values())
                if (group.size() >= minGroupSize)
                    System.out.println(group.size() + ": " + group);
        }
        private static String alphabetize(String s) {
            char[] a = s.toCharArray();
            Arrays.sort(a);
            return new String(a);
        }
    }

    // Overuse of streams - don't do this!
    // Overusing streams makes programs hard to read and maintain.
    public static class Anagrams2 {
        public static void main(String[] args) throws IOException {
            Path dictionary = Paths.get(args[0]);
            int minGroupSize = Integer.parseInt(args[1]);
            try (Stream<String> words = Files.lines(dictionary)) {
                words.collect(groupingBy(word -> word.chars().sorted()
                                .collect(StringBuilder::new,
                                        (sb, c) -> sb.append((char) c),
                                        StringBuilder::append).toString()))
                        .values().stream()
                        .filter(group -> group.size() >= minGroupSize)
                        .map(group -> group.size() + ": " + group)
                        .forEach(System.out::println);
            }
        }
    }

    // Tasteful use of streams enhances clarity and conciseness
    public static class Anagrams3 {
        public static void main(String[] args) throws IOException {
            Path dictionary = Paths.get(args[0]);
            int minGroupSize = Integer.parseInt(args[1]);
            try (Stream<String> words = Files.lines(dictionary)) {
                words.collect(groupingBy(word -> alphabetize(word)))
                        .values().stream()
                        .filter(group -> group.size() >= minGroupSize)
                        .forEach(g -> System.out.println(g.size() + ": " + g));
            }
        }
        private static String alphabetize(String s) {
            char[] a = s.toCharArray();
            Arrays.sort(a);
            return new String(a);
        }
    }

}
