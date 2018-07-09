import com.webwalker.runner.panel.CoinBigPanel;
import com.webwalker.runner.utility.FrameUtil;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Mon Jul 09 20:42:45 CST 2018
 */



/**
 * @author xujian
 */
public class MainPanel extends JPanel {
    public MainPanel() {
        initComponents();
    }

    private void label1MouseClicked(MouseEvent e) {
        FrameUtil.open(new CoinBigPanel(), "CoinBig挖矿机器人");
    }

    private void label1MouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void label1MouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        label1 = new JLabel();

        //======== this ========
        setBackground(Color.white);
        setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
        setForeground(UIManager.getColor("Button.darcula.color1"));
        setLayout(null);

        //---- label1 ----
        label1.setIcon(new ImageIcon("/Users/xujian/GitHub/CoinGodRunner/src/img/cb.jpg"));
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setText("CoinBig");
        label1.setToolTipText("CoinBig\u4ea4\u6613\u6240");
        label1.setHorizontalTextPosition(SwingConstants.CENTER);
        label1.setVerticalTextPosition(SwingConstants.BOTTOM);
        label1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                label1MouseClicked(e);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                label1MouseEntered(e);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                label1MouseExited(e);
            }
        });
        add(label1);
        label1.setBounds(30, 15, 80, 95);

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
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
