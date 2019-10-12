package Game.World;

import Game.Entities.Dynamic.Player;
import Game.Entities.Static.LillyPad;
import Game.Entities.Static.Log;
import Game.Entities.Static.HiddenFrog;
import Game.Entities.Static.StaticBase;
import Game.Entities.Static.Tree;
import Game.Entities.Static.Turtle;
import Main.Handler;
import UI.UIManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

/**
 * Literally the world.
 * Spawn hazards (StaticBase), and tiles (BaseArea)
 * 
 * Move the screen, the player, and some hazards.
 */
public class WorldManager {

	private ArrayList<BaseArea> AreasAvailables;			// Lake, empty and grass area (NOTE: The empty tile is just the "sand" tile. Ik, weird name.)
	private ArrayList<StaticBase> StaticEntitiesAvailables;	// Has the hazards: LillyPad, Log, Tree, and Turtle.

	private ArrayList<BaseArea> SpawnedAreas;				// Areas currently on world
	private ArrayList<StaticBase> SpawnedHazards;			// Hazards currently on world.
	public ArrayList<Integer> treeSpawnPosition = new ArrayList<>(); // Receives all the y-positions visited by the player.

	Long time;
	Boolean reset = true;
	Handler handler;



	UIManager object = new UIManager(handler);
	UI.UIManager.Vector object2 = object.new Vector();


	private Player player; // Use this to find player coordinates and collisions.
	private ID[][] grid;
	private int gridWidth,gridHeight; // Size of the grid.
	private int movementSpeed; // Movement of the tiles going downwards.
	private int counter = 0;




	public WorldManager(Handler handler) {
		this.handler = handler;

		AreasAvailables = new ArrayList<>(); // Add the Tiles to be utilized.
		StaticEntitiesAvailables = new ArrayList<>(); // Add the Hazards to be utilized.

		AreasAvailables.add(new GrassArea(handler, 0));		
		AreasAvailables.add(new WaterArea(handler, 0));
		AreasAvailables.add(new EmptyArea(handler, 0));

		StaticEntitiesAvailables.add(new LillyPad(handler, 0, 0));
		StaticEntitiesAvailables.add(new Log(handler, 0, 0));
		StaticEntitiesAvailables.add(new Tree(handler, 0, 0));
		StaticEntitiesAvailables.add(new Turtle(handler, 0, 0));

		SpawnedAreas = new ArrayList<>();
		SpawnedHazards = new ArrayList<>();

		player = new Player(handler);       

		gridWidth = handler.getWidth()/64;
		gridHeight = handler.getHeight()/64;
		movementSpeed = 1;

		/* 
		 * 	Spawn Areas in Map (2 extra areas spawned off screen)
		 *  To understand this, go down to randomArea(int yPosition) 
		 */
		for (int i=0; i<gridHeight+2; i++) {
			SpawnedAreas.add(randomArea((-2+i)*64));
		}

		player.setX((gridWidth/2)*64);
		player.setY((gridHeight-3)*64);

		// Not used atm.
		grid = new ID[gridWidth][gridHeight];
		for (int x = 0; x < gridWidth; x++) {
			for (int y = 0; y < gridHeight; y++) {
				grid[x][y]=ID.EMPTY;
			}
		}
	}

	public void tick() {

		if (this.handler.getKeyManager().keyJustPressed(this.handler.getKeyManager().num[2])) {
			this.object2.word = this.object2.word + this.handler.getKeyManager().str[1];
		}
		if (this.handler.getKeyManager().keyJustPressed(this.handler.getKeyManager().num[0])) {
			this.object2.word = this.object2.word + this.handler.getKeyManager().str[2];
		}
		if (this.handler.getKeyManager().keyJustPressed(this.handler.getKeyManager().num[1])) {
			this.object2.word = this.object2.word + this.handler.getKeyManager().str[0];
		}
		if (this.handler.getKeyManager().keyJustPressed(this.handler.getKeyManager().num[3])) {
			this.object2.addVectors();
		}
		if (this.handler.getKeyManager().keyJustPressed(this.handler.getKeyManager().num[4]) && this.object2.isUIInstance) {
			this.object2.scalarProduct(handler);
		}

		if (this.reset) {
			time = System.currentTimeMillis();
			this.reset = false;
		}

		if (this.object2.isSorted) {

			if (System.currentTimeMillis() - this.time >= 2000) {
				this.object2.setOnScreen(true);	
				this.reset = true;
			}

		}

		for (BaseArea area : SpawnedAreas) {
			area.tick();
		}
		for (StaticBase hazard : SpawnedHazards) {
			hazard.tick();
		}



		for (int i = 0; i < SpawnedAreas.size(); i++) {
			SpawnedAreas.get(i).setYPosition(SpawnedAreas.get(i).getYPosition() + movementSpeed);

			// Check if Area (thus a hazard as well) passed the screen.
			if (SpawnedAreas.get(i).getYPosition() > handler.getHeight()) {
				// Replace with a new random area and position it on top
				SpawnedAreas.set(i, randomArea(-2 * 64));
			}
			//Make sure players position is synchronized with area's movement
			if (SpawnedAreas.get(i).getYPosition() < player.getY()
					&& player.getY() - SpawnedAreas.get(i).getYPosition() < 3) {
				player.setY(SpawnedAreas.get(i).getYPosition());
			}
		}

		HazardMovement();
		HazardTreeCollision(); // Check if player collided with the tree.

		player.tick();
		//make player move the same as the areas
		player.setY(player.getY()+movementSpeed); 

		object2.tick();

	}

