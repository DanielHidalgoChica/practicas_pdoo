package irrgarten;
import java.util.ArrayList;

/**
 * Class for a fuzzy player. A fuzzy player is a player that makes decisions
 * based on a fuzzy logic system.
 */
public class FuzzyPlayer extends Player {
    
    /**
     * Constructor for the fuzzy player. Initializes the fuzzy player with the
     * number, intelligence, and strength of the player.
     * @param other Player to copy
     */
    public FuzzyPlayer(Player other){
        super(other.getNumber(), other.getIntelligence(), other.getStrength());
        this.setPos(other.getRow(), other.getCol());
    }
    
    /**
     * Moves the player in the given direction. The player chooses the direction
     * based on the fuzzy logic system.
     * @param direction The direction to move the player.
     * @param validMoves The valid moves for the player.
     * @return The direction the player moves.
     */
    @Override
    public Directions move(Directions direction, 
                            ArrayList<Directions> validMoves){
        Directions chosenDirection = this.move(direction, validMoves);
        Directions resultingDirection = 
            Dice.nextStep(chosenDirection, validMoves, this.getIntelligence());
        return resultingDirection;
    }
    
    /**
     * Attacks the opponent. The player chooses the with a dice and the sum of
     * weapons the player has.
     * @return The attack value of the player.
     */
    @Override
    public float attack(){
        return Dice.intensity(this.getStrength()) + this.sumWeapons();
    }
    
    /**
     * Defends the player. The player chooses the defense value with a dice and
     * the sum of shields the player has.
     * @return The defense value of the player.
     */
    @Override
    protected float defensiveEnergy(){
        return Dice.intensity(this.getIntelligence()) + this.sumShields();
    }
    
    /**
     * Returns the string representation of the fuzzy player.
     * @return The string representation of the fuzzy player.
     */
    @Override
    public String toString(){
        return "Fuzzy " + super.toString();
    }
    
    
}
