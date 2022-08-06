package effective.g5generics;

import java.util.*;

/**
 * Item 30: Favor generic methods
 */
public class Eg30GenericMethod {

    // Uses raw types - unacceptable! (Item 26)
    public static Set union(Set s1, Set s2) {
        Set result = new HashSet(s1);
        result.addAll(s2);
        return result;
    }

    // Generic method
    public static <E> Set<E> union2(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }

    // Generic static factory method
    public static <K,V> HashMap<K,V> newHashMap() {
        return new HashMap<K,V>();
    }

    // Generic singleton factory pattern
    public interface UnaryOperator<T> { T apply(T arg);}
    private static UnaryOperator<Object> IDENTITY_FN = (t) -> t;
    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identityFunction() {
        return (UnaryOperator<T>) IDENTITY_FN;
    }

    // Using a recursive type bound to express mutual comparability
    public interface Comparable<T> { int compareTo(T o); }
    public static <E extends Comparable<E>> E max(Collection<E> c) {
        if (c.isEmpty())
            throw new IllegalArgumentException("Empty collection");
        E result = null;
        for (E e : c)
            if (result == null || e.compareTo(result) > 0)
                result = Objects.requireNonNull(e);
        return result;
    }

    // Simple program to exercise generic method
    public static void main(String[] args) {
        Set<String> guys = new HashSet<>(Arrays.asList("Tom", "Dick", "Harry"));
        Set<String> stooges = new HashSet<>(Arrays.asList("Larry", "Moe", "Curly"));
        Set<String> aflCio = union2(guys, stooges);
        System.out.println(aflCio);

        Map<String, List<String>> anagrams1 = new HashMap<String, List<String>>();
        Map<String, List<String>> anagrams2 = newHashMap();

        String[] strings = { "jute", "hemp", "nylon" };
        UnaryOperator<String> sameString = identityFunction();
        for (String s : strings)
            System.out.println(sameString.apply(s));
        Number[] numbers = { 1, 2.0, 3L };
        UnaryOperator<Number> sameNumber = identityFunction();
        for (Number n : numbers)
            System.out.println(sameNumber.apply(n));
    }

}
