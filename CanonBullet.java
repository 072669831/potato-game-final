import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CanonBullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CanonBullet extends Canon
{
    private boolean removeMe;
    private Player player;

    public CanonBullet ()
    {
        removeMe = false;
        setImage("canonBullet.png");
    }

    public void act() 
    {
        move(8);  
        //Removes canon bullet if it is at the edge of the world 
        if (isAtEdge())
        {
            removeMe = true;
        }
        if (removeMe)
        {
            getWorld().removeObject(this);
        }
    }  
}
