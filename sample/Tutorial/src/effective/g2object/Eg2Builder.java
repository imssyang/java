package effective.g2object;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

import static effective.g2object.Eg2Builder.NyPizza.Size.SMALL;
import static effective.g2object.Eg2Builder.Pizza.Topping.*;

/**
 * Item 2: Consider a builder when faced with many constructor parameters
 */
public class Eg2Builder {

    // Telescoping constructor pattern - does not scale well!
    public static class NutritionFacts {
        private final int servingSize;  // (mL) required
        private final int servings;     // (per container) required
        private final int calories;     // (per serving) optional
        private final int fat;          // (g/serving) optional
        private final int sodium;       // (mg/serving) optional
        private final int carbohydrate; // (g/serving) optional

        public NutritionFacts(int servingSize, int servings) {
            this(servingSize, servings, 0);
        }
        public NutritionFacts(int servingSize, int servings, int calories) {
            this(servingSize, servings, calories, 0);
        }
        public NutritionFacts(int servingSize, int servings, int calories, int fat) {
            this(servingSize, servings, calories, fat, 0);
        }
        public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
            this(servingSize, servings, calories, fat, sodium, 0);
        }

        public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
            this.servingSize = servingSize;
            this.servings = servings;
            this.calories = calories;
            this.fat = fat;
            this.sodium = sodium;
            this.carbohydrate = carbohydrate;
        }
    }

    // JavaBeans Pattern - allows inconsistency, mandates mutability
    public static class NutritionFacts2 {
        // Parameters initialized to default values (if any)
        private int servingSize = -1; // Required; no default value
        private int servings = -1;    // Required; no default value
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;
        public NutritionFacts2() { }

        // Setters
        public void setServingSize(int val) { servingSize = val; }
        public void setServings(int val) { servings = val; }
        public void setCalories(int val) { calories = val; }
        public void setFat(int val) { fat = val; }
        public void setSodium(int val) { sodium = val; }
        public void setCarbohydrate(int val) { carbohydrate = val; }
    }

    // Builder Pattern
    public static class NutritionFacts3 {
        private final int servingSize;
        private final int servings;
        private final int calories;
        private final int fat;
        private final int sodium;
        private final int carbohydrate;

        public static class Builder {
            // Required parameters
            private final int servingSize;
            private final int servings;
            // Optional parameters - initialized to default values
            private int calories = 0;
            private int fat = 0;
            private int sodium = 0;
            private int carbohydrate = 0;

            public Builder(int servingSize, int servings) {
                this.servingSize = servingSize;
                this.servings = servings;
            }

            public Builder calories(int val) {
                calories = val;
                return this;
            }

            public Builder fat(int val) {
                fat = val;
                return this;
            }

            public Builder sodium(int val) {
                sodium = val;
                return this;
            }

            public Builder carbohydrate(int val) {
                carbohydrate = val;
                return this;
            }

            public NutritionFacts3 build() {
                return new NutritionFacts3(this);
            }
        }

        private NutritionFacts3(Builder builder) {
            servingSize = builder.servingSize;
            servings = builder.servings;
            calories = builder.calories;
            fat = builder.fat;
            sodium = builder.sodium;
            carbohydrate = builder.carbohydrate;
        }
    }

    // Builder pattern for class hierarchies
    public abstract static class Pizza {
        public enum Topping { HAM, MUSHROOM, ONION, PEPPER, SAUSAGE }
        final Set<Topping> toppings;
        abstract static class Builder<T extends Builder<T>> {
            EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
            public T addTopping(Topping topping) {
                toppings.add(Objects.requireNonNull(topping));
                return self();
            }
            abstract Pizza build();
            // Subclasses must override this method to return "this"
            protected abstract T self();
        }
        Pizza(Builder<?> builder) {
            toppings = builder.toppings.clone(); // See Item 50
        }
    }

    public static class NyPizza extends Pizza {
        public enum Size { SMALL, MEDIUM, LARGE }
        private final Size size;
        public static class Builder extends Pizza.Builder<Builder> {
            private final Size size;
            public Builder(Size size) {
                this.size = Objects.requireNonNull(size);
            }
            @Override
            public NyPizza build() {
                return new NyPizza(this);
            }
            @Override
            protected Builder self() { return this; }
        }
        private NyPizza(Builder builder) {
            super(builder);
            size = builder.size;
        }
    }

    public static class Calzone extends Pizza {
        private final boolean sauceInside;

        public static class Builder extends Pizza.Builder<Builder> {
            private boolean sauceInside = false; // Default

            public Builder sauceInside() {
                sauceInside = true;
                return this;
            }

            @Override
            public Calzone build() {
                return new Calzone(this);
            }

            @Override
            protected Builder self() {
                return this;
            }
        }

        private Calzone(Builder builder) {
            super(builder);
            sauceInside = builder.sauceInside;
        }
    }

    public static void main(String[] args) {
        NutritionFacts cocaCola = new NutritionFacts(240, 8, 100, 0, 35, 27);

        NutritionFacts2 cocaCola2 = new NutritionFacts2();
        cocaCola2.setServingSize(240);
        cocaCola2.setServings(8);
        cocaCola2.setCalories(100);
        cocaCola2.setSodium(35);
        cocaCola2.setCarbohydrate(27);

        NutritionFacts3 cocaCola3 = new NutritionFacts3.Builder(240, 8)
                .calories(100).sodium(35).carbohydrate(27).build();

        NyPizza pizza = new NyPizza.Builder(SMALL)
                .addTopping(SAUSAGE).addTopping(ONION).build();
        Calzone calzone = new Calzone.Builder()
                .addTopping(HAM).sauceInside().build();
    }

}
