package effective.g11concurrency;

import effective.g4class.Eg18Inheritance;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Item 79: Avoid excessive synchronization
 */
public class Eg79ExcessiveSynchronization {

    // (Observer pattern)
    @FunctionalInterface public interface SetObserver<E> {
        // Invoked when an element is added to the observable set
        void added(ObservableSet<E> set, E element);
    }
    public static class ObservableSet<E> extends Eg18Inheritance.ForwardingSet<E> {
        public ObservableSet(Set<E> set) { super(set); }
        private final List<SetObserver<E>> observers = new ArrayList<>();
        public void addObserver(SetObserver<E> observer) {
            synchronized(observers) {
                observers.add(observer);
            }
        }
        public boolean removeObserver(SetObserver<E> observer) {
            synchronized(observers) {
                return observers.remove(observer);
            }
        }
        // Dangerous!
        private void notifyElementAdded(E element) {
            synchronized(observers) {
                for (SetObserver<E> observer : observers)
                    observer.added(this, element); // Don't call addObserver/removeObserver!!!
            }
        }
        // Good - Alien method moved outside of synchronized block
        private void notifyElementAdded2(E element) {
            List<SetObserver<E>> snapshot = null;
            synchronized(observers) {
                snapshot = new ArrayList<>(observers);
            }
            for (SetObserver<E> observer : snapshot)
                observer.added(this, element);
        }
        @Override public boolean add(E element) {
            boolean added = super.add(element);
            if (added)
                notifyElementAdded(element);
            return added;
        }
        @Override public boolean addAll(Collection<? extends E> c) {
            boolean result = false;
            for (E element : c)
                result |= add(element); // Calls notifyElementAdded
            return result;
        }
    }

    public static void test1() {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());
        set.addObserver((s, e) -> System.out.println(e));
        set.addObserver(new SetObserver<Integer>() {
            public void added(ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23) {
                    // throw ConcurrentModificationException: trying to remove an element from a list in
                    // the midst of iterating over it, which is illegal.
                    //s.removeObserver(this);
                }
            }
        });
        set.addObserver(new SetObserver<Integer>() {
            public void added(ObservableSet<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23) {
                    // Observer that uses a background thread needlessly
                    ExecutorService exec = Executors.newSingleThreadExecutor();
                    try {
                        // deadlock: The background thread calls s.removeObserver,
                        // which attempts to lock observers, but it can’t acquire the lock,
                        // because the main thread already has the lock.
                        exec.submit(() -> s.removeObserver(this)).get();
                    } catch (ExecutionException | InterruptedException ex) {
                        throw new AssertionError(ex);
                    } finally {
                        exec.shutdown();
                    }
                }
            }
        });
        for (int i = 0; i < 100; i++)
            set.add(i);
    }

    /**
     * CopyOnWriteArrayList that is tailor-made for this purpose. This List
     * implementation is a variant of ArrayList in which all modification operations are
     * implemented by making a fresh copy of the entire underlying array. Because the
     * internal array is never modified, iteration requires no locking and is very fast.
     * @param <E>
     */
    // (Observer pattern - Thread-safe observable set with CopyOnWriteArrayList)
    @FunctionalInterface public interface SetObserver2<E> {
        // Invoked when an element is added to the observable set
        void added(ObservableSet2<E> set, E element);
    }
    public static class ObservableSet2<E> extends Eg18Inheritance.ForwardingSet<E> {
        public ObservableSet2(Set<E> set) { super(set); }
        private final List<SetObserver2<E>> observers = new CopyOnWriteArrayList<>();
        public void addObserver(SetObserver2<E> observer) {
            observers.add(observer);
        }
        public boolean removeObserver(SetObserver2<E> observer) {
            return observers.remove(observer);
        }
        private void notifyElementAdded(E element) {
            for (SetObserver2<E> observer : observers)
                observer.added(this, element);
        }
        @Override public boolean add(E element) {
            boolean added = super.add(element);
            if (added)
                notifyElementAdded(element);
            return added;
        }
        @Override public boolean addAll(Collection<? extends E> c) {
            boolean result = false;
            for (E element : c)
                result |= add(element); // Calls notifyElementAdded
            return result;
        }
    }

    public static void test2() {
        ObservableSet2<Integer> set = new ObservableSet2<>(new HashSet<>());
        set.addObserver((s, e) -> System.out.println(e));
        set.addObserver(new SetObserver2<Integer>() {
            public void added(ObservableSet2<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23) {
                    s.removeObserver(this);
                }
            }
        });
        set.addObserver(new SetObserver2<Integer>() {
            public void added(ObservableSet2<Integer> s, Integer e) {
                System.out.println(e);
                if (e == 23) {
                    // Observer that uses a background thread needlessly
                    ExecutorService exec = Executors.newSingleThreadExecutor();
                    try {
                        exec.submit(() -> s.removeObserver(this)).get();
                    } catch (ExecutionException | InterruptedException ex) {
                        throw new AssertionError(ex);
                    } finally {
                        exec.shutdown();
                    }
                }
            }
        });
        for (int i = 0; i < 100; i++)
            set.add(i);
    }

    public static void main(String[] args) {
        //test1();
        test2();
    }

}
