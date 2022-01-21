package GameComponents;

import java.awt.*;

public class Block extends GameObject
{
    public Block(int posX, int posY, Color color)
    {
        this.posX = posX;
        this.posY = posY;
        this.color = color;

        this.width = 50;
        this.height = 25;
    }
}
