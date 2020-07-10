import javax.swing.*;

public class Main extends JFrame
{
    public static void main (String[]args)
    {
        ClassManager rf=new ClassManager();
        rf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        rf.setSize(650,570);
        /*rf.setResizable(false);*/
        rf.setLocation(420,150);
        rf.setTitle("Operacje na plikach");
        rf.setVisible(true);
    }

}
