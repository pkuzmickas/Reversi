/**
 * Handles the player information
 * 
 * @author Paulius Kuzmickas
 * @version 1.0
 */
public class Player
{
    int color;
    String name;
    boolean isComputer;
    int numOfPieces=2;   
 
    /**
     * A method to get the player's color.
     * 
     * @return returns the color of the player.
     */
    public int getColor()
    {
        return color;
    }
    /**
     * A method to set the player's color.
     * 
     * @param num set the number of the color.
     */
    public void setColor(int num)
    {
        color = num;
    }
    /**
     * A method to get the player's name.
     * 
     * @return returns the name of the player.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * A method to set the player's name.
     * 
     * @param theName the name of the player
     */
    public void setName(String theName)
    {
        name = theName;
    }
    
    /**
     * A method to know if the player is a computer (AI).
     * 
     * @return returns true if the player is computer.
     */
    public boolean isItComputer()
    {
        return isComputer;
    }
    /**
     * A method to set if the player is computer.
     * 
     * @param state true if player is computer, false if not
     */
    public void setIfComputer(boolean state)
    {
        isComputer = state;
    }
}
