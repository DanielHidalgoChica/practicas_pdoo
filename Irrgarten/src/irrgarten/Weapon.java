package irrgarten;

/**
 * Represents a weapon from the game Irrgarten

 */
/**
 * The Weapon class represents a weapon with a certain power and number of uses.
 */
public class Weapon extends CombatElement {
    /**
     * Parameterized constructor to create a Weapon object.
     * 
     * @param power the power of the weapon
     * @param uses the number of uses left for the weapon
     */
    public Weapon(float power, int uses) {
        super(power,uses);
    }
    
    /**
     * Makes the weapon attack. If the weapon has no uses left, the power is 0.
     * 
     * @return the power of the weapon as a float point number
     */
    public float attack() {
        return this.produceEffect();
    }
    
    /**
     * Returns a string representation of the weapon.
     * 
     * @return a string with information about the weapon in the format "W[power, uses]"
     */
    @Override
    public String toString() {
        return "W" + super.toString();
    }
}
