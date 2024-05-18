package irrgarten;

/**
 * Abstract class for a character in the labyrinth.
 */
public abstract class LabyrinthCharacter {
    private static final int INVALID_POS = -1;
    
    /**
     * The name of the character.
     */
    private String name;

    /**
     * The intelligence of the character.
     */
    private float intelligence;

    /**
     * The strength of the character.
     */
    private float strength;

    /**
     * The health of the character.
     */
    private float health;
    /**
     * The position of the character in the labyrinth.
     */
    private int row;
    private int col;
    
    /**
     * Constructs a new LabyrinthCharacter object with the specified name, intelligence, strength, and health.
     * @param name the name of the character
     * @param intelligence the intelligence of the character
     * @param strength the strength of the character
     * @param health the health of the character
     */
    public LabyrinthCharacter(String name, float intelligence, float strength,
                              float health){
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = health;
        this.setPos(LabyrinthCharacter.INVALID_POS, LabyrinthCharacter.INVALID_POS);
    }
    
    /**
     * Constructs a new LabyrinthCharacter object with the specified name, intelligence, and strength.
     * @param other the character to copy
     */
    public LabyrinthCharacter(LabyrinthCharacter other){
        this(other.name, other.intelligence,other.strength, other.health);
        this.setPos(other.row, other.col);
    }
    
    /**
     * Checks if the Labyrinth character is dead
     * @return true if the character's health is less than or equal to 0, false otherwise
     */
    public boolean dead(){
        return this.health <= 0;
    }
    
    /**
     * @return the row of the character.
     */
    public int getRow(){
        return this.row;
    }
    
    /**
     * @return the column of the character.
     */
    public int getCol(){
        return this.col;
    }
    
    /**
     * @return the intelligence of the character.
     */
    protected float getIntelligence(){
        return this.intelligence;
    }
    
    /**
     * @return the strength of the character.
     */
    protected float getStrength(){
        return this.strength;
    }
    
    /**
     * @return the health of the character.
     */
    protected float getHealth(){
        return this.health;
    }
    
    /**
     * Sets the health of the character.
     * @param health the health of the character.
     */
    protected void setHealth(float health){
        this.health = health;
    }
    
    /**
     * Sets the position of the monster.
     * @param row the row position.
     * @param col the column position.
     */
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    /**
     * Return a visual representation of the characters atributes.
     * @return A string containing a visual representation of the character's attributes.
     */
    @Override
    public String toString(){
        String ret = this.name
                     + ",Intelligence: " + Float.toString(intelligence)
                     + ", Strength: "+ Float.toString(strength)
                     + ", Health: "+  Float.toString(health)
                     + ", Pos(" + Integer.toString(row) + "," 
                     + Integer.toString(col) + ")";
        return ret;
    }
    
    /**
     * Decreases the health of the character by 1.
     */
    protected void gotWounded(){
        this.health--;
    }
    
    /**
     * Abstract method to attack another character.
     * @return Returns the attack of the character.
     */
    public abstract float attack();

    /**
     * Abstract method to defend against an attack.
     * @param attack Received attack by the character.
     * @return Returns the defense of the character.
     */
    public abstract boolean defend(float attack);
    
    
}
