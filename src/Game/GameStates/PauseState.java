package Game.GameStates;

import Game.Entities.Dynamic.Player;
import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

public class PauseState extends State {

    private int count = 0;
    private UIManager uiManager;

    public PauseState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        /*
         * RESUME PLAYING BUTTON.
         */
        uiManager.addObjects(new UIImageButton(25, handler.getGame().getHeight() - 150, 128, 64, Images.Resume, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().gameState);
        }));

        /*
         * TITLE SCREEN BUTTON.
         */
        uiManager.addObjects(new UIImageButton(425,  handler.getGame().getHeight() - 150, 128, 64, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));

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

    /*
     * Pause screen art and current score on top.
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Images.Pause,0,0,handler.getGame().getWidth(),handler.getGame().getHeight(),null);
        uiManager.Render(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif",Font.BOLD,25));
        g.drawString("CURRENT SCORE: " + Player.highestScoreSoFar, 15, 30);

    }
}
