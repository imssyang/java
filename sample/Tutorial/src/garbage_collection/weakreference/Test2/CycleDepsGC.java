package garbage_collection.weakreference.Test2;

import java.lang.ref.WeakReference;

public class CycleDepsGC {
    private static Person newPerson(int number) {
        return new Person("Person" + number);
    }

    private static void beforeGC() {
        Person person = newPerson(1);
        WeakReference<Person> personRef = new WeakReference<>(person);
        Apple apple = new Apple("Apple001", person);
        WeakReference<Apple> appleRef = new WeakReference<>(apple);
        person.setApple(apple);
        System.out.println("=====gc调用前=====");
        System.out.println(person);
        System.out.println(apple);
        System.out.println("person:" + personRef);
        System.out.println(personRef.get());
        System.out.println("apple:" + appleRef);
        System.out.println(appleRef.get());

        System.out.println("=====调用gc=====");
        //System.gc(); //主动调用GC不会回收person和apple
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("=====gc调用后=====");
        for (int i = 0;; i++) {
            System.out.println("person:" + personRef + " " + personRef.get());
            System.out.println("apple:" + appleRef + " " + appleRef.get());
            if (null == personRef.get() && null == appleRef.get()) {
                System.out.println("Object has been collected.");
                break;
            }
        }
    }

    public static void main(String[] args) {
        beforeGC();
    }
}
