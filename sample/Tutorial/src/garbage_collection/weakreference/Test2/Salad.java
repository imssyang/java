package garbage_collection.weakreference.Test2;

import java.lang.ref.WeakReference;

public class Salad extends WeakReference<Apple> {
    public Salad(Apple apple) {
        super(apple);
    }
}
