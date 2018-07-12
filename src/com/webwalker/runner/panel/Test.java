/*
 * Created by JFormDesigner on Tue Jul 10 13:41:00 CST 2018
 */

package com.webwalker.runner.panel;

import java.awt.*;
import javax.swing.*;

/**
 * @author xujian
 */
public class Test extends JPanel {
    public Test() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane2 = new JScrollPane();
        editorPane1 = new JEditorPane();

        //======== this ========
        setLayout(null);

        //======== scrollPane2 ========
        {

            //---- editorPane1 ----
            editorPane1.setText("<b>fsdfdsf</b> <br /> fsfsda");
            editorPane1.setContentType("text/html");
            scrollPane2.setViewportView(editorPane1);
        }
        add(scrollPane2);
        scrollPane2.setBounds(35, 20, 535, 420);

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
    private JScrollPane scrollPane2;
    private JEditorPane editorPane1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
