
package irrgarten;

/**
 * This class allow to store a representation of the global state of the game.
 * @author luisvaldivieso
 */
public class GameState {

    private String labyrinth;
    private String players;
    private String monsters;
    private int currentPlayer;
    private boolean winner;
    private String log;
    
    /**
     * Paremeterized constructor
     * @param labyrinth
     * @param players
     * @param monsters
     * @param currentPlayer index of the current player
     * @param winner
     * @param log register file of the game
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
    
    // GETTERS
    public String getLabyrinth(){
        return this.labyrinth;
    }
    
    public String getPlayers(){
        return this.players;
    }
    
    public String getMonsters(){
        return this.monsters;
    }
    
    public int getCurrentPlayer(){
        return this.currentPlayer;
    }
    
    public boolean getWinner(){
        return this.winner;
    }
    
    public String getLog(){
        return this.log;
    }
    
}
