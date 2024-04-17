package irrgarten;

import java.util.ArrayList;

/**
 * Represents a labyrinth where players and monsters can move around.
 */
public class Labyrinth {
    // Constants for characters used in the labyrinth
    public static final char BLOCK_CHAR = 'X';
    public static final char EMPTY_CHAR = '-';
    public static final char MONSTER_CHAR = 'M';
    public static final char COMBAT_CHAR= 'C';
    public static final char EXIT_CHAR = 'E';
    public static final int ROW = 0;
    public static final int COL = 1;

    // Labyrinth properties
    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;

    // Matrices to hold monsters, players, and labyrinth layout
    private Monster[][] monsters;
    private Player[][] players;
    private char labyrinth[][];

    /**
     * Constructor to initialize the labyrinth with given dimensions and exit position.
     * @param nRows Number of rows in the labyrinth.
     * @param nCols Number of columns in the labyrinth.
     * @param exitRow Row index of the exit position.
     * @param exitCol Column index of the exit position.
     */
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol){
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        this.nRows = nRows;
        this.nCols = nCols;
        
        // Initialize matrices
        monsters = new Monster[nRows][nCols];
        players = new Player[nRows][nCols];
        labyrinth =  new char[nRows][nCols];
        
        // Fill labyrinth with empty characters
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++){
                monsters[i][j] = null;
                players[i][j] = null;
                labyrinth[i][j] = Labyrinth.EMPTY_CHAR;
            }
        }

        // Set the exit position
        this.labyrinth[exitRow][exitCol] = Labyrinth.EXIT_CHAR;
    }

    /**
     * Spread players across the labyrinth.
     * @param players List of players to spread.
     */
    public void spreadPlayers(ArrayList<Player> players){
        for (int i = 0; i < players.size(); i++){
            Player p = players.get(i);
            int [] pos = randomEmptyPos();
            putPlayer2D(-1,-1, pos[ROW], pos[COL], p);
        }
    }

    /**
     * Check if a player has reached the exit position.
     * @return True if a player has reached the exit, false otherwise.
     */
    public boolean haveAWinner(){
        return (this.players[exitRow][exitCol] != null);
    }

    /**
     * Convert the labyrinth to a string representation.
     * @return String representation of the labyrinth.
     */
    public String toString(){
        String labyrinthString = "";
        for (int i = 0; i < nRows; i ++){
            labyrinthString += "|";
            for (int j = 0; j < nCols; j++){
                labyrinthString += labyrinth[i][j] + "|";
            }
            labyrinthString += "\n";
        }
        return labyrinthString;
    }


    /**
     * Adds a monster to the specified position in the labyrinth.
     * @param row The row index where the monster will be added.
     * @param col The column index where the monster will be added.
     * @param monster The monster to be added.
     */
    public void addMonster(int row, int col, Monster monster){
        if (posOK(row, col) && emptyPos(row,col)) {
            // Save a reference to the monster in the corresponding attributes
            this.monsters[row][col] = monster;
            // Set the monster character in the labyrinth
            this.labyrinth[row][col] = Labyrinth.MONSTER_CHAR;
            // Set the position of the monster
            monster.setPos(row, col);
        }
    }

    /**
     * Places a player in the labyrinth based on the given direction.
     * @param direction The direction in which the player should be placed.
     * @param player The player to be placed.
     * @return The monster encountered, if any, while moving the player.
     */
    public Monster putPlayer(Directions direction, Player player){
        int oldRow = player.getRow();
        int oldCol = player.getCol();
        int [] newPos = dir2Pos(oldRow, oldCol, direction);
        Monster monster = putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL],player);
        return monster;
        
    }

    /**
     * Adds a block to the labyrinth at the specified position and orientation.
     * @param orientation The orientation of the block.
     * @param startRow The starting row index of the block.
     * @param startCol The starting column index of the block.
     * @param length The length of the block.
     */
    public void addBlock(Orientation orientation, int startRow, int startCol, int length) {
        int incRow, incCol;
        if(orientation == Orientation.VERTICAL){
            incRow = 1;
            incCol = 0;
        }
        else{
            incRow = 0;
            incCol = 1;
        }
        
        int row = startRow, col = startCol;
        
        while (posOK(row,col) && emptyPos(row,col) && (length > 0)){
            labyrinth[row][col] = BLOCK_CHAR;
            length-=1;
            row+=incRow;
            col+=incCol;
        }
            
    }

    /**
     * Determines valid moves from the given position.
     * @param row The row index of the position.
     * @param col The column index of the position.
     * @return List of valid moves from the given position.
     */
    public ArrayList<Directions> validMoves(int row, int col) {
        ArrayList<Directions> output = new ArrayList(0);
        
        if      (canStepOn(row+1,col))
            output.add(Directions.DOWN);
        if (canStepOn(row-1,col))
            output.add(Directions.UP);
        if (canStepOn(row,col+1))
            output.add(Directions.RIGHT);
        if (canStepOn(row,col-1))
            output.add(Directions.LEFT);
        
        return output;
    }

    /**
     * Checks if a position is within the bounds of the labyrinth.
     * @param row The row index of the position.
     * @param col The column index of the position.
     * @return True if the position is within the labyrinth bounds, false otherwise.
     */
    private boolean posOK(int row, int col){
        boolean validPos = (0 <= row) && (row < this.nRows) && (0 <= col) && (col < this.nCols);
        return validPos;
    }

    /**
     * Checks if a position in the labyrinth is empty.
     * @param row The row index of the position.
     * @param col The column index of the position.
     * @return True if the position is empty, false otherwise.
     */
    private boolean emptyPos(int row, int col){
        return (this.labyrinth[row][col] == Labyrinth.EMPTY_CHAR);
    }

    /**
     * Checks if there is a monster at the specified position in the labyrinth.
     * @param row The row index of the position.
     * @param col The column index of the position.
     * @return True if there is a monster at the position, false otherwise.
     */
    private boolean monsterPos(int row, int col){
	    return (this.labyrinth[row][col] == MONSTER_CHAR);
    }

    /**
     * Checks if there is an exit at the specified position in the labyrinth.
     * @param row The row index of the position.
     * @param col The column index of the position.
     * @return True if there is an exit at the position, false otherwise.
     */
    private boolean exitPos(int row, int col){
	    return (this.labyrinth[row][col] == EXIT_CHAR);
    }

    /**
     * Checks if there is a combat position at the specified position in the labyrinth.
     * @param row The row index of the position.
     * @param col The column index of the position.
     * @return True if there is a combat position at the position, false otherwise.
     */
    private boolean combatPos(int row, int col){
        return (this.labyrinth[row][col] == COMBAT_CHAR);
    }

    /**
     * Checks if it is possible to step on the specified position in the labyrinth.
     * @param row The row index of the position.
     * @param col The column index of the position.
     * @return True if it is possible to step on the position, false otherwise.
     */
    private boolean canStepOn(int row, int col){
        boolean validPos = posOK(row, col);
        boolean canStep = false;
        if(validPos) {
            boolean empty = emptyPos(row, col);
            boolean monsterPos = monsterPos(row, col);
            boolean exit = exitPos(row, col);
            canStep = empty || monsterPos || exit;
        }
        
        return canStep;
    }

    /**
     * Updates the old position in the labyrinth after moving.
     * @param row The row index of the old position.
     * @param col The column index of the old position.
     */
    private void updateOldPos(int row, int col){
        if (posOK(row, col)) {
            if (combatPos(row, col))
                this.labyrinth[row][col] = Labyrinth.MONSTER_CHAR;
            else 
                this.labyrinth[row][col] = Labyrinth.EMPTY_CHAR;
            
        }
    }

    /**
     * Converts a position and direction to a new position in the labyrinth.
     * @param row The current row index.
     * @param col The current column index.
     * @param direction The direction of movement.
     * @return An array containing the new row and column indices.
     */
    private int[] dir2Pos(int row, int col, Directions direction) {
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
    
    /**
     * Finds a random empty position in the labyrinth.
     * @return An array containing the row and column indices of the random empty position.
     */
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

    /**
     * Placeholder method for placing a player in the labyrinth in 2D.
     * @return The monster encountered, if any, while putting the player.
     * @throws UnsupportedOperationException if the method is not implemented.
     */
    private Monster putPlayer2D (int oldRow, int oldCol, int row, int col, Player player){
        Monster output = null;
        if (canStepOn(row,col)){
            
            if(posOK(oldRow,oldCol)){
                Player p = players[oldRow][oldCol];
                if(p == player){
                    updateOldPos(oldRow,oldCol);
                    players[oldRow][oldCol] = null;
                }
            }
            
            boolean monsterPos = monsterPos(row,col);
            if(monsterPos){
                labyrinth[row][col]=COMBAT_CHAR;
                output = monsters[row][col];
            }
            else{
                char number = player.getNumber();
                labyrinth[row][col]=number;
            }
            
            players[row][col]=player;
            player.setPos(row,col);
        }
        
        return output;
    }

}
