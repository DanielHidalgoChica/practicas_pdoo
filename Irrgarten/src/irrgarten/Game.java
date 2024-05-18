package irrgarten;
import java.util.ArrayList;

/**
 * The Game class represents a game of Irrgarten.
 * It manages the players, monsters, labyrinth, and game state.
 */
public class Game {
    /**
     * Max rounds of the game
     */
    public static final int MAX_ROUNDS = 10;
    /**
     * The index of the current player
     */
    private int currentPlayerIndex;
    /**
     * Log of the game
     */
    private String log;

    /**
     * The player who is playing at the moment
     */
    private Player currentPlayer;
    
    /**
     * Labyrinth of the game
     */
    private Labyrinth labyrinth;
    /**
     * Monsters in the game at the moment
     */
    private ArrayList<Monster> monsters;
    
    /**
     * Players in the game at the moment
     */
    private ArrayList<Player> players;

    /**
     * Constructs a new Game object with the specified number of players.
     * @param nplayers The number of players in the game.
     */
    public Game(int nplayers){
        this.log = "";
        this.players = new ArrayList<Player>(nplayers);
        for (int i = 0; i < nplayers; i++){
            Player newPlayer = new Player((char)(i+'0'), 
                            Dice.randomIntelligence(), Dice.randomStrength());
            this.players.add(newPlayer);
        }
        this.currentPlayerIndex = Dice.whoStarts(nplayers);
        this.currentPlayer = this.players.get(currentPlayerIndex);
        this.monsters = new ArrayList<Monster> (0);
        this.configureLabyrinth();
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
        boolean dead = this.currentPlayer.dead();

        if(!dead) {
            Directions direction = this.actualDirection(preferredDirection);
            if(direction!=preferredDirection)
                this.logPlayerNoOrders();
            Monster monster = this.labyrinth.putPlayer(direction, this.currentPlayer);
            
            if(monster==null) // There is no monster
                this.logNoMonster();
            else{
                GameCharacter winner = this.combat(monster);
                this.manageReward(winner);
            }
        }
        else{
            this.manageResurrection();
        }
        
        boolean endGame = this.finished();
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
        String labyrinth_s = this.labyrinth.toString();
        String players_s = this.players.toString();
        String monsters_s = this.monsters.toString();
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
        
        this.labyrinth = new Labyrinth(nRows,nCols,exitRow, exitCol);
        this.labyrinth.addBlock(Orientation.HORIZONTAL, 0, 0, 2);

        Monster goblin = new Monster ("Goblin", Dice.randomIntelligence(), Dice.randomStrength());
        this.labyrinth.addMonster(2,2,goblin);
        this.monsters.add(goblin);
        
        Monster piglin = new Monster ("Piglin", Dice.randomIntelligence(), Dice.randomStrength());
        this.labyrinth.addMonster(4, 4, piglin);
        this.monsters.add(piglin);
        
        Monster zombie = new Monster ("Zombie", Dice.randomIntelligence(), Dice.randomStrength());
        this.labyrinth.addMonster(4, 0, zombie);
        this.monsters.add(zombie);
        
        Monster skeleton = new Monster ("Skeleton", Dice.randomIntelligence(), Dice.randomStrength());
        this.labyrinth.addMonster(0, 4, skeleton);
        this.monsters.add(skeleton);
        
        this.labyrinth.spreadPlayers(this.players);
    }

    /**
     * Advances the game to the next player.
     */
    private void nextPlayer(){
        int nPlayers = this.players.size();
        if (this.currentPlayerIndex == nPlayers-1){
            this.currentPlayerIndex = 0;
        } else {
            this.currentPlayerIndex++;
        }

        this.currentPlayer = this.players.get(this.currentPlayerIndex);
    }

    /**
     * Returns the actual direction of the player.
     * If the preferred direction is not valid, it returns the first valid direction.
     * @param preferredDirection The preferred direction of the player.
     * @return The actual direction of the player.
     */
    private Directions actualDirection(Directions preferredDirection) {
        int currentRow = this.currentPlayer.getRow();
        int currentCol = this.currentPlayer.getCol();
        ArrayList<Directions> validMoves = 
                            this.labyrinth.validMoves(currentRow, currentCol);
        Directions output = 
                        this.currentPlayer.move(preferredDirection, validMoves);
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
        float playerAttack = this.currentPlayer.attack();
        boolean lose = monster.defend(playerAttack);
        float monsterAttack;
        
        while(!lose && (rounds < Game.MAX_ROUNDS)){
            winner = GameCharacter.MONSTER;
            rounds++;
            monsterAttack = monster.attack();
            lose = this.currentPlayer.defend(monsterAttack);
            if(!lose){
                playerAttack = this.currentPlayer.attack();
                winner = GameCharacter.PLAYER;
                lose = monster.defend(playerAttack); 
            }
        }
        
        this.logRounds(rounds, Game.MAX_ROUNDS);
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
            this.currentPlayer.receiveReward();
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
            this.currentPlayer.resurrect();
            this.currentPlayer = new FuzzyPlayer(this.currentPlayer);
            this.players.set(this.currentPlayerIndex, this.currentPlayer);
            this.labyrinth.updatePlayer(currentPlayer);
            this.logResurrected();
        }
        else
            this.logPlayerSkipTurn();
    }

    /**
     * Logs the player's victory.
     */
    private void logPlayerWon(){
        this.log += "Player number " + this.currentPlayer.getNumber() + 
                    " has won the combat. \n";
    }

    /**
     * Logs the monster's victory.
     */
    private void logMonsterWon(){
        this.log += "The monster has won the combat\n";
    }
    
    /**
     * Logs the resurrection of the player.
     */
    private void logResurrected(){
        this.log += "The player" + this.currentPlayer.getNumber() + 
                    " has resurrected.\n";
    }
    
    /**
     * Logs the player's turn skip.
     */
    private void logPlayerSkipTurn(){
        this.log += "Player number " + this.currentPlayer.getNumber() + 
                    " has lost the turn because he was dead. \n";
    }
    
    /**
     * Logs the player's turn skip.
     */
    private void logPlayerNoOrders(){
        this.log += "Player number " + this.currentPlayer.getNumber() + 
                    " moved somewhere else.\n"; 
    }
    
    /**
     * Logs the player's turn skip.
     */
    private void logNoMonster(){
        this.log += "Player number " + this.currentPlayer.getNumber() + 
                    " couldn't move or moved to an empty box.\n";
    }
    
    /**
     * Logs the number of rounds passed.
     */
    private void logRounds(int rounds, int max){
        this.log += "Rounds passed: " + rounds + " / " + max + "\n"; 
    }
}

