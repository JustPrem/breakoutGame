import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.time.Duration;
import java.time.Instant;

public class Frame extends JFrame
{
    public static MenuWindow menu;
    public static GameWindow game;
    public Boolean isGameRunning = false;

    public Frame()
    {
        this.setTitle("JavaBreakOut");
        this.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
        this.setResizable(false);
        this.setVisible(true);
    }

    /** Changes the windows. **/
    public void ChangeWindow(int window)
    {
        if (this.getContentPane().getComponents() != null)
        {
            this.getContentPane().removeAll();
        }

        switch (window)
        {
            case 0:
            {
                isGameRunning = false;
                this.getContentPane().add(menu = new MenuWindow(this));
                break;
            }
            case 1:
            {
                isGameRunning = true;
                this.getContentPane().add(game = new GameWindow(this));
                this.transferFocus();                                                   // Give new wi, otherwise controls won't work till the window is interacted with.
                break;
            }
        }

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
