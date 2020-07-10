import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.Arrays;

public class CopyFiles extends JPanel implements ActionListener {
    private JLabel fromLabel = new JLabel("Kopiuj z");
    private JLabel toLabel = new JLabel("Kopiuj do");
    private JLabel copyFromLogAreaLabel = new JLabel("<html><body><font size=2>Kopiuj<br> wybrane<br></font></body></html>");
    private JTextField fromField = new JTextField(10);
    private JTextField toField = new JTextField(10);
    private JButton chooseFromButton = new JButton("Wybierz");
    private JButton chooseWhereButton = new JButton("Wybierz");
    private JButton copyButton = new JButton("Kopiuj");
    private JButton overwriteButton = new JButton("Zast¹p");
    private JTextArea logArea = new JTextArea(11, 27);
    private JCheckBox copyFromLogAreaCheckbox = new JCheckBox();
    private int checkBoxVersion;

    public CopyFiles() {
        Dimension dimension = getPreferredSize();
        dimension.height = 210;
        dimension.width = 80;
        setPreferredSize(dimension);
        Border outerBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        Border innerBorder = BorderFactory.createTitledBorder("Kopiuj Pliki");
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(fromLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(fromField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(chooseFromButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 10;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(toLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.EAST;
        gbc.anchor = GridBagConstraints.EAST;
        add(toField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.EAST;
        gbc.anchor = GridBagConstraints.EAST;
        add(chooseWhereButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NORTHWEST;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(copyButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.WEST;
        gbc.anchor = GridBagConstraints.WEST;
        add(overwriteButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.CENTER;
        add(copyFromLogAreaLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.SOUTHEAST;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        add(copyFromLogAreaCheckbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NORTHWEST;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(new JScrollPane(logArea), gbc);

        copyButton.addActionListener(this);
        overwriteButton.addActionListener(this);
        chooseFromButton.addActionListener(this);
        chooseWhereButton.addActionListener(this);
        copyFromLogAreaCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged( ItemEvent e ) {
                checkBoxVersion = 1;
            }
        });

    }

    public void setFromField( String txt ) {
        fromField.setText(txt);
    }

    public void setToField( String txt ) {
        toField.setText(txt);
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        Object source = e.getSource();
        JButton clicked = (JButton) source;
        String dirFrom = fromField.getText();
        String dirTo = toField.getText();
        File sourceLocation = new File(dirFrom);
        File targetLocation = new File(dirTo);

        if (clicked == copyButton && checkBoxVersion != 1) {
            clearLogArea();

            try {
                copyFiles(sourceLocation, targetLocation);
            } catch (FileNotFoundException ex) {
                logArea.append("Nie znaleziono pliku");
            }
        }
        if (clicked == overwriteButton) {
            clearLogArea();

            try {
                replaceFiles(sourceLocation, targetLocation);
            } catch (FileNotFoundException ex) {
                logArea.append("Nie znaleziono pliku!\n");
            }
        }
        if (clicked == copyButton && checkBoxVersion == 1) {
            /*clearLogArea();*/

            try {
                copySelectedFiles(sourceLocation, targetLocation);
            } catch (FileNotFoundException exception) {
                logArea.append("Nie znaleziono pliku\n");
            }
        }
        if (clicked == chooseFromButton) {
            createFileChooser(3);
        }
        if (clicked == chooseWhereButton) {
            createFileChooser(4);
        }

    }

    public void copyFiles( File sourceLocation, File targetLocation ) throws FileNotFoundException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdirs();
            }
            File sourceFile = null;
            File targetFile = null;

            String[] listContents = sourceLocation.list();
            for (int i = 0; i < listContents.length; i++) {
                String name = listContents[i];
                sourceFile = new File(sourceLocation, name);
                targetFile = new File(targetLocation, name);
                if (targetFile.exists()) {
                    logArea.append("Pomin¹³em \n" + sourceFile + "\n");
                    return;
                }
                copyFiles(sourceFile, targetFile);
            }

        } else {
            RandomAccessFile inputStream = new RandomAccessFile(sourceLocation, "r");
            RandomAccessFile outputStream = new RandomAccessFile(targetLocation, "rw");
          /*  InputStream inputStream = new FileInputStream(sourceLocation);
            OutputStream outputStream = new FileOutputStream(targetLocation);*/
            int count = 0;
            byte[] buffer = new byte[10000];
            try {
                while ((count = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, count);
                }
                if (true) {
                    logArea.append("Skopiowa³em \n" + sourceLocation + "\ndo " + targetLocation + "\n");
                }

                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                logArea.append("B³¹d wejœcia wyjœcia");
            }
        }
    }

    public void copySelectedFiles( File sourceLocation, File targetLocation ) throws FileNotFoundException {
        /* if (sourceLocation.isDirectory()) {*/
        if (!targetLocation.exists()) {
            targetLocation.mkdirs();
        }
        File sourceFile = null;
        File targetFile = null;
        RandomAccessFile inputStream;
        RandomAccessFile outputStream;
        String[] logAreaArray = logArea.getText().split("\\n");
        System.out.println(Arrays.toString(logAreaArray));
        for (int i = 0; i < logAreaArray.length; i++) {
            String name = logAreaArray[i];
            System.out.println(name);
            sourceFile = new File(sourceLocation, name);
            targetFile = new File(targetLocation, name);
            inputStream = new RandomAccessFile(sourceFile, "r");
            outputStream = new RandomAccessFile(targetFile, "rw");
          /*  InputStream inputStream = new FileInputStream(sourceLocation);
            OutputStream outputStream = new FileOutputStream(targetLocation);*/
            int count = 0;
            byte[] buffer = new byte[10000];
            try {
                while ((count = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, count);
                }
                if (true) {
                    /*logArea.append("Skopiowa³em \n" + sourceFile + "\ndo " + targetFile + "\n");*/
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                logArea.append("B³¹d wejœcia wyjœcia");
            }
            /*copySelectedFiles(sourceFile, targetFile);*/
        }
    }

    public void replaceFiles( File sourceLocation, File targetLocation ) throws FileNotFoundException {
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }
            File sourceFile;
            File targetFile = null;
            String[] listContents = sourceLocation.list();
            for (int i = 0; i < listContents.length; i++) {
                String name = listContents[i];
                sourceFile = new File(sourceLocation, name);
                targetFile = new File(targetLocation, name);
                replaceFiles(sourceFile, targetFile);
            }
            if (targetLocation.exists()) {
                targetLocation.delete();
            }

        } else {
            RandomAccessFile inputStream = new RandomAccessFile(sourceLocation, "r");
            RandomAccessFile outputStream = new RandomAccessFile(targetLocation, "rw");
            /*InputStream inputStream = new FileInputStream(sourceLocation);
            OutputStream outputStream = new FileOutputStream(targetLocation);*/
            int count = 0;
            byte[] buffer = new byte[1024];
            try {
                while ((count = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, count);
                }
                if (true) {
                    logArea.append("Zast¹pi³em \n" + sourceLocation + "\ndo " + targetLocation + "\n");
                }
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                logArea.append("B³¹d wejœcia wyjœcia");
            }
        }
    }

    public void createFileChooser( int buttonVersion ) {
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
            if (buttonVersion == 3) {
                setFromField(file.getAbsolutePath());
            }
            if (buttonVersion == 4) {
                setToField(file.getAbsolutePath());
            }

        } else {
            logArea.append("Anulowano otwieranie");
        }
    }

    public void clearLogArea() {
        logArea.selectAll();
        logArea.replaceSelection("");
    }
}

