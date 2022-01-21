package GameComponents;

import javafx.geometry.Bounds;

import javax.swing.*;
import java.awt.*;

public class Ball extends GameObject
{
    public enum BounceAngle
    {
        Top,
        Right,
        Bottom,
        Left,
        Null
    }

    public int direction = 45;
    public int speed = 5;

    public Boolean isEjected = false;
    public Boolean isDead = false;

    public Ball(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
        this.color = Color.WHITE;
        this.height = 20;
        this.width = 20;
    }

    public void Update(Platform platform, Canvas canvas, Block[] blocks)
    {
        //region Circle around the Direction.
            if (direction < 0)
            {
                direction += 360;
            }
            else if (direction > 360)
            {
                direction -= 360;
            }
        //endregion
        //region  Check if the Ball has been ejected from the platform.
            if (isEjected)
            {
                posX += Math.round(speed * Math.sin(direction * Math.PI/180));
                posY -= Math.round(speed * Math.cos(direction * Math.PI/180));
            }
            else
            {
                this.posX = platform.posX + (platform.width / 2) - 10;
                this.posY = platform.posY - 25;
            }
        //endregion
        //region  Bounce the ball off walls.
            // Left Wall.
            if (posX <= 0)
            {
                if (direction > 270)
                {
                    direction += 90;
                }
                else
                {
                    direction -= 90;
                }
            }

            // Top Wall.
            if (posY <= 0)
            {
                if (direction < 360 && direction > 270)
                {
                    direction -= 90;
                }
                else if (direction > 0 && direction < 90)
                {
                    direction += 90;
                }
            }

            // Right Wall.
            if (posX + width > canvas.getWidth())
            {
                if (direction > 90)
                {
                    direction += 90;
                }
                else
                {
                    direction -= 90;
                }
            }

            // Bottom Wall.
            if (posY > canvas.getHeight())
            {
                isDead = true;
            }
        //endregion
        //region Bounce of Platform.
            if (posY + height >= platform.posY)
            {
                if (posX > platform.posX && (posX + width) < (platform.posX + platform.width))
                {
                    if (direction > 180)
                    {
                        direction = -45;
                    }
                    else
                    {
                        direction = 45;
                    }
                }
            }
        //endregion
    }
    /** Bounce of a Block **/
    public void Bounce(Block block)
    {
        switch (GetBounceAngle(block))
        {
            case Top:
            {
                if (direction > 180)
                {
                    direction += 90;
                }
                else
                {
                    direction -= 90;
                }
                break;
            }
            case Right:
            {
                if (direction > 270)
                {
                    direction += 90;
                }
                else
                {
                    direction -= 90;
                }
                break;
            }
            case Bottom:
            {
                if (direction < 360 && direction > 180)
                {
                    direction -= 90;
                }
                else if (direction > 0 && direction < 180)
                {
                    direction += 90;
                }
                break;
            }
            case Left:
            {
                if (direction > 90)
                {
                    direction += 90;
                }
                else
                {
                    direction -= 90;
                }
                break;
            }
        }
        System.out.println(GetBounceAngle(block));
    }
    public BounceAngle GetBounceAngle(Block block)
    {
        int blockX = block.posX + (block.width / 2);
        int blockY = block.posY + (block.height / 2);
        int ballX = posX + (width / 2);
        int ballY = posY + (height / 2);

        if (ballX > blockX && (ballY > block.posY && ballY < (block.posY + block.height)))
        {
            return BounceAngle.Right;
        }
        else if (ballX < blockX && (ballY > block.posY && ballY < (block.posY + block.height)))
        {
            return BounceAngle.Left;
        }
        else if (ballY < blockY && (ballX > block.posX && ballX < (block.posX + block.width)))
        {
            return BounceAngle.Top;
        }
        else if (ballY > blockY && (ballX > block.posX && ballX < (block.posX + block.width)))
        {
            return BounceAngle.Bottom;
        }

        return BounceAngle.Null;
    }
    public void Eject()
    {
        isEjected = true;
    }
    public void ChangeDirection(int i)
    {
        direction = i;
    }
}

