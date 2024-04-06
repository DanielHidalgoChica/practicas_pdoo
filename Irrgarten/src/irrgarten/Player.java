package irrgarten;
import java.util.ArrayList;

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

    /**Parametrized constructor
      *      ...
    */

    public Player(char number, float intelligence, float strength, int row, int col) {
        this.number = number;
        this.name = "Player # " + Integer.toString(this.number);
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;
        this.setPos(0,0);
        weapons = new ArrayList<Weapon>(0);
        shields = new ArrayList<Shield> (0);
        // Put the rest of the atributes to default
        resurrect(); 
    }

    public int getRow() {
	return this.row;
    }

    public int getCol() {
	    return this.col;
    }

    public char getNumber(){
	    return this.number;
    }

    public void resurrect() {
        weapons.clear();
        shields.clear();
        health = INITIAL_HEALTH;
        resetHits();
    }

    public void setPos(int row, int col){
	this.row = row;
	this.col = col;
    }

    public boolean dead(){
	return (this.health <= 0);
    }

    private float sumWeapons(){
        float sum = 0; 
        for (Weapon aWeapon : weapons){
            sum += aWeapon.attack();
        }
        return sum;
    }

    public float attack(){
	    return this.strength+this.sumWeapons();
    }


    public boolean defend(float receivedAttack){
	    throw new UnsupportedOperationException();
    }

    public String toString(){
        String ret = "\nPlayer State"
                    + "\nName:" + this.name
                    + "\nIntelligence:" + Float.toString(intelligence)
                    + "\nStrength:"+ Float.toString(strength)
                    + "\nHealth:"+  Float.toString(health)
                    + "\nPosition: (" + Integer.toString(row) + "," + Integer.toString(col) + ")\n"
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
        return ret;
    }
    
    private Weapon newWeapon(){
        float rPower = Dice.weaponPower();
        int rUses = Dice.usesLeft();
        Weapon newWeapon = new Weapon(rPower, rUses);
        return newWeapon;
    }

    private Shield newShield(){
        float rProtection = Dice.shieldPower();
        int rUses = Dice.usesLeft();
        Shield newShield = new Shield(rProtection, rUses);
        return newShield;
    }

    private float sumShields(){
        float sum = 0;
        for (Shield aShield : this.shields){
            sum += aShield.protect();
        }
        return sum;
    }
    private float defensiveEnergy(){
	    return this.sumShields() + this.intelligence;
    }

    private void resetHits(){
	    this.consecutiveHits = 0;
    }

    private void incConsecutiveHits(){
	    this.consecutiveHits++;
    }

    public Directions move(Directions direction, ArrayList<Directions> validMoves) {
	    throw new UnsupportedOperationException();
    }

    public void receiveReward(){	
	    throw new UnsupportedOperationException();
    }

    private void receiveWeapon(Weapon w){
	    throw new UnsupportedOperationException();
    }

    private void receiveShield(Shield s){
	    throw new UnsupportedOperationException();
    }

    private void manageHit(float receivedAttack){
	    throw new UnsupportedOperationException();
    }
        
    private void gotWounded(){
        this.health-=1;
    }
    
    private void incConsecutiveHits(){
        this.consecutiveHits++;
    }
}


