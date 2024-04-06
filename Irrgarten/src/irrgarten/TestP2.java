package irrgarten;

/**
 * Test for practice 2
 * @author luisvaldivieso
 */
public class TestP2 {
    public static void testMonster(){
        Monster monster = new Monster("Piglin", Dice.randomIntelligence(), Dice.randomStrength());
        System.out.println(monster.toString());
    }
    
    public static void testPlayer(){
        Player player = new Player('1', 9.0f, 4);
        System.out.println(player.toString());
        player.setPos(1,1);
        System.out.println(player.attack());
    }
    
    public static void testLabyrinth(){
        Monster monster = new Monster("Goblin", Dice.randomIntelligence(), Dice.randomStrength());
        Labyrinth labyrinth = new Labyrinth(5,5,0,0);
        System.out.println(labyrinth.toString());
        labyrinth.addMonster(2,2,monster);
        System.out.println(labyrinth.toString());
    }
    
    public static void testGame(){
        Game game = new Game(5);
        GameState gameState = game.getGameState();
        System.out.println(gameState.getCurrentPlayer());
        System.out.println(gameState.getPlayers());
        System.out.println(gameState.getMonsters());
        System.out.println(gameState.getLabyrinth());
    }
    
    public static void main(String args[]){
        testMonster();
        testPlayer();
        testLabyrinth();
        testGame();
    }
}
