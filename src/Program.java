import javax.swing.*;

public class Program
{
    //region Window Variables.
    static Frame frame;
    //endregion

    //region Program Variables.
    static Program program;
    //endregion

    public static void main(String[] args)
    {
        program = new Program();
    }

    public Program()
    {
        frame = new Frame();
        frame.ChangeWindow(0);
    }
}