import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenameFiles extends JPanel implements ActionListener {
    private JLabel oldNameLabel = new JLabel("Lista starych nazw");
    private JLabel newNameLabel = new JLabel("Lista nowych nazw");
    private JLabel addEndingLabel = new JLabel("Dodaæ koñcówkê?");
    private JTextField pathField = new JTextField(10);
    private JButton oldNameButton = new JButton("Stare nazwy");
    private JButton newNameButton = new JButton("Nowe nazwy");
    private JButton changeButton = new JButton("Zmieñ nazwy");
    private JButton chooseFromButton = new JButton("Wybierz katalog");
    private JCheckBox addEndingCheckbox=new JCheckBox();
    int checkBoxVerion=0;

    public RenameFiles() {

        Dimension dimension = getPreferredSize();
        dimension.height = 210;
        dimension.width = 80;
        setPreferredSize(dimension);
        Border outerBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        Border innerBorder = BorderFactory.createTitledBorder("Zmiana nazw po pocz¹tku nazwy");
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(oldNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(oldNameButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 10;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(newNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.EAST;
        gbc.anchor = GridBagConstraints.CENTER;
        add(newNameButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.EAST;
        add(changeButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        add(pathField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        add(chooseFromButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.WEST;
        gbc.anchor = GridBagConstraints.WEST;
        add(addEndingLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.LINE_START;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(addEndingCheckbox, gbc);
        changeButton.addActionListener(this);
        chooseFromButton.addActionListener(this);
        oldNameButton.addActionListener(this);
        newNameButton.addActionListener(this);
        addEndingCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged( ItemEvent e ) {
                checkBoxVerion=1;
            }
        });
    }

    public void createFileChooser() {
        JFileChooser fileChooser = new JFileChooser("C:/");
        UIManager.put("FileChooser.openDialogTitleText", "Otwórz");
        UIManager.put("FileChooser.lookInLabelText", "LookIn");
        UIManager.put("FileChooser.openButtonText", "Otwórz");
        UIManager.put("FileChooser.cancelButtonText", "Anuluj");
        UIManager.put("FileChooser.fileNameLabelText", "Nazwa");
        UIManager.put("FileChooser.filesOfTypeLabelText", "Typ pliku");
        UIManager.put("FileChooser.openButtonToolTipText", "Otwórz wybrany plik");
        UIManager.put("FileChooser.cancelButtonToolTipText", "Anuluj");
        UIManager.put("FileChooser.fileNameHeaderText", "Nazwa pliku");
        UIManager.put("FileChooser.upFolderToolTipText", "W górê");
        UIManager.put("FileChooser.homeFolderToolTipText", "Pulpit");
        UIManager.put("FileChooser.newFolderToolTipText", "Utwórz nowy katalog");
        UIManager.put("FileChooser.listViewButtonToolTipText", "Listuj");
        UIManager.put("FileChooser.newFolderButtonText", "Utwórz nowy katalog");
        UIManager.put("FileChooser.renameFileButtonText", "Zmieñ nazwê");
        UIManager.put("FileChooser.deleteFileButtonText", "Usuñ plik");
        UIManager.put("FileChooser.filterLabelText", "Typ pliku");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Szczegó³y");
        UIManager.put("FileChooser.fileSizeHeaderText", "Rozmiar");
        UIManager.put("FileChooser.fileDateHeaderText", "Data modyfikacji");
        SwingUtilities.updateComponentTreeUI(fileChooser);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            setPathField(file.getAbsolutePath());
        }/*else{
            logArea.append("Anulowano otwieranie");
        }*/
    }

    public void renameFiles( File oldName, File newName, String diskPath, int checkBoxVerion ) throws FileNotFoundException {
        BufferedReader fileReaderSource = new BufferedReader(new FileReader(oldName));
        BufferedReader fileReaderSource2 = new BufferedReader(new FileReader(oldName));
        BufferedReader fileReaderTarget = new BufferedReader(new FileReader(newName));
        String line = "";
        try {
            int countTableSize = 0;
            while ((line = fileReaderSource.readLine()) != null) {
                countTableSize += 1;
            }
            String[] oldNameArray = new String[countTableSize];
            String[] newNameArray = new String[countTableSize];
            for (int i = 0; i < oldNameArray.length; i++) {
                /*Czytamy plik ze starymy nazwami*/
                oldNameArray[i] = fileReaderSource2.readLine();
                System.out.println(oldNameArray[i]);
                /*czytamy jednoczeœnie plik z nowymi nazwami*/
                newNameArray[i] = fileReaderTarget.readLine();
                System.out.println(newNameArray[i]);
                /*ka¿da linia bêdzie wzorcem dla filtra*/
                Pattern pattern = Pattern.compile("^" + oldNameArray[i] + ".*");
                FilenameFilter fileFilter = new FilenameFilter() {
                    @Override
                    public boolean accept( File dir, String name ) {
                        Matcher matcher = pattern.matcher(name);
                        return matcher.matches();
                    }
                };
                /*Czytamy zawartoœæ katalogu ze zdjêciami*/
                File file = new File(diskPath);
                /*Robimy listê plików pasuj¹cych do wzorca*/
                File[] listFile = file.listFiles(fileFilter);
                String[] listOfFiles = file.list(fileFilter);
                for (int j = 0; j < listOfFiles.length; j++) {
                    if (listFile[j].isFile()) {
                        System.out.println("ListofFiles[j]: " + listOfFiles[j]);
                        File fileToMove = new File(diskPath, listOfFiles[j]);
                        String[] splitOldName = listOfFiles[j].split("_");
                        int extensionIndex = splitOldName.length - 1;
                        String newFileName="";
                        if (checkBoxVerion==1)
                        {
                            newFileName = newNameArray[i] + "_" + splitOldName[extensionIndex];
                        }
                        else if(checkBoxVerion==0)
                        {
                            newFileName=newNameArray[i] + ".jpg";
                        }
                        System.out.println("newname: " + newFileName);
                        fileToMove.renameTo(new File(diskPath, newFileName));
                    }
                }
            }
        } catch (IOException e) {
            e.toString();
            return;
        }
    }

    public void setPathField( String txt ) {
        pathField.setText(txt);
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        Object source = e.getSource();
        JButton clicked = (JButton) source;
        File oldName = new File("src\\oldname.txt");
        File newName = new File("src\\newname.txt");
        String diskPath = pathField.getText();
        if (clicked == changeButton) {
            try {
                renameFiles(oldName, newName, diskPath, checkBoxVerion);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        if (clicked == chooseFromButton) {
            createFileChooser();
        }
        if (clicked == oldNameButton) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(oldName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (clicked == newNameButton) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.open(newName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
