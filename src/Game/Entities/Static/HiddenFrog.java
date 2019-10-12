package Game.Entities.Static;

import java.awt.Graphics;
import java.awt.Rectangle;

import Game.Entities.Dynamic.Player;
import Game.GameStates.State;
import Main.Handler;
import Resources.Images;

public class HiddenFrog extends StaticBase {

    private Rectangle hiddenFrog;
    private Player player;

    public HiddenFrog(Handler handler,int xPosition, int yPosition) {
        super(handler);
        // Sets original position.
        this.setX(xPosition);
        this.setY(yPosition);
        player = new Player(handler);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

        // Draw the frog.
    	g.drawImage(Images.hiddenFrog, this.getX() + 10, this.getY() + 10, 45, 45, null);
    	hiddenFrog = new Rectangle(this.getX() + 25, this.getY() + 30, 10, 10);
        playerCollectedFrog();
    }

    @Override
    public Rectangle GetCollision() {
    	return hiddenFrog;
    }

    /*
     * If the player collects the frog; he wins;
     */
    private void playerCollectedFrog() {
        if (hiddenFrog != null && player.getPlayerCollision().intersects(hiddenFrog)) {
            State.setState(handler.getGame().youWonState); // Call the You Win state.

        }
    }
}
