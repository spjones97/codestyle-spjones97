package style;

public class DeckImpl implements Deck {
	// Instance fields
	private Card[] cards;
	private int numberLeftToDeal;
	
	
	
	// Creates a deck of cards
	public DeckImpl() {
		numberLeftToDeal = 52;
		cards = new Card[numberLeftToDeal];

		int cardsIndex = 0;
		for (Card.Suit suit : Card.Suit.values()) {
			for (int rank = 2; rank <= CardImpl.ACE; rank++) {
				//System.out.println(rank);
				cards[cardsIndex] = new CardImpl(rank, suit);
				cardsIndex += 1;
			}
		}
		
		for (int i = 0; i< cards.length; i++) {
			int swapIndex = i + ((int) (Math.random() * (cards.length - i)));
			Card tempCard = cards[i];
			cards[i] = cards[swapIndex];
			cards[swapIndex] = tempCard;
		}		
	}

	// Returns whether of not someone has a hand of cards
	public boolean hasHand() {
		boolean hasHand = false;
		if (numberLeftToDeal >= 5) {
			hasHand = true;
		}
		return hasHand;
	}

	// Returns the next card in the deck
	public Card dealNextCard() {
		if (numberLeftToDeal == 0) {
			throw new RuntimeException();
		}
		Card dealtCard = cards[nextDealtIndex()];
		numberLeftToDeal -= 1;
		return dealtCard;
	}

	// Returns a poker hand
	public PokerHand dealHand() {
		if (hasHand() == false) {
			throw new RuntimeException("Person does not have a hand of cards");
		}
		
		Card[] handOfCards = new Card[5];
		for (int i=0; i<handOfCards.length; i++) {
			handOfCards[i] = dealNextCard();
		}
		PokerHand hand = new PokerHandImpl(handOfCards);
		return hand;
	}	

	// Finds and removes card from the deck of cards
	public void findAndRemove(Card card) {
		if (numberLeftToDeal == 0) {
			return;
		}
		for (int i = nextDealtIndex(); i<52; i++) {
			if (cards[i].equals(card)) {
				Card tempCard = cards[i];
				cards[i] = cards[nextDealtIndex()];
				cards[nextDealtIndex()] = tempCard;
				dealNextCard();
				return;
			}
		}
		return;
	}

	private int nextDealtIndex() {
		int index = 52- numberLeftToDeal;
		return index;
	}
}
