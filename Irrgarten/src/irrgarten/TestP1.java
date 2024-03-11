package irrgarten;

/**
 * Test class for P1
 * @author luisvaldivieso
 */
public class TestP1 {
    public static void testWeapon(){
        System.out.println("WEAPON CLASS\n");
        Weapon hammer = new Weapon(4,5);
        String test = hammer.toString() + "\n";
        test += hammer.attack();
        test += hammer.toString() + "\n";
        test += hammer.discard();
        
        System.out.println(test);
    }
    
    public static void testShield(){
        System.out.println("SHIELD CLASS\n");
        Shield shield = new Shield(4,5);
        String test = shield.toString() + "\n";
        test += shield.protect();
        test += shield.toString() + "\n";
        test += shield.discard();
        
        System.out.println(test);
    }
    
    public static void testDice(){
        String test = "DICE CLASS \n";
        for (int i=0;i<100;i++){
            test+= "Random pos : " + Dice.randomPos(50) + "\n";
            test+= "Who starts : " + Dice.whoStarts(5) + "\n";
            test+= "Random Intelligence : " + Dice.randomIntelligence() + "\n";
            test+= "Random Strength : " + Dice.randomStrength() + "\n";
            test+= "Resurrect Player : " + Dice.resurrectPlayer() + "\n";
            test+= "Weapons Reward : " + Dice.weaponsReward() + "\n";
            test+= "Shields Reward : " + Dice.shieldsReward() + "\n";
            test+= "Health Reward : " + Dice.healthReward() + "\n";
            test+= "Weapon Power : " + Dice.weaponPower() + "\n";
            test+= "Shield Power : " + Dice.shieldPower() + "\n";
            test+= "Uses left : " + Dice.usesLeft() + "\n";
            test+= "Intensity: " + Dice.intensity(10) + "\n";
            test+= "Discard element : " + Dice.discardElement(Dice.usesLeft()) + "\n";
            
        }
        System.out.println(test);
    }
    
    public static void testGameState(){
        String test = "GAME STATE CLASS \n";
        GameState gamestate = new GameState ("labyrinth", "players","monsters",3,false,"log");
        test+=gamestate.getLabyrinth() + gamestate.getPlayers() + gamestate.getMonsters() +
          gamestate.getCurrentPlayer() + gamestate.isThereWinner() + gamestate.getLog();
        System.out.println(test);
    }
    
    public static void main (String args[]){
        testWeapon();
        testShield();
        testDice();
        testGameState();
    }
}
