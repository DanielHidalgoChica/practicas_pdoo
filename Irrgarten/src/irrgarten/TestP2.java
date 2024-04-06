package irrgarten;

/**
 * Test for practice 2
 * @author luisvaldivieso
 */
public class TestP2 {
    public static void testMonster(){
        Monster monster = new Monster("Piglin", Dice.randomIntelligence(), Dice.randomStrength());
        System.out.println(monster.toString());
        System.out.println("IS DEAD " + monster.dead());
        for (int i = 0; i < 5; i++) {
            monster.gotWounded();
        }
        System.out.println("Monster after got wounded five times");
        System.out.println(monster.toString());
        System.out.println("IS DEAD " + monster.dead());
    }
    
    public static void testPlayer(){
        Player player = new Player('1', 9.0f, 4);
        System.out.println(player.toString());
        player.setPos(1,1);
        System.out.println("Attack:");
        System.out.println(player.attack());
    }
    
    public static void testLabyrinth(){
        Monster monster = new Monster("Goblin", Dice.randomIntelligence(), Dice.randomStrength());
        System.out.print(monster.toString());
        Labyrinth labyrinth = new Labyrinth(5,5,0,0);
        System.out.println(labyrinth.toString());
        labyrinth.addMonster(2,2,monster);
        System.out.println("Added monster");
        System.out.print(monster.toString());
        System.out.println(labyrinth.toString());
    }
    
    public static void testGame(){
        Game game = new Game(3);
        GameState gameState = game.getGameState();
        System.out.println("CURRENT GAME STATE:");
        System.out.println("Current player: ");
        System.out.println(gameState.getCurrentPlayer());
        System.out.println("PLAYERS:");
        System.out.println(gameState.getPlayers().toString());
        System.out.println("MONSTERS");
        System.out.println(gameState.getMonsters().toString());
        System.out.println("LABYRINTH");
        System.out.println(gameState.getLabyrinth().toString());
    }
    
    public static void main(String args[]){
        testMonster();
        testPlayer();
        testLabyrinth();
        testGame();
    }
}
