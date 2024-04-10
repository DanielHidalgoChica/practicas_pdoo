package irrgarten;
import java.util.ArrayList;

/**
 * The Game class represents a game of Irrgarten.
 * It manages the players, monsters, labyrinth, and game state.
 */
public class Game {
    public static final int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;

    private Player currentPlayer;
    private Labyrinth labyrinth;
    private ArrayList<Monster> monsters;
    private ArrayList<Player> players;

    /**
     * Constructs a new Game object with the specified number of players.
     *
     * @param nplayers The number of players in the game.
     */
    public Game(int nplayers){
        log = "";
        players = new ArrayList<Player>(0);
        for (int i = 0; i < nplayers; i++){
            players.add(new Player((char)(i+'0'), Dice.randomIntelligence(), Dice.randomStrength()));
        }
        currentPlayerIndex = Dice.whoStarts(nplayers);
        currentPlayer = players.get(currentPlayerIndex);
        monsters = new ArrayList<Monster> (0);
        configureLabyrinth();
    }

    /**
     * Checks if the game has finished.
     *
     * @return true if the game has finished, false otherwise.
     */
    public boolean finished(){
        return this.labyrinth.haveAWinner();
    }

    /**
     * Makes the next step in the game with the specified preferred direction.
     *
     * @param preferredDirection The preferred direction to move in.
     * @return true if the step was successful, false otherwise.
     */
    public boolean nextStep(Directions preferredDirection) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the current game state.
     *
     * @return The current game state.
     */
    public GameState getGameState() {
        String labyrinth_s = labyrinth.toString();
        String players_s = players.toString();
        String monsters_s = monsters.toString();
        int currentP = this.currentPlayerIndex;
        boolean winner = this.finished();

        GameState state = new GameState(labyrinth_s, players_s, monsters_s, currentP, winner, this.log);

        return state;
    }

    private void configureLabyrinth(){
        int nRows = 5, nCols = 5;
        int exitRow = 1, exitCol = 1;
        labyrinth = new Labyrinth(nRows,nCols,exitRow, exitCol);
        Monster goblin = new Monster ("Goblin", Dice.randomIntelligence(), Dice.randomStrength());
        labyrinth.addMonster(2,2,goblin);
        monsters.add(goblin);
    }

    private void nextPlayer(){
        // Itero sobre el array de jugadores 
        // y actualizo la variable de currentPlayerIndex
        int nPlayers = players.size();
        if (currentPlayerIndex == nPlayers-1){
            currentPlayerIndex = 0;
        } else {
            currentPlayerIndex++;
        }

        currentPlayer = this.players.get(currentPlayerIndex);
    }

    private Directions actualDirection(Directions preferredDirection) {
        throw new UnsupportedOperationException();
    }

    private GameCharacter combat(Monster monster){
        // No explicado en la práctica
        return GameCharacter.MONSTER;
    }

    private void manageReward(GameCharacter winner) {
        throw new UnsupportedOperationException();
    }

    private void manageResurrection() {
        throw new UnsupportedOperationException();
    }

    private void logPlayerWon(){
        log += "Player number " + currentPlayer.getNumber() + "has won. \n";
    }

    private void logMonsterWon(){
        log += "The monster has won\n";
    }
    
    private void logResurrected(){
        log += "The player" + currentPlayer.getNumber() + " has resurrected.\n";
    }
    
    private void logPlayerSkipTurn(){
        log += "Player number " + currentPlayer.getNumber() + " has lost the turn"
          + " because he was dead. \n";
    }
    
    private void logPlayerNoOrders(){
        log += "Player number " + currentPlayer.getNumber() + " couldn't follow the instructions from the human player.\n"; 
    }
    
    private void logNoMonster(){
        log += "Player number " + currentPlayer.getNumber() + " couldn't move or moved to an empty box.\n";
    }
    
    private void logRounds(int rounds, int max){
        log += "Rounds passed: " + rounds + " / " + max + "\n"; 
    }
}

