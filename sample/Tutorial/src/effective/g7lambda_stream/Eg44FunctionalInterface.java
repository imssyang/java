package effective.g7lambda_stream;

import java.util.Map;

/**
 * Item 44: Favor the use of standard functional interfaces
 *   - java.util.function.*
 *   Interface           Function Signature    Example
 *   UnaryOperator<T>    T apply(T t)          String::toLowerCase
 *   BinaryOperator<T>   T apply(T t1, T t2)   BigInteger::add
 *   Predicate<T>        boolean test(T t)     Collection::isEmpty
 *   Function<T,R>       R apply(T t)          Arrays::asList
 *   Supplier<T>         T get()               Instant::now
 *   Consumer<T>         void accept(T t)      System.out::println
 */
public class Eg44FunctionalInterface {

    // Unnecessary functional interface; use a standard one instead.
    @FunctionalInterface interface EldestEntryRemovalFunction<K,V>{
        boolean remove(Map<K,V> map, Map.Entry<K,V> eldest);
    }

}
