package effective.g7lambda_stream;

import java.util.Arrays;
import java.util.List;

/**
 * Item 43: Prefer method references to lambdas
 *   - Where method references are shorter and clearer, use them; where they aren’t, stick with lambdas.
 *   Method Ref Type     Example                   Lambda Equivalent
 *   Static              Integer::parseInt         str -> Integer.parseInt(str)
 *   Bound               Instant.now()::isAfter    Instant then = Instant.now(); t -> then.isAfter(t)
 *   Unbound             String::toLowerCase       str -> str.toLowerCase()
 *   Class Constructor   TreeMap<K,V>::new         () -> new TreeMap<K,V>
 *   Array Constructor   int[]::new                len -> new int[len]
 */
public class Eg43MethodReference {

    @FunctionalInterface
    public interface Supplier<T> {
        T get();
    }

    static class Car {
        public static Car create(final Supplier<Car> supplier) {
            return supplier.get();
        }

        public static void collide(final Car car) {
            System.out.println("Collided " + car.toString());
        }

        public void follow(final Car another) {
            System.out.println("Following the " + another.toString());
        }

        public void repair() {
            System.out.println("Repaired " + this.toString());
        }
    }

    public static void main(String[] args) {
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);

        cars.forEach(Car::collide);
        cars.forEach(Car::repair);

        final Car police = Car.create(Car::new);
        cars.forEach(police::follow);
    }

}
