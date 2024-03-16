package irrgarten;

/**
 * Test class for P1
 * @author luisvaldivieso
 */
public class TestP1 {
    public static void testWeapon(){
        System.out.println("\nWEAPON CLASS\n");
        Weapon hammer = new Weapon(4,5);
        String test = "Hammer: " + hammer.toString() + "\n";
        test+=hammer.discard() + "\n";
        test += hammer.attack();
        test += hammer.toString() + "\n";
        test += hammer.discard();
        
        Weapon sword = new Weapon (0,0);
        test += "Sword: " + sword.toString() + "\n";
        test += sword.attack();
        test += sword.toString() + "\n";
        test += sword.discard() + "\n";
        System.out.println(test);
    }
    
    public static void testShield(){
        System.out.println("SHIELD CLASS\n");
        Shield shield = new Shield(4,5);
        String test = "Shield: " + shield.toString() + "\n";
        test+=shield.discard() + "\n";
        test += shield.protect();
        test += shield.toString() + "\n";
        test += shield.discard();
        Shield medievalShield = new Shield(0,0);
        test += "Medieval Shield" + medievalShield.toString() + "\n";
        test += medievalShield.protect();
        test += medievalShield.toString() + "\n";
        test += medievalShield.discard();
        System.out.println(test);
    }
    
    public static void testDiceLoop(){
        String test = "DICE CLASS \n";
        for (int i=0;i<100;i++){
            test+= "Random pos : " + Dice.randomPos(50) + "\n";
        }
        for (int i=0;i<100;i++){
            test+= "Who starts : " + Dice.whoStarts(5) + "\n";
        }
        for (int i=0;i<100;i++){
            test+= "Random Intelligence : " + Dice.randomIntelligence() + "\n";
        }
        for (int i=0;i<100;i++){
            test+= "Random Strength : " + Dice.randomStrength() + "\n";
        }
        for (int i=0;i<100;i++){
            test+= "Resurrect Player : " + Dice.resurrectPlayer() + "\n";
        }
        for (int i=0;i<100;i++){
            test+= "Weapons Reward : " + Dice.weaponsReward() + "\n";
        }
        for (int i=0;i<100;i++){
            test+= "Shields Reward : " + Dice.shieldsReward() + "\n";
        }
        for (int i=0;i<100;i++){
            test+= "Health Reward : " + Dice.healthReward() + "\n";
        }
        for (int i=0;i<100;i++){
            test+= "Weapon Power : " + Dice.weaponPower() + "\n";
        }
        for (int i=0;i<100;i++){
            test+= "Shield Power : " + Dice.shieldPower() + "\n";
        }
        for (int i=0;i<100;i++){
            test+= "Uses left : " + Dice.usesLeft() + "\n";
        }
        for (int i=0;i<100;i++){
            test+= "Intensity: " + Dice.intensity(10) + "\n";
        }
        for (int i=0;i<100;i++){
            test+= "Discard element : " + Dice.discardElement(Dice.usesLeft()) + "\n";
        }
            
        System.out.println(test);
    }
    
    public static void testDice(){
        System.out.println("Dice test for discard element");
        String test = "";
        for (int i = 0; i < 5; i++){
            test += "5 uses left: " + String.valueOf(Dice.discardElement(5)) + "\n";
            test+= "0 uses left " + String.valueOf(Dice.discardElement(0))+"\n";
        }
        System.out.println(test);
    }
    public static void testGameState(){
        String test = "GAME STATE CLASS \n";
        GameState gamestate = new GameState ("labyrinth", "players","monsters",1,true,"log");
        test+=gamestate.getLabyrinth() + "\n" + gamestate.getPlayers() + "\n" + gamestate.getMonsters() + "\n" +
        gamestate.getCurrentPlayer()+ "\n" + gamestate.getWinner()+ "\n" + gamestate.getLog()+ "\n";
        System.out.println(test);
    }
    
    public static void main (String args[]){
        testWeapon();
        testShield();
        testDiceLoop();
        testDice();
        testGameState();
    }
}
