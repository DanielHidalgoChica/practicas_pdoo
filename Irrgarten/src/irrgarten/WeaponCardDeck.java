package irrgarten;

/**
 * Represents a deck of weapon cards.
 */
public class WeaponCardDeck extends CardDeck <Weapon> {
    /**
     * Adds weapons cards to the maze
     */
    @Override
    protected void addCards(){
        int nCards = 10;
        for(int i = 0; i < nCards; i++){
            Weapon newWeapon = new Weapon(Dice.weaponPower(), Dice.usesLeft());
            this.addCard(newWeapon);
        }
    }
}
