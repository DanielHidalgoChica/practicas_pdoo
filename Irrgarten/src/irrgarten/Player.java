package irrgarten;
import java.util.ArrayList;

/**
 * The Player class represents a player in the game.
 */
public class Player extends LabyrinthCharacter {

    /*s
    
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
    
    /**
     * The player number.
     */
    private final char number;
    
    /**
     * The number of consecutive hits the player has received.
     */
    private int consecutiveHits;

    /**
     * The player's weapons and shields.
     */
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;

    private ShieldCardDeck shieldCardDeck;
    private WeaponCardDeck weaponCardDeck;
    /**
     * Parametrized constructor to create a Player object.
     * @param number The player number.
     * @param intelligence The intelligence level of the player.
     * @param strength The strength level of the player.
     */
    public Player(char number, float intelligence, float strength) {
        super("Player " + (char) number, intelligence, strength, (float) Player.INITIAL_HEALTH);
        this.number = number;
        this.weapons = new ArrayList<Weapon>(0);
        this.shields = new ArrayList<Shield> (0);
        this.shieldCardDeck = new ShieldCardDeck();
        this.weaponCardDeck = new WeaponCardDeck();
        this.resetHits();
    }

    public Player(Player other){
        super(other);
        this.number = other.number;
        this.weapons = other.weapons;
        this.shields = other.shields;
        this.shieldCardDeck = other.shieldCardDeck;
        this.weaponCardDeck = other.weaponCardDeck;
        this.consecutiveHits = other.consecutiveHits;
    }
    /**
     * Resurrects the player by resetting their attributes.
     */
    public void resurrect() {
        this.weapons.clear();
        this.shields.clear();
        this.setHealth(Player.INITIAL_HEALTH);
        this.resetHits();
    }
    
    /**
     * Gets the player number.
     * @return The player number.
     */
    public char getNumber(){
        return this.number;
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
    @Override
    public float attack(){
        return this.getStrength() + this.sumWeapons();
    }
    
    /**
     * Defends against an attack.
     * @param receivedAttack The attack power received.
     * @return true if the player successfully defended, false otherwise.
     */
    @Override
    public boolean defend(float receivedAttack){
        return this.manageHit(receivedAttack);
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
        float newHealth = this.getHealth() + extraHealth;
        this.setHealth(newHealth);
    }
    
    /**
     * Returns a string representation of the player.
     * @return The string representation of the player.
     */
    @Override
    public String toString(){
        String ret = "P[" + super.toString()
                    + ", ConsecHits: " + Integer.toString(this.consecutiveHits) 
                    + "]\n\tWeapons: ";
	    // Show the weapons
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
        if(size < Player.MAX_WEAPONS)
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
        return this.weaponCardDeck.nextCard();
    }

    /**
     * Creates a new shield.
     * @return The new shield.
     */
    private Shield newShield(){
        return this.shieldCardDeck.nextCard();
    }
    
    /**
     * Calculates the sum of the weapons' attack power.
     * @return The sum of the weapons' attack power.
     */
    protected float sumWeapons(){
        float sum = 0; 
        for (Weapon aWeapon : this.weapons){
            sum += aWeapon.attack();
        }
        return sum;
    }
    
    /**
     * Calculates the sum of the shields' protection.
     * @return The sum of the shields' protection.
     */
    protected float sumShields(){
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
    protected float defensiveEnergy(){
        return this.sumShields() + this.getIntelligence();
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
     * Increases the consecutive hits counter.
     */
    private void incConsecutiveHits(){
        this.consecutiveHits++;
    }
}


