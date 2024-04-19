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
     * Advances the game one step.
     * The player moves in the preferred direction and fights the monster in the new position.
     * If the player dies, it may resurrect.
     * If the game has finished, it advances to the next player.
     * @param preferredDirection The preferred direction of the player.
     * @return true if the game has finished, false otherwise.
     */
    public boolean nextStep(Directions preferredDirection) {
        this.log = ""; // Clear the log of the game because of a new step
        boolean dead = currentPlayer.dead();
        if(!dead) {
            Directions direction = this.actualDirection(preferredDirection);
            if(direction!=preferredDirection)
                this.logPlayerNoOrders();
            Monster monster = labyrinth.putPlayer(direction, currentPlayer);
            
            if(monster==null) // There is no monster
                this.logNoMonster();
            else{
                GameCharacter winner = combat(monster);
                this.manageReward(winner);
            }
        }
        else{
            this.manageResurrection();
        }
        
        
        boolean endGame = finished();
        
        if(!endGame){
            this.nextPlayer();
        }
        
        return endGame;
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

    /**
     * Configures the labyrinth with the specified parameters.
     */
    private void configureLabyrinth(){
        int nRows = 5, nCols = 5;
        int exitRow = 1, exitCol = 1;
        labyrinth = new Labyrinth(nRows,nCols,exitRow, exitCol);
        
        labyrinth.addBlock(Orientation.HORIZONTAL, 0, 0, 2);
        labyrinth.addBlock(Orientation.VERTICAL, 0, 1, 2);

        Monster goblin = new Monster ("Goblin", Dice.randomIntelligence(), Dice.randomStrength());
        labyrinth.addMonster(2,2,goblin);
        monsters.add(goblin);
        Monster piglin = new Monster ("Piglin", Dice.randomIntelligence(), Dice.randomStrength());
        labyrinth.addMonster(4, 4, piglin);
        monsters.add(piglin);
        Monster zombie = new Monster ("Zombie", Dice.randomIntelligence(), Dice.randomStrength());
        labyrinth.addMonster(4, 0, zombie);
        monsters.add(zombie);
        Monster skeleton = new Monster ("Skeleton", Dice.randomIntelligence(), Dice.randomStrength());
        labyrinth.addMonster(0, 4, skeleton);
        monsters.add(skeleton);
        
        labyrinth.spreadPlayers(players);
    }

    /**
     * Advances the game to the next player.
     */
    private void nextPlayer(){
        int nPlayers = players.size();
        if (currentPlayerIndex == nPlayers-1){
            currentPlayerIndex = 0;
        } else {
            currentPlayerIndex++;
        }

        currentPlayer = this.players.get(currentPlayerIndex);
    }

    /**
     * Returns the actual direction of the player.
     * If the preferred direction is not valid, it returns the first valid direction.
     * @param preferredDirection The preferred direction of the player.
     * @return The actual direction of the player.
     */
    private Directions actualDirection(Directions preferredDirection) {
        int currentRow = currentPlayer.getRow();
        int currentCol = currentPlayer.getCol();
        ArrayList<Directions> validMoves = labyrinth.validMoves(currentRow, currentCol);
        Directions output = currentPlayer.move(preferredDirection, validMoves);
        return output;
    }

    /**
     * Simulates a combat between the player and the monster.
     * The combat ends when one of the characters dies or the maximum number of rounds is reached.
     * @param monster The monster to fight.
     * @return The winner of the combat.
     */
    private GameCharacter combat(Monster monster){
        int rounds = 0;
        GameCharacter winner = GameCharacter.PLAYER;
        float playerAttack = currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);
        float monsterAttack;
        while(!lose && (rounds< MAX_ROUNDS)){
            winner = GameCharacter.MONSTER;
            rounds++;
            monsterAttack = monster.attack();
            lose = currentPlayer.defend(monsterAttack);
            if(!lose){
                playerAttack = currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack);
                
            }
        }
        this.logRounds(rounds, MAX_ROUNDS);
        return winner;
    }

    /**
     * Manages the reward of the combat.
     * If the player wins, it receives a reward.
     * @param winner The winner of the combat.
     * @return The winner of the combat.
     */
    private void manageReward(GameCharacter winner) {
        if (winner == GameCharacter.PLAYER){
            currentPlayer.receiveReward();
            this.logPlayerWon();
        }
        else
            this.logMonsterWon();
    }

    /**
     * Manages the resurrection of the player.
     * If the player doesn't resurrect, it loses the turn.
     * @return The winner of the combat.
     */
    private void manageResurrection() {
        boolean resurrect = Dice.resurrectPlayer();
        if (resurrect){
            currentPlayer.resurrect();
            this.logResurrected();
            
        }
        else
            this.logPlayerSkipTurn();
    }

    /**
     * Logs the player's victory.
     */
    private void logPlayerWon(){
        log += "Player number " + currentPlayer.getNumber() + " has won. \n";
    }

    /**
     * Logs the monster's victory.
     */
    private void logMonsterWon(){
        log += "The monster has won\n";
    }
    
    /**
     * Logs the resurrection of the player.
     */
    private void logResurrected(){
        log += "The player" + currentPlayer.getNumber() + " has resurrected.\n";
    }
    
    /**
     * Logs the player's turn skip.
     */
    private void logPlayerSkipTurn(){
        log += "Player number " + currentPlayer.getNumber() + " has lost the turn"
          + " because he was dead. \n";
    }
    
    /**
     * Logs the player's turn skip.
     */
    private void logPlayerNoOrders(){
        log += "Player number " + currentPlayer.getNumber() + " couldn't follow the instructions from the human player.\n"; 
    }
    
    /**
     * Logs the player's turn skip.
     */
    private void logNoMonster(){
        log += "Player number " + currentPlayer.getNumber() + " couldn't move or moved to an empty box.\n";
    }
    
    /**
     * Logs the number of rounds passed.
     */
    private void logRounds(int rounds, int max){
        log += "Rounds passed: " + rounds + " / " + max + "\n"; 
    }
}

