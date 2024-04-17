
package irrgarten;

/**
 * @brief Test para la práctica 3
 * @author luisvaldivieso
 */
public class TestP3 {
    private static java.util.Random generator = new java.util.Random();
    
    public static void main(String args[]){
        Game game = new Game(3);
        Directions [] directions = new Directions[4];
        directions[0]=Directions.DOWN;
        directions[1]=Directions.UP;
        directions[2]=Directions.LEFT;
        directions[3]=Directions.RIGHT;
        
        GameState gameState;
        boolean endGame = false;
        int rounds = 0;
        while (!endGame && rounds < 10){
            Directions generatedDirection = directions[generator.nextInt(4)];
            
            game.nextStep(generatedDirection);
            
            gameState = game.getGameState();
            System.out.println(gameState.getLabyrinth().toString());
            System.out.println("Current Player: " + gameState.getCurrentPlayer());
            System.out.println("Chosen direction: " + generatedDirection.toString());
            System.out.println(gameState.getLog());
            System.out.println(gameState.getMonsters());
            System.out.println(gameState.getPlayers());
            rounds++;
        }
        
        
        
    }
}
