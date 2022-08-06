package effective.g5generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Item 28: Prefer lists to arrays
 */
public class Eg28List {

    static class Test1 {
        // Fails at runtime!
        Object[] objectArray = new Long[1];
        //objectArray[0] = "I don't fit in"; // Throws ArrayStoreException

        // Won't compile!
        //List<Object> ol = new ArrayList<Long>(); // Incompatible types
        //ol.add("I don't fit in");

        // Why generic array creation is illegal - won't compile!
        //List<String>[] stringLists = new List<String>[1]; // (1)
        //List<Integer> intList = List.of(42); // (2)
        //Object[] objects = stringLists; // (3)
        //objects[0] = intList; // (4)
        //String s = stringLists[0].get(0); // (5)
    }

    // Chooser - a class badly in need of generics!
    public class Chooser {
        private final Object[] choiceArray;
        public Chooser(Collection choices) {
            choiceArray = choices.toArray();
        }
        public Object choose() {
            Random rnd = ThreadLocalRandom.current();
            return choiceArray[rnd.nextInt(choiceArray.length)];
        }
    }

    // A first cut at making Chooser generic - won't compile
    /*
    public class Chooser2<T> {
        private final T[] choiceArray;
        public Chooser(Collection<T> choices) {
            choiceArray = choices.toArray();
        }
        public Object choose() {
            Random rnd = ThreadLocalRandom.current();
            return choiceArray[rnd.nextInt(choiceArray.length)];
        }
    }
    */

    // List-based Chooser - typesafe
    public class Chooser3<T> {
        private final List<T> choiceList;
        public Chooser3(Collection<T> choices) {
            choiceList = new ArrayList<>(choices);
        }
        public T choose() {
            Random rnd = ThreadLocalRandom.current();
            return choiceList.get(rnd.nextInt(choiceList.size()));
        }
    }


}
