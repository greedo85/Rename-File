import javafx.scene.control.CheckBox;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class RegexList extends JPanel implements ItemListener
{
    InterfaceSetRightFields interfaceSetRightFields;
    JLabel authorLabel = new JLabel("  Created by Andrzej Kuczmierowski 2020");

    public JLabel allLabel=new JLabel("Wszystko:");
    public JLabel startsWithLabel=new JLabel("Zaczyna siê na:");
    public JLabel endsWithLabel=new JLabel("Koñczy siê na:");
    public JLabel includeSpecificLabel=new JLabel("Zawiera dok³.:");
    public JLabel numberStartsLabel=new JLabel("Zaczyna siê od cyfry:");
    public JLabel includeAnyLabel=new JLabel("Zawiera dow.:");
    private JCheckBox allCheckBox=new JCheckBox();
    private JCheckBox startsWithCheckBox=new JCheckBox();
    private JCheckBox endsWithCheckBox=new JCheckBox();
    private JCheckBox includeSpecificCheckBox=new JCheckBox();
    private JCheckBox numberStartsCheckBox=new JCheckBox();
    private JCheckBox includeAnyCheckBox=new JCheckBox();
    private JTextField startsWithField=new JTextField(5);
    private JTextField endsWithField=new JTextField(5);
    private JTextField includeSpecificField=new JTextField(5);
    private JTextField includeAnyField=new JTextField(5);

    public RegexList()
    {
        Dimension dimension = getPreferredSize();
        dimension.height = 120;
        dimension.width = 120;
        setPreferredSize(dimension);
        Border outerBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        Border innerBorder = BorderFactory.createTitledBorder("Regex Lista");
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(allLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(allCheckBox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(numberStartsLabel, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(numberStartsCheckBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(startsWithLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(startsWithCheckBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(startsWithField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(endsWithLabel, gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(endsWithCheckBox, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(endsWithField, gbc);

        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(includeSpecificLabel, gbc);
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(includeSpecificCheckBox, gbc);
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(includeSpecificField, gbc);

        gbc.gridx = 6;
        gbc.gridy = 1;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(includeAnyLabel, gbc);
        gbc.gridx = 7;
        gbc.gridy = 2;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(includeAnyCheckBox, gbc);
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(includeAnyField, gbc);

        allCheckBox.addItemListener(this);
        startsWithCheckBox.addItemListener(this);
        endsWithCheckBox.addItemListener(this);
        includeSpecificCheckBox.addItemListener(this);
        includeAnyCheckBox.addItemListener(this);
        numberStartsCheckBox.addItemListener(this);

    }

    public void setDeleteNonEmptyField(InterfaceSetRightFields passText)
    {
        this.interfaceSetRightFields=passText;
    }

    @Override
    public void itemStateChanged( ItemEvent e ) {
        Object source=e.getSource();
        JCheckBox checked=(JCheckBox) source;
        if (checked==allCheckBox)
        {
            startsWithCheckBox.setSelected(false);
            interfaceSetRightFields.setTextToField(".*");
            interfaceSetRightFields.setFieldDirFields(".*");

        }
        if(checked==startsWithCheckBox)
        {
            String startsWith=startsWithField.getText();
            allCheckBox.setSelected(false);
            interfaceSetRightFields.setTextToField("^"+startsWith+".*");
            interfaceSetRightFields.setFieldDirFields("^"+startsWith+".*");

        }
        if(checked==endsWithCheckBox)
        {
            String endsWith=endsWithField.getText();
            interfaceSetRightFields.setTextToField(".*"+endsWith+".?$");
            interfaceSetRightFields.setFieldDirFields(".*"+endsWith+".?$");
        }
        if(checked==includeSpecificCheckBox)
        {
            String includeSpecific=includeSpecificField.getText();
            interfaceSetRightFields.setTextToField(".*"+includeSpecific+".*");
            interfaceSetRightFields.setFieldDirFields(".*"+includeSpecific+".*");
        }
        if(checked==includeAnyCheckBox)
        {
            String includeAny=includeAnyField.getText();
            interfaceSetRightFields.setTextToField(".*["+includeAny+"].*");
            interfaceSetRightFields.setFieldDirFields(".*["+includeAny+"].*");
        }
        if(checked==numberStartsCheckBox)
        {
            String number="\\d.*";
            interfaceSetRightFields.setTextToField(number);
            interfaceSetRightFields.setFieldDirFields(number);
        }
    }
}
