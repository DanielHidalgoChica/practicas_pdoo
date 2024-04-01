package irrgarten;

import java.util.ArrayList;

public class Labyrinth {
    public static char BLOCK_CHAR = 'X';
    public static char EMPTY_CHAR = '-';
    public static char MONSTER_CHAR = 'M';
    public static char COMBAT_CHAR= 'C';
    public static char EXIT_CHAR = 'E';
    public static int ROW = 0;
    public static int COL = 1;

    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    
    // Matrices
    private Monster[][] monsters = new Monster[nRows][nCols];
    private Player[][] players = new Player[nRows][nCols];
    private char labyrinth[][] =  new char[nRows][nCols];

    // Constructor provisional porque no tengo ni idea
    public Labyrinth(int numRows, int numCols, int exCol, int exRow) {
	this.exitRow = exRow;
	this.exitCol = exCol;
	this.nRows = numRows;
	this.nCols = numCols;
	
	for (int i = 0; i < nRows; i++) {
	    for (int j = 0; j < nCols; j++){
		// Todas las referencias nulas
		// y todas las casillas vacías, veré
		// si con los otros métodos puedo hacer algo
		// que tenga sentido
		this.monsters[i][j] = null;
		this.players[i][j] = null;
		this.labyrinth[i][j] = Labyrinth.EMPTY_CHAR;
	    }
	}


	this.labyrinth[exRow][exCol] = Labyrinth.EXIT_CHAR;
    }

    public Labyrinth() {
	this (1,1,0,0);
    }

    public boolean haveAWinner(){
	return (this.players[exitRow][exitCol] != null);
    }

    public String toString(){
	// Para más tarde
	return "HAY QUE HACER ESTE TO_STRING DE LA CLAS Labyrinth";
    }

    public void addMonster(int row, int col, Monster monster){
	if (PosOK(row, col) && emptyPos(row,col)) {
	    // Guardo la referencia del mosntruo
	    // en los atributos correspondientes
	    this.monsters[row][col] = monster;
	    this.labyrinth[row][col] = Labyrinth.MONSTER_CHAR;
	    
	    // Le indico al monstruo su posición
	    monster.setPos(row, col);
	}
    }

    public void spreadPlayers(ArrayList<Player> players){
        throw new UnsupportedOperationException();
    }

    public Monster putPlayer(Directions direction, Player player){
        throw new UnsupportedOperationException();
    }

    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {
        throw new UnsupportedOperationException();
    }

    public ArrayList<Directions> validMoves(int row, int col) {
        throw new UnsupportedOperationException();
    }

    private Monster putPlayer2D (){
        throw new UnsupportedOperationException();
    }

    private boolean emptyPos(int row, int col){
	return (this.labyrinth[row][col] == Labyrinth.EMPTY_CHAR);
    }

    private boolean PosOK(int row, int col){
	boolean validPos = (0 <= row) && (row < this.nRows) && (0 <= col) && (col < this.nCols);
	return validPos;
    }

    private boolean monsterPos(int row, int col){
	return (this.labyrinth[row][col] == Labyrinth.MONSTER_CHAR);
    }

    private boolean exitPos(int row, int col){
	return (this.labyrinth[row][col] == Labyrinth.EXIT_CHAR);
    }

    private boolean combatPos(int row, int col){
        return (this.labyrinth[row][col] == Labyrinth.COMBAT_CHAR);
    }

    private boolean canStepOn(int row, int col){
	boolean validPos = PosOK(row, col);
	boolean empty = emptyPos(row, col);
	boolean monsterPos = monsterPos(row, col);
	boolean exit = exitPos(row, col);
        return (validPos || empty || monsterPos || exit);
    }

    private void updateOldPos(int row, int col){
	if (PosOK(row, col)) {
	    if (combatPos(row, col)) {
		this.labyrinth[row][col] = Labyrinth.MONSTER_CHAR;
	    } else {
		this.labyrinth[row][col] = Labyrinth.EMPTY_CHAR;
	    }
	}
    }

    private int[] randomEmptyPos(){
	int[] rPos = new int[2];
	int rRow = Dice.randomPos(this.nRows);
	int rCol = Dice.randomPos(this.nCols);

	while (!PosOK(rRow, rCol) || !emptyPos(rRow, rCol)) {
	    rRow = Dice.randomPos(this.nRows);
	    rCol = Dice.randomPos(this.nCols);
	}

	rPos[1] = rRow;
	rPos[2] = rCol;
	return rPos;
    }

    private int[] dir2pos(int row, int col, Directions direction) {
	int[] final_pos = new int[2]; 
	switch (direction){
	    case LEFT:
		final_pos[0] = row-1;
		final_pos[1] = col;
		break;
	    case RIGHT:
		final_pos[0] = row+1;
		final_pos[1] = col;
		break;
	    case UP:
		final_pos[0] = row;
		final_pos[1] = col-1; // Porque la casilla de arriba a la izquierda es la (0,0)
		break;
	    case DOWN:
		final_pos[0] = row;
		final_pos[1] = col+1; // Porque la casilla de arriba a la izquierda es la (0,0)
		break;
	}
	return final_pos;
    }
}
