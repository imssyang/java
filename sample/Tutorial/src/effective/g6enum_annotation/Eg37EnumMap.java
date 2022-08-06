package effective.g6enum_annotation;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Item 37: Use EnumMap instead of ordinal indexing
 */
public class Eg37EnumMap {

    static class Plant {
        enum LifeCycle { ANNUAL, PERENNIAL, BIENNIAL }
        final String name;
        final LifeCycle lifeCycle;
        Plant(String name, LifeCycle lifeCycle) {
            this.name = name;
            this.lifeCycle = lifeCycle;
        }
        @Override public String toString() {
            return name;
        }

        public static void main(String[] args) {
            Plant[] garden = {
                    new Plant("a", LifeCycle.ANNUAL),
                    new Plant("b", LifeCycle.PERENNIAL),
                    new Plant("c", LifeCycle.BIENNIAL)
            };

            // Using ordinal() to index into an array - DON'T DO THIS!
            Set<Plant>[] plantsByLifeCycle = (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];
            for (int i = 0; i < plantsByLifeCycle.length; i++)
                plantsByLifeCycle[i] = new HashSet<>();
            for (Plant p : garden)
                plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);
            for (int i = 0; i < plantsByLifeCycle.length; i++) {
                System.out.printf("%s: %s%n", Plant.LifeCycle.values()[i], plantsByLifeCycle[i]);
            }

            // Using an EnumMap to associate data with an enum
            Map<LifeCycle, Set<Plant>> plantsByLifeCycle2 = new EnumMap<>(Plant.LifeCycle.class);
            for (Plant.LifeCycle lc : Plant.LifeCycle.values())
                plantsByLifeCycle2.put(lc, new HashSet<>());
            for (Plant p : garden)
                plantsByLifeCycle2.get(p.lifeCycle).add(p);
            System.out.println(plantsByLifeCycle);

            // Naive stream-based approach - unlikely to produce an EnumMap!
            System.out.println(Arrays.stream(garden)
                    .collect(groupingBy(p -> p.lifeCycle)));

            // Using a stream and an EnumMap to associate data with an enum
            System.out.println(Arrays.stream(garden)
                    .collect(groupingBy(p -> p.lifeCycle,
                            () -> new EnumMap<>(LifeCycle.class), toSet())));
        }
    }

    // Using ordinal() to index array of arrays - DON'T DO THIS!
    public enum Phase1 {
        SOLID, LIQUID, GAS;
        public enum Transition {
            MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;
            // Rows indexed by from-ordinal, cols by to-ordinal
            private static final Transition[][] TRANSITIONS = {
                    { null, MELT, SUBLIME },
                    { FREEZE, null, BOIL },
                    { DEPOSIT, CONDENSE, null }
            };
            // Returns the phase transition from one phase to another
            public static Transition from(Phase from, Phase to) {
                return TRANSITIONS[from.ordinal()][to.ordinal()];
            }
        }
    }

    // Using a nested EnumMap to associate data with enum pairs
    public enum Phase {
        SOLID, LIQUID, GAS;
        public enum Transition {
            MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
            BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID),
            SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID);
            private final Phase from;
            private final Phase to;
            Transition(Phase from, Phase to) {
                this.from = from;
                this.to = to;
            }
            // Initialize the phase transition map
            private static final Map<Phase, Map<Phase, Transition>> m1 = new EnumMap<>(Phase.class);
            static {
                for (Phase p : Phase.values())
                    m1.put(p, new EnumMap<Phase, Transition>(Phase.class));
                for (Transition trans : Transition.values())
                    m1.get(trans.from).put(trans.to, trans);
            }

            private static final Map<Phase, Map<Phase, Transition>> m2 =
                    Stream.of(values()).collect(groupingBy(t -> t.from,
                    () -> new EnumMap<>(Phase.class),
                    toMap(t -> t.to, t -> t, (x, y) -> y,
                            () -> new EnumMap<>(Phase.class))));

            public static Transition from(Phase from, Phase to) {
                return m2.get(from).get(to);
            }
        }
    }

}
