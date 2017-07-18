import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Thorns here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Thorns extends Enemies
{
    private int randomRotation = 0;
    private boolean removeMe;
    public Thorns()
    {
        removeMe = false;
        //Randomly sets the rotation of the thorns
        randomRotation = Greenfoot.getRandomNumber(360);
        setRotation(randomRotation);
    }

    /**
     * Act - do whatever the Thorns wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        move(6);
        //Remove thorn if it is at the edge of the world
        if (isAtEdge())
            removeMe = true;
        if (removeMe)
        {
            getWorld().removeObject(this);
        }
    }    
}
