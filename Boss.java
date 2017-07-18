import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Boss here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Boss extends Enemies
{
    public int health = 100;
    private Text text;
    private Boss boss; 
    private int shoot = 0;

    public Boss()
    {
        setImage("Boss.png");
    }

    public void act()
    {
        randomShoot(); 
        touchingBullet();       
    }   

    private void touchingBullet()
    {
        Bullet bullet = (Bullet)getOneIntersectingObject(Bullet.class);
        if (bullet != null)       
        {
            setImage("BossHurt.png");
            if (health > 0)
            {    
                health--;
                displayHealth();
            }
            if (health <= 0)
            {
                bossDeath();
            }
            //Removes the bullet it touched from the world 
            getWorld().removeObject(bullet);
        } 
    }

    private void randomShoot()
    {
        if (this != null)
        {
            //Shoots out thorns if the random number generated is 1 or 2 (limits the flow of the thorns)
            shoot = Greenfoot.getRandomNumber(30);
            if (shoot == 1 || shoot == 2 )
                getWorld().addObject (new Thorns(), getX(), getY());
        }
    }

    /*
     * Displays the Boss' health
     */
    public void displayHealth()
    {
        //Remove the previous text object 
        getWorld().removeObject(text);
        //Add the boss lives text
        text = new Text("Boss Lives: " + health);
        getWorld().addObject(text, 80, 40);
    }

    /*
     * When the boss dies, remove boss and create a door for the player to "escape" the dungeon 
     */
    public void bossDeath()
    {
        Levels world1 = (Levels)getWorld();
        world1.addObject(new Door(), 550, 495);
    }
}