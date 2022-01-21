package GameComponents;

import java.awt.*;

public class Platform extends GameObject
{
    public Platform(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
        this.width = 150;
        this.height = 25;
        this.color = Color.WHITE;
    }
}
