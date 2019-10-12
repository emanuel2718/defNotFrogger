package Resources;

import javax.imageio.ImageIO;
import javax.swing.*;

import Main.GameSetUp;
import UI.UIObject;

import java.awt.image.BufferedImage;
import java.io.IOException;


public class Images {


    public static BufferedImage[] butstart;
    public static BufferedImage[] playAgain;
    public static BufferedImage[] gTitle;
    public static BufferedImage title;
    public static BufferedImage Pause;
    public static BufferedImage gameOver;
    public static BufferedImage youWinScreen;
    public static BufferedImage[] Resume;
    public static BufferedImage[] BTitle;
    public static BufferedImage[] Options;
    public static ImageIcon icon;
    public static String str;
    public static BufferedImage icon2;

    public static SpriteSheet playerSheet;
    public static SpriteSheet hiddenFrogSheet;
    public static BufferedImage[] Player;
    public static BufferedImage[] Turtle;
    public static SpriteSheet WaterSheet;
    public static BufferedImage[] Water;


    public static BufferedImage player;
    
    public static BufferedImage grassArea;
    public static BufferedImage waterArea;
    public static BufferedImage emptyArea;
    public static BufferedImage lilly;
    public static BufferedImage log;
    public static BufferedImage tree;
    public static BufferedImage hiddenFrog;
    public static BufferedImage grass;

    public static BufferedImage[] object;


