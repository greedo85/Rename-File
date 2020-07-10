import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TextPanel extends JPanel
{
    JTextArea textArea;
    public TextPanel()
    {
        Border outerBorder= BorderFactory.createEmptyBorder(2, 2, 2, 2);
        Border innerBorder=BorderFactory.createTitledBorder("Podgl¹d");
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
        textArea=new JTextArea();
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea),BorderLayout.CENTER);
    }
    public void setTextArea(String text)
    {
        textArea.append(text+"\n");
    }
    public String[] readLineByLine()
    {
       return textArea.getText().split("\\n");
    }
    public void clearTextArea()
    {
        textArea.selectAll();
        textArea.replaceSelection("");
    }

}
