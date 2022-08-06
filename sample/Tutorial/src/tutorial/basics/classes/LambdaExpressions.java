package tutorial.basics.classes;

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaExpressions {

    /**
     * Like local and anonymous classes, lambda expressions can capture variables;
     * they have the same access to local variables of the enclosing scope.
     * However, unlike local and anonymous classes, lambda expressions do not have any shadowing issues.
     * Lambda expressions are lexically scoped.
     * This means that they do not inherit any names from a supertype or introduce a new level of scoping.
     */
    public static class LambdaScope {
        public int x = 11;

        class FirstLevel {
            public int x = 22;

            void methodInFirstLevel(int x) {
                // The following statement causes the compiler to generate
                // the error "local variables referenced from a lambda expression
                // must be final or effectively final" in statement A:
                //
                // x = 99;

                /**
                 * If you substitute the parameter x in place of y in the declaration of the lambda expression myConsumer,
                 * The compiler generates the error "variable x is already defined in method methodInFirstLevel(int)"
                 * because the lambda expression does not introduce a new level of scoping.
                 */
                Consumer<Integer> myConsumer = (y) ->
                {
                    System.out.println("x = " + x); // Statement A
                    System.out.println("y = " + y);
                    System.out.println("this.x = " + this.x);
                    System.out.println("LambdaScopeTest.this.x = " +
                            LambdaScope.this.x);
                };

                myConsumer.accept(x);
            }
        }

        public static void testLambdaScope(String... args) {
            LambdaScope st = new LambdaScope();
            LambdaScope.FirstLevel fl = st.new FirstLevel();
            fl.methodInFirstLevel(33);
        }
    }

    /**
     * Note that a lambda expression looks a lot like a method declaration;
     * you can consider lambda expressions as anonymous methods—methods without a name.
     */
    public static class Calculator {
        interface IntegerMath {
            int operation(int a, int b);
        }

        public int operateBinary(int a, int b, IntegerMath op) {
            return op.operation(a, b);
        }

        public static void testCalculator(String... args) {

            Calculator myApp = new Calculator();
            IntegerMath addition = (a, b) -> a + b;
            IntegerMath subtraction = (a, b) -> a - b;
            System.out.println("40 + 2 = " +
                    myApp.operateBinary(40, 2, addition));
            System.out.println("20 - 10 = " +
                    myApp.operateBinary(20, 10, subtraction));
        }
    }


    public static class Person {

        public enum Sex {
            MALE, FEMALE
        }

        String name;
        Sex gender;
        LocalDate birthday;
        String emailAddress;

        Person(String nameArg, Sex genderArg,
               LocalDate birthdayArg, String emailArg) {
            name = nameArg;
            birthday = birthdayArg;
            gender = genderArg;
            emailAddress = emailArg;
        }

        public int getAge() {
            return birthday
                    .until(IsoChronology.INSTANCE.dateNow())
                    .getYears();
        }

        public void printPerson() {
            System.out.println(name + " " + gender + " " + getAge() + " " + emailAddress);
        }

        //Remember, to use a lambda expression, you need to implement a functional interface.
        interface CheckPerson { boolean test(Person p); }

        public static void printPersons(
                List<Person> roster,
                CheckPerson tester
        ) {
            for (Person p : roster) {
                if (tester.test(p)) {
                    p.printPerson();
                }
            }
        }

        /**
         * JDK defines several standard functional interfaces, which you can find in the package java.util.function.
         * For example:
         *   The Predicate<T> interface contains the method boolean test(T t):
         *   The Consumer<T> interface contains the method void accept(T t);
         *   The Function<T,R> interface contains the method R apply(T t);
         */
        public static void printPersonsWithPredicate(
                List<Person> roster,
                Predicate<Person> tester
        ) {
            for (Person p : roster) {
                if (tester.test(p)) {
                    p.printPerson();
                }
            }
        }

        /**
         * Instead of invoking the method printPerson, you can specify a different action to perform
         * on those Person instances that satisfy the criteria specified by tester.
         */
        public static void processPersons(
                List<Person> roster,
                Predicate<Person> tester,
                Consumer<Person> block
        ) {
            for (Person p : roster) {
                if (tester.test(p)) {
                    block.accept(p);
                }
            }
        }

        /**
         * The following method retrieves the data specified by the parameter mapper,
         * and then performs an action on it specified by the parameter block.
         */
        public static void processPersonsWithFunction(
                List<Person> roster,
                Predicate<Person> tester,
                Function<Person, String> mapper,
                Consumer<String> block
        ) {
            for (Person p : roster) {
                if (tester.test(p)) {
                    String data = mapper.apply(p);
                    block.accept(data);
                }
            }
        }

        /**
         * The following is a generic version of processPersonsWithFunction:
         *   1. the type of List is a collection, is also an object of type Iterable.
         *   2. Filters objects that match the Predicate object tester.
         *   3. Maps each filtered object to a value as specified by the Function object mapper.
         *   4. Performs an action on each mapped object as specified by the Consumer object block.
         */
        public static <X, Y> void processElements(
                Iterable<X> source,
                Predicate<X> tester,
                Function<X, Y> mapper,
                Consumer<Y> block
        ) {
            for (X p : source) {
                if (tester.test(p)) {
                    Y data = mapper.apply(p);
                    block.accept(data);
                }
            }
        }

        public static void printAgeRange(List<Person> roster, int low, int high) {
            for (Person p : roster) {
                if (low <= p.getAge() && p.getAge() < high) {
                    p.printPerson();
                }
            }
        }

        public static List<Person> createRoster() {
            List<Person> roster = new ArrayList<>();
            roster.add(new Person("Fred", Person.Sex.MALE,
                    IsoChronology.INSTANCE.date(1972, 6, 20),
                    "fred@example.com"));
            roster.add(new Person("Jane", Person.Sex.FEMALE,
                    IsoChronology.INSTANCE.date(1982, 7, 15),
                    "jane@example.com"));
            roster.add(new Person("Quincy", Person.Sex.MALE,
                    IsoChronology.INSTANCE.date(1992, 10, 10),
                    "quincy@example.com"));
            return roster;
        }

        public static void testPerson() {
            List<Person> roster = createRoster();
            for (Person p : roster) {
                p.printPerson();
            }
            System.out.println();

            //Generalized Search Methods
            printAgeRange(roster, 20, 40);
            System.out.println();

            //Specify Search Criteria Code in a Local Class
            class CheckPersonEligible implements CheckPerson {
                public boolean test(Person p) {
                    return p.gender == Sex.MALE
                            && p.getAge() >= 30
                            && p.getAge() <= 50;
                }
            }
            printPersons(roster, new CheckPersonEligible());
            System.out.println();

            //Specify Search Criteria Code in an Anonymous Class
            printPersons(
                    roster,
                    new CheckPerson() {
                        public boolean test(Person p) {
                            return p.gender == Sex.MALE
                                    && p.getAge() >= 30
                                    && p.getAge() <= 50;
                        }
                    }
                    );
            System.out.println();

            /**
             * A lambda expression consists of the following:
             *   1. A comma-separated list of formal parameters enclosed in parentheses.
             *      Note: You can omit the data type of the parameters in a lambda expression.
             *            In addition, you can omit the parentheses if there is only one parameter.
             *   2. The arrow token, ->
             *   3. A body, which consists of a single expression or a statement block.
             *      Note: If you specify a single expression, then the Java runtime evaluates the expression
             *            and then returns its value. Alternatively, you can use a return statement.
             *
             * Target Typing:
             *   1. To determine the type of a lambda expression, the Java compiler uses the target type of the context
             *      or situation in which the lambda expression was found.
             *   2. For method arguments, the Java compiler determines the target type with two other language features:
             *      overload resolution and type argument inference.
             */

            //Specify Search Criteria Code with a Lambda Expression (1)
            printPersons(
                    roster,
                    (Person p) -> {
                        return p.gender == Sex.FEMALE
                                && p.getAge() >= 30
                                && p.getAge() <= 50;
                    }
            );
            System.out.println();

            //Specify Search Criteria Code with a Lambda Expression (2)
            printPersons(
                    roster,
                    (Person p) -> p.gender == Sex.FEMALE
                            && p.getAge() >= 30
                            && p.getAge() <= 50
                    );
            System.out.println();

            //Use Standard Functional Interfaces with Lambda Expressions
            printPersonsWithPredicate(
                    roster,
                    p -> p.gender == Sex.FEMALE
                            && p.getAge() >= 30
                            && p.getAge() <= 50
            );
            System.out.println();

            //Use Lambda Expressions Throughout Your Application (1)
            processPersons(
                    roster,
                    p -> p.gender == Sex.MALE
                            && p.getAge() >= 20
                            && p.getAge() <= 30,
                    p -> p.printPerson()
            );
            System.out.println();

            //Use Lambda Expressions Throughout Your Application (2)
            processPersonsWithFunction(
                    roster,
                    p -> p.gender == Sex.MALE
                            && p.getAge() >= 20
                            && p.getAge() <= 30,
                    p -> p.emailAddress,
                    email -> System.out.println(email)
            );
            System.out.println();

            //Use Generics More Extensively
            processElements(
                    roster,
                    p -> p.gender == Sex.MALE
                            && p.getAge() >= 20
                            && p.getAge() <= 30,
                    p -> p.emailAddress,
                    email -> System.out.println(email)
            );
            System.out.println();

            /**
             * Use Aggregate Operations That Accept Lambda Expressions as Parameters
             *   1. Stream<E> stream()
             *   2. Stream<T> filter(Predicate<? super T> predicate)
             *   3. <R> Stream<R> map(Function<? super T,? extends R> mapper)
             *   4. void forEach(Consumer<? super T> action)
             *
             * The operations filter, map, and forEach are aggregate operations.
             * Aggregate operations process elements from a stream, not directly from a collection.
             * A stream is a sequence of elements. Unlike a collection, it is not a data structure that stores elements.
             * aggregate operations typically accept lambda expressions as parameters.
             */
            roster
                    .stream()
                    .filter(
                            p -> p.gender == Sex.MALE
                                    && p.getAge() >= 20
                                    && p.getAge() <= 30
                    )
                    .map(p -> p.emailAddress)
                    .forEach(email -> System.out.println(email));
            System.out.println();
        }

        public static void printCollection(Iterable<Person> source) {
            for (Person p : source) {
                p.printPerson();
            }
            System.out.println();
        }

        public static void printCollection(Person[] source) {
            for (Person p : source) {
                p.printPerson();
            }
            System.out.println();
        }

        public static int compareByAge(Person a, Person b) {
            return a.birthday.compareTo(b.birthday);
        }

        /**
         * The following method copies elements from one collection to another.
         * The functional interface Supplier contains one method get that takes no arguments and returns an object.
         */
        public static <T, SOURCE extends Collection<T>, DEST extends Collection<T>>
        DEST transferElements(SOURCE sourceCollection, Supplier<DEST> collectionFactory) {
            DEST result = collectionFactory.get();
            for (T t : sourceCollection) {
                result.add(t);
            }
            return result;
        }

        /**
         * Sometimes, a lambda expression does nothing but call an existing method.
         * In those cases, it's often clearer to refer to the existing method by name.
         * Method references enable you to do this, and they are compact,
         * easy-to-read lambda expressions for methods that already have a name.
         */
        public static void testMethodReferences() {
            List<Person> roster = createRoster();
            printCollection(roster);

            Person[] rosterAsArray = roster.toArray(new Person[roster.size()]);

            class PersonAgeComparator implements Comparator<Person> {
                public int compare(Person a, Person b) {
                    return b.birthday.compareTo(a.birthday);
                }
            }
            Arrays.sort(rosterAsArray, new PersonAgeComparator());
            printCollection(rosterAsArray);

            Arrays.sort(rosterAsArray,
                    (a, b) -> Person.compareByAge(a, b)
            );
            printCollection(rosterAsArray);

            /**
             * Because this lambda expression invokes an existing method,
             * you can use a method reference instead of a lambda expression.
             */

            //(1) Reference to a Static Method
            Arrays.sort(rosterAsArray, Person::compareByAge);
            printCollection(rosterAsArray);

            //(2) Reference to an Instance Method of a Particular Object
            class ComparisonProvider {
                public int compareByName(Person a, Person b) {
                    return b.name.compareTo(a.name);
                }
            }
            ComparisonProvider myComparisonProvider = new ComparisonProvider();
            Arrays.sort(rosterAsArray, myComparisonProvider::compareByName);
            printCollection(rosterAsArray);

            //(3) Reference to an Instance Method of an Arbitrary Object of a Particular Type
            String[] stringArray = { "Barbara", "James", "Mary", "John",
                    "Patricia", "Robert", "Michael", "Linda" };
            Arrays.sort(stringArray, String::compareToIgnoreCase);
            for (String s : stringArray) {
                System.out.print(s + " ");
            }
            System.out.println();
            System.out.println();

            Set<Person> rosterSetLambda = transferElements(roster, () -> { return new HashSet<>(); });
            printCollection(rosterSetLambda);

            //(4) Reference to a Constructor
            Set<Person> rosterSet = transferElements(roster, HashSet::new);
            printCollection(rosterSet);
        }
    }

    public static void main(String... args) {
        LambdaExpressions.LambdaScope.testLambdaScope();

        LambdaExpressions.Calculator.testCalculator();

        LambdaExpressions.Person.testPerson();

        LambdaExpressions.Person.testMethodReferences();
    }
}

