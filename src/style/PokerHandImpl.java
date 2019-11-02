package style;

public class PokerHandImpl implements PokerHand {

	private Card[] cards;

	/*
	Creates a poker hand that consists of 5 cards containing a hand value
	and hand rank depending on the relationship between the rank and suits
	of the cards.
	 */
	public PokerHandImpl(Card[] cards) {
		if (cards == null) {
			throw new RuntimeException("cards cannot be null");
		}
		if (cards.length != 5) {
			throw new RuntimeException("cards length needs to be five");
		}
		for (int i=0; i<5; i++) {
			if (cards[i] == null) {
				throw new RuntimeException("card cannot be null");
			}
		}

		this.cards = cards.clone();
		
		for (int i=0; i<5; i++) {			
			for (int j=i; j<5; j++) {
				if (this.cards[j].getRank() < this.cards[i].getRank()) {
					Card tempCard = this.cards[i];
					this.cards[i] = this.cards[j];
					this.cards[j] = tempCard;
				}
			}
		}
	}

	/*
	Returns as an array the five cards in the hand.
	 */
	public Card[] getCards() {
		return cards.clone();
	}

	/*
	Returns true if one of the cards in the hand matches in rank and
	suit the card passed in as a parameter.
	 */
	public boolean contains(Card c) {
		for (int i=0; i<5; i++) {
			if (cards[i].equals(c)) {
				return true;
			}
		}
		return false;
	}

	/*
	Indicates whether the hand is a flush.
	Note, a hand that is a straight flush should return true for this method
	 */
	public boolean isFlush() {
		for (int i=1; i<5; i++) {
			if (cards[i].getSuit() != cards[0].getSuit()) {
				return false;
			}
		}
		return true;
	}

	/*
	Indicates whether the hand is a straight.
	Note, a hand that is a straight flush should return true for this
	method.
	 */
	public boolean isStraight() {
		boolean output = true;
		for (int i=0; i<4; i++) {
			if (cards[i].getRank()+1 != cards[i+1].getRank()) {
				output = false;
				break;
			}
		}
		return output || isTheWheel();
	}

	private boolean isTheWheel() {
		if (cards[0].getRank() == 2) {
			if (cards[1].getRank() == 3) {
				if (cards[2].getRank() == 4) {
					if (cards[3].getRank() == 5) {
						if (cards[4].getRank() == 14) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/*
	Indicates whether the hand is a one pair hand.
	 */
	public boolean isOnePair() {
		int pair_idx = findPairStartingAt(0);
		boolean no_other_pairs = findPairStartingAt(pair_idx+1) == -1;
		return ((pair_idx != -1) && no_other_pairs);
	}

	/*
	Indicates whether the hand is a two pair hand.
	 */
	public boolean isTwoPair() {
		int firstPairIdx = findPairStartingAt(0);
		int second_pairIdx = findPairStartingAt(firstPairIdx+2);
		return ((firstPairIdx != -1) &&
				(second_pairIdx != -1) &&
				!isFourOfAKind() &&
				!isFullHouse());
	}

	/*
	Indicates whether the hand is a three of a kind.
	 */
	public boolean isThreeOfAKind() {
		int firstPairIndex = findPairStartingAt(0);
		if ((firstPairIndex == -1) || (firstPairIndex == 3)) {
			return false;
		}
		return ((cards[firstPairIndex].getRank() == cards[firstPairIndex+2].getRank()) &&
				!isFourOfAKind() &&
				!isFullHouse());
	}

	/*
	Indicates whether the hand is a full house.
	 */
	public boolean isFullHouse() {
		return (((cards[0].getRank() == cards[1].getRank()) &&
				 (cards[2].getRank() == cards[3].getRank()) &&
				 (cards[3].getRank() == cards[4].getRank())) ||
				((cards[0].getRank() == cards[1].getRank()) &&
				 (cards[1].getRank() == cards[2].getRank()) &&
				 (cards[3].getRank() == cards[4].getRank())));
	}

	/*
	Indicates whether the hand is a four of a kind.
	 */
	public boolean isFourOfAKind() {
		return (((cards[0].getRank() == cards[1].getRank()) &&
				 (cards[1].getRank() == cards[2].getRank()) &&
				 (cards[2].getRank() == cards[3].getRank()))||
				((cards[1].getRank() == cards[2].getRank()) &&
				 (cards[2].getRank() == cards[3].getRank()) &&
				 (cards[3].getRank() == cards[4].getRank())));
	}	

	/*
	Indicates whether the hand is a straight flush (i.e., both a straight
	and a flush).
	 */
	public boolean isStraightFlush() {
		if (isStraight() == true && isFlush() == true) {
			return true;
		}
		return false;
	}

	/*
	Returns the hand rank of the hand as described above.
	 */
	public int getHandRank() {
		if (isOnePair() == true) {
			return cards[findPairStartingAt(0)].getRank();
		} else if (isTwoPair() == true) {
			return cards[3].getRank();
		} else if (isThreeOfAKind() == true || isFourOfAKind() == true || isFullHouse() == true) {
			return cards[2].getRank();
		} else if (isTheWheel() == true) {
			return 5;
		} else {
			return cards[4].getRank();
		}
	}

	/*
	Compares a hand with the hand passed in as other.
	Returns -1 if the hand value is smaller and 1 if the hand value is
	larger than the hand passed in as other.
	If the hand values are equal, then returns -1 if the hand rank is
	smaller or 1 if the hand rank is larger.
	If both hand value and hand rank are the same, returns 0.
	 */
	public int compareTo(PokerHand otherHand) {
		if (getHandTypeValue() < otherHand.getHandTypeValue()) {
			return -1;
		} else if (getHandTypeValue() > otherHand.getHandTypeValue()) {
			return 1;
		} else {
			if (getHandRank() < otherHand.getHandRank()) {
				return -1;
			} else if (getHandRank() > otherHand.getHandRank()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	/*
	Returns the hand value of the hand as described above.
	 */
	public int getHandTypeValue() {
		if (isStraightFlush()){
			return 9;
		}
		if (isOnePair()){
			return 2;
		}
		if (isTwoPair()){
			return 3;
		}
		if (isThreeOfAKind()) {
			return 4;
		}
		if (isStraight()){
			return 5;
		}
		if (isFlush()){
			return 6;
		}
		if (isFullHouse()){
			return 7;
		}
		if (isFourOfAKind()){
			return 8;
		}
		return 1;
	}
	
	private int findPairStartingAt(int num) {
		if (num < 0) {
			num = 0;
		}
		if (num >= 4){
			return -1;
		}
		for (int i=num; i<4; i++) {
			if (cards[i].getRank() == cards[i+1].getRank()) {
				return i;
			}
		}
		return -1;
	}

}
