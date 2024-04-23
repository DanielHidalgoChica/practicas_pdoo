package irrgarten;

/**
 * This class allow to store a representation of the global state of the game.
 * @author luisvaldivieso
 */
/**
 * Represents the state of the game.
 */
public class GameState {

    private String labyrinth;
    private String players;
    private String monsters;
    private int currentPlayer;
    private boolean winner;
    private String log;
    
    /**
     * Parameterized constructor.
     * 
     * @param labyrinth the labyrinth of the game
     * @param players the players in the game
     * @param monsters the monsters in the game
     * @param currentPlayer index of the current player
     * @param winner indicates if there is a winner in the game
     * @param log the register file of the game
     */
    GameState(String labyrinth, String players, String monsters, 
            int currentPlayer, boolean winner, String log){
        this.labyrinth=labyrinth;
        this.players=players;
        this.monsters=monsters;
        this.currentPlayer=currentPlayer;
        this.winner=winner;
        this.log=log;
    }
    
    /**
     * Retrieves the labyrinth of the game.
     * 
     * @return the labyrinth of the game
     */
    public String getLabyrinth(){
        return this.labyrinth;
    }
    
    /**
     * Retrieves the players in the game.
     * 
     * @return the players in the game
     */
    public String getPlayers(){
        return this.players;
    }
    
    /**
     * Retrieves the monsters in the game.
     * 
     * @return the monsters in the game
     */
    public String getMonsters(){
        return this.monsters;
    }
    
    /**
     * Retrieves the index of the current player.
     * 
     * @return the index of the current player
     */
    public int getCurrentPlayer(){
        return this.currentPlayer;
    }
    
    /**
     * Checks if there is a winner in the game.
     * 
     * @return true if there is a winner, false otherwise
     */
    public boolean getWinner(){
        return this.winner;
    }
    
    /**
     * Retrieves the register file of the game.
     * 
     * @return the register file of the game
     */
    public String getLog(){
        return this.log;
    }
    
}
