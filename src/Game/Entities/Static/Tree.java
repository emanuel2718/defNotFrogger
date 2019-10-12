package Game.Entities.Static;

import java.awt.Graphics;
import java.awt.Rectangle;

import Main.Handler;
import Resources.Images;

public class Tree extends StaticBase {
	private Rectangle tree;

	public Tree(Handler handler,int xPosition, int yPosition) {  // added x position and y position
		super(handler);
		this.setX(xPosition);
		this.setY(yPosition);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Images.tree, this.getX(), this.getY(), 64, 64, null);
		tree = new Rectangle(this.getX() + 10, this.getY() + 10, 45, 45);

	}
	
    @Override
    public Rectangle GetCollision() {
    	
    	return tree;
    }
}
