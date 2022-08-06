package tutorial.collections;

import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ArrayList is a resizable-array implementation of the List interface.
 * It implements all optional list operations, and permits all elements, including null.
 *
 * The limitation with array:
 *   1. The array has a fixed length so if it is full you cannot add any more elements to it,
 *   2. if there are number of elements gets removed from it the memory consumption would be the same as it doesn’t shrink.
 *
 * ArrayList:
 *   1. ArrayList can dynamically grow and shrink after addition and removal of elements;
 *   2. ArrayList class enables us to use predefined methods of it which makes our task easy.
 */
public class ArrayListDemo {

    void testInitialization() {
        //Method 1: Initialization using Arrays.asList
        ArrayList<String> obj = new ArrayList<>(Arrays.asList("Pratap", "Peter", "Harsh"));
        System.out.println("Elements are: " + obj);

        //Method 2: Anonymous inner class method to initialize ArrayList
        ArrayList<String> cities = new ArrayList<String>(){{
            add("Delhi");
            add("Agra");
            add("Chennai");
        }};
        System.out.println("Content of Array list cities: " + cities);

        //Method3: Normal way of ArrayList initialization
        ArrayList<String> books = new ArrayList<>();
        books.add("Java Book1");
        books.add("Java Book2");
        books.add("Java Book3");
        System.out.println("Books stored in array list are: " + books);

        //Method 4: Use Collections.ncopies
        ArrayList<Integer> intlist = new ArrayList<>(Collections.nCopies(10, 5));
        System.out.println("ArrayList items: " + intlist);
    }

