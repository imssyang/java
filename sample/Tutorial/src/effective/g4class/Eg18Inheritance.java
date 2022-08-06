package effective.g4class;

import java.util.*;

/**
 * Item 18: Favor composition over inheritance
 */
public class Eg18Inheritance {

    // Broken - Inappropriate use of inheritance!
    public static class InstrumentedHashSet<E> extends HashSet<E> {
        // The number of attempted element insertions
        private int addCount = 0;
        public InstrumentedHashSet() {
        }
        public InstrumentedHashSet(int initCap, float loadFactor) {
            super(initCap, loadFactor);
        }
        @Override public boolean add(E e) {
            addCount++;
            return super.add(e);
        }
        // Internally, HashSet’s addAll method is implemented on top of its add method,
        // although HashSet, quite reasonably, does not document this implementation detail.
        @Override public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }
        public int getAddCount() {
            return addCount;
        }
        public static void main(String[] args) {
            InstrumentedHashSet<String> s = new InstrumentedHashSet<>();
            s.addAll(Arrays.asList("Snap", "Crackle", "Pop"));
            System.out.println("AddCount:" + s.getAddCount()); // is 6
        }
    }

    // Wrapper class - uses composition in place of inheritance
    public static class ForwardingSet<E> implements Set<E> {
        private final Set<E> s; // (composition) a private field that references an instance of the existing class.
        public ForwardingSet(Set<E> s) { this.s = s; }
        public void clear() { s.clear(); } // (forwarding)
        public boolean contains(Object o) { return s.contains(o); }// (forwarding)
        public boolean isEmpty() { return s.isEmpty(); }// (forwarding)
        public int size() { return s.size(); }// (forwarding)
        public Iterator<E> iterator() { return s.iterator(); }// (forwarding)
        public boolean add(E e) { return s.add(e); }// (forwarding)
        public boolean remove(Object o) { return s.remove(o); }// (forwarding)
        public boolean containsAll(Collection<?> c)
        { return s.containsAll(c); }
        public boolean addAll(Collection<? extends E> c)
        { return s.addAll(c); }
        public boolean removeAll(Collection<?> c)
        { return s.removeAll(c); }
        public boolean retainAll(Collection<?> c)
        { return s.retainAll(c); }
        public Object[] toArray() { return s.toArray(); }
        public <T> T[] toArray(T[] a) { return s.toArray(a); }
        @Override public boolean equals(Object o)
        { return s.equals(o); }
        @Override public int hashCode() { return s.hashCode(); }
        @Override public String toString() { return s.toString(); }
    }

    public static class InstrumentedSet<E> extends ForwardingSet<E> {
        private int addCount = 0;
        public InstrumentedSet(Set<E> s) {
            super(s);
        }
        @Override public boolean add(E e) {
            addCount++;
            return super.add(e);
        }
        @Override public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return super.addAll(c);
        }
        public int getAddCount() {
            return addCount;
        }
        public static void main(String[] args) {
            Set<Date> s = new InstrumentedSet<Date>(new TreeSet<Date>());
        }
    }

}
