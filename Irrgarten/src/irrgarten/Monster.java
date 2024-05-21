package irrgarten;

/**
 * Represents a monster in the game
 */
public class Monster extends LabyrinthCharacter{
    private static final int INITIAL_HEALTH = 5;
    /**
     * Constructs a new Monster object with the specified name, intelligence, and strength.
     * @param name the name of the monster
     * @param intelligence the intelligence of the monster
     * @param strength the strength of the monster
     */
    public Monster(String name, float intelligence, float strength) {
        super(name,intelligence,strength, Monster.INITIAL_HEALTH);
    }

    /**
     * Calculates the attack intensity of the monster.
     * @return the attack intensity
     */
    @Override
    public float attack(){
        return Dice.intensity(this.getStrength());
    }
    
    /**
     * Defends against an attack.
     * @param receivedAttack the attack intensity received
     * @return true if the monster successfully defended, false otherwise
     */
    @Override
    public boolean defend(float receivedAttack) {
        boolean isDead = this.dead();
        if(!isDead){
            float defensiveEnergy = Dice.intensity(this.getIntelligence());
            if(defensiveEnergy < receivedAttack){
                this.gotWounded();
                isDead=this.dead();
            }
                
        }
        return isDead;
    }

    /**
     * Returns a string representation of the monster's state.
     * @return a string representation of the monster
     */
    @Override
    public String toString(){
        return "M[" + super.toString() + "]\n";
    }
}
