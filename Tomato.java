import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Tomato here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tomato extends Enemies
{
    ////////////////////////////////////////////////////////Start of borrowed code from Mr.Cohen///////////////////////////////////////////////////////////////
    //Arrays for animation
    private GreenfootImage[] walkRight;
    private GreenfootImage[]walkLeft;
    //boolean for controlling the flow of the action
    private boolean facingRight;
    //Controls speed of animation
    private int animationCounter;
    private int animationDelay;
    private int animationDelayCounter;
    //Parameters given to Tomato to tell it when to turn on the platform 
    private int platformLengthStart, platformLengthEnd;
    private int platformStart, platformEnd;
    public Tomato(int platformLengthStart, int platformLengthEnd)
    {
        String fileName;
        //initialize walking arrays
        walkRight = new GreenfootImage[6];
        walkLeft = new GreenfootImage[6];

        for (int i = 0; i < walkRight.length; i++)
        {
            //Building the file name for potato walking right
            fileName = "TomatoWalkRight" + (i+1) + ".png";
            //Building array with potato walking right photos
            walkRight[i] = new GreenfootImage(fileName);
            //Flip the walkRight array to make walkLeft
            walkLeft[i] = new GreenfootImage (walkRight[i]);
            walkLeft[i].mirrorHorizontally();
        }
        //Starting values for control variable
        facingRight = true;
        // How many acts as a delay between changing frames
        animationDelay = 8;
        // Start delay counter  at 0
        animationDelayCounter = 0;
        // Start the animation counter at 0. The animation counter keeps
        // track of what frame the animation is on, and gets reset if the
        // player changes direction. It is only updated every 
        // *animationDelayCounter* acts, so the animation isn't too fast.
        animationCounter = 0;

        //Set starting image
        this.setImage("TomatoWalkRight1.png");
        ////////////////////////////////////////////////////////////////////End of borrowed code//////////////////////////////////////////////////////////////

        platformStart = platformLengthStart;
        platformEnd = platformLengthEnd;
    }

    public void act() 
    {
        ////////////////////////////////////////////////////////////////Borrowed code from Mr.Cohen///////////////////////////////////////////////////////////
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
        ///////////////////////////////////////////////////////////////////End of borrowed code///////////////////////////////////////////////////////////////
        walk(platformStart, platformEnd);
    }

    /*
     * Tomato walks until it hits the end of the platform (platformStart/ plarformEnd), turns around and continues walking again
     */
    public void walk(int platformStart, int platformEnd)
    {    
        if (facingRight == true)
        {  
            setImage (walkRight[animationCounter]);
            move(1);
            //If location of tomato is greater than the location of the end of the platform, turn around (facingRight = false)
            if (getX() >= platformEnd)
                facingRight = false;
        }

        if (facingRight == false)
        {
            setImage (walkLeft[animationCounter]);   
            move(-1);
            //If location of tomato is greater than the location of the start of the platform, turn around (facingRight = true)
            if (getX() <= platformStart)
                facingRight = true;
        } 
    }
}
