/*
 * Created by JFormDesigner on Mon Jul 09 22:00:39 CST 2018
 */

package com.webwalker.runner.panel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;

/**
 * @author xujian
 */
public class CoinBigPanel extends JPanel {
    public CoinBigPanel() {
        initComponents();
    }

    private void radioButton1MouseClicked(MouseEvent e) {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        tabbedPane1 = new JTabbedPane();
        panel3 = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel4 = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        label7 = new JLabel();
        textField5 = new JTextField();
        panel5 = new JPanel();
        radioButton1 = new JRadioButton();
        radioButton2 = new JRadioButton();
        radioButton3 = new JRadioButton();
        radioButton4 = new JRadioButton();
        label3 = new JLabel();
        comboBox1 = new JComboBox();
        button1 = new JButton();
        button2 = new JButton();
        panel6 = new JPanel();
        label4 = new JLabel();
        label5 = new JLabel();
        textField3 = new JTextField();
        radioButton5 = new JRadioButton();
        radioButton6 = new JRadioButton();
        label10 = new JLabel();
        label11 = new JLabel();
        textField4 = new JTextField();
        label12 = new JLabel();
        textField8 = new JTextField();
        label13 = new JLabel();
        textField9 = new JTextField();
        label14 = new JLabel();
        radioButton7 = new JRadioButton();
        label15 = new JLabel();
        textField10 = new JTextField();
        panel7 = new JPanel();
        label6 = new JLabel();
        comboBox2 = new JComboBox<>();
        label8 = new JLabel();
        textField6 = new JTextField();
        label9 = new JLabel();
        textField7 = new JTextField();

        //======== this ========
        setLayout(null);

        //======== tabbedPane1 ========
        {

            //======== panel3 ========
            {
                panel3.setLayout(null);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel3.getComponentCount(); i++) {
                        Rectangle bounds = panel3.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel3.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel3.setMinimumSize(preferredSize);
                    panel3.setPreferredSize(preferredSize);
                }
            }
            tabbedPane1.addTab("\u4f7f\u7528\u8bf4\u660e", panel3);

            //======== panel1 ========
            {
                panel1.setLayout(null);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel1.getComponentCount(); i++) {
                        Rectangle bounds = panel1.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel1.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel1.setMinimumSize(preferredSize);
                    panel1.setPreferredSize(preferredSize);
                }
            }
            tabbedPane1.addTab("\u53c2\u6570\u914d\u7f6e", panel1);

            //======== panel2 ========
            {
                panel2.setLayout(null);

                //======== panel4 ========
                {
                    panel4.setBorder(new TitledBorder("API\u8bbe\u7f6e"));

                    //---- label1 ----
                    label1.setText("ApiKey");
                    label1.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- label2 ----
                    label2.setText("SecretKey");
                    label2.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- label7 ----
                    label7.setText("Auth Code");
                    label7.setHorizontalAlignment(SwingConstants.LEFT);

                    GroupLayout panel4Layout = new GroupLayout(panel4);
                    panel4.setLayout(panel4Layout);
                    panel4Layout.setHorizontalGroup(
                        panel4Layout.createParallelGroup()
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addGroup(panel4Layout.createParallelGroup()
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(panel4Layout.createParallelGroup()
                                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panel4Layout.createParallelGroup()
                                            .addComponent(textField1, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                            .addComponent(textField2, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)))
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
                                        .addContainerGap(18, Short.MAX_VALUE)
                                        .addComponent(label7, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(textField5, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                    );
                    panel4Layout.setVerticalGroup(
                        panel4Layout.createParallelGroup()
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel4Layout.createSequentialGroup()
                                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panel4Layout.createSequentialGroup()
                                        .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel4Layout.createParallelGroup()
                                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 8, Short.MAX_VALUE))
                    );
                }
                panel2.add(panel4);
                panel4.setBounds(20, 5, 335, 140);

                //======== panel5 ========
                {
                    panel5.setBorder(new TitledBorder("\u4ea4\u6613\u5bf9\u8bbe\u7f6e"));

                    //---- radioButton1 ----
                    radioButton1.setText("USDT");
                    radioButton1.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            radioButton1MouseClicked(e);
                        }
                    });

                    //---- radioButton2 ----
                    radioButton2.setText("BTC");

                    //---- radioButton3 ----
                    radioButton3.setText("ETH");

                    //---- radioButton4 ----
                    radioButton4.setText("BCH");

