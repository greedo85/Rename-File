import javax.print.DocFlavor;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPBar extends JPanel implements ActionListener {
    private JLabel serverAdressLabel = new JLabel("Adres serwera:");
    private JLabel userNameLabel = new JLabel("U¿ytkownik:");
    private JLabel passworLabel = new JLabel("Has³o:");
    private JLabel fromLabel = new JLabel("Wyœlij z:");
    private JLabel sendPathLabel = new JLabel("Wyœlij do:");
    private JTextField serverAdressField = new JTextField(10);
    private JTextField userNameField = new JTextField(10);
    private JTextField passwordField = new JTextField(10);
    private JTextField fromField= new JTextField(10);
    private JTextField sendToField = new JTextField(10);
    private JButton sendButton = new JButton("Wyœlij");
    private JButton chooseWhereButton=new JButton("Wybierz");
    private TextArea logArea = new TextArea(7, 50);
    FTPClient ftpClient;
    File serverConfig;
    String server;
    String userName;
    String password;
    Scanner key;

    public FTPBar() {
        ftpClient = new FTPClient();
        serverConfig = new File("src/ftpconfig.txt");
        try {
            key = new Scanner(serverConfig);
        } catch (IOException e) {
            return;
        }
        server = key.nextLine();
        serverAdressField.setText(server);
        userName = key.nextLine();
        userNameField.setText(userName);
        password = key.nextLine();
        passwordField.setText(password);
        try {
            ftpClient.connect(server);
            ftpClient.login(userName, password);
            ftpClient.enterLocalPassiveMode();
            if (true) {
                logArea.append("Zalogowano pomyœlnie\n");
            } else if (false) {
                logArea.append("Nie mogê zalogowaæ\n");
            }
        } catch (Exception ex) {
            return;
        }

        Dimension dimension = getPreferredSize();
        dimension.height = 210;
        dimension.width = 210;
        setPreferredSize(dimension);
        Border outerBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        Border innerBorder = BorderFactory.createTitledBorder("Wyœlij na FTP");
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(serverAdressLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(serverAdressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(userNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(userNameField, gbc);


        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.WEST;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passworLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 2;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.WEST;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(fromLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(fromField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(sendPathLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(sendToField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(chooseWhereButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.WEST;
        gbc.anchor = GridBagConstraints.WEST;
        add(sendButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(new JScrollPane(logArea), gbc);
        sendButton.addActionListener(this);
        chooseWhereButton.addActionListener(this);
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        Object source = e.getSource();
        JButton clicked = (JButton) source;
        if (clicked == sendButton) {
            try {
                sendFiles();
            } catch (Exception ex) {
                return;
            }
        }
        if(clicked==chooseWhereButton)
        {
            createFileChooser(2);
        }
    }

    public void sendFiles() throws FileNotFoundException {
        String sendTo = sendToField.getText();
        String dirContentsToSend = fromField.getText();
        File file = new File(dirContentsToSend);
        File[] listFiles = file.listFiles();
        String[] listOfFiles = file.list();
        System.out.println(Arrays.toString(listOfFiles));
        try {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile()) {
                    String fileToSend = listOfFiles[i];
                    File uploadFile = new File(dirContentsToSend +"/"+ fileToSend);
                    InputStream inputStream = new FileInputStream(uploadFile);
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                    ftpClient.storeFile(sendTo +"/"+ fileToSend, inputStream);
                    if (true) {
                        logArea.append("Wys³ano pomyœlnie: " + sendTo+"/" + fileToSend + "\n");
                    } else if (false) {
                        logArea.append("Nie wys³ano\n");
                    }
                }
            }
        } catch (Exception ex) {
            return;
        }
    }
    public void createFileChooser(int buttonVersion)
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
            if(buttonVersion==1)
            {
                setFromField(file.getAbsolutePath());
            }
            if(buttonVersion==2)
            {
                setToField(file.getAbsolutePath());
            }

        }else{
            logArea.append("Anulowano otwieranie");
        }
    }
    public void setFromField(String txt)
    {
        fromField.setText(txt);
    }
    public void setToField(String txt)
    {
        sendToField.setText(txt);
    }
}



