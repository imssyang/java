package effective.g5generics;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Item 33: Consider typesafe heterogeneous containers
 */
public class Eg33HeterogeneousContainer {

    // Typesafe heterogeneous container
    // A Favorites instance is typesafe: it will never return an Integer when you ask it for a String.
    // A Favorites instance is heterogeneous: unlike an ordinary map, all the keys are of different types.
    public static class Favorites {
        private Map<Class<?>, Object> favorites = new HashMap<>();
        public <T> void putFavorite(Class<T> type, T instance) {
            // Achieving runtime type safety with a dynamic cast
            favorites.put(Objects.requireNonNull(type), type.cast(instance));
        }
        public <T> T getFavorite(Class<T> type) {
            return type.cast(favorites.get(type));
        }

        // Typesafe heterogeneous container pattern - client
        public static void main(String[] args) {
            Favorites f = new Favorites();
            f.putFavorite(String.class, "Java");
            f.putFavorite(Integer.class, 0xcafebabe);
            f.putFavorite(Class.class, Favorites.class);
            String favoriteString = f.getFavorite(String.class);
            int favoriteInteger = f.getFavorite(Integer.class);
            Class<?> favoriteClass = f.getFavorite(Class.class);
            System.out.printf("%s %x %s%n", favoriteString,
                    favoriteInteger, favoriteClass.getName());
        }
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        return null;
    }

    // Use of asSubclass to safely cast to a bounded type token
    static Annotation getAnnotation2(AnnotatedElement element, String annotationTypeName) {
        Class<?> annotationType = null; // Unbounded type token
        try {
            annotationType = Class.forName(annotationTypeName);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
        return element.getAnnotation(annotationType.asSubclass(Annotation.class));
    }

}
