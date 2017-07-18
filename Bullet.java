import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Bullet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bullet extends Actor
{
    private boolean removeMe;
    private Tomato tomato;
    private Boss boss;
    
    //Variable for if player is facing right/left
    private boolean isFacingRight;
    public Bullet(boolean facingRight)
    {
        removeMe = false;
        isFacingRight = facingRight;
        setImage("ball.png");
    }

    /**
     * Act - do whatever the Bullet wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        //Checks which direction the player is facing and moves in that direction 
        if (isFacingRight)
            move(5);
        if (!(isFacingRight))
            move(-5);          
        
        //Checks if the bullet is touching a tomato enemy. If true, remove tomato 
        //and bullet and add "dead" tomato actor
        Tomato tomato = (Tomato)getOneIntersectingObject(Tomato.class);
        if (tomato != null)
        {                     
            Levels world = (Levels)getWorld();    
            world.addObject(new DeadTomato(), tomato.getX(), tomato.getY());
            world.removeObject(tomato);
            removeMe = true; 
        }

        //Checks if the bullet is touching the boss. If this is true and the boss' health is zero, remove the boss
        Boss boss = (Boss)getOneIntersectingObject(Boss.class);
        if (boss != null)
        {           
            Levels world = (Levels)getWorld();  
            if (boss.health <= 0)
                world.removeObject(boss);
        }
        
        //Remove bullet if at the edge of the world 
        if (isAtEdge())
        {
            removeMe = true;
        }
        //Removes bullet if removeMe == true;
        if (removeMe)
        {
            getWorld().removeObject(this);
        }
    }    
}
