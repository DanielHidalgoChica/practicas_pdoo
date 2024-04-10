package irrgarten;

/**
 * Represents a monster in the game
 * @author Daniel Hidalgo Chica
 */
/**
 * Represents a monster in the game.
 */
public class Monster {
    private static final int INITIAL_HEALTH = 5;
    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    
    /**
     * Constructs a new Monster object with the specified name, intelligence, and strength.
     * @param name the name of the monster
     * @param intelligence the intelligence of the monster
     * @param strength the strength of the monster
     */
    public Monster(String name, float intelligence, float strength) {
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;
    }
    
    /**
     * Checks if the monster is dead.
     * @return true if the monster's health is less than or equal to 0, false otherwise
     */
    public boolean dead() {
        return (health <= 0);
    }

    /**
     * Calculates the attack intensity of the monster.
     * @return the attack intensity
     */
    public float attack(){
        return Dice.intensity(this.strength);
    }
    
    /**
     * Defends against an attack.
     * @param receiveAttack the attack intensity received
     * @return true if the monster successfully defended, false otherwise
     */
    boolean defend(float receiveAttack) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Sets the position of the monster.
     * @param row the row position
     * @param col the column position
     */
    void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns a string representation of the monster's state.
     * @return a string representation of the monster
     */
    public String toString(){
        String ret = "\nMonster State"
                    + "\nName:" + this.name
                    + "\nIntelligence:" + Float.toString(intelligence)
                    + "\nStrength:"+ Float.toString(strength)
                    + "\nHealth:"+  Float.toString(health)
                    + "\nPosition: (" + Integer.toString(row) + "," + Integer.toString(col) + ")\n";
        return ret;
    }

    /**
     * Decreases the health of the monster by 1.
     */
    void gotWounded() {
        health -= 1;
    }
}