    public Images() {

        gTitle = new BufferedImage[2];
        playAgain = new BufferedImage[3];
        butstart = new BufferedImage[3];
        Resume = new BufferedImage[2];
        BTitle = new BufferedImage[2];
        Options = new BufferedImage[2];
        Player = new BufferedImage[8];
        Turtle = new BufferedImage[20];
        Water = new BufferedImage[3];
        object = new BufferedImage[6];

        try {
            playerSheet = new SpriteSheet(ImageIO.read(getClass().getResourceAsStream("/Sheets/gameSprites.png")));
            WaterSheet = new SpriteSheet(ImageIO.read(getClass().getResourceAsStream("/Sheets/water.png")));
            hiddenFrogSheet = new SpriteSheet(ImageIO.read(getClass().getResourceAsStream("/Sheets/frogSoldierSprite.png")));

            title = ImageIO.read(getClass().getResourceAsStream("/Sheets/Frogger2.png"));
            Pause = ImageIO.read(getClass().getResourceAsStream("/Buttons/Pause.png"));
            gameOver = ImageIO.read(getClass().getResourceAsStream("/Sheets/gameOverScreen.png"));
            youWinScreen = ImageIO.read(getClass().getResourceAsStream("/Sheets/YouWinScreen.png"));
            Resume[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ResumeButton.png"));
            Resume[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/ResumeButton2.png"));
            BTitle[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/TitleButton.png"));
            BTitle[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/TitleButton2.png"));
            Options[0] = ImageIO.read(getClass().getResourceAsStream("/Buttons/OptionsButton.png"));
            Options[1] = ImageIO.read(getClass().getResourceAsStream("/Buttons/OptinsButton2.png"));
            butstart[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/StartButton.png"));//normbut
            butstart[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/Startbutton2.png"));//hoverbut
            butstart[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/StartButton3.png"));//clickbut
            playAgain[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/PlayAgainButton.png"));//norm
            playAgain[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/PlayAgainButton2.png"));//hover
            playAgain[2]= ImageIO.read(getClass().getResourceAsStream("/Buttons/PlayAgainButton3.png"));//click
            gTitle[0]= ImageIO.read(getClass().getResourceAsStream("/Buttons/TitleGameOver.png"));//norm
            gTitle[1]= ImageIO.read(getClass().getResourceAsStream("/Buttons/TitleGameOver2.png"));//click


            Player[0]= playerSheet.crop(3,21,46,53);
            Player[1]= playerSheet.crop(57,8,47,62);
            Player[2]= playerSheet.crop(57,8,47,62);
            Player[3]= playerSheet.crop(176,14,48,55);
            Player[4]= playerSheet.crop(176,14,48,55);
            Player[5]= playerSheet.crop(290,10,47,62);
            Player[6]= playerSheet.crop(290,10,47,62);
            Player[7]= playerSheet.crop(3,21,46,53);
            Player[0]= playerSheet.crop(3,21,46,53);

            hiddenFrog=hiddenFrogSheet.crop(3,0,30,30);

            // emerges
            Turtle[0] = playerSheet.crop(560, 100, 40, 30);
            Turtle[1] = playerSheet.crop(447, 88, 66, 62);
            Turtle[2] = playerSheet.crop(379, 88, 71, 57);
            
            Turtle[3] = playerSheet.crop(406, 3, 66, 73);
            Turtle[4] = playerSheet.crop(482, 3, 66, 73);
            
            Turtle[5] = playerSheet.crop(406, 3, 66, 73);
            Turtle[6] = playerSheet.crop(482, 3, 66, 73);
            Turtle[7] = playerSheet.crop(406, 3, 66, 73);
            
            Turtle[8] = playerSheet.crop(482, 3, 66, 73);
            Turtle[9] = playerSheet.crop(406, 3, 66, 73);
            Turtle[10] = playerSheet.crop(482, 3, 66, 73);
            
            Turtle[11] = playerSheet.crop(406, 3, 66, 73);
            Turtle[12] = playerSheet.crop(482, 3, 66, 73);
            Turtle[13] = playerSheet.crop(406, 3, 66, 73);
            

            Turtle[14] = playerSheet.crop(482, 3, 66, 73);
            Turtle[15] = playerSheet.crop(406, 3, 66, 73);
            Turtle[16] = playerSheet.crop(482, 3, 66, 73);
            
            // sinks
            Turtle[17] = playerSheet.crop(379, 88, 71, 57);
            Turtle[18] = playerSheet.crop(447, 88, 66, 62);
            Turtle[19] = playerSheet.crop(560, 100, 40, 30);

            Water[0]= WaterSheet.crop(0,0,32,32);
            Water[1]= WaterSheet.crop(32,0,32,32);
            Water[2]= WaterSheet.crop(64,0,32,32);


            lilly = playerSheet.crop(6,170,61,55);
            log = playerSheet.crop(387,259,184,57);
            tree = ImageIO.read(getClass().getResourceAsStream("/Sheets/Tree1.png"));

            object[0] = ImageIO.read(getClass().getResourceAsStream("/Sheets/Objects/object1.png"));
            object[1] = ImageIO.read(getClass().getResourceAsStream("/Sheets/Objects/object2.png"));
            object[2] = ImageIO.read(getClass().getResourceAsStream("/Sheets/Objects/object3.png"));
            object[3] = ImageIO.read(getClass().getResourceAsStream("/Sheets/Objects/object4.png"));
            object[4] = ImageIO.read(getClass().getResourceAsStream("/Sheets/Objects/object5.png"));
            object[5] = ImageIO.read(getClass().getResourceAsStream("/Sheets/Objects/object6.png"));
            str = GameSetUp.str;

            icon =  new ImageIcon(ImageIO.read(getClass().getResourceAsStream("/Sheets/froggy.png")));
            
            player = ImageIO.read(getClass().getResourceAsStream("/Sheets/froggy.png"));
            grass = ImageIO.read(getClass().getResourceAsStream("/Sheets/grass.jpg"));

            grassArea = ImageIO.read(getClass().getResourceAsStream("/Sheets/grassArea.png"));
            waterArea = ImageIO.read(getClass().getResourceAsStream("/Sheets/waterArea.png"));
            emptyArea = ImageIO.read(getClass().getResourceAsStream("/Sheets/sand.jpg"));

            
        }catch (IOException e) {
        e.printStackTrace();
    }


    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Images.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

}
