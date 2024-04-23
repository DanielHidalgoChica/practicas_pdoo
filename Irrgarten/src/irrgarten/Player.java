package irrgarten;
import java.util.ArrayList;

/**
 * The Player class represents a player in the game.
 */
public class Player {
    // Constants

    /*
    * The maximum number of weapons a player can have.
    */
    public static final int MAX_WEAPONS = 2;

    /**
     * The maximum number of shields a player can have.
     */
    public static final int MAX_SHIELDS = 3;

    /**
     * The initial health of the player.
     */
    public static final int INITIAL_HEALTH = 10;

    /**
     * The number of consecutive hits a player can receive before losing.
     */
    public static final int HITS2LOSE = 3;
    
    // Attributes
    /**
     * The player's name.
     */
    private String name;

    /**
     * The player number.
     */
    private char number;

    /**
     * The player's intelligence level.
     */
    private float intelligence;

    /**
     * The player's strength level.
     */
    private float strength;

    /**
     * The player's health.
     */
    private float health;

    /**
     * The player's position.
     */
    private int row;
    private int col;

    /**
     * The number of consecutive hits the player has received.
     */
    private int consecutiveHits;

    /**
     * The player's weapons and shields.
     */
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
        int size = validMoves.size();
        boolean contained = validMoves.contains(direction);
        if(size > 0  && !contained){
            Directions firstElement = validMoves.get(0);
            return firstElement;
        }
        else
            return direction;
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
        boolean lose = manageHit(receivedAttack);
        return lose;
    }
    
    /**
     * Receives a reward for defeating a monster.
     * The reward consists of new weapons, shields, and health.
     * The number of weapons and shields and the health reward received is determined by the dice.
     * 
     */
    public void receiveReward(){	
        int wReward = Dice.weaponsReward();
        int sReward = Dice.shieldsReward();
        for (int i= 0; i < wReward; i++){
            Weapon wNew = this.newWeapon();
            this.receiveWeapon(wNew);
        }
        
        for (int i= 0; i < sReward; i++){
            Shield sNew = this.newShield();
            this.receiveShield(sNew);
        }
        
        int extraHealth = Dice.healthReward();
        this.health+=extraHealth;
    }
    
    /**
     * Returns a string representation of the player.
     * @return The string representation of the player.
     */
    public String toString(){
        String ret = "P[" + this.name
                    + ", Intelligence: " + Float.toString(intelligence)
                    + ", Strength: "+ Float.toString(strength)
                    + ", Health: "+  Float.toString(health)
                    + ", Pos:(" + Integer.toString(row) + "," + Integer.toString(col) + ")"
                    + ", ConsecHits: "+  Integer.toString(this.consecutiveHits)
                    + "]\n\tWeapons: ";
	    // Muestro las armas
	    for (Weapon aWeapon : this.weapons) {
	        ret +=  "\n\t" + aWeapon.toString();
	    }
	    ret += "\n\tShields: ";
	    for (Shield aShield : this.shields) {
	        ret += "\n\t" + aShield.toString();
	    }
            ret += "\n";
        return ret;
    }
    
    /**
     * Receives a weapon.
     * @param w The weapon to receive.
     */
    private void receiveWeapon(Weapon w){
        
        this.weapons.removeIf(wi -> wi.discard());
        int size = this.weapons.size();
        if(size < MAX_WEAPONS)
            this.weapons.add(w);
    }

    /**
     * Receives a shield.
     * @param s The shield to receive.
     */
    private void receiveShield(Shield s){
        
        this.shields.removeIf(si -> si.discard());
        int size = this.shields.size();
        if(size < Player.MAX_SHIELDS)
            this.shields.add(s);

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

    /**
     * Manages the hit received by the player.
     * @param receivedAttack The attack power received.
     * @return true if the player lost, false otherwise.
     */
    private boolean manageHit(float receivedAttack){
        float defense = this.defensiveEnergy();
        if(defense < receivedAttack){
            this.gotWounded();
            this.incConsecutiveHits();
        }
        else
            this.resetHits();
        
        boolean lose;
        if( (this.consecutiveHits == Player.HITS2LOSE) || dead()){
            this.resetHits();
            lose = true;
        }
        else
            lose = false;
        
        return lose;
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


