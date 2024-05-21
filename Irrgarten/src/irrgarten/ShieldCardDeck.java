package irrgarten;

/**
 * Represents a deck of shield cards.
 */
public class ShieldCardDeck extends CardDeck<Shield>{
    /**
     * Adds shields cards to the maze
     */
    @Override
    protected void addCards(){
        int nCards = 10;
        for(int i = 0; i < nCards; i++){
            Shield newShield = new Shield(Dice.shieldPower(), Dice.usesLeft());
            this.addCard(newShield);
        }
    }
}
