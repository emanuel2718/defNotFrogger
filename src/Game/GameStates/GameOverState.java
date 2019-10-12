package Game.GameStates;

import Game.Entities.Dynamic.Player;
import Main.Handler;
import Resources.Images;
import UI.ClickListlener;
import UI.UIImageButton;
import UI.UIManager;
import java.awt.*;


/**
 * Created by Emanuel Ramirez on 04/21/2018
 */
public class GameOverState extends State {
    private int count = 0;
    private UIManager uiManager;

    public GameOverState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);


        /*
         * PLAY AGAIN BUTTON.
         */
        uiManager.addObjects(new UIImageButton(20, handler.getHeight() - 100, 128, 64, Images.playAgain, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                handler.getGame().reStart();
                Player.highestScoreSoFar = 0; // reset score on new game state
                State.setState(handler.getGame().gameState);
            }
        }));

        /*
         * MENU BUTTON.
         */
        uiManager.addObjects(new UIImageButton(428,  handler.getGame().getHeight() - 100, 128, 64, Images.gTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));
    }
    public void render(Graphics g) {
        // Draw Game Over screen and display high score obtained on that run.
        g.drawImage(Images.gameOver,0,0,handler.getGame().getWidth(),handler.getGame().getHeight(),null);
        uiManager.Render(g);
        g.setColor(Color.WHITE);
        // Adjust score text size based on the magnitude of the high score so it would look nice on the screen.
        if (Player.highestScoreSoFar < 10) {
            g.setFont(new Font("SansSerif",Font.BOLD | Font.ITALIC,30));
            g.drawString("YOUR SCORE: " + Player.highestScoreSoFar, 162, 720);
        }
        else if (Player.highestScoreSoFar >= 10 && Player.highestScoreSoFar < 100) {
            g.setFont(new Font("SansSerif",Font.BOLD | Font.ITALIC,29));
            g.drawString("YOUR SCORE: " + Player.highestScoreSoFar, 160, 720);

        }
        else if (Player.highestScoreSoFar >= 100 && Player.highestScoreSoFar < 999) {
            g.setColor(new Color(255,215,0));
            g.setFont(new Font("SansSerif",Font.BOLD | Font.ITALIC,28));
            g.drawString("YOUR SCORE: " + Player.highestScoreSoFar, 157, 720);

        } else {
            /*
             * Player score is over 1000. Quite impressive, but could be better. Over 9000?
             */
            g.setColor(new Color(255, 215, 0));
            g.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 27));
            g.drawString("YOUR SCORE: " + Player.highestScoreSoFar, 152, 720);

            g.setColor(new Color(255, 215, 0));
            g.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
            g.drawString("If you read this, go play outside", 160, 735);

        }
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pbutt && count>=30){
            count=0;
            State.setState(handler.getGame().gameState);
        }
    }
}