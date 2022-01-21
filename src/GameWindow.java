import GameComponents.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class GameWindow extends Canvas implements Runnable
{
    public Frame frame;
    Thread thread;
    Boolean isRunning = false;
    Random random = new Random();

    //region Tick System.
        public float frameRateLock = 60;
        public Instant lastTick = Instant.now();
    //endregion

    //region Components.
        Platform platform = new Platform(320 - 75, 440);
        Block[] blocks = new Block[0];
        Ball ball = new Ball(platform.posX + (platform.width / 2) - 10, platform.posY - 25);
    //endregion

    //region Functions.
        Boolean isKey = false;
        int platformSpeed = 15;
        int winCounter = 0;
        int points;
    //endregion

    public GameWindow(Frame frame)
    {
        this.frame = frame;

        this.setPreferredSize(new Dimension(640, 480));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyListener());

        Start();
    }
    /** Start Game Thread. **/
    synchronized void Start()
    {
        isRunning = true;
        thread = new Thread(this, "Game");
        thread.start();
    }
    /** Stop Game Thread. **/
    synchronized void Stop() throws InterruptedException
    {
        thread.join();
        isRunning = false;
    }
    /** Run the Program. **/
    public void run()
    {
        CreateBlocks();

        while(isRunning)
        {
            Update();
        }
    }
    /** Updates the Game Engine. **/
    public void Update()
    {
        // Game Ticks
        if (Duration.between(lastTick, Instant.now()).toMillis() > 1000 / frameRateLock)
        {
            GameUpdate();
            Render();
            lastTick = Instant.now();
        }
    }
    /** Handles Game Event Updates. **/
    private void GameUpdate()
    {
        //TODO Game Update.
        isKey = true;

        CheckBlockCollision();

        ball.Update(platform, this, blocks);

        // Remove Null from Blocks Array.
        while(CheckBlockArray())
        {
            for (int o = 0; o < blocks.length; o++)
            {
                if (blocks[o] == null)
                {
                    if (o != blocks.length - 1)
                    {
                        blocks[o] = blocks[o + 1];
                        blocks[o + 1] = null;
                    }
                }
            }

            Block[] oldBlocks = blocks;
            blocks = new Block[oldBlocks.length - 1];

            for (int o = 0; o < blocks.length; o++)
            {
                blocks[o] = oldBlocks[o];
            }
        }

        if (blocks.length == 0)
        {
            CreateBlocks();
            winCounter++;
            ball.isEjected = false;
            ball.speed = 5 + (5 * winCounter);
        }

        if (ball.isDead)
        {
            frame.ChangeWindow(0);
        }
    }
    /** Handles On-Screen rendering updates. **/
    private void Render()
    {
        BufferStrategy bufferStrategy = this.getBufferStrategy();

        // if a Buffer strategy doesn't exist, create a new one with a param of 3.
        if (bufferStrategy == null)
        {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bufferStrategy.getDrawGraphics();

        //region Render Objects Here.
            // Background.
            g.setColor(new Color(100, 100, 100));
            g.fillRect(0, 0, getWidth(), getHeight());

            // Platform.
            g.setColor(Color.BLACK);
            g.fillRect(platform.posX - 2, platform.posY - 2, platform.width + 4, platform.height + 4);
            g.setColor(platform.color);
            g.fillRect(platform.posX, platform.posY, platform.width, platform.height);

            // Blocks.
            if (blocks.length != 0)
            {
                for (int i = 0; i < blocks.length; i++)
                {
                    if (blocks[i] != null)
                    {
                        g.setColor(Color.BLACK);
                        g.fillRect(blocks[i].posX - 2, blocks[i].posY - 2, blocks[i].width + 4, blocks[i].height + 4);
                        g.setColor(blocks[i].color);
                        g.fillRect(blocks[i].posX, blocks[i].posY, blocks[i].width, blocks[i].height);
                    }
                }
            }

            // Ball.
            g.setColor(Color.BLACK);
            g.fillRoundRect(ball.posX - 2, ball.posY - 2, ball.width  + 4, ball.height + 4, ball.width + 4, ball.height + 4);
            g.setColor(ball.color);
            g.fillRoundRect(ball.posX, ball.posY, ball.width, ball.height, ball.width, ball.height);

            // Points.
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("Points: " + points, 10, 480 - 12);
        //endregion

        g.dispose();
        bufferStrategy.show();
    }
    /** Create Blocks. **/
    private void CreateBlocks()
    {
        int xLimit = 12;
        int yLimit = 6;

        for (int x = 0; x < xLimit; x++)
        {
            for (int y = 0; y < yLimit; y++)
            {
                AddBlock(new Block(8 + (52 * x), 10 + (27 * y), RandomColor(x + y)));
            }
        }
    }
    /** Get Random Color. **/
    private Color RandomColor(int i)
    {
        while (i > 3)
        {
            i -= 4;
        }

        switch (i)
        {
            case 0:
                return new Color(75, 75, 250);
            case 1:
                return Color.RED;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.ORANGE;
            case 4:
                return Color.PINK;
        }

        return null;
    }
    /** Add a new Block to the Blocks Array **/
    private void AddBlock(Block block)
    {
        if (blocks.length == 0)
        {
            blocks = new Block[] {block};
        }
        else
        {
            Block[] oldBlocks = blocks;
            blocks = new Block[oldBlocks.length + 1];

            for (int i = 0; i < blocks.length; i++)
            {
                if (i == oldBlocks.length)
                {
                    blocks[i] = block;
                }
                else
                {
                    blocks[i] = oldBlocks[i];
                }
            }
        }
    }
    /** Check for Nulls in Blocks Array **/
    private Boolean CheckBlockArray()
    {
        for (int i = 0; i < blocks.length; i++)
        {
            if (blocks[i] == null)
            {
                return true;
            }
        }
        return false;
    }
    /** Check Block Collision **/
    private void CheckBlockCollision()
    {
        for (int i = 0; i < blocks.length; i++)
        {
            if (ball.CollisionCheck(blocks[i]))
            {
                if (ball.GetBounceAngle(blocks[i]) != Ball.BounceAngle.Null)
                {
                    ball.Bounce(blocks[i]);
                    blocks[i] = null;
                    points += 100;
                    break;
                }
            }
        }
    }
    /** The main Key Listener **/
    public class MyKeyListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if (isKey)
            {
                switch(e.getKeyCode())
                {
                    case KeyEvent.VK_LEFT:
                    {
                        // Change Ball Eject Direction.
                        if (!ball.isEjected)
                        {
                            ball.ChangeDirection(-45);
                        }

                        // Move the Platform.
                        if (platform.posX > 0)
                        {
                            platform.posX -= platformSpeed + (10 * winCounter);
                            isKey = false;
                        }
                        break;
                    }
                    case KeyEvent.VK_RIGHT:
                    {
                        // Change Ball Eject Direction.
                        if (!ball.isEjected)
                        {
                            ball.ChangeDirection(45);
                        }

                        // Move the Platform.
                        if (platform.posX + platform.width < getWidth())
                        {
                            platform.posX += platformSpeed + (10 * winCounter);
                            isKey = false;
                        }
                        break;
                    }
                    case KeyEvent.VK_SPACE:
                    {
                        ball.Eject();
                    }
                }
            }
        }
    }
}