    void testIterate() {
        ArrayList<Integer> arrlist = new ArrayList<Integer>();
        arrlist.add(14);
        arrlist.add(7);
        arrlist.add(39);
        arrlist.add(40);

        // For Loop for iterating ArrayList
        System.out.println("For Loop");
        for (int counter = 0; counter < arrlist.size(); counter++) {
            System.out.println(arrlist.get(counter));
        }

        // Advanced For Loop
        System.out.println("Advanced For Loop");
        for (Integer num : arrlist) {
            System.out.println(num);
        }

        // While Loop for iterating ArrayList
        System.out.println("While Loop");
        int count = 0;
        while (arrlist.size() > count) {
            System.out.println(arrlist.get(count));
            count++;
        }

        // Looping Array List using Iterator
        System.out.println("Iterator");
        Iterator iter = arrlist.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

    void testSize() {
        ArrayList<Integer> al = new ArrayList<>();
        System.out.println("Initial size: " + al.size());
        al.add(1);
        al.add(13);
        al.add(45);
        al.add(44);
        al.add(99);
        System.out.println("Size after few additions: " + al.size());
        al.remove(1);
        al.remove(2);
        System.out.println("Size after remove operations: " + al.size());
        System.out.println("Final ArrayList: ");
        for(int num: al){
            System.out.println(num);
        }
    }

    void testSort() {
        ArrayList<String> listofcountries = new ArrayList<>();
        listofcountries.add("India");
        listofcountries.add("US");
        listofcountries.add("China");
        listofcountries.add("Denmark");

        // Unsorted List
        System.out.println("Before Sorting:");
        for (String counter: listofcountries){
            System.out.println(counter);
        }

        // Sorting in ascending order ( alphabetically )
        Collections.sort(listofcountries);

        // Sorted List
        System.out.println("After Sorting:");
        for (String counter: listofcountries){
            System.out.println(counter);
        }

        // Sorting in decreasing order
        Collections.sort(listofcountries, Collections.reverseOrder());

        // Sorted List in reverse order
        System.out.println("After Sorting in reverse order:");
        for (String counter: listofcountries){
            System.out.println(counter);
        }
    }

    void testSortObject() {
        class Student implements Comparable  {
            private String name;
            private int no;
            private int age;

            Student(int no, String name, int age) {
                this.no = no;
                this.name = name;
                this.age = age;
            }

            String getName() {
                return name;
            }

            int getAge() {
                return age;
            }

            @Override
            public int compareTo(Object obj) {
                int compareAge = ((Student)obj).getAge();
                return this.age - compareAge;// For Ascending order
            }

            @Override
            public String toString() {
                return "[ no=" + no + ", name=" + name + ", age=" + age + " ]";
            }
        }

        ArrayList<Student> arraylist = new ArrayList<>();
        arraylist.add(new Student(223, "Chaitanya", 26));
        arraylist.add(new Student(245, "Rahul", 24));
        arraylist.add(new Student(209, "Ajeet", 32));

        Collections.sort(arraylist);

        System.out.println("Sort in age:");
        for(Student str : arraylist) {
            System.out.println(str);
        }

        Comparator<Student> nameComparator = (s1, s2) -> {
            String name1 = s1.getName().toUpperCase();
            String name2 = s2.getName().toUpperCase();
            return name1.compareTo(name2);//ascending order
        };
        Collections.sort(arraylist, nameComparator);

        System.out.println("Sort in name:");
        for(Student str : arraylist) {
            System.out.println(str);
        }
    }

    void testSynchronize() {
        //Method 1: Collections.synchronizedList() method for Synchronizing ArrayList
        List<String> syncal = Collections.synchronizedList(new ArrayList<>());
        syncal.add("Pen");
        syncal.add("NoteBook");
        syncal.add("Ink");

        System.out.println("Iterating synchronized ArrayList:");
        synchronized(syncal) {
            Iterator<String> iterator = syncal.iterator();
            while (iterator.hasNext())
                System.out.println(iterator.next());
        }

        //Method 2: Using CopyOnWriteArrayList
        //CopyOnWriteArrayList is a thread-safe variant of ArrayList.
        CopyOnWriteArrayList<String> al = new CopyOnWriteArrayList<>();
        al.add("Pen");
        al.add("NoteBook");
        al.add("Ink");

        System.out.println("Displaying synchronized ArrayList Elements:");
        Iterator<String> iterator = al.iterator();
        while (iterator.hasNext())
            System.out.println(iterator.next());
    }

    void testSerialization() {
        //Serialization
        //Note: We did not implemented the Serializable interface in the below class
        //      because ArrayList is already been serialized by default.
        ArrayList<String> al= new ArrayList<>();
        al.add("Hello");
        al.add("Hi");
        al.add("Howdy");

        try {
            FileOutputStream fos= new FileOutputStream("test/myfile");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(al);
            oos.close();
            fos.close();
        } catch(IOException e){
            e.printStackTrace();
        }

        //De-Serialization
        ArrayList<String> arraylist = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("test/myfile");
            ObjectInputStream ois = new ObjectInputStream(fis);
            arraylist = (ArrayList)ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        for(String tmp: arraylist){
            System.out.println(tmp);
        }
    }

    void testClone() {
        ArrayList<String> al = new ArrayList<>();
        al.add("Apple");
        al.add("Orange");
        al.add("Mango");
        al.add("Grapes");
        System.out.println("ArrayList: " + al);

        /**
         * The interesting point to see here is when we added and removed few elements from original ArrayList
         * after the clone() method, the cloned ArrayList didn’t get affected.
         */
        ArrayList<String> al2 = (ArrayList<String>)al.clone();
        System.out.println("Shallow copy of ArrayList: "+ al2);

        //add and remove on original ArrayList
        al.add("Fig");
        al.remove("Orange");

        //Display of both ArrayLists after add & remove
        System.out.println("Original ArrayList:"+al);
        System.out.println("Cloned ArrayList:"+al2);
    }

    void testConvertToArray() {
        ArrayList<String> arrlist= new ArrayList<>();
        arrlist.add("String1");
        arrlist.add("String2");
        arrlist.add("String3");
        arrlist.add("String4");

        //Method 1: Manual way of conversion using ArrayList get() method
        String array[] = new String[arrlist.size()];
        for (int j =0; j < arrlist.size(); j++) {
            array[j] = arrlist.get(j);
        }

        for (String k : array) {
            System.out.println(k);
        }

        //Method2: Conversion using toArray() method
        String array2[] = arrlist.toArray(new String[arrlist.size()]);
        for (String k : array2) {
            System.out.println(k);
        }
    }

    void testConvertFromArray() {
        System.out.println("Method 1: Conversion using Arrays.asList():");
        String citys[]={"Agra", "Mysore", "Chandigarh", "Bhopal"};

        ArrayList<String> cityList= new ArrayList<>(Arrays.asList(citys));
        cityList.add("New City2");
        cityList.add("New City3");

        for (String str : cityList) {
            System.out.println(str);
        }

        System.out.println("Method 2: Collections.addAll method:");
        String array[]={"Hi", "Hello", "Howdy", "Bye"};

        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, array);
        arrayList.add("String1");
        arrayList.add("String2");

        for (String str : arrayList) {
            System.out.println(str);
        }
    }

    public static void main(String args[]) {
        ArrayListDemo arrayListDemo = new ArrayListDemo();
        arrayListDemo.testInitialization();
        arrayListDemo.testIterate();
        arrayListDemo.testSize();
        arrayListDemo.testSort();
        arrayListDemo.testSortObject();
        arrayListDemo.testSynchronize();
        arrayListDemo.testSerialization();
        arrayListDemo.testClone();
        arrayListDemo.testConvertToArray();
        arrayListDemo.testConvertFromArray();
    }

}
