package irrgarten;

/**
 * Represents a weapon from the game Irrgarten
 * @author luisvaldivieso
 */
/**
 * The Weapon class represents a weapon with a certain power and number of uses.
 */
public class Weapon {
    private float power = 0;
    private int uses = 0;
    
    /**
     * Parameterized constructor to create a Weapon object.
     * 
     * @param power the power of the weapon
     * @param uses the number of uses left for the weapon
     */
    public Weapon(float power, int uses) {
        this.power = power;
        this.uses = uses;
    }
    
    /**
     * Makes the weapon attack. If the weapon has no uses left, the power is 0.
     * 
     * @return the power of the weapon as a float point number
     */
    public float attack() {
        if (uses > 0) {
            uses--;
            return power;
        } else {
            return 0;
        }
    }
    
    /**
     * Decides if a weapon must be discarded.
     * 
     * @return true if the weapon must be discarded, false otherwise
     */
    public boolean discard() {
        Dice dice = new Dice();
        return dice.discardElement(uses); 
    }
    
    /**
     * Returns a string representation of the weapon.
     * 
     * @return a string with information about the weapon in the format "W[power, uses]"
     */
    public String toString() {
        return "W[" + power + "," + uses + "]";
    }
}
