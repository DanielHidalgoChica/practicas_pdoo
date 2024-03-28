package irrgarten;

/**
 * This class take all the decisions of the game that depend on luck. It will
 * produce a series of random values bounded with some given parameters.
 * @author luisvaldivieso
 */
public class Dice {
    /**
     * Max number of uses of weapons and shields
     */
    private static final int MAX_USES = 5;
    /**
     * Max value for the player/monster intelligence
     */
    private static final float MAX_INTELLIGENCE = 10.0f;
    
    /**
     * Max value for strength of players and monsters
     */
    private static final float MAX_STRENGTH = 10.0f;
    
    /**
     * Probability of resurrection for a player
     */
    private static final float RESURRECT_PROB = 0.3f;
    /**
     * Max number of weapons received when winning a combat
     */
    private static final int WEAPONS_REWARD = 2;
    /**
     * Max number of shields received when winning a combat
     */
    private static final int SHIELDS_REWARD = 3;
    /**
     * Max number of health points received when winning a combat
     */
    private static final int HEALTH_REWARD = 5;
    /**
     * Max attack power of weapons
     */
    private static final int MAX_ATTACK = 3;
    /**
     * Max defensive power of the shields
     */
    private static final int MAX_SHIELD = 2;
    
    /**
     * Random object used to generate a stream of pseudorandom numbers
     */
    private static java.util.Random generator = new java.util.Random();
    
    /**
     * Return a random column or row
     * @param max max value of the columns or rows
     * @return  A random number representing the row or the column, 
     * between [0,max[
     */
    public static int randomPos(int max){
        return generator.nextInt(max);
    }
    /**
     * Returns the index of the player who will start the game
     * @param nPlayers number of current players in the game
     * @return Index of the player who will start the game (between [0,nPlayers[
     */
    public static int whoStarts (int nPlayers){
        return generator.nextInt(nPlayers);
    }
    
    /**
     * Gives a random value of intelligence
     * @return A value of intelligence between [0,MAX_INTELLIGENCE[
     */
    public static float randomIntelligence(){
        return generator.nextFloat()*MAX_INTELLIGENCE;
    }
    
    /**
     * Gives a random value of strength 
     * @return A value of strength between [0,MAX_INTELLIGENCE[
     */
    public static float randomStrength(){
        return generator.nextFloat()*MAX_STRENGTH;
    }
    
    /**
     * Determine if a player must be revived based on @param RESURRECT_PROB
     * @return 1(true) if the player must be revived or 0 if not
     */
    public static boolean resurrectPlayer(){
        return generator.nextFloat()<RESURRECT_PROB;
    }
    
    /**
     * Decide how many weapons should the player earn when winning a combat
     * @return Number of weapons given to the player when winning a combat. It
     * is a number between [0,WEAPONS_REWARD]
     */
    public static int weaponsReward(){
        return generator.nextInt(WEAPONS_REWARD+1);
    }
    
    /**
     * Decide how many shields should the player earn when winning a combat
     * @return Number of shields given to the player when winning a combat. It
     * is a number between [0,SHIELDS_REWARD]
     */
    public static int shieldsReward(){
        return generator.nextInt(SHIELDS_REWARD+1);
    }
    /**
     * Decide how many health points will the player earn when winning a combat
     * @return Number of health points given to the player when winning a combat. It
     * is a number between [0,HEALTH_REWARD]
     */
    public static int healthReward(){
        return generator.nextInt(HEALTH_REWARD+1);
    }
    
    /**
     * Return a random weapon power
     * @return A float number Weapon power between [0,MAX_ATTACK[
     */
    public static float weaponPower(){
        return generator.nextFloat()*MAX_ATTACK;
    }
    
    /**
     * Return a random shield power
     * @return A float number Shield power between [0,MAX_SHIELD[
     */
    public static float shieldPower(){
        return generator.nextFloat()*MAX_SHIELD;
    }
    
    /**
     * Generate a random number of uses left
     * @return Number of uses left for a shield or weapon. It is an int number
     * between [0,MAX_USES]
     */
    public static int usesLeft(){
        return generator.nextInt(MAX_USES+1);
    }
    
    /**
     * Generate a random number of competition applied
     * @param competence Max number of competence applied (exclusive)
     * @return A floating point number that represents applied intensity by
     * a weapon between [0,competence[ 
     */
    public static float intensity(float competence){
        return generator.nextFloat()*competence;
    }
    
    /**
     * Determine if a weapon must be discarded. It is easier for a weapon to 
     * be discarded as the @param usesLeft parameter aproaches the constant
     * MAX_USES, and if the uses 
     * @param usesLeft remaining uses of the element
     * @return true if a weapon must be discarded, false otherwise
     */
    public static boolean discardElement(int usesLeft){
        
        if (usesLeft ==MAX_USES) return false;
        else {
            float discardProbability = 1 - (usesLeft/MAX_USES);
            return (generator.nextFloat()<= discardProbability);
        }  
    }
    
    
    
}
