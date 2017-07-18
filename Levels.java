import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Rooms Escape
 * Make your way through each room and defeat the boss to escape!
 * 
 * @author Emily Wu
 * @version 4.0
 * 
 * Credits: 
 * 
 * Code: - Animation of Potato and Tomato borrowed from Mr.Cohen (http://www.greenfoot.org/scenarios/8407)
 *       - Potato jump/ fall code borrowed from http://www.greenfoot.org/scenarios/3061
 * 
 * Pictures: - Ladder (http://www.engine001.com/community/resources/ladder-RLL7Q8.gif)
 *           - Door (https://s-media-cache-ak0.pinimg.com/originals/6a/d8/9f/6ad89f3f85f0f1d650fe562b0084435e.png)
 *           - Spacebar (http://www.clker.com/cliparts/i/K/s/w/t/o/handbrake.svg)
 *           - Arrow Keys (https://image.freepik.com/free-vector/arrow-keys-vectors_637061.jpg)
 *           - Last background (http://orig02.deviantart.net/15ca/f/2011/261/f/8/f869873820869a31a771f7a50bf0c488-d4a6hvp.jpg)
 * 
 * Music: Background music (https://www.youtube.com/watch?v=BksG0hGu21E)
 *        Player shooting sound (https://www.youtube.com/watch?v=J37kP0e_NRo)
 * 
 * Bugs: - Player falls through platforms at high falling speeds or when ducking and falling
 *       - Player may get stuck below platforms when ducking or pressing multiple keys at once
 *       - Holding down the spacebar to shoot for a long time or pressing the button quickly makes the game lag 
 */          
public class Levels extends World
{
    private boolean isKeyPressed; //Checks if the user is pressing a button
    public Player player;
    private Tomato tomato1;
    private Tomato tomato2;
    private Tomato tomato3;
    private Tomato tomato4;
    private Door door;
    private Door door2;
    private Text text;
    private Boss boss;
    
    GreenfootSound backgroundMusic = new GreenfootSound("backgroundMusic.mp3"); 

    //Arrays to add objects to World    
    private Platforms[] platforms;
    private Ladder[] ladders;
    private SmallPlatform[] smallPlatforms;
    private Canon[] canon;
    private Pedestal[] pedestal;

    private int health = 5; //Player's health
    private int timer = 300; //Timer variable to prevent player from losing health all at once
    public int level = 0; //Monitors which level the player is on

    /**
     * Constructor for objects of class Levels.
     * 
     */
    public Levels()
    {          
        super(1070, 570, 1);
              
        //Add the bottom platform in the menu screen
        platforms = new Platforms[7]; 
        for (int i = 0; i < platforms.length; i++)
        {
            platforms[i] = new Platforms();
        }
        bottomPlatform();

        //Add the Potato (player)
        player = new Player();
        addObject (player, 100, 512);  

        setBackground("menu.png");
    }

    public void act()
    {
        backgroundMusic.playLoop();
        changeLivesDisplay();
        checkKeys();
        player.checkFall();
        player.touchingTomato();
        //Check if player is touching canon bullet from level 2
        player.touchingCanonBullet();
        //Check if player is touching a thorn from Boss level
        player.touchingThorns();
        checkLives();
    }

    /*
     * Adds the floor/ bottom platform of each level
     */
    public void bottomPlatform()
    {
        //Bottom platform/ floor
        addObject (platforms[0], 141, 555);
        addObject (platforms[1], 424, 555);
        addObject (platforms[2], 700, 555);
        addObject (platforms[3], 950, 555);
    }

    /*
     * Updates the health text at the top left corner of the world 
     */
    public void changeLivesDisplay()
    {
        removeObject(text);
        //Add the lives text
        text = new Text("Lives: " + health);
        addObject(text, 60, 15);
    }

    public void checkKeys()
    {
        isKeyPressed = false;
        //Calls walkRight() from Player
        if (Greenfoot.isKeyDown("right"))
        {       
            player.walkRight();
            isKeyPressed = true;
        }
        //Calls walkRight() from Player
        if (Greenfoot.isKeyDown("left"))
        {
            player.walkLeft();
            isKeyPressed = true;
        } 

        if (Greenfoot.isKeyDown("up"))
        {
            //Calls jump() from Player if player is on the ground(standing on a platform) and is not touching a ladder
            if(player.checkOnGround() && !player.touchingLadder())
                player.jump();   

            // Calls climbUp() from Player if player is touching a ladder 
            else if(player.touchingLadder())
                player.climbUp();

            isKeyPressed = true;
        }
        if (Greenfoot.isKeyDown("down"))
        {
            //Calls potatoDuck() from Player if player is on the ground(standing on a platform) and is not touching a ladder
            if (player.checkOnGround() && !(player.touchingLadder()))
                player.potatoDuck();

            // Calls climbDown() from Player if player is touching a ladder 
            else if(player.touchingLadder())
                player.climbDown();

            isKeyPressed = true;
        }

        //Calls potatoShoot from Player if spacebar is pressed
        if (Greenfoot.isKeyDown("space"))
        { 
            player.potatoShoot();
            isKeyPressed = true;
        }
        if (Greenfoot.isKeyDown("enter"))
        { 
            //Changes level if player is touching a door
            if (player.touchingDoor())
            { 
                level += 1;
                changeLevels();               
            }
            //Goes to level one from the menu page when enter button is pressed 
            else if(level == 0)
            {
                level = 1;
                changeLevels();
            }               
        }
        //When no buttons are pressed, call standing still potato animation method 
        if (!(isKeyPressed))
        {
            player.idlePotato();
        }
    }

    public void checkLives()
    {
        if (player.death == true)        
        {
            if (timer == 300 && !(timer < 300))
            {
                //If timer is 300 and health is greater than 0, then decrease health
                if (health > 0)
                {    
                    health--;
                    changeLivesDisplay();
                    timer--;
                }
                if (health == 0)
                {
                    gameOver();
                }
            }
            /*
             * Keep on decreasing timer until it reaches 0 and then set it back to 300
             * This gives the player some time to move (without losing health) after coming in contact with a tomato
             */
            if (timer < 300)
            {
                timer--;
                if (player.facingRight)
                {
                    player.setImage("hurtRight.png");
                }

                if (!(player.facingRight))
                {
                    player.setImage("hurtLeft.png");    
                }                     
            }
            if (timer == 0)
            {
                timer = 300;
                player.death = false;
            }
        } 
    }

    /*
     * Removes the objects/ actors in each room when changing levels
     */
    public void removeAll()
    {
        if (level ==2)
        {
            removeObjects(getObjects(Platforms.class));
            removeObjects(getObjects(Ladder.class));
            removeObjects(getObjects(SmallPlatform.class));
            removeObjects(getObjects(Door.class));    
            removeObjects(getObjects(Tomato.class));    
            removeObjects(getObjects(Bullet.class));
            removeObjects(getObjects(DeadTomato.class));
            player.setLocation();
        }
        if (level == 3)
        {
            removeObjects(getObjects(Tomato.class));
            removeObject(door2);
            removeObjects(getObjects(Canon.class));
            removeObjects(getObjects(CanonBullet.class));
            removeObjects(getObjects(Bullet.class));
            removeObjects(getObjects(Pedestal.class));
            removeObjects(getObjects(Platforms.class));
            removeObjects(getObjects(DeadTomato.class));
        }
        if (level == 4)
        {
            removeObjects(getObjects(Platforms.class));
            removeObjects(getObjects(Text.class));
            removeObjects(getObjects(Bullet.class));
            removeObjects(getObjects(Ladder.class));
            removeObjects(getObjects(Door.class)); 
            removeObjects(getObjects(Boss.class)); 
            removeObjects(getObjects(Thorns.class));
            player.setLocation();
        }
    }

    public void gameOver()
    {
        //Print "Game Over" on screen and then stop the game
        addObject(new Text("Game Over"), 550, 300);
        backgroundMusic.pause();
        Greenfoot.stop();
    }

    public void changeLevels()
    {
        if (level == 1)
        {
            setBackground("bricks2.jpg");
            setPaintOrder(Player.class, Tomato.class, DeadTomato.class, Ladder.class, Platforms.class);

            smallPlatforms = new SmallPlatform[6];
            ladders = new Ladder[8];

            for (int i = 0; i < smallPlatforms.length; i++)
            {
                smallPlatforms[i] = new SmallPlatform();
            }
            for (int i = 0; i < ladders.length; i++)
            {
                ladders[i] = new Ladder();
            }

            //Add platforms to the world 
            bottomPlatform();
            //First row of platforms 
            addObject (platforms[4], 141, 325);
            addObject (platforms[5], 500, 380);
            addObject (smallPlatforms[0], 818, 355);

            //Second row of Platforms
            addObject (smallPlatforms[1], 390, 200);
            addObject (smallPlatforms[2], 600, 275); 
            addObject (smallPlatforms[3], 960, 218);

            //Third row of Platforms
            addObject (smallPlatforms[4], 190, 105);
            addObject (smallPlatforms[5], 680, 88);
            addObject (platforms[6], 940, 115);

            //Add ladders 
            //First row ladders
            addObject (ladders[0], 240, 390);

            addObject (ladders[1], 560, 440);

            addObject (ladders[2], 760, 415);

            //Second row ladders
            addObject (ladders[3], 442, 260);

            addObject (ladders[4], 990, 278);
            addObject (ladders[5], 990, 418);

            //Third row ladders
            addObject (ladders[6], 130, 163);

            addObject (ladders[7], 650, 147);

            //Add the door
            door = new Door();
            addObject (door, 995, 55);

            //Add enemies to the world
            tomato1 = new Tomato(30,243);
            tomato2 = new Tomato(320,458);
            tomato3 = new Tomato(600,749);
            tomato4 = new Tomato(743,876);

            addObject (tomato1, 40, 300);
            addObject (tomato2, 330, 170);       
            addObject (tomato3, 610, 60);
            addObject (tomato4, 753, 328);
        }
        if (level == 2)
        {
            removeAll();
            setBackground("bluerock.jpg");

            platforms = new Platforms[12]; 
            canon = new Canon[2];
            pedestal = new Pedestal[2];

            setPaintOrder(Player.class, Tomato.class,  DeadTomato.class, Canon.class, Platforms.class);
            for (int i = 0; i < platforms.length; i++)
            {
                platforms[i] = new Platforms();
            }
            for (int i = 0; i < canon.length; i++)
            {
                canon[i] = new Canon();
            }
            for (int i = 0; i < pedestal.length; i++)
            {
                pedestal[i] = new Pedestal();
            }
            //Add platforms to the world       
            bottomPlatform();

            //first row of Platforms 
            addObject (platforms[4], 141, 420);
            addObject (platforms[5], 550, 420);
            addObject (platforms[6], 996, 420);
            //second row platforms
            addObject (platforms[7], 400, 270);
            addObject (platforms[8], 800, 270);
            //third row of plarforms 
            addObject (platforms[9], 140, 120);
            addObject (platforms[10], 550, 120);
            addObject (platforms[11], 996, 120);
            //Add pedestals for canons
            addObject (pedestal[0], 42, 388);
            addObject (pedestal[1], 42, 88);
            //Add canons 
            addObject (canon[0], 48, 350);
            addObject (canon[1], 48, 50);

            //Add door
            door2 = new Door();
            addObject (door2, 550, 496);

            //Add Tomatoes 
            tomato1 = new Tomato(320,510);
            tomato2 = new Tomato(720, 910);

            addObject(tomato1, 400, 242);
            addObject (tomato2, 800, 242);

        }

        if (level == 3)
        {
            removeAll();
            setBackground("granite-light.jpg");

            setPaintOrder(Player.class, Text.class, Thorns.class, Boss.class, Ladder.class, Platforms.class);

            boss = new Boss(); 
            addObject(boss, 550, 338);

            player.setLocation();
            //Add platforms 
            bottomPlatform();
            //Left platform
            addObject(new Platforms(), 141, 160);
            //Middle platform
            addObject(new Platforms(), 550, 95);        
            //Right platform
            addObject(new Platforms(), 995, 160);

            //Ladder for left platform
            addObject(new Ladder(), 120, 225);
            addObject(new Ladder(), 120, 370);
            addObject(new Ladder(), 120, 450);
            //Ladder for right platform
            addObject(new Ladder(), 1000, 225);            
            addObject(new Ladder(), 1000, 370);
            addObject(new Ladder(), 1000, 450);

        }
        if (level == 4)
        {
            removeAll();
            setBackground("ATbackground.jpg");
            addObject(new Text("Congratulations! You Win"), 550, 240);
            backgroundMusic.pause();
            Greenfoot.stop();
        }
    }
}