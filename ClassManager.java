import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class ClassManager extends JFrame {
    JLabel authorLabel;
    ToolBar toolBar;
    TextPanel textPanel;
    RightToolBar rightToolBar;
    FTPBar ftpBar;
    UpperMenu menuBar;
    JMenuBar jMenuBar = new JMenuBar();
    RegexList regexList;

    public ClassManager() {
        regexList = new RegexList();
        setJMenuBar(jMenuBar);
        menuBar = new UpperMenu();
        jMenuBar.add(menuBar.fileMenu);
        jMenuBar.add(menuBar.toolsMenu);
        setLayout(new BorderLayout());
        textPanel = new TextPanel();
        toolBar = new ToolBar();
        rightToolBar = new RightToolBar();
        ftpBar = new FTPBar();

        add(toolBar, BorderLayout.LINE_START);
        add(textPanel, BorderLayout.CENTER);
        add(rightToolBar, BorderLayout.EAST);
        add(regexList,BorderLayout.SOUTH);

        toolBar.setInterFaceSetTextPanel(new InterFaceSetTextPanel() {
            @Override
            public void passText( String txt ) {
                textPanel.setTextArea(txt);
            }

            @Override
            public void clearText() {
                textPanel.clearTextArea();
            }
        });
        toolBar.readInterfaceTextPanel(new InterfaceReadTextPanel() {
            @Override
            public String[] readTextPanel() {
                return textPanel.readLineByLine();
            }
        });
        rightToolBar.passTextToTextArea(new InterFaceSetTextPanel() {
            @Override
            public void passText( String txt ) {
                textPanel.setTextArea(txt);
            }

            @Override
            public void clearText() {
                textPanel.clearTextArea();
            }
        });
        rightToolBar.getFilePath(new InterfaceReadField() {
            @Override
            public String readTextFromField() {
                return toolBar.readText();
            }
        });
        menuBar.passText(new InterFaceSetTextPanel() {
            @Override
            public void passText( String txt ) {
                textPanel.setTextArea(txt);
            }

            @Override
            public void clearText() {
                textPanel.clearTextArea();
            }
        });
       /* ftpBar.getPathFromField(new InterfaceReadField() {
            @Override
            public String readTextFromField() {
                return toolBar.readText();
            }
        });*/
        menuBar.setTextField(new InterfaceSetTextField() {
            @Override
            public void setTextToField( String txt ) {
                toolBar.setTextField(txt);
            }
        });
        menuBar.readToolBarField(new InterfaceReadField() {
            @Override
            public String readTextFromField() {
                return toolBar.readText();
            }
        });
        menuBar.setFTPField(new InterFaceSetFTPFields() {
            @Override
            public void setFields( String txt ) {
                ftpBar.setFromField(txt);
            }
        });
        regexList.setDeleteNonEmptyField(new InterfaceSetRightFields() {
            @Override
            public void setTextToField( String txt ) {
                rightToolBar.setField(txt);
            }

            @Override
            public void setFieldDirFields( String txt ) {
                toolBar.setFileAndDirFields(txt);
            }
        });


    }

}

