package irrgarten;

import java.util.ArrayList;

public class Labyrinth {
    public static final char BLOCK_CHAR = 'X';
    public static final char EMPTY_CHAR = '-';
    public static final char MONSTER_CHAR = 'M';
    public static final char COMBAT_CHAR= 'C';
    public static final char EXIT_CHAR = 'E';
    public static final int ROW = 0;
    public static final int COL = 1;

    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    
    // Matrices
    private Monster[][] monsters;
    private Player[][] players;
    private char labyrinth[][];

    // Constructor provisional porque no tengo ni idea
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol){
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        this.nRows = nRows;
        this.nCols = nCols;
        monsters = new Monster[nRows][nCols];
        players = new Player[nRows][nCols];
        labyrinth =  new char[nRows][nCols];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++){
                // Iniciar todas las referencias a null para evitar valores basura
                // y poner el laberinto a casillas vacías
            this.monsters[i][j] = null;
            this.players[i][j] = null;
            this.labyrinth[i][j] = Labyrinth.EMPTY_CHAR;
            }
        }

        this.labyrinth[exitRow][exitCol] = Labyrinth.EXIT_CHAR;
    }


    public boolean haveAWinner(){
	return (this.players[exitRow][exitCol] != null);
    }

    public String toString(){
        String labyrinth_string = "";
        for (int i = 0; i < nRows; i ++){
            labyrinth_string += "|";
            for (int j = 0; j < nCols; j++){
                labyrinth_string+=labyrinth[i][j] + "|";
            }
            labyrinth_string += "\n";
        }
        return labyrinth_string;
    }

    public void addMonster(int row, int col, Monster monster){
	if (posOK(row, col) && emptyPos(row,col)) {
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

    private boolean posOK(int row, int col){
	boolean validPos = (0 <= row) && (row < this.nRows) && (0 <= col) && (col < this.nCols);
	return validPos;
    }

    private boolean monsterPos(int row, int col){
	return (this.labyrinth[row][col] == MONSTER_CHAR);
    }

    private boolean exitPos(int row, int col){
	return (this.labyrinth[row][col] == EXIT_CHAR);
    }

    private boolean combatPos(int row, int col){
        return (this.labyrinth[row][col] == COMBAT_CHAR);
    }

    private boolean canStepOn(int row, int col){
        boolean validPos = posOK(row, col);
        boolean empty = emptyPos(row, col);
        boolean monsterPos = monsterPos(row, col);
        boolean exit = exitPos(row, col);
        return (validPos || empty || monsterPos || exit);
    }

    private void updateOldPos(int row, int col){
	if (posOK(row, col)) {
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

	while (!posOK(rRow, rCol) || !emptyPos(rRow, rCol)) {
	    rRow = Dice.randomPos(this.nRows);
	    rCol = Dice.randomPos(this.nCols);
	}

	rPos[ROW] = rRow;
	rPos[COL] = rCol;
	return rPos;
    }

    private int[] dir2pos(int row, int col, Directions direction) {
        int [] pos = {row,col};
        switch (direction){
            case LEFT: 
                pos[COL]--;  break;
            case RIGHT:
                pos[COL]++;  break;
            case UP:
                pos[ROW]--;  break;
            case DOWN:
                pos[ROW]++;  break; 
        }
        return pos;
    }
}
