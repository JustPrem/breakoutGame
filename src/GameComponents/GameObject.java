package GameComponents;

import java.awt.*;

public abstract class GameObject
{
    public int posX = 0;
    public int posY = 0;
    public Color color = Color.WHITE;
    public int width = 1;
    public int height = 1;

    public Boolean CollisionCheck(GameObject other)
    {
        if (posX < (other.posX + other.width) && (posX + width) > other.posX && posY < (other.posY + other.height) && (posY + height) > other.posY)
        {
            return true;
        }
        return false;
    }
}


/* Old Collision Code
    if ((posX > other.posX && posX < (other.posX + other.width)) || ((posX + width) < other.posX && (posX + width) > (other.posX + other.width)))
        {
            // Check if the position is of this GameObject on the Y axis is inside the other GameObject Y position.
            if ((posY > other.posY && posY < (other.posY + other.height)) || ((posY + height) < other.posY && (posY + height) > (other.posY + other.height)))
            {
                return true;
            }
        }
 */