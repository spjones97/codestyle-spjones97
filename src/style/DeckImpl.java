package style;

public class DeckImpl implements Deck {

	private Card[] cards;
	private int numberLeftToDeal;
	
	// Creates a deck of cards
	public DeckImpl() {
		numberLeftToDeal = 52;
		cards = new Card[numberLeftToDeal];

		int cardsIndex = 0;
		for (Card.Suit suit : Card.Suit.values()) {
			for (int rank = 2; rank <= CardImpl.ACE; rank++) {
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
		if (numberLeftToDeal >= 5) {
			return true;
		}
		return false;
	}

	// Returns the next card in the deck
	public Card dealNextCard() {
		if (numberLeftToDeal == 0) {
			throw new RuntimeException("No cards left");
		}
		Card dealtCard = cards[nextDealtIndex()];
		numberLeftToDeal -= 1;
		return dealtCard;
	}

	// Returns a poker hand
	public PokerHand dealHand() {
		if (!hasHand()) {
			throw new RuntimeException("Person does not have a hand of cards");
		}
		
		Card[] handOfCards = new Card[5];
		for (int i=0; i<handOfCards.length; i++) {
			handOfCards[i] = dealNextCard();
		}
		return new PokerHandImpl(handOfCards);
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
		return (52 - numberLeftToDeal);
	}
}