	private void HazardMovement() {

		for (int i = 0; i < SpawnedHazards.size(); i++) {

			// Moves hazard down
			SpawnedHazards.get(i).setY(SpawnedHazards.get(i).getY() + movementSpeed);

			// Moves Log or Turtle to the right
			if (SpawnedHazards.get(i) instanceof Log ) {
				SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX() + 2);

				// Loop the logs to spawn just outside the left side of the screen
				// as they disappear from the screen on the right.
				// Added random speed to tree on different rows. Keep the same speed for same
				// row logs.
				if (SpawnedHazards.get(i).getX() > 576) {
					int yPos = SpawnedHazards.get(i).getY();
					SpawnedHazards.remove(i);
					SpawnedHazards.add(new Log(handler, -128, yPos));
				}

				// Verifies the hazards Rectangles aren't null and
				// If the player Rectangle intersects with the Log or Turtle Rectangle, then
				// move player to the right.
				if (SpawnedHazards.get(i).GetCollision() != null
						&& player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())) {
					player.setX(player.getX() + 2);
				}

			}

			// Move the turtles from right to left and loop them when they get out of the screen.
			if (SpawnedHazards.get(i) instanceof Turtle) {
				SpawnedHazards.get(i).setX(SpawnedHazards.get(i).getX()-2);
				if (SpawnedHazards.get(i).getX() < - 128) {
					int yPos = SpawnedHazards.get(i).getY();
					SpawnedHazards.remove(i);
					SpawnedHazards.add(new Turtle(handler, 576, yPos));
				}
				if (SpawnedHazards.get(i).GetCollision() != null
						&& player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())){
					player.setX(player.getX()-2);

				}
			}
			// if hazard has passed the screen height, then remove this hazard.
			if (SpawnedHazards.get(i).getY() > handler.getHeight()) {
				SpawnedHazards.remove(i);
			}
		}
	}

	public void render(Graphics g){

		for (BaseArea area : SpawnedAreas) {
			area.render(g);
		}

		for (StaticBase hazards : SpawnedHazards) {
			hazards.render(g);

		}

		player.render(g);       
		this.object2.render(g);      

	}

	/*
	 * Given a yPosition, this method will return a random Area out of the Available ones.)
	 * It is also in charge of spawning hazards at a specific condition.
	 */
	private BaseArea randomArea(int yPosition) {
		Random rand = new Random();

		// From the AreasAvailable, get me any random one.
		BaseArea randomArea = AreasAvailables.get(rand.nextInt(AreasAvailables.size()));
		// Loop once to make the spawning position of the player be the Sand Area; Avoid Water spawning
		for (int i = 0; i < 1; i++) {
			if (yPosition == 512) {
				randomArea = new EmptyArea(handler, yPosition);
				i++;
			}
		}

		if (randomArea instanceof GrassArea) {
			randomArea = new GrassArea(handler, yPosition);
			spawnTreeHazard(yPosition);
		}
		else if (randomArea instanceof WaterArea) {
			randomArea = new WaterArea(handler, yPosition);
			SpawnHazard(yPosition);
		}
		else {
			randomArea = new EmptyArea(handler, yPosition);
			/*
			 * Frog have a 0.01% chance of spawning on a random empty area.
			 * Change the bound of this next random variable to a lower number
			 * in order to see the effects of capturing the frog.
			 * For a far greater chance of seeing the frog; uncomment the line below.
			 */
			//int randomNumber = rand.nextInt(15);
			int randomNumber = rand.nextInt(10000);
			if (randomNumber == 1) {
				spawnHiddenFrog(yPosition);
			}
		}
		return randomArea;
	}
	public void spawnHiddenFrog (int yPosition) {
		Random rand = new Random();
		int randPos;
		randPos = 64 * rand.nextInt(4);
		SpawnedHazards.add(new HiddenFrog(handler, randPos, yPosition));
	}

	/*
	 * Given a yPosition this method will add a new hazard to the SpawnedHazards ArrayList.
	 */
	public void SpawnHazard(int yPosition) {
		Random rand = new Random();
		int randInt;
		int randomLilySpawn = 4 + rand.nextInt(3); // Minimum of 4 lilly pads.
		int randomLogSpawnCount = 1 + rand.nextInt(3); // 1-4 logs per tile.
		int logLoopSpawnPosition = handler.getWidth() - 704; // Spawn the logs off the screen on the left.
		int turtleLoopSpawnPosition = handler.getWidth(); // Spawn the turtles off the screen on the right.

		int logPositionAdjuster = 140; // Adjust the x-spawn position of the logs. To make them spawn side by side.
		int turtlePositionAdjuster = 80; // Same adjustment with the turtles. More close together than the logs.
		int choice = rand.nextInt(7); // This will determine, randomly, what hazard spawn on that water tile.
		/*
		 * Choose between Log or Lily pad. 
		 * Prevent two Lily pads from spawning two Y levels consecutively.
		 */
		if (choice <=2) {
			// Randomly choose how many logs on each tile.
			for (int i = 0; i < randomLogSpawnCount; i++) {
				SpawnedHazards.add(new Log(handler, logLoopSpawnPosition, yPosition));
				counter = 0;
				logLoopSpawnPosition -= logPositionAdjuster;
			}
		}
		else if (choice >=5) {
			if (counter < 1) {
				// Randomly choose how many Lilly pads on each tile.
				for (int i =0; i <= randomLilySpawn; i++) {
					randInt = 64 * rand.nextInt(9);
					SpawnedHazards.add(new LillyPad(handler, randInt, yPosition));
					counter++;
				}
            // If there was a lilly pad on the previous water tile; make it a row of logs.
			} else {
				for (int i = 0; i < randomLogSpawnCount; i++) {
					SpawnedHazards.add(new Log(handler, logLoopSpawnPosition, yPosition));
					counter = 0;
					logLoopSpawnPosition -= logPositionAdjuster;
				}
			}
		} else {
			// Randomly choose how many turtles on each tile.
			for (int i = 0; i<2; i++) {
				SpawnedHazards.add(new Turtle(handler, turtleLoopSpawnPosition, yPosition));
				turtleLoopSpawnPosition += turtlePositionAdjuster;
				counter = 0;
			}
		}
	}
	/*
	 *  This will spawn random number of trees (0-3) on each grass tile.
	 *  Having tress spawn on the same x position on the same grass tile will be avoided.
	 */
	private void spawnTreeHazard(int yPosition) {
		Random rand = new Random();
		int randInt;
		int choice = rand.nextInt(8);
		int randomTreeSpawnQuantity = rand.nextInt(4);
		// Chooses between Trees or nothing. If tree; spawn at most tree of them on each line.
		if (choice <= 5) {
			for (int i = 0; i < randomTreeSpawnQuantity; i++) {
				randInt = 64 * rand.nextInt(9);
				// Ensure that the array != null
				if (treeSpawnPosition.isEmpty()) {
					SpawnedHazards.add(new Tree(handler, randInt, yPosition));
					treeSpawnPosition.add(randInt/64); // Add the x position to the blacklist for that tile.
				}
				// Avoid having trees on the same x position.
				if (!(treeSpawnPosition.contains(randInt/64))) {
					SpawnedHazards.add(new Tree(handler, randInt, yPosition));
					treeSpawnPosition.add(randInt/64); // Add the x position to the blacklist for that tile.
				}
			}
			// Reset the array.
			treeSpawnPosition.clear();
		}
	}
	/*
	 * Avoid player movement through trees.
	 * If tree hazard is present. Move player backwards from the direction he's facing.
	 */
	private void HazardTreeCollision() {
		for (int i = 0; i < SpawnedHazards.size(); i++) {
			if (SpawnedHazards.get(i) instanceof Tree && SpawnedHazards.get(i).GetCollision() != null
					&& player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())) {
				if (player.facing.equals("UP")) {
					player.setY(player.getY() + 32);
					/* The purpose of this following block of code is to counter the effects that moving the player
					 * forward and colliding with a tree have on the score. Which is adding +1 to the score each time.
					 * Thus; we subtract 1 from the score each time the player collides with a tree and set the score
					 * to be one less than the maximum value of the list of scores. Then we add that number to the list
					 * and finally we set the current high score to that score.
					 */
					player.score--;
					player.score = player.scoreSoFar.get(0) - 1;
					player.scoreSoFar.add(player.score);
					player.scoreSoFar.sort(Comparator.reverseOrder());
					Player.highestScoreSoFar = player.scoreSoFar.get(0) - 1;
					player.scoreSoFar.add(player.score);
				}
				if (player.facing.equals("DOWN")) {
					player.setY(player.getY() - 32);
					player.score = player.scoreSoFar.get(0); // set the score variable to the maximum score so far.
				}
				if (player.facing.equals("LEFT")) {
					player.setX(player.getX() + 32);
				}
				if (player.facing.equals("RIGHT")) {
					player.setX(player.getX() - 32);
				}
			}
		}
	}
	/*
	 * This will be called to check whether player is on top of a water hazard.
	 */
	public boolean playerInWaterHazard() {
		for (int i = 0; i < SpawnedHazards.size(); i++) {
			if ((SpawnedHazards.get(i) instanceof Log || SpawnedHazards.get(i) instanceof Turtle
					|| SpawnedHazards.get(i) instanceof LillyPad) && SpawnedHazards.get(i).GetCollision() != null
					&& player.getPlayerCollision().intersects(SpawnedHazards.get(i).GetCollision())) {
				return true;
			}
		}
		return false;
	}
}
