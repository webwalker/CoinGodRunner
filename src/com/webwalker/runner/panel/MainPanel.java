package com.webwalker.runner.panel;

import com.webwalker.runner.utility.FrameUtil;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author xujian
 */
public class MainPanel extends JPanel {
    public MainPanel() {
        initComponents();
    }

    private void label1MouseClicked(MouseEvent e) {

    }

    private void label1MouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void label1MouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void label1MousePressed(MouseEvent e) {
        FrameUtil.open(new CoinBigPanel(), "CoinBig挖矿机器人");
    }

    private void label2MousePressed(MouseEvent e) {
        FrameUtil.open(new CoinBigPanel(), "ZBG挖矿机器人");
    }

    private void label2MouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void label2MouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void label3MouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void label3MouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void label3MousePressed(MouseEvent e) {
        FrameUtil.open(new CoinBigPanel(), "EXX挖矿机器人");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();

        //======== this ========
        setBackground(new Color(239, 254, 244));
        setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        setForeground(UIManager.getColor("Button.darcula.color1"));
        setLayout(null);

        //---- label1 ----
        label1.setIcon(new ImageIcon(getClass().getResource("/img/cb.jpg")));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setText("CoinBig");
        label1.setToolTipText("CoinBig\u4ea4\u6613\u6240");
        label1.setHorizontalTextPosition(SwingConstants.CENTER);
        label1.setVerticalTextPosition(SwingConstants.BOTTOM);
        label1.setBorder(UIManager.getBorder("TitledBorder.border"));
        label1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label1MouseEntered(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                label1MouseExited(e);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                label1MousePressed(e);
            }
        });
        add(label1);
        label1.setBounds(30, 15, 80, 95);

        //---- label2 ----
        label2.setIcon(new ImageIcon(getClass().getResource("/img/zbg.jpg")));
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setText("ZBG");
        label2.setToolTipText("CoinBig\u4ea4\u6613\u6240");
        label2.setHorizontalTextPosition(SwingConstants.CENTER);
        label2.setVerticalTextPosition(SwingConstants.BOTTOM);
        label2.setBorder(UIManager.getBorder("TitledBorder.border"));
        label2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label2MouseEntered(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                label2MouseExited(e);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                label2MousePressed(e);
            }
        });
        add(label2);
        label2.setBounds(140, 15, 80, 95);

        //---- label3 ----
        label3.setIcon(new ImageIcon(getClass().getResource("/img/exx.jpg")));
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        label3.setText("EXX");
        label3.setToolTipText("CoinBig\u4ea4\u6613\u6240");
        label3.setHorizontalTextPosition(SwingConstants.CENTER);
        label3.setVerticalTextPosition(SwingConstants.BOTTOM);
        label3.setBorder(UIManager.getBorder("TitledBorder.border"));
        label3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label3MouseEntered(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                label3MouseExited(e);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                label3MousePressed(e);
            }
        });
        add(label3);
        label3.setBounds(250, 15, 80, 95);

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
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
