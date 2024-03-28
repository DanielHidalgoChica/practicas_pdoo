package irrgarten;

/**
 * Represents a monster in the game
 * @author Daniel Hidalgo Chica
 */
public class Monster {
    private static int INITIAL_HEALTH = 5;
    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    
    /**
      * Paramtrized constructor
      * @param name Name of the mosnter
      * @param intelligence Intelligence of the mosnter
      * @param strength Strength of the monster 
      * @param healt Health of the monster 
      * @param row Row positioning fo the monster
      * @param col Column positioning fo the monster
    */

    Monster(String name, float intelligence, float strength, float health, int row, int col) {
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = health;
        this.row = row;
        this.col = col; 
    }

    boolean dead() {
        return (health <= 0);
    }

    float attack(){
        return Dice.intensity(this.strength);
    }
    
      
}
