package irrgarten;
import java.util.ArrayList;

/**
 * The Player class represents a player in the game.
 */
public class Player {
    public static final int MAX_WEAPONS = 2;
    public static final int MAX_SHIELDS = 3;
    public static final int INITIAL_HEALTH = 10;
    public static final int HITS2LOSE = 3;
    
    private String name;
    private char number;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits = 0;

    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;

    /**
     * Parametrized constructor to create a Player object.
     * @param number The player number.
     * @param intelligence The intelligence level of the player.
     * @param strength The strength level of the player.
     */
    public Player(char number, float intelligence, float strength) {
        this.number = number;
        this.name = "Player # " + number;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;;
        weapons = new ArrayList<Weapon>(0);
        shields = new ArrayList<Shield> (0);
        // Put the rest of the attributes to default
        resurrect(); 
    }

    /**
     * Resurrects the player by resetting their attributes.
     */
    public void resurrect() {
        weapons.clear();
        shields.clear();
        health = INITIAL_HEALTH;
        resetHits();
    }
    
    /**
     * Gets the row position of the player.
     * @return The row position.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Gets the column position of the player.
     * @return The column position.
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Gets the player number.
     * @return The player number.
     */
    public char getNumber(){
        return this.number;
    }

    /**
     * Sets the position of the player.
     * @param row The row position.
     * @param col The column position.
     */
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Checks if the player is dead.
     * @return true if the player is dead, false otherwise.
     */
    public boolean dead(){
        return (this.health <= 0);
    }
    
    /**
     * Moves the player in the specified direction.
     * @param direction The direction to move.
     * @param validMoves The list of valid moves.
     * @return The direction the player moved to.
     */
    public Directions move(Directions direction, ArrayList<Directions> validMoves) {
        throw new UnsupportedOperationException();
    }

    /**
     * Calculates the attack power of the player.
     * @return The attack power.
     */
    public float attack(){
        return this.strength + this.sumWeapons();
    }
    
    /**
     * Defends against an attack.
     * @param receivedAttack The attack power received.
     * @return true if the player successfully defended, false otherwise.
     */
    public boolean defend(float receivedAttack){
        throw new UnsupportedOperationException();
    }
    
    /**
     * Receives a reward.
     */
    public void receiveReward(){	
        throw new UnsupportedOperationException();
    }
    
    /**
     * Returns a string representation of the player.
     * @return The string representation of the player.
     */
    public String toString(){
        String ret = "\nPlayer State"
                    + "\nName:" + this.name
                    + "\nIntelligence:" + Float.toString(intelligence)
                    + "\nStrength:"+ Float.toString(strength)
                    + "\nHealth:"+  Float.toString(health)
                    + "\nPosition: (" + Integer.toString(row) + "," + Integer.toString(col) + ")"
                    + "\nConsecutive Hits:"+  Integer.toString(this.consecutiveHits)
                    + "\nWeapons:";
	    // Muestro las armas
	    for (Weapon aWeapon : this.weapons) {
	        ret +=  "\n\t" + aWeapon.toString();
	    }
	    ret += "\nShields:";
	    for (Shield aShield : this.shields) {
	        ret += "\n\t" + aShield.toString();
	    }
            ret += "\n";
        return ret;
    }
    
    private void receiveWeapon(Weapon w){
        throw new UnsupportedOperationException();
    }

    private void receiveShield(Shield s){
        throw new UnsupportedOperationException();
    }
    
    /**
     * Creates a new weapon.
     * @return The new weapon.
     */
    private Weapon newWeapon(){
        float rPower = Dice.weaponPower();
        int rUses = Dice.usesLeft();
        Weapon newWeapon = new Weapon(rPower, rUses);
        return newWeapon;
    }

    /**
     * Creates a new shield.
     * @return The new shield.
     */
    private Shield newShield(){
        float rProtection = Dice.shieldPower();
        int rUses = Dice.usesLeft();
        Shield newShield = new Shield(rProtection, rUses);
        return newShield;
    }
    
    /**
     * Calculates the sum of the weapons' attack power.
     * @return The sum of the weapons' attack power.
     */
    private float sumWeapons(){
        float sum = 0; 
        for (Weapon aWeapon : weapons){
            sum += aWeapon.attack();
        }
        return sum;
    }
    
    /**
     * Calculates the sum of the shields' protection.
     * @return The sum of the shields' protection.
     */
    private float sumShields(){
        float sum = 0;
        for (Shield aShield : this.shields){
            sum += aShield.protect();
        }
        return sum;
    }
    
    /**
     * Calculates the defensive energy of the player.
     * @return The defensive energy.
     */
    private float defensiveEnergy(){
        return this.sumShields() + this.intelligence;
    }

    private void manageHit(float receivedAttack){
        throw new UnsupportedOperationException();
    }
    
    /**
     * Resets the consecutive hits counter.
     */
    private void resetHits(){
        this.consecutiveHits = 0;
    }

    /**
     * Decreases the player health by 1.
     */
    private void gotWounded(){
        this.health -= 1;
    }
    
    /**
     * Increases the consecutive hits counter.
     */
    private void incConsecutiveHits(){
        this.consecutiveHits++;
    }
}


