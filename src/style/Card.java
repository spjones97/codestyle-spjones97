package style;

public interface Card {

    enum Suit {SPADES, HEARTS, DIAMONDS, CLUBS};

    int getRank();
    Card.Suit getSuit();
    String toString();
    boolean equals(Card otherCard);

    static String suitToString(Card.Suit suit) {

        switch (suit) {
            case SPADES:
                return "Spades";
            case HEARTS:
                return "Hearts";
            case DIAMONDS:
                return "Diamonds";
            case CLUBS:
                return "Clubs";
        }
        return null;
    }
}