package irrgarten;

/**
 * Abstract class for a combat element. A combat element is an element that can
 * be used in combat to affect the outcome of the combat.
 */
public abstract class CombatElement {
    /**
     * The effect of the combat element.
     */
    private float effect;

    /**
     * The number of uses of the combat element.
     */
    private int uses;
    
    /**
     * Constructor for the combat element. Initializes the combat element with the
     * effect and the number of uses.
     * @param effect The effect of the combat element.
     * @param uses The number of uses of the combat element.
     */
    public CombatElement(float effect, int uses){
        this.effect = effect;
        this.uses = uses;
    }
    
    /**
     * Produces the effect of the combat element. If the combat element has no
     * uses left, the effect is 0.
     * @return The effect of the combat element.
     */
    protected float produceEffect(){
        float result;
        if (this.uses > 0){
            result = this.effect;
            this.uses--;
        }    
        else
            result = 0f;
        return result;
    }
    
    /**
     * Decides if a combat element must be discarded
     * 
     * @return true if the combat element must be discarded, false otherwise
     */
    public boolean discard(){
        return Dice.discardElement(this.uses); 
    }
    
    /**
     * Return a string representation of the combat element.
     * @return A string representation of the combat element.
     */
    public String toString(){
        return "[" + this.effect + "," + this.uses + "]";
    };
}
