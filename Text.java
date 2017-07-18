import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Text here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Text extends Actor
{
    public Text(String text)
    {
        //Uses the String from the parameter to create a text with a transparent background
        setImage(new GreenfootImage (text, 25, Color.BLACK, new Color(0,0,0,0)));
    }
}


