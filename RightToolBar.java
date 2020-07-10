import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class RightToolBar extends JPanel implements ActionListener {
    private JLabel makeDirectoryLabel = new JLabel("<html><body><center>Utwórz katalog</center></body></html>");
    private JLabel deleteDirectoryNonEmptyLabel = new JLabel("<html><body><center>Usuñ katalog" +
            "<br><font size=2>(U¿yj listy Regex)</font></center></body></html>");
    private JLabel moveFilesMakeDirsLabel = new JLabel("<html><body><center>Przenieœ pliki " +
            "do katalogów <br>o tych samych nazwach<br><font size=2>(U¿yj listy Regex)</font></center></body></html>");
    private JTextField makeDirectoryNameField = new JTextField(10);
    public JTextField deleteDirectoryNonEmptyField = new JTextField(10);
    private JTextField moveFilesMakeDirsField = new JTextField(10);
    private JButton deleteDirectoryNonEmptyButton = new JButton("Usuñ");
    private JButton makeDirectoryButton = new JButton("Utwórz");
    private JButton moveFilesMakeDirsButton = new JButton("Przenieœ do");
    private JButton moveFilesFromDirsButton=new JButton("Przenieœ z");
    private InterFaceSetTextPanel interFaceSetTextPanel;
    private InterfaceReadField interfaceReadField;


    public RightToolBar() {
        Dimension dimension = getPreferredSize();
        dimension.width = 170;
        setPreferredSize(dimension);
        Border outerBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        Border innerBorder = BorderFactory.createTitledBorder("<html><body>Modyfikacje katalogów<br><font size=2>(œcie¿ka z" +
        " pola Katalog)</font></body></html>");
        add(makeDirectoryLabel);
        add(makeDirectoryNameField);
        add(makeDirectoryButton);
        add(moveFilesMakeDirsLabel);
        add(moveFilesMakeDirsField);
        add(moveFilesMakeDirsButton);
        add(moveFilesFromDirsButton);
        add(deleteDirectoryNonEmptyLabel);
        add(deleteDirectoryNonEmptyField);
        add(deleteDirectoryNonEmptyButton);
        makeDirectoryButton.addActionListener(this);
        deleteDirectoryNonEmptyButton.addActionListener(this);
        moveFilesMakeDirsButton.addActionListener(this);
        moveFilesFromDirsButton.addActionListener(this);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        Object source = e.getSource();
        JButton clicked = (JButton) source;
        String moveFiles = moveFilesMakeDirsField.getText();
        if (clicked == makeDirectoryButton) {
            makeDir();
        }  if (clicked == moveFilesMakeDirsButton) {
            interFaceSetTextPanel.clearText();
            moveFilesToDirs(moveFiles);
        }
        if (clicked==moveFilesFromDirsButton)
        {
            interFaceSetTextPanel.clearText();
            String path = interfaceReadField.readTextFromField();
            File file3 = new File(path);
            File[] dirList = file3.listFiles();
            String[] dirListed = file3.list();
            for(int i=0;i<dirList.length;i++) {
                if (dirList[i].isDirectory()) {
                moveFilesFromDirs(path+"/"+dirListed[i]);
                }
            }
        }
        if (clicked == deleteDirectoryNonEmptyButton) {
            interFaceSetTextPanel.clearText();
            String path = interfaceReadField.readTextFromField();
            String toDeleteName = deleteDirectoryNonEmptyField.getText();
            /*Najpierw listujemy katalogi które chcemy usun¹æ*/
            String forPattern = toDeleteName;
            Pattern pattern = compile(forPattern);
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept( File dir, String name ) {
                    Matcher matcher = pattern.matcher(name);
                    return matcher.matches();
                }
            };
            File file = new File(path);
            File[] listAllDirs=file.listFiles(filter);
            String[] listAll = file.list(filter);
            /*Wywo³ujemy metodê usuwaj¹c¹ rekurencyjnie nie puste katalogi dla ka¿dego z przefiltrowanej
             * listy*/

            for (int j = 0; j < listAll.length; j++) {
                if(listAllDirs[j].isDirectory()) {
                    deleteNonEmptyDirecotories(path+"/"+listAll[j]);
                }
            }
        }
    }
    public void moveFilesToDirs(String moveFiles) {
        String path = interfaceReadField.readTextFromField();
        String forPattern = moveFiles;
        Pattern pattern = compile(forPattern);
        FilenameFilter filterToMove = new FilenameFilter() {
            @Override
            public boolean accept( File dir, String name ) {
                Matcher matcher = pattern.matcher(name);
                return matcher.matches();
            }
        };
        File file = new File(path);
        File[] listFiltered = file.listFiles(filterToMove);
        String[] filteredFiles = file.list(filterToMove);
        for (int i = 0; i < listFiltered.length; i++) {
            if (listFiltered[i].isFile() && !listFiltered[i].isDirectory()) {
                String fileName = filteredFiles[i];
                String[] fileNameSplits = fileName.split("\\.");
                int extensionIndex = fileNameSplits.length - 1;
                String dirName = fileNameSplits[0];
                File file2 = new File(path + "/" + dirName);
                if (!file2.exists()) {
                    file2.mkdir();

                    interFaceSetTextPanel.passText("Utworzy³em katalog:\n " + file2);
                } else if (file2.exists()) {
                    interFaceSetTextPanel.passText("Katalog ju¿ istnieje!:\n " + file2);
                }
                File fileToMove = new File(path + "/" + fileName);
                fileToMove.renameTo(new File(path + "/" + dirName + "/" + fileName));
                if (true) {
                    interFaceSetTextPanel.passText("Przenios³em: \n" + fileName + " do " + dirName);
                } else if (false) {
                    interFaceSetTextPanel.passText("Nie przenios³em: \n" + fileName + " do " + dirName);
                }
            }
        }
    }
    public void deleteNonEmptyDirecotories( String absolutePath ) {

        File file2 = new File(absolutePath);
        File[] directoryList = file2.listFiles();
        for (int i = 0; i < directoryList.length; i++) {
            if (directoryList[i].isDirectory()) {
                deleteNonEmptyDirecotories(directoryList[i].getPath());
                if (true) {
                    interFaceSetTextPanel.passText("Usun¹³em katalog: \n" + (directoryList[i]));
                } else if (false) {
                    interFaceSetTextPanel.passText("Nie mogê usun¹æ \n" + (directoryList[i]));
                }
            }
            directoryList[i].delete();
        }
        file2.delete();
        if (true) {
            interFaceSetTextPanel.passText("Usun¹³em katalog: \n" + file2);
        } else if (false) {
            interFaceSetTextPanel.passText("Nie mogê usun¹æ \n" + file2);
        }
    }
    public void moveFilesFromDirs(String dirName)
    {
        String path = interfaceReadField.readTextFromField();
        File file4 = new File(dirName);
        File[] dirContent = file4.listFiles();
        String[] dirContentFile = file4.list();
        for (int j = 0; j < dirContent.length; j++){
            if(dirContent[j].isFile())
            {
                String fileName=dirContentFile[j];
                File fileToMoveBack=new File(dirName+"/"+fileName);
                fileToMoveBack.renameTo(new File(path+"/"+fileName));
                if (true) {
                    interFaceSetTextPanel.passText("Przenios³em: \n" + fileName + " do " + path+"/"+fileName);
                } else if (false) {
                    interFaceSetTextPanel.passText("Nie przenios³em: \n"+ fileName + " do " + path+"/"+fileName);
                }
            }
        }
    }
    public void makeDir()
    {
        String path = interfaceReadField.readTextFromField();
        String directoryName = makeDirectoryNameField.getText();
            File file = new File(path + "/" + directoryName);
            interFaceSetTextPanel.clearText();
            if (!file.exists()) {
                file.mkdir();
                interFaceSetTextPanel.clearText();
                interFaceSetTextPanel.passText("Utworzy³em katalog:\n " + file);
            } else if (file.exists()) {
                interFaceSetTextPanel.passText("Katalog ju¿ istnieje!:\n " + file);
            }
    }

    public void passTextToTextArea( InterFaceSetTextPanel passText ) {
        this.interFaceSetTextPanel = passText;
    }

    public void getFilePath( InterfaceReadField readField ) {
        this.interfaceReadField = readField;
    }
    public void setField(String txt)
    {
        moveFilesMakeDirsField.setText(txt);
        deleteDirectoryNonEmptyField.setText(txt);
    }
}
