package effective.g9programming;

import java.math.BigDecimal;

/**
 * Item 60: Avoid float and double if exact answers are required
 *   - They perform binary floating-point arithmetic, however, not provide exact results.
 */
public class Eg60AvoidFloat_Double {

    public static void main(String[] args) {
        // not provide exact results
        System.out.println(1.03 - 0.42);
        System.out.println(1.00 - 9 * 0.10);

        // Broken - uses floating point for monetary calculation!
        double funds = 1.00;
        int itemsBought = 0;
        for (double price = 0.10; funds >= price; price += 0.10) {
            funds -= price;
            itemsBought++;
        }
        System.out.println(itemsBought + " items bought.");
        System.out.println("Change: $" + funds);

        // use BigDecimal, int, or long for monetary calculations
        final BigDecimal TEN_CENTS = new BigDecimal(".10");
        int itemsBought2 = 0;
        BigDecimal funds2 = new BigDecimal("1.00");
        for (BigDecimal price = TEN_CENTS;
             funds2.compareTo(price) >= 0;
             price = price.add(TEN_CENTS)) {
            funds2 = funds2.subtract(price);
            itemsBought2++;
        }
        System.out.println(itemsBought2 + " items bought.");
        System.out.println("Money left over: $" + funds2);
    }

}
