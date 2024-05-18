package irrgarten;

/**
 * Represents a shield in the game Irrgarten
 */
public class Shield extends CombatElement {
    /**
     * Parameterized constructor to create a Shield object.
     * 
     * @param protection the protection that the shield offers
     * @param uses the number of uses left for the shield
     */
    Shield(float protection, int uses){
        super(protection,uses);
    }
    
    /**
     * Returns the protection that the shield offers. If the shield has no uses left, the protection is 0.
     * 
     * @return the protection value as a float
     */
    public float protect(){
        return this.produceEffect();
    }
    
    /**
     * Returns a string representation of the shield.
     * 
     * @return a string with information about the shield in the format "S[protection,uses]"
     */
    @Override
    public String toString(){
        return "S"+super.toString();
    }
}
