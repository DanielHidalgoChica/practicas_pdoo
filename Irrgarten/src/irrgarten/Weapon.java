package irrgarten;

/**
 *
 * @author luisvaldivieso
 */
public class Weapon {
    private float power = 0;
    private int uses = 0;
    
    /**
     * Parameterized constructor
     * @param power power of the weapon
     * @param uses  uses left for the weapon
     */
    Weapon (float power, int uses){
        this.power = power;
        this.uses = uses;
    }
    
    /**
     * Make the weapon attack. If the weapon has no uses left, it is 0.
     * @return A float point number with the weapons power.
     */
    public float attack(){
        if(uses > 0){
            uses--;
            return power;
        } 
        else
            return 0;
    }
    
    
    /**
     * Decides if a weapon must be discarded
     * @return true if it must be discarded and false othewise
     */
    public boolean discard(){
        Dice dice = new Dice();
        return dice.discardElement(uses); 
    }
    
    /**
     * Prints the power and uses of the weapon
     * @return A string with information about the weapon
     */
    public String toString(){
        return "W["+power+","+uses+"]";
    }
    
    
}