                    //---- label3 ----
                    label3.setText("\u5b50\u5e01\u79cd\u9009\u62e9");
                    label3.setHorizontalAlignment(SwingConstants.LEFT);

                    GroupLayout panel5Layout = new GroupLayout(panel5);
                    panel5.setLayout(panel5Layout);
                    panel5Layout.setHorizontalGroup(
                        panel5Layout.createParallelGroup()
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(radioButton1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioButton2)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioButton3)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                .addComponent(radioButton4)
                                .addContainerGap())
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(25, Short.MAX_VALUE))
                    );
                    panel5Layout.setVerticalGroup(
                        panel5Layout.createParallelGroup()
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(radioButton1)
                                    .addComponent(radioButton2)
                                    .addComponent(radioButton3)
                                    .addComponent(radioButton4))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(13, Short.MAX_VALUE))
                    );
                }
                panel2.add(panel5);
                panel5.setBounds(20, 155, 335, 115);

                //---- button1 ----
                button1.setText("\u542f\u52a8");
                panel2.add(button1);
                button1.setBounds(255, 400, 100, 40);

                //---- button2 ----
                button2.setText("\u6682\u505c");
                panel2.add(button2);
                button2.setBounds(370, 400, 100, 40);

                //======== panel6 ========
                {
                    panel6.setBorder(new TitledBorder("\u5176\u4ed6\u53c2\u6570\u8bbe\u7f6e"));

                    //---- label4 ----
                    label4.setText("\u5355\u7b14\u4ea4\u6613\u6570\u91cf:");
                    label4.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- label5 ----
                    label5.setText("\u4ea4\u6613\u7c7b\u578b:");
                    label5.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- textField3 ----
                    textField3.setText("0.01");

                    //---- radioButton5 ----
                    radioButton5.setText("\u9650\u4ef7\u5355");

                    //---- radioButton6 ----
                    radioButton6.setText("\u5e02\u4ef7\u5355");

                    //---- label10 ----
                    label10.setText("\u4ef7\u5dee\u8bbe\u7f6e:");
                    label10.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- label11 ----
                    label11.setText("\u4e70\u5165\u4ef7-\u5356\u51fa\u4ef7=");
                    label11.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- textField4 ----
                    textField4.setText("0.01");
                    textField4.setHorizontalAlignment(SwingConstants.CENTER);

                    //---- label12 ----
                    label12.setText("\u4f5c\u4e1a\u5468\u671f/s:");
                    label12.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- textField8 ----
                    textField8.setText("10");

                    //---- label13 ----
                    label13.setText("\u8ba2\u5355\u53d6\u6d88\u5468\u671f/s:");
                    label13.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- textField9 ----
                    textField9.setText("30");

                    //---- label14 ----
                    label14.setText("\u90e8\u5206\u6210\u4ea4\u5355\u81ea\u52a8\u5356\u51fa:");
                    label14.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- radioButton7 ----
                    radioButton7.setText("\u662f");

                    //---- label15 ----
                    label15.setText("\u4ea4\u6613\u5ef6\u65f6\u5468\u671f/s:");
                    label15.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- textField10 ----
                    textField10.setText("3.1");

                    GroupLayout panel6Layout = new GroupLayout(panel6);
                    panel6.setLayout(panel6Layout);
                    panel6Layout.setHorizontalGroup(
                        panel6Layout.createParallelGroup()
                            .addGroup(panel6Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel6Layout.createParallelGroup()
                                    .addGroup(panel6Layout.createSequentialGroup()
                                        .addGroup(panel6Layout.createParallelGroup()
                                            .addGroup(panel6Layout.createSequentialGroup()
                                                .addComponent(label5, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                .addGap(59, 59, 59))
                                            .addGroup(panel6Layout.createSequentialGroup()
                                                .addComponent(label4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(panel6Layout.createParallelGroup()
                                            .addGroup(panel6Layout.createSequentialGroup()
                                                .addComponent(radioButton5)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(radioButton6))
                                            .addGroup(panel6Layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(label11, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18))
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                                        .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addGroup(panel6Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(textField3, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(GroupLayout.Alignment.LEADING, panel6Layout.createSequentialGroup()
                                                .addComponent(label10, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(textField4, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
                                        .addGap(27, 27, 27))
                                    .addGroup(panel6Layout.createSequentialGroup()
                                        .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addGroup(panel6Layout.createSequentialGroup()
                                                .addComponent(label12, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(textField8))
                                            .addGroup(panel6Layout.createSequentialGroup()
                                                .addComponent(label13, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(textField9, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panel6Layout.createSequentialGroup()
                                                .addComponent(label14, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(radioButton7))
                                            .addGroup(panel6Layout.createSequentialGroup()
                                                .addComponent(label15, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                                                .addGap(31, 31, 31)
                                                .addComponent(textField10, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                    );
                    panel6Layout.setVerticalGroup(
                        panel6Layout.createParallelGroup()
                            .addGroup(panel6Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label5, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(radioButton5)
                                    .addComponent(radioButton6))
                                .addGap(12, 12, 12)
                                .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label4, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label10, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label11, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label12, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(panel6Layout.createParallelGroup()
                                    .addComponent(label15, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label13, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(label14, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(radioButton7))
                                .addContainerGap(72, Short.MAX_VALUE))
                    );
                }
                panel2.add(panel6);
                panel6.setBounds(370, 5, 325, 385);

                //======== panel7 ========
                {
                    panel7.setBorder(new TitledBorder("\u4ea4\u6613\u7b56\u7565\u8bbe\u7f6e"));

                    //---- label6 ----
                    label6.setText("\u7b56\u7565\u9009\u62e9");
                    label6.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- comboBox2 ----
                    comboBox2.setModel(new DefaultComboBoxModel<>(new String[] {
                        "Avg",
                        "Last",
                        "Bid",
                        "Mid"
                    }));

                    //---- label8 ----
                    label8.setText("\u6df1\u5ea6\u7cfb\u6570");
                    label8.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- textField6 ----
                    textField6.setText("5");
                    textField6.setHorizontalAlignment(SwingConstants.CENTER);

                    //---- label9 ----
                    label9.setText("\u5e73\u5747\u503c");
                    label9.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- textField7 ----
                    textField7.setHorizontalAlignment(SwingConstants.CENTER);
                    textField7.setText("0.009");

                    GroupLayout panel7Layout = new GroupLayout(panel7);
                    panel7.setLayout(panel7Layout);
                    panel7Layout.setHorizontalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(panel7Layout.createParallelGroup()
                                    .addComponent(label6, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label8))
                                .addGap(18, 18, 18)
                                .addGroup(panel7Layout.createParallelGroup()
                                    .addGroup(panel7Layout.createSequentialGroup()
                                        .addComponent(textField6, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(label9)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textField7, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
                                    .addComponent(comboBox2, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(24, Short.MAX_VALUE))
                    );
                    panel7Layout.setVerticalGroup(
                        panel7Layout.createParallelGroup()
                            .addGroup(panel7Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label6, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label8, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label9, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(18, Short.MAX_VALUE))
                    );
                }
                panel2.add(panel7);
                panel7.setBounds(20, 280, 335, 110);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel2.getComponentCount(); i++) {
                        Rectangle bounds = panel2.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel2.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel2.setMinimumSize(preferredSize);
                    panel2.setPreferredSize(preferredSize);
                }
            }
            tabbedPane1.addTab("\u8fd0\u884c\u72b6\u6001", panel2);

            tabbedPane1.setSelectedIndex(0);
        }
        add(tabbedPane1);
        tabbedPane1.setBounds(0, 0, 730, 490);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < getComponentCount(); i++) {
                Rectangle bounds = getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            setMinimumSize(preferredSize);
            setPreferredSize(preferredSize);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTabbedPane tabbedPane1;
    private JPanel panel3;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel4;
    private JLabel label1;
    private JLabel label2;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel label7;
    private JTextField textField5;
    private JPanel panel5;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JLabel label3;
    private JComboBox comboBox1;
    private JButton button1;
    private JButton button2;
    private JPanel panel6;
    private JLabel label4;
    private JLabel label5;
    private JTextField textField3;
    private JRadioButton radioButton5;
    private JRadioButton radioButton6;
    private JLabel label10;
    private JLabel label11;
    private JTextField textField4;
    private JLabel label12;
    private JTextField textField8;
    private JLabel label13;
    private JTextField textField9;
    private JLabel label14;
    private JRadioButton radioButton7;
    private JLabel label15;
    private JTextField textField10;
    private JPanel panel7;
    private JLabel label6;
    private JComboBox<String> comboBox2;
    private JLabel label8;
    private JTextField textField6;
    private JLabel label9;
    private JTextField textField7;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
