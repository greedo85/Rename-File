import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UpperMenu extends JMenu implements ActionListener
{
    public JMenuBar menu=new JMenuBar();
    public JMenu fileMenu=new JMenu("Plik");;
    public JMenu toolsMenu=new JMenu("Narzêdzia");;
    public JMenuItem ftpItem=new JMenuItem("FTP");;
    public JMenuItem chooseFileItem=new JMenuItem("Otwórz plik");;
    public JMenuItem openItem=new JMenuItem("Otwórz oldname.txt");
    public JMenuItem copyFilesItem=new JMenuItem("Kopiuj pliki");
    public JMenuItem createUpdateFileItem=new JMenuItem("Generuj update");
    public JMenuItem renameFilesItem=new JMenuItem("Zmieñ nazwy plików");
    FTPBar ftpBar;
    CopyFiles copyFiles;
    WoocommerceUpdateCSV woocommerceUpdateCSV;
    RenameFiles renameFiles;
    InterFaceSetTextPanel interFaceSetTextPanel;
    InterfaceSetTextField interfaceSetTextField;
    InterfaceReadField interfaceReadField;
    InterFaceSetFTPFields interFaceSetFTPFields;

    public UpperMenu()
    {
        ftpBar=new FTPBar();
        copyFiles=new CopyFiles();
        try {
            woocommerceUpdateCSV = new WoocommerceUpdateCSV();
        }
        catch (FileNotFoundException e)
        {
            return;
        }
        renameFiles=new RenameFiles();
        setLayout(new FlowLayout());
        menu.add(fileMenu);
        menu.add(toolsMenu);
        fileMenu.add(openItem);
        fileMenu.add(chooseFileItem);
        toolsMenu.add(ftpItem);
        toolsMenu.add(copyFilesItem);
        toolsMenu.add(createUpdateFileItem);
        toolsMenu.add(renameFilesItem);
        openItem.addActionListener(this);
        chooseFileItem.addActionListener(this);
        ftpItem.addActionListener(this);
        copyFilesItem.addActionListener(this);
        createUpdateFileItem.addActionListener(this);
        renameFilesItem.addActionListener(this);
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        Object source=e.getSource();
        JMenuItem clicked=(JMenuItem) source;
        if(clicked==openItem) {
            try {
                File file = new File("src/oldname.txt");
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
                if (!file.exists()) {
                    interFaceSetTextPanel.passText("Nie mogê znaleŸæ pliku!");
                }
            } catch (IOException ex) {
                interFaceSetTextPanel.passText("Nie mogê znaleŸæ pliku!");
            }
        }
        if(clicked==chooseFileItem)
        {
            JFileChooser fileChooser = new JFileChooser("C:/");
            UIManager.put("FileChooser.openDialogTitleText", "Otwórz");
            UIManager.put("FileChooser.lookInLabelText", "LookIn");
            UIManager.put("FileChooser.openButtonText", "Otwórz");
            UIManager.put("FileChooser.cancelButtonText", "Anuluj");
            UIManager.put("FileChooser.fileNameLabelText", "Nazwa");
            UIManager.put("FileChooser.filesOfTypeLabelText", "Typ pliku");
            UIManager.put("FileChooser.openButtonToolTipText", "Otwórz wybrany plik");
            UIManager.put("FileChooser.cancelButtonToolTipText","Anuluj");
            UIManager.put("FileChooser.fileNameHeaderText","Nazwa pliku");
            UIManager.put("FileChooser.upFolderToolTipText", "W górê");
            UIManager.put("FileChooser.homeFolderToolTipText","Pulpit");
            UIManager.put("FileChooser.newFolderToolTipText","Utwórz nowy katalog");
            UIManager.put("FileChooser.listViewButtonToolTipText","Listuj");
            UIManager.put("FileChooser.newFolderButtonText","Utwórz nowy katalog");
            UIManager.put("FileChooser.renameFileButtonText", "Zmieñ nazwê");
            UIManager.put("FileChooser.deleteFileButtonText", "Usuñ plik");
            UIManager.put("FileChooser.filterLabelText", "Typ pliku");
            UIManager.put("FileChooser.detailsViewButtonToolTipText", "Szczegó³y");
            UIManager.put("FileChooser.fileSizeHeaderText","Rozmiar");
            UIManager.put("FileChooser.fileDateHeaderText", "Data modyfikacji");
            SwingUtilities.updateComponentTreeUI(fileChooser);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int option = fileChooser.showOpenDialog(this);
            if(option == JFileChooser.APPROVE_OPTION){
                File file = fileChooser.getSelectedFile();
                interfaceSetTextField.setTextToField(file.getAbsolutePath());
                System.out.println(file.getName());
            }else{
                interfaceSetTextField.setTextToField("Anulowano otwieranie");
            }
        }
        if(clicked==ftpItem)
        {
            /*Odczytywanie pola toolbar*/
           /* System.out.println(interfaceReadField.readTextFromField());//testowy println*/
            JFrame frame=new JFrame("FTP");
            ftpBar.setFromField(interfaceReadField.readTextFromField());
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.add(ftpBar,BorderLayout.CENTER);
            frame.setSize(550,350);
            frame.setResizable(false);
            frame.setLocation(530,250);
            frame.setVisible(true);
        }
        if(clicked==copyFilesItem) {
            /* System.out.println(interfaceReadField.readTextFromField());//testowy println*/
            JFrame frame=new JFrame("Kopiuj pliki");
            copyFiles.setFromField(interfaceReadField.readTextFromField());
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.add(copyFiles,BorderLayout.CENTER);
            frame.setSize(430,350);
            frame.setResizable(false);
            frame.setLocation(530,250);
            frame.setVisible(true);

        }
        if (clicked==createUpdateFileItem)
        {
            JFrame frame=new JFrame("Aktualizacja sklepu");
            /*createUpdateFileItem.setFromField(interfaceReadField.readTextFromField());*/
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.add(woocommerceUpdateCSV,BorderLayout.CENTER);
            frame.setSize(420,240);
            frame.setResizable(false);
            frame.setLocation(530,250);
            frame.setVisible(true);
        }
        if(clicked==renameFilesItem)
        {
            JFrame frame=new JFrame("Zmieñ nazwy plików");
            /*createUpdateFileItem.setFromField(interfaceReadField.readTextFromField());*/
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.add(renameFiles,BorderLayout.CENTER);
            frame.setSize(420,195);
            frame.setResizable(false);
            frame.setLocation(530,250);
            frame.setVisible(true);
        }
    }
    public void passText(InterFaceSetTextPanel passText)
    {
        this.interFaceSetTextPanel=passText;
    }
    public void setTextField(InterfaceSetTextField setTextField)
    {
        this.interfaceSetTextField=setTextField;
    }

    public void setFTPField(InterFaceSetFTPFields setField)
    {
        this.interFaceSetFTPFields=setField;
    }
    public void readToolBarField( InterfaceReadField readField)
    {
        this.interfaceReadField=readField;
    }
}
