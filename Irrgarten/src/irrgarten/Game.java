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
    public Game(ArrayList<Player> players, ArrayList<Monster> monsters) {
	this.players = players;
	this.monsters = monsters;
	// Escojo aleatoriamente un jugador para empezar
	this.currentPlayerIndex = Dice.whoStarts(players.size());
	this.currentPlayer = this.players.get(this.currentPlayerIndex);
	
	this.log = "";
	this.labyrinth = new Labyrinth();
	this.configureLabyrinth();
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
	// Luego lo escribimos para configurar 
	// un laberinto concreto
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
	this.log += "EL JUGADOR HA GANADO EL COMBATE\n";
    }

    private void logMonsterWon(){
	this.log += "EL MONSTRUO HA GANADO EL COMBATE\n";
    }

    private void logResurrected() {
	this.log += "EL JUGADOR HA RESUCITADO\n";
    }

    private void logPlayerSkipTurn() {
	this.log += "EL JUGADOR HA PERDIDO EL TURNO POR ESTAR MUERTO\n";
    }

    private void logPlayerNoOrders() {
	this.log += "EL JUGADOR NO HA PODIDO SEGUIR LAS INSTRUCCIONES QUE SE LE HAN DADO\n";
    }

    private void logNoMonster() {
	this.log += "EL JUGADOR SE HA MOVIDO A UNA CELDA VACÍA O NO LE HA SIDO POSIBLE MOVERSE\n";
    }

    private void logRounds(int rounds, int max){
	this.log += "SE HAN PRODUCIDO " + Integer.toString(rounds) + " RONDAS DE UN TOTAL DE " + Integer.toString(max) + "\n";
    }


}

