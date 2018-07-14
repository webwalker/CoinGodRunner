package com.webwalker.runner.view;

import com.webwalker.core.utility.Logger;
import com.webwalker.runner.panel.CoinSettingPanel;
import com.webwalker.runner.utility.FrameUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

public class AccountButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private boolean hasClicked;

    public AccountButtonEditor(JTextField checkBox) {
        super(checkBox);
        this.setClickCountToStart(1);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    public Component getTableCellEditorComponent(final JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        button.setForeground(Color.black);
        button.setBackground(Color.ORANGE);
        label = "配置";
        button.setText(label);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hasClicked) return;
                String platform = table.getValueAt(table.getSelectedRow(), 0).toString();
                String key = table.getValueAt(table.getSelectedRow(), 1).toString();

                CoinSettingPanel panel = CoinSettingPanel.getInstance();
                panel.preInit(platform, key);
                if (!panel.isVisible()) {
                    hasClicked = true;
                    FrameUtil.open2(panel, platform, new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            super.windowClosing(e);
                            hasClicked = false;
                        }
                    });
                }
            }
        });
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            //
            //
            // JOptionPane.showMessageDialog(button, label + ": Ouch!");
            // System.out.println(label + ": Ouch!");
        }
        isPushed = false;
        return new String(label);
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        //System.out.println(1);
        return super.shouldSelectCell(anEvent);
    }
}
