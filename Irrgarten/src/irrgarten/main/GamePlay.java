package irrgarten.main;
import irrgarten.Game;
import irrgarten.controller.Controller;
import irrgarten.UI.TextUI;

/**
 *
 * @author luisvaldivieso
 */
public class GamePlay {
    static final int N_PLAYERS=4;
    public static void main (String [] args){
        
        Game game = new Game(N_PLAYERS);
        TextUI textUI = new TextUI();
        Controller controller = new Controller(game, textUI);
        controller.play();
    }
}
