import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{  
    public boolean death = false;
    private Text text;
    /////////////////////////////////////////////////////Start of borrowed code from Mr.Cohen////////////////////////////////////////////////////////////////
    //Arrays for walking animation
    private GreenfootImage[] walkRight;
    private GreenfootImage[]walkLeft;
    //Standing animation arrays
    private GreenfootImage[] idlePotatoRight; 
    private GreenfootImage[] idlePotatoLeft;
    //booleans for controlling the flow of the action
    private boolean walking;
    public boolean facingRight;
    //Controls speed of animation
    private int animationCounter;
    private int animationDelay;
    private int animationDelayCounter;
    private int speed;

    private int jumpStrength = 13; //How high player will jump
    private int vSpeed = 0; //Vertical speed (for jumping)
    private int acceleration = 2; //Falling down speed

    public Player()
    {       
        String fileName, fileName2, fileName3;
        //initialize walking arrays
        walkRight = new GreenfootImage[7];
        walkLeft = new GreenfootImage[7];
        //initialize idle arrays
        idlePotatoRight = new GreenfootImage[8];
        idlePotatoLeft = new GreenfootImage[8];

        for (int i = 0; i < walkRight.length; i++)
        {
            //Building the file name for potato walking right
            fileName = "PotatoWalkRight" + (i+1) + ".png";
            //Building array with potato walking right photos
            walkRight[i] = new GreenfootImage(fileName);
            //Flip the walkRight array to make walkLeft
            walkLeft[i] = new GreenfootImage (walkRight[i]);
            walkLeft[i].mirrorHorizontally();
        }

        for (int i = 0; i < idlePotatoRight.length; i++)
        {
            //Animation for when potato isn't moving 
            fileName2 = "IdlePotato" + (i+1) + ".png";
            idlePotatoRight[i] = new GreenfootImage (fileName2);
            idlePotatoLeft[i] = new GreenfootImage(idlePotatoRight[i]);
            idlePotatoLeft[i].mirrorHorizontally();
        }

        //Starting values for control variables
        walking = false;
        facingRight = true;

        //Actor's speed
        speed = 4;
        // How many acts as a delay between changing frames
        animationDelay = 6;
        // Start delay counter  at 0
        animationDelayCounter = 0;
        // Start the animation counter at 0. The animation counter keeps
        // track of what frame the animation is on, and gets reset if the
        // player changes direction. It is only updated every 
        // *animationDelayCounter* acts, so the animation isn't too fast.
        animationCounter = 0;

        //Set starting image
        this.setImage("PotatoWalkRight1.png");

    }

    public void act() 
    {   
        animationDelayCounter++;
        if (animationDelayCounter == animationDelay)
        {
            animationCounter++;
            animationDelayCounter = 0;
        }

        if (animationCounter > walkRight.length-1)
        {
            animationCounter = 0;
        }
    }

    /**
     * Called by Levels, causes the player to move left when left arrow key is pressed.
     */
    public void walkLeft()
    {
        // Check if direction is changing, and if so, reset counter
        if (facingRight)
            animationCounter = 0;
        // Set control booleans to not facing right and walking
        walking = true;
        facingRight = false;
        // Set the appropriate image. Note that animationCounter is
        // controlled by the act() method.
        setImage (walkLeft[animationCounter]);
        // Move the actor
        setLocation (getX() - speed, getY());   
    }

    /**
     * Called by Levels, causes the player to move right when right arrow key is pressed.
     */
    public void walkRight()
    {
        // Check if direction is changing, and if so, reset counter
        if (!(facingRight))
            animationCounter = 0;
        // Set control booleans to facing right and walking
        walking = true;
        facingRight = true;
        // Set the appropriate image. Note that animationCounter is
        // controlled by the act() method.
        setImage (walkRight[animationCounter]);
        // Move the actor
        setLocation (getX() + speed, getY());
    }
    //////////////////////////////////////////////////////////////////End of borrowed code//////////////////////////////////////////////////////////////////////
    /**
     * Called by Levels, causes the player to stand still.
     * This method gets called when the World decides that neither
     * directional button has been pushed (walking = false).
     */
    public void idlePotato()
    {
        if (facingRight)
        {
            if (animationCounter > 7)
                animationCounter = 0;
            // Set control booleans to facing right and walking
            walking = false;
            facingRight = true;
            // Set the appropriate animation
            setImage (idlePotatoRight[animationCounter]);
        }
        else
        {
            if (animationCounter > 7)
                animationCounter = 0;
            // Set control booleans to facing right and walking
            walking = false;
            facingRight = false;
            // Set the appropriate animation
            setImage (idlePotatoLeft[animationCounter]);
        }
    }

    /*
     * Called by Levels when the down arrow key is pressed (causes player to duck)
     */
    public void potatoDuck()
    {
        setImage ("potatoDuck.png");
    }

    /*
     * Method called from Levels when player is pressing the up arrow key and touching a ladder
     */
    public void climbUp()
    {
        setImage("backPotato.png");
        setLocation(getX(), getY() - 3);
    }

    /*
     * Method called from Levels when player is pressing the down arrow key and touching a ladder
     */
    public void climbDown()
    {
        setImage("backPotato.png");
        setLocation(getX(), getY() + 3);
    }   

    /*
     * Called by Levels when space button is pressed. Adds bullets to the world.
     */
    public void potatoShoot()
    {
        getWorld().addObject (new Bullet(facingRight), getX(), getY());
        Greenfoot.playSound("shoot.mp3");
    }

    ///////////////////////////////////////////Start of borrowed code from http://www.greenfoot.org/scenarios/3061/////////////////////////////////////////////
    /*
     * Called from Levels when the up arrow key is pressed and player is on a platform but not touching a ladder
     * Sets the vertical speed as negative jumpStrength (how high the player will "jump" since the y-coordinates 
     * at the top of the screen is 0 - negative vSpeed moves player up the screen) 
     * and then calls the fall() method to set its location/ "fall"
     */
    public void jump()
    {
        vSpeed = (-jumpStrength);
        fall();
    }

    /*
     * Sets the location of the Player to make it jump
     * Add acceleration (the speed the player "falls") to vSpeed to move the player down the screen/ fall
     */
    private void fall()
    {
        setLocation (getX(), getY() + vSpeed);
        vSpeed = vSpeed + acceleration;
    }

    /*
     * Check if the player is on the platform/ ladder. If not, then call the fall method until player is on a platform or ladder
     */
    public void checkFall()
    {
        if (checkOnGround() || touchingLadder())
            vSpeed = 0;
        else
            fall();
    }
    ///////////////////////////////////////////////////////////////////////////End of borrowed code////////////////////////////////////////////////////////////

    /*
     * Checks if player is standing on the platform (checks if bottom of potato is touching the platform)
     * returns true if player is standing on platform and false otherwise.
     */
    public boolean checkOnGround()
    {
        Object potato = getOneObjectAtOffset(0, getImage().getHeight()/2, Platforms.class);
        if (potato != null)
            return true;
        else
            return false;
    }

    /*
     * Method that checks if the player is touching a ladder (used by Levels)
     */
    public boolean touchingLadder()
    {
        if (isTouching(Ladder.class))
            return true;
        else
            return false;
    }

    /*
     * Method that checks if the player is touching a door (used by Levels)
     */
    public boolean touchingDoor()
    {
        if (isTouching(Door.class))
            return true;
        else
            return false;
    }

    /*
     * Checks if player is touching a tomato. If true, then call the checkLives method from Levels to decrease player's health
     */
    public void touchingTomato()
    {
        if (isTouching (Tomato.class))
        {
            death = true;
            Levels world1 = (Levels)getWorld();
            world1.checkLives(); 
        }
    }

    /*
     * Checks if player is touching a canon bullet. If true, then call the checkLives method from Levels to decrease player's health
     */
    public void touchingCanonBullet()
    {
        CanonBullet bullet = (CanonBullet)getOneIntersectingObject(CanonBullet.class);
        if (bullet != null)
        {
            Levels world1 = (Levels)getWorld();
            death = true;
            world1.checkLives();
            world1.removeObject(bullet);    
        }
    }

    /*
     * Checks if player is touching a thorn from the Boss. If true, then call the checkLives method from Levels to decrease player's health
     */
    public void touchingThorns()
    {    
        Thorns thorn = (Thorns)getOneIntersectingObject(Thorns.class);
        if (isTouching (Thorns.class))
        {
            death = true;
            Levels world1 = (Levels)getWorld();
            world1.checkLives(); 
            world1.removeObject(thorn);
        }
    }

    /*
     * This is called by Levels when changing levels/rooms to set the player in a different location in the world
     */
    public void setLocation()
    {
        setLocation (500, 20);
        Levels world1 = (Levels)getWorld();
        if (world1.level == 4)
            setLocation(500, 500);
    }
}
