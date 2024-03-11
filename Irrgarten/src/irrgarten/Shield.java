
package irrgarten;

/**
 *
 * @author luisvaldivieso
 */
public class Shield {
    private float protection;
    private int uses;
    
    /**
     * Parameterized constructor
     * @param protection Protection that the shield offer
     * @param uses Uses left for the shield
     */
    Shield(float protection, int uses){
        this.protection = protection;
        this.uses = uses;
    }
    
    /**
     * Return the protection that the shield offer. If the shield has no uses
     * left, it is 0.
     * @return A float point number with the protection value
     */
    public float protect(){
        if (uses > 0){
            uses--;
            return protection;
        }
        else
            return 0;
    }
    
    /**
     * Decides if a shield must be discarded
     * @return true if it must be discarded and false othewise
     */
    public boolean discard(){
        Dice dice = new Dice();
        return dice.discardElement(uses); 
    }
    
    /**
     * Prints the protection and uses of the shield
     * @return A string with information about the shield
     */
    public String toString(){
        return "S["+protection+","+uses+"]";
    }
    
    
}
