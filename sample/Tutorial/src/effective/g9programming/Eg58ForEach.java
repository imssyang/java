package effective.g9programming;

import java.util.*;

/**
 * Item 58: Prefer for-each loops to traditional for loops
 */
public class Eg58ForEach {

    enum Suit { CLUB, DIAMOND, HEART, SPADE }
    enum Rank { ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT,
        NINE, TEN, JACK, QUEEN, KING }

    static class Card {
        Card(Suit s, Rank r) { }
    }

    static Collection<Suit> suits = Arrays.asList(Suit.values());
    static Collection<Rank> ranks = Arrays.asList(Rank.values());

    void test() {
        List<Card> deck = new ArrayList<>();
        for (Iterator<Suit> i = suits.iterator(); i.hasNext(); ) {
            Suit suit = i.next();
            for (Iterator<Rank> j = ranks.iterator(); j.hasNext(); )
                deck.add(new Card(suit, j.next()));
        }

        // Preferred idiom for nested iteration on tutorial.collections and arrays
        for (Suit suit : suits)
            for (Rank rank : ranks)
                deck.add(new Card(suit, rank));
    }

}
