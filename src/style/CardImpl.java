package style;

public class CardImpl implements Card {
	private static String[] strings = {null, null, "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
	
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int ACE = 14;

	public int rank;
	public Card.Suit suit;

	public CardImpl(int rank, Card.Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	/*
	Returns the "rank" of a playing card. Each card should have a rank
	between 2 and 14.
	 */
	public int getRank() {
		return rank;
	}

	/*
	Returns the "suit" of a playing card (i.e., spades, hearts, diamonds,
	or clubs). Each card should have a suit that is one of the enumerated
	symbols defined in the Card.Suit enumeration.
	 */
	public Suit getSuit() {
		return suit;
	}

	/*
	Returns true if the card passed in as other has the same rank and
	suit values.
	 */
	public boolean equals(Card other) {
		return (rank == other.getRank()) && (suit == other.getSuit());
	}

	/*
	Returns a string representation of the card. This should be in the
	form of "Rank of Suit" where "Rank" is the word representation of each
	rank.
	 */
	public String toString() {
		return strings[getRank()] + " of " + Card.suitToString(getSuit());
	}

}
