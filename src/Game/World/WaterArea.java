package Game.World;

import java.awt.Graphics;
import java.util.Random;

import Game.Entities.Dynamic.Player;
import Game.GameStates.State;
import Main.Handler;
import Resources.Animation;
import Resources.Images;
import java.awt.Rectangle;

public class WaterArea extends BaseArea {

    private Animation anim;
    public Rectangle water;
    private Player player;


    WaterArea(Handler handler, int yPosition) {
        super(handler, yPosition);
        // Instantiate the animation of this Water, and it starts it at a random frame.
        anim=new Animation(384,Images.Water,new Random().nextInt(3));
        player = new Player(handler);

    }

    @Override
    public void tick() {
        anim.tick();	// Animation frame movement.
        playerInWater();

    }

    @Override
    public void render(Graphics g) {
        for (int i = 0; i < 9; i++) {
            g.drawImage(anim.getCurrentFrame(), i*64, yPosition,64,66, null);
            // Make water area be a rectangle to detect intersection with player.
            // Make width be the size of the screen and
            // height be smaller than 64 so it looks like the player jumps into the water.
            water = new Rectangle(0, yPosition + 32, handler.getWidth(), 10);
        }
    }

    /*
     *  Game Over if player landed on a water tile without being in contact with a water hazard.
     */
    private void playerInWater() {
        if (water != null && player.getPlayerCollision().intersects(water)
            && (!(handler.getWorld().playerInWaterHazard()))) {
            State.setState(handler.getGame().gameOverState);
        }
    }
}
