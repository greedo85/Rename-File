import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolBar extends JPanel implements ActionListener {
    private JLabel diskPathLabel = new JLabel("Katalog:");
    private JLabel fileStartsWithLabel = new JLabel("<html><body>Zmieñ  pliki<br><font size=2>(U¿yj listy Regex)" +
            "</font></center></body></body></html>");
    private JLabel dirStartsWithLabel = new JLabel("<html><body>Zmieñ katalogi<br>" +
            "<font size=2>(U¿yj listy Regex)</font></center></body></body></html>");
    private JLabel makeIndexHtmlLabel = new JLabel("<html><body>Utwórz index.html<br>" +
            "<font size=2>(na podst. TextPanelu)</font></center></body></body></html>");
    public JTextField filePathField = new JTextField(10);
    private JTextField dirStartsWithField = new JTextField(10);
    private JTextField fileStartsWithField = new JTextField(10);
    private JTextField indexHtmlSourceField = new JTextField(10);
    private JButton startButton = new JButton("Poka¿ pliki");
    private JButton upperCaseButton = new JButton("Zmieñ na du¿e");
    private JButton lowerCaseButton = new JButton("Zmieñ na ma³e");
    private JButton makeIndexHtmlButton = new JButton("Uwtórz index.html");
    private JCheckBox fileStartsWithCheckBox = new JCheckBox();
    private JCheckBox dirStartsWithCheckBox = new JCheckBox();
    public int checkBoxChoice;
    private InterFaceSetTextPanel interFaceSetTextPanel;
    private InterfaceReadTextPanel interfaceReadTextPanel;
    CheckBoxHandler handler = new CheckBoxHandler();

    public ToolBar() {
        Dimension dimension = getPreferredSize();
        dimension.width = 170;
        setPreferredSize(dimension);
        Border outerBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        Border innerBorder = BorderFactory.createTitledBorder("Zmieñ nazwy");
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        add(diskPathLabel);
        add(filePathField);
        add(startButton);
        setLayout(new BorderLayout());
        setLayout(new FlowLayout());

        add(fileStartsWithLabel);
        setLayout(new BorderLayout());
        add(fileStartsWithCheckBox, BorderLayout.EAST);
        setLayout(new FlowLayout());
        add(fileStartsWithField);
        add(dirStartsWithLabel);
        setLayout(new BorderLayout());
        add(dirStartsWithCheckBox, BorderLayout.EAST);
        setLayout(new FlowLayout());
        add(dirStartsWithField);
        add(upperCaseButton);
        add(lowerCaseButton);
        add(makeIndexHtmlLabel);
        add(indexHtmlSourceField);
        indexHtmlSourceField.setText("D:\\scadar_NET\\foto\\400x300");
        add(makeIndexHtmlButton);
        fileStartsWithCheckBox.addItemListener(handler);
        dirStartsWithCheckBox.addItemListener(handler);
        startButton.addActionListener(this);
        upperCaseButton.addActionListener(this);
        lowerCaseButton.addActionListener(this);
        makeIndexHtmlButton.addActionListener(this);
    }

    public class CheckBoxHandler implements ItemListener {
        @Override
        public void itemStateChanged( ItemEvent e ) {
            Object source = e.getSource();
            JCheckBox checked = (JCheckBox) source;
            if (fileStartsWithCheckBox == checked) {
                checkBoxChoice = 1;
                dirStartsWithCheckBox.setSelected(false);
                dirStartsWithField.setText(null);
            }
            if (dirStartsWithCheckBox == checked) {
                checkBoxChoice = 2;
                fileStartsWithCheckBox.setSelected(false);
                fileStartsWithField.setText(null);
            }
        }
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        String diskPath = filePathField.getText();
        File file = new File(diskPath);
        Object source = e.getSource();
        JButton clicked = (JButton) source;
        if (clicked == startButton) {
            interFaceSetTextPanel.clearText();
            File[] dirList = file.listFiles();
            String[] dirContents = file.list();
            try {
                interFaceSetTextPanel.passText("Katalogi: \n------------");
                for (int i = 0; i < dirList.length; i++) {
                    if (dirList[i].isDirectory()) {
                        interFaceSetTextPanel.passText(dirContents[i]);
                    }
                }
                interFaceSetTextPanel.passText(" \nPliki: \n------------");
                for (int i = 0; i < dirList.length; i++) {
                    if (dirList[i].isFile()) {
                        interFaceSetTextPanel.passText(dirContents[i]);
                    }
                }
            } catch (Exception ex) {
                return;
            }
        } else if (clicked == upperCaseButton) {
            interFaceSetTextPanel.clearText();
            if (checkBoxChoice == 1) {
                try {
                    modifyFiles(diskPath, 1);
                } catch (Exception ex) {
                    interFaceSetTextPanel.passText("B³êdna komenda\n" + ex.toString());
                    ex.toString();
                }
            } else if (checkBoxChoice == 2) {
                try {
                    modifyDirs(diskPath, 1);
                } catch (Exception ex) {
                    interFaceSetTextPanel.passText("B³êdna komenda\n" + ex.toString());
                    ex.toString();
                }
            }
            revalidate();
        } else if (clicked == lowerCaseButton) {
            interFaceSetTextPanel.clearText();
            if (checkBoxChoice == 1) {
                try {
                    modifyFiles(diskPath, 2);
                } catch (Exception ex) {
                    interFaceSetTextPanel.passText("B³êdna komenda\n" + ex.toString());
                    ex.toString();
                }
            } else if (checkBoxChoice == 2) {
                try {
                    modifyDirs(diskPath, 2);
                } catch (Exception ex) {
                    interFaceSetTextPanel.passText("B³êdna komenda\n" + ex.toString());
                    ex.toString();
                }
            }
            revalidate();
        }
        if(clicked==makeIndexHtmlButton)
        {
            makeIndexHtml();
        }
    }
    public void makeIndexHtml()
    {
        String indexSource=indexHtmlSourceField.getText();
        File file=new File("src/index.html");
        if (file.exists())
        {
            file.delete();
        }
        String[] textArea=interfaceReadTextPanel.readTextPanel();
        RandomAccessFile randomAccessFile=null;
        try
        {
            randomAccessFile=new RandomAccessFile(file, "rw");
        }
        catch (FileNotFoundException e)
        {
            return;
        }
        String line="";
        try
        {
            randomAccessFile.writeBytes("<TABLE>\n");
            /*Zaczynamy od 1 indexu bo wkleja siê z Accessa z jpg*/
            for(int i=1;i<textArea.length;i++)
            {
                line=textArea[i];
                randomAccessFile.writeBytes("<TR><TD><img src=file:///"+indexSource+"\\"+
                line + ".jpg max height=120 ></TD></TR>"+"\n");
            }
            randomAccessFile.writeBytes("</TABLE>");
            if(true)
            {
                interFaceSetTextPanel.passText("\nUtworzy³em: "+file);
            }
            randomAccessFile.close();
        }
        catch (IOException e)
        {
            return;
        }
    }

    public void modifyFiles( String diskPath, int buttonUpperLowerVersion ) {
        String fileStartsWith = fileStartsWithField.getText();
        Pattern pattern = Pattern.compile(fileStartsWith);
        FilenameFilter fileFilter = new FilenameFilter() {
            @Override
            public boolean accept( File dir, String name ) {
                Matcher matcher = pattern.matcher(name);
                return matcher.matches();
            }
        };
        File file = new File(diskPath);
        File[] listOfFile = file.listFiles(fileFilter);
        String[] dirList = file.list(fileFilter);
        for (int i = 0; i < listOfFile.length; i++) {
            if (listOfFile[i].isFile() && !listOfFile[i].isDirectory()) {
                String oldName = dirList[i];
                String[] oldFileNameSplits = oldName.split("\\.");
                int extensionIndex = oldFileNameSplits.length - 1;
                File fileToMove = new File(diskPath + "/" + oldName);
                String newName = "";
                if (buttonUpperLowerVersion == 1) {
                    newName = oldFileNameSplits[0].toUpperCase() + "." + oldFileNameSplits[extensionIndex].toLowerCase();
                } else if (buttonUpperLowerVersion == 2) {
                    newName = oldFileNameSplits[0].toLowerCase() + "." + oldFileNameSplits[extensionIndex].toLowerCase();
                }
                fileToMove.renameTo(new File(diskPath + "/" + newName));
                if (true && !oldName.equals(newName)) {
                    interFaceSetTextPanel.passText("Zmieni³em: \n" + oldName + "\n" + newName);
                } else if (false || newName.equals(oldName)) {
                    interFaceSetTextPanel.passText("Nic nie zmieni³em: \n" + oldName + "\n" + newName);
                }
            }
        }
    }

    public void modifyDirs( String diskPath, int buttonUpperLowerVersion ) {
        String dirStartsWith = dirStartsWithField.getText();
        Pattern pattern = Pattern.compile(dirStartsWith);
        FilenameFilter dirFilter = new FilenameFilter() {
            @Override
            public boolean accept( File dir, String name ) {
                Matcher matcher = pattern.matcher(name);
                return matcher.matches();
            }
        };
        File file = new File(diskPath);
        File[] listOfFile = file.listFiles(dirFilter);
        String[] dirList = file.list(dirFilter);
        for (int i = 0; i < listOfFile.length; i++) {
            if (!listOfFile[i].isFile() && listOfFile[i].isDirectory()) {
                String oldName = dirList[i];
                File fileToMove = new File(diskPath + "/" + oldName);
                String newName = "";
                if (buttonUpperLowerVersion == 1) {
                    newName = oldName.toUpperCase();
                } else if (buttonUpperLowerVersion == 2) {
                    newName = oldName.toLowerCase();
                }
                fileToMove.renameTo(new File(diskPath + "/" + newName));
                if (true && !oldName.equals(newName)) {
                    interFaceSetTextPanel.passText("Zmieni³em: \n" + oldName + "\n" + newName);
                } else if (false || newName.equals(oldName)) {
                    interFaceSetTextPanel.passText("Nic nie zmieni³em: \n" + oldName + "\n" + newName);
                }
            }
        }
    }

    public void setInterFaceSetTextPanel( InterFaceSetTextPanel setTextPanel ) {
        this.interFaceSetTextPanel = setTextPanel;
    }
    public void readInterfaceTextPanel(InterfaceReadTextPanel readLines)
    {
        this.interfaceReadTextPanel=readLines;
    }
    public String readText() {
        return filePathField.getText();
    }
    public void setTextField(String txt)
    {
        filePathField.setText(txt);
    }
    public void setFileAndDirFields(String txt)
    {
        fileStartsWithField.setText(txt);
        dirStartsWithField.setText(txt);
    }
}