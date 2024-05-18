package irrgarten;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Abstract class for a deck of cards. The deck is a list of cards that can be
 * drawn from. If the deck is empty, the deck is refilled and shuffled.
 * 
 */
public abstract class CardDeck <T extends CombatElement>{
    
    /**
     * The list of cards in the deck.
     * @param T Type of the cards contained in the deck
     */
    private ArrayList<T> cardDeck;
    
    /**
     * Constructor for the card deck. Initializes the card deck.
     */
    public CardDeck(){
        this.cardDeck = new ArrayList<T>();
    };
    
    /**
     * Abstract method to add cards to the deck. This method should be
     * implemented by the subclass to add the cards to the deck.
     */
    protected abstract void addCards();
    
    /**
     * Adds a card to the deck.
     * @param card The card to add to the deck.
     */
    protected void addCard (T card){
        this.cardDeck.add(card);
    }
    
    /**
     * Draws the next card from the deck. If the deck is empty, the deck is
     * refilled and shuffled.
     * @return The next card in the deck.
     */
    public T nextCard(){
        if (this.cardDeck.isEmpty()){
            this.addCards();
            // Shuffle the deck
            Collections.shuffle(cardDeck);
        }
        T card = this.cardDeck.get(0);
        this.cardDeck.remove(0);
        return card;     
    }
}
