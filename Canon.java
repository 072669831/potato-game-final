import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Canon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Canon extends Enemies
{
    private int randomShoot = 0;
    public void act()
    {
        //If the random number generated is 1 or 2, then add a canon bullet to the world 
        //Prevents the canon from continuously shooting out bullets
        randomShoot = Greenfoot.getRandomNumber(200);
        if (randomShoot == 1 || randomShoot == 2 )
            getWorld().addObject (new CanonBullet(), getX(), getY());
    }    
}
