package irrgarten;

/**
 * Represents a shield in the game Irrgarten
 * @author Daniel Hidalgo Chica and Luis Esteban Valdivieso
 */
public class Shield {
    /**
     * The protection that the shield offers.
     */
    private float protection;

    /**
     * The number of uses left for the shield.
     */
    private int uses;
    
    /**
     * Parameterized constructor to create a Shield object.
     * 
     * @param protection the protection that the shield offers
     * @param uses the number of uses left for the shield
     */
    Shield(float protection, int uses){
        this.protection = protection;
        this.uses = uses;
    }
    
    /**
     * Returns the protection that the shield offers. If the shield has no uses left, the protection is 0.
     * 
     * @return the protection value as a float
     */
    public float protect(){
        if (uses > 0){
            uses--;
            return protection;
        }
        else return 0;
    }
    
    /**
     * Decides if a shield must be discarded.
     * 
     * @return true if the shield must be discarded, false otherwise
     */
    public boolean discard(){
        Dice dice = new Dice();
        return dice.discardElement(uses); 
    }
    
    /**
     * Returns a string representation of the shield.
     * 
     * @return a string with information about the shield in the format "S[protection,uses]"
     */
    public String toString(){
        return "S["+protection+","+uses+"]";
    }
}
