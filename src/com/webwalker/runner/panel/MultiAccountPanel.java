/*
 * Created by JFormDesigner on Fri Jul 13 17:03:48 CST 2018
 */

package com.webwalker.runner.panel;

import javax.swing.table.*;
import com.webwalker.core.config.PlatformType;

import java.awt.*;
import javax.swing.*;

/**
 * @author xujian
 */
public class MultiAccountPanel extends JPanel {
    private PlatformType platform;

    public void setPlatform(PlatformType platform) {
        this.platform = platform;
    }

    public MultiAccountPanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        btnAddAccount = new JButton();
        btnDeleteAccount = new JButton();

        //======== this ========
        setLayout(null);

        //======== scrollPane1 ========
        {

            //---- table1 ----
            table1.setModel(new DefaultTableModel(
                new Object[][] {
                    {"", null, "", null, null, null, null},
                    {null, null, null, null, null, null, null},
                },
                new String[] {
                    "\u5e73\u53f0", "\u516c\u94a5", "\u4ea4\u6613\u5bf9", "\u7b56\u7565", "\u6570\u91cf", "\u4ef7\u5dee", "\u64cd\u4f5c"
                }
            ));
            scrollPane1.setViewportView(table1);
        }
        add(scrollPane1);
        scrollPane1.setBounds(15, 55, 730, 380);

        //---- btnAddAccount ----
        btnAddAccount.setText("\u65b0\u589e");
        add(btnAddAccount);
        btnAddAccount.setBounds(295, 15, 85, btnAddAccount.getPreferredSize().height);

        //---- btnDeleteAccount ----
        btnDeleteAccount.setText("\u5220\u9664");
        add(btnDeleteAccount);
        btnDeleteAccount.setBounds(385, 15, 85, btnDeleteAccount.getPreferredSize().height);

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
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton btnAddAccount;
    private JButton btnDeleteAccount;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
