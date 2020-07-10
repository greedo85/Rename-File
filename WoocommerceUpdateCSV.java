import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.nio.charset.StandardCharsets;

public class WoocommerceUpdateCSV extends JPanel implements ActionListener {
    FTPClient ftpClient;
    private JLabel sourceFileNameLabel = new JLabel("Plik Ÿród³owy");
    private JLabel destFileNameLabel = new JLabel("Plik docelowy");
    private JLabel checkURLLabel = new JLabel("<html><body>SprawdŸ<br> linki HTTP</body></html>");
    private JLabel checkFTPLabel = new JLabel("<html><body>SprawdŸ<br> linki FTP</body></html>");
    private JTextField sourceFileNameField = new JTextField(10);
    private JTextField destFileNameField = new JTextField(10);
    private JButton sourceFileButton = new JButton("Wybierz");
    private JButton destFileButton = new JButton("Wybierz");
    private JButton createFileButton = new JButton("Utwórz plik");
    private JCheckBox checkURLCheckbox = new JCheckBox();
    private JCheckBox checkFTPCheckbox = new JCheckBox();
    int checkBoxVersion;

    public WoocommerceUpdateCSV() throws FileNotFoundException {
        ftpClient = new FTPClient();

        Dimension dimension = getPreferredSize();
        dimension.height = 210;
        dimension.width = 80;
        setPreferredSize(dimension);
        Border outerBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        Border innerBorder = BorderFactory.createTitledBorder("Utwórz plik CSV");
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(sourceFileNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(sourceFileNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(sourceFileButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 10;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(destFileNameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.EAST;
        gbc.anchor = GridBagConstraints.CENTER;
        add(destFileNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.EAST;
        gbc.anchor = GridBagConstraints.CENTER;
        add(destFileButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.CENTER;
        gbc.anchor = GridBagConstraints.EAST;
        add(createFileButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 200;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.SOUTHEAST;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        add(checkURLCheckbox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NORTHEAST;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(checkURLLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NORTHEAST;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(checkFTPLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 2;
        gbc.weighty = 2;
        gbc.fill = GridBagConstraints.NORTHEAST;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(checkFTPCheckbox, gbc);

        sourceFileButton.addActionListener(this);
        destFileButton.addActionListener(this::actionPerformed);
        createFileButton.addActionListener(this);
        destFileNameField.setText("baza.csv");
        checkURLCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged( ItemEvent e ) {
                Object source = e.getSource();
                JCheckBox checked = (JCheckBox) source;
                if (checked == checkURLCheckbox) {
                    checkBoxVersion = 1;
                }
            }
        });
        checkFTPCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged( ItemEvent e ) {
                checkBoxVersion = 2;
            }
        });
    }

    @Override
    public void actionPerformed( ActionEvent e ) {
        Object source = e.getSource();
        JButton clicked = (JButton) source;
        String sourceFileName = sourceFileNameField.getText();
        String targetFileName = destFileNameField.getText();
        File sourceFile = new File(sourceFileName);
        File targetFile = new File(targetFileName);
        if (clicked == sourceFileButton) {
            createFileChooser(3);
        }
        if (clicked == destFileButton) {
            createFileChooser(4);
        }
        if (clicked == createFileButton) {
            try {
                createFile(sourceFile, targetFile, checkBoxVersion);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
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
                setSourceFileNameField(file.getAbsolutePath());
            }
            if (buttonVersion == 4) {
                setDestFileNameField(file.getAbsolutePath());
            }

        }/*else{
            logArea.append("Anulowano otwieranie");
        }*/
    }

    public void setSourceFileNameField( String txt ) {
        sourceFileNameField.setText(txt);
    }

    public void setDestFileNameField( String txt ) {
        destFileNameField.setText(txt);
    }

    public void createFile( File sourceFile, File targetFile, int checkBoxVersion ) throws FileNotFoundException {
        /*Writer writeFileFirst = null;*/
        Writer writeFileSecond = null;//UTF_8 !!!!!
        BufferedReader fileReaderFirst = new BufferedReader(new FileReader(sourceFile));
        BufferedReader fileReaderSecond = new BufferedReader(new FileReader(sourceFile));
        String line = "";
        try {
            int countRows = 0;
            while ((line = fileReaderFirst.readLine()) != null) {
                /*Przy odczycie pliku liczymy sobie rzêdy dla tablicy*/
                countRows += 1;
            }
            String[] tablica = new String[countRows + 1];
            for (int i = 0; i < tablica.length - 1; i++) {
                /*Czytamy zapisany plik dla ka¿dego rzêdu tablicy*/
                tablica[0] = "post_title;post_excerpt;post_content;post_status;menu_order;post_date;parent_sku;sku;downloadable;virtual;visibility;stock;stock_status;backorders;manage_stock;regular_price;sale_price;weight;length;width;height;tax_status;tax_class;upsell_ids;crosssell_ids;featured;file_path;download_limit;download_expiry;product_url;button_text;images;tax:product_type;Kategorie;tax:product_tag;tax:product_shipping_class;meta:woocommerce_sale_flash_type;Column1;attribute:pa_size;attribute_data:pa_size\n";
                tablica[i] = fileReaderSecond.readLine().replaceAll("\"", "") + "\n";
            }
            if (targetFile.exists()) {
                targetFile.delete();
            }
            writeFileSecond = new OutputStreamWriter(new FileOutputStream(targetFile), StandardCharsets.UTF_8);//UTF_8!!!!
            String[] splittedLine;
            for (int j = 1; j < tablica.length - 1; j++) {
                splittedLine = tablica[j].split(";");
                /* I tu mamy ju¿ tablicê Stringów do podzia³u.
                 * Trzeba ka¿dy index tej tablicy podzieliæ czyli zrobiæ drug¹ tablicê i indexy tej drugiej tablicy
                 * gdzieœ przechowaæ, ¿eby móc podaæ je do zapisu wraz ze znakiem ";"*/
                String[] splitStock = splittedLine[7].split(",");
                int extensionIndex = splitStock.length - 1;
                String stockAmount = splitStock[0].replaceAll("\\s", "");
                /*int stock = Integer.parseInt(stockAmount);*/
                /*splittedLine[7]=splittedLine[7].replace(" ", ""); //tu jest b³¹d*/

                if (splittedLine[7].equals("0") /*|| stock < 30*/) {
                    continue;
                }
                String stockStatus = "";
                if (!splittedLine[7].equals("0")) {
                    stockStatus = "instock";
                } else {
                    stockStatus = "outofstock";
                }
                String fotoURL1 = "";
                String fotoURL2 = "";
                String fotoURL3 = "";
                int code1;
                int code2;
                int code3;
                URL url1;
                URL url2;
                URL url3;
                HttpURLConnection httpURLConnection1;
                HttpURLConnection httpURLConnection2;
                HttpURLConnection httpURLConnection3;
                String fotoURL = "https://store.onedollar.pl/wp-content/uploads/2020/06/" + splittedLine[1] + ".jpg";/*,"+splittedLine[1]+"_2.jpg,;"+splittedLine[1]+"_1.jpg";*/
                String imageLinks=fotoURL;
                if (checkBoxVersion == 1) {

                    try {
                        url1 = new URL("https://store.onedollar.pl/wp-content/uploads/2020/06/" + splittedLine[1] + "_2.jpg");
                        url2 = new URL("https://store.onedollar.pl/wp-content/uploads/2020/06/" + splittedLine[1] + "_3.jpg");
                        url3 = new URL("https://store.onedollar.pl/wp-content/uploads/2020/06/" + splittedLine[1] + "_4.jpg");
                    } catch (MalformedURLException e) {
                        e.toString();
                        return;
                    }
                    try {
                        httpURLConnection1 = (HttpURLConnection) url1.openConnection();
                        httpURLConnection2 = (HttpURLConnection) url2.openConnection();
                        httpURLConnection3 = (HttpURLConnection) url3.openConnection();
                        httpURLConnection1.setRequestMethod("GET");  //OR  httpURLConnection.setRequestMethod ("HEAD");
                        httpURLConnection2.setRequestMethod("GET");  //OR  httpURLConnection.setRequestMethod ("HEAD");
                        httpURLConnection3.setRequestMethod("GET"); //OR  httpURLConnection.setRequestMethod ("HEAD");
                        httpURLConnection1.connect();
                        httpURLConnection2.connect();
                        httpURLConnection3.connect();
                        code1 = httpURLConnection1.getResponseCode();
                        code2 = httpURLConnection2.getResponseCode();
                        code3 = httpURLConnection3.getResponseCode();
                    } catch (IOException e) {
                        e.toString();
                        return;
                    }
                    if (code1 == 200) {
                        fotoURL1 = "https://store.onedollar.pl/wp-content/uploads/2020/06/" + splittedLine[1] + "_2.jpg";
                    }
                    if (code2 == 200) {
                        fotoURL2 = "https://store.onedollar.pl/wp-content/uploads/2020/06/" + splittedLine[1] + "_3.jpg";
                    }
                    if (code3 == 200) {
                        fotoURL3 = "https://store.onedollar.pl/wp-content/uploads/2020/06/" + splittedLine[1] + "_4.jpg";
                    }
                    imageLinks=fotoURL+ ","+fotoURL1+","+fotoURL2+","+fotoURL3;
                } else if (checkBoxVersion == 2) {
                    File serverConfig = new File("src/ftpconfig.txt");
                    BufferedReader fileReader = new BufferedReader(new FileReader(serverConfig));
                    String server;
                    String userName;
                    String password;
                    try {
                        server = fileReader.readLine();
                        userName = fileReader.readLine();
                        password = fileReader.readLine();
                        ftpClient.connect(server);
                        ftpClient.login(userName, password);
                        ftpClient.enterLocalPassiveMode();
                        ftpClient.changeWorkingDirectory("strona_glowna/woofotogal");
                        if (true) {
                            System.out.println("Zalogowano pomyœlnie");
                        }
                    } catch (IOException e) {
                        System.out.println("Nie mo¿na po³¹czyæ");
                        return;
                    }
                    if (true) {
                        System.out.println("Zmieni³em na katalog");
                    }
                    String filePath1 = splittedLine[1] + "_1.jpg";
                    String filePath2 = splittedLine[1] + "_2.jpg";
                    String filePath3 = splittedLine[1] + "_3.jpg";
                    InputStream inputStream1 = ftpClient.retrieveFileStream(filePath1);
                    int returnCode1 = ftpClient.getReplyCode();
                    InputStream inputStream2 = ftpClient.retrieveFileStream(filePath2);
                    int returnCode2 = ftpClient.getReplyCode();
                    InputStream inputStream3 = ftpClient.retrieveFileStream(filePath3);
                    int returnCode3 = ftpClient.getReplyCode();
                    if (inputStream1 != null || returnCode1 != 550) {
                        fotoURL1 = "https://store.onedollar.pl/wp-content/uploads/2020/06/" + splittedLine[1] + "_1.jpg";
                    } else
                        continue;
                    if (inputStream2 != null || returnCode2 != 550) {
                        fotoURL2 = "https://store.onedollar.pl/wp-content/uploads/2020/06/" + splittedLine[1] + "_2.jpg";
                    } else
                        continue;
                    if (inputStream3 != null || returnCode3 != 550) {
                        fotoURL3 = "https://store.onedollar.pl/wp-content/uploads/2020/06/" + splittedLine[1] + "_3.jpg";
                    } else
                        continue;
                }
                /*System.out.println(code1+" "+code2+" "+code3);
                System.out.println(fotoURL1+" "+fotoURL2+" "+fotoURL3);*/
                String[] splitLine;
                String category = "";
                splittedLine[41] = splittedLine[41].replaceAll(",", "");
                if (!splittedLine[41].equals(null)) {
                    splitLine = splittedLine[41].split("\\\\");
                    if (true && splitLine.length <= 1) {
                        category = splitLine[0];
                    } else if (true && splitLine.length > 1) {
                        category = splitLine[1];
                    } else if (false)
                        category = "INNE";
                }
                String description = splittedLine[3] + " " + splittedLine[4];
                tablica[j] = (splittedLine[3]+" "+splittedLine[2] + ";" + splittedLine[4] + ";" + description + ";publish;" + ";0" + ";" + splittedLine[2] + ";" + splittedLine[2] +
                ";" + "no;" + "no;" + "visible" + ";" + stockAmount + ";" + stockStatus + ";no" + ";yes;" + splittedLine[9] + ";" + ";" +
                ";" + ";" + ";" + ";" + ";" + ";" + ";" + ";" + ";" + ";" + ";" + ";" + ";" + ";" + imageLinks+ ";simple;" + category +";;;;;;;"+ "\n");
                writeFileSecond.write(tablica[j - 1]);
            }
            writeFileSecond.write(tablica[tablica.length - 2]);
            writeFileSecond.close();
            fileReaderFirst.close();
            fileReaderSecond.close();
            /*System.out.println("Oryginalna tablica\n" + Arrays.toString(tablica));*/
        } catch (IOException e) {
            System.out.println("B³¹d wejœcia wyjœcia");
            e.toString();
            return;
        }
    }
}
