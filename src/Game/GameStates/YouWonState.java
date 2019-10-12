package Game.GameStates;

import Game.Entities.Dynamic.Player;
import Main.Handler;
import Resources.Images;
import UI.ClickListlener;
import UI.UIImageButton;
import UI.UIManager;
import java.awt.*;


public class YouWonState extends State {
    private int count = 0;
    private UIManager uiManager;

    public YouWonState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);


        /*
         * PLAY AGAIN BUTTON.
         */
        uiManager.addObjects(new UIImageButton(430, handler.getHeight() - 225, 128, 64,
                Images.playAgain, new ClickListlener() {
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
        uiManager.addObjects(new UIImageButton(430,  handler.getGame().getHeight() - 150, 128, 64,
                Images.gTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));
    }
    public void render(Graphics g) {
        // Draw You Win screen. Link captured the frog.
        g.drawImage(Images.youWinScreen,0,0,handler.getGame().getWidth(),handler.getGame().getHeight(),
                null);
        uiManager.Render(g);
        g.setColor(new Color(255, 215, 0));
        g.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 27));
        g.drawString("FINAL SCORE: OVER 9000", 5, 745);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
        g.drawString("If you read this, either you are EXTREMELY lucky or you need " +
                "to go play outside!", 5, 762);

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
