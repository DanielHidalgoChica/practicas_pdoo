package irrgarten;
import java.util.ArrayList;

public class Game {
    public static int MAX_ROUNDS = 10;
    private int currentPlayerIndex;
    private String log;

    private Player currentPlayer;
    private Labyrinth labyrinth;
    private ArrayList<Monster> monsters;
    private ArrayList<Player> players;

    // Parametrized
    public Game(int nplayers){
        log = "";
        char number;
        // Number of rows and columns
        int nRows = 5, nCols = 5;
        // int exitRow = Dice.randomPos(nRows), exitCol = Dice.randomPos(nCols);
        int exitRow = 1, exitCol = 0;
        players = new ArrayList<Player>(0);
        for (int i = 0; i < nplayers; i++){
            players.add(new Player((char)(i+'0'), Dice.randomIntelligence(), Dice.randomStrength()));
        }
        currentPlayerIndex = Dice.whoStarts(nplayers);
        currentPlayer = players.get(currentPlayerIndex);
        monsters = new ArrayList<Monster> (0);
        labyrinth = new Labyrinth(nRows,nCols,exitRow, exitCol);
        configureLabyrinth();
        
    }

    public boolean finished(){
	return this.labyrinth.haveAWinner();
    }

    public boolean nextStep(Directions preferredDirection) {
        throw new UnsupportedOperationException();
    }

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

