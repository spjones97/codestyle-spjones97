package style;

public interface PokerHand {

	Card[] getCards();
	boolean contains(Card card);

	boolean isOnePair();
	boolean isTwoPair();
	boolean isThreeOfAKind();
	boolean isStraight();
	boolean isFlush();
	boolean isFullHouse();
	boolean isFourOfAKind();
	boolean isStraightFlush();

	int getHandTypeValue();
	int getHandRank();

	int compareTo(PokerHand otherHand);
}
