package com.webwalker.runner.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class AccountButtonRender extends JButton implements TableCellRenderer {
    public AccountButtonRender() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        setForeground(Color.black);
        setBackground(Color.ORANGE);
        setText("配置");
        return this;
    }
}