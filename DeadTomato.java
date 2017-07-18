import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DeadTomato here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DeadTomato extends Enemies
{
    private int timer = 150;
    public DeadTomato()
    {
        setImage("tomatoHit.png");
        this.getImage().setTransparency(210);
    }

    /*
     * Remove the dead tomato when timer equals 0
     */
    public void act()
    {
        timer--;
        if (timer == 0)
        {
            Levels world1 = (Levels)getWorld();
            world1.removeObject(this);
        }
    }
}
