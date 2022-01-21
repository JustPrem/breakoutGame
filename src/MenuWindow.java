import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow extends JPanel
{
    public Frame frame;

    //region Components.
    JButton PlayButton;
    //endregion

    public MenuWindow(Frame frame)
    {
        this.frame = frame;

        this.setPreferredSize(new Dimension(640, 480));
        this.setLayout(null);
        this.setBackground(new Color(50, 50, 50));
        this.setFocusable(true);

        // Create a new button.
        PlayButton = new JButton("Play");
        PlayButton.setBounds(
                270,
                240,
                100,
                40
        );
        // Add an action listener to the button to switch windows.
        PlayButton.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        frame.ChangeWindow(1);
                    }
                }
        );
        this.add(PlayButton);
    }
}