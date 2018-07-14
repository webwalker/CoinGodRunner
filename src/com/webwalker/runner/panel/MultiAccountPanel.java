/*
 * Created by JFormDesigner on Fri Jul 13 17:03:48 CST 2018
 */

package com.webwalker.runner.panel;

import java.awt.event.*;
import javax.swing.table.*;

import com.webwalker.core.config.ConfigItem;
import com.webwalker.core.config.ConfigResolver;
import com.webwalker.core.config.PlatformType;
import com.webwalker.core.config.TaskParams;
import com.webwalker.core.utility.StringUtil;
import com.webwalker.runner.task.TaskController;
import com.webwalker.runner.utility.FrameUtil;
import com.webwalker.runner.view.AccountButtonEditor;
import com.webwalker.runner.view.AccountButtonRender;

import java.awt.*;
import javax.swing.*;

/**
 * @author xujian
 */
public class MultiAccountPanel extends JPanel {
    private static PlatformType platform;
    private static MultiAccountPanel instance = null;
    private ConfigItem config;
    private String key;
    private DefaultTableModel model;

    public static MultiAccountPanel getInstance(String platform) {
        PlatformType type = PlatformType.getByName(platform);
        return getInstance(type);
    }

    public static MultiAccountPanel getInstance(PlatformType platform) {
        setPlatform(platform);
        if (instance == null) {
            instance = new MultiAccountPanel();
            instance.setVisible(false);
        }
        return instance;
    }

    private static void setPlatform(PlatformType platform) {
        MultiAccountPanel.platform = platform;
        TaskController.loadPlatform(platform);
    }

    public MultiAccountPanel() {
        initComponents();
    }

    private void thisComponentShown(ComponentEvent e) {
//        this.init();
    }

    private void thisComponentHidden(ComponentEvent e) {
    }

    private void init() {
        config = TaskController.getPlatform(platform);
        if (config == null || config.configs.size() == 0) return;
        Object[][] rows = new Object[config.configs.size()][7];
        for (int i = 0; i < config.configs.size(); i++) {
            TaskParams params = config.configs.get(i);
            for (int j = 0; j < 7; j++) {
                switch (j) {
                    case 0:
                        rows[i][j] = platform;
                        break;
                    case 1:
                        rows[i][j] = params.accessKey;
                        break;
                    case 2:
                        rows[i][j] = params.symbolValue;
                        break;
                    case 3:
                        rows[i][j] = params.strategyType;
                        break;
                    case 4:
                        rows[i][j] = params.amount;
                        break;
                    case 5:
                        rows[i][j] = params.priceDiff;
                        break;
                    case 6:
                        rows[i][j] = TaskController.getStatus(params.accessKey);
                        break;
                    case 7:
                        rows[i][j] = "打开";
                        break;
                }
            }
        }
        model = new DefaultTableModel(
                rows,
                new String[]{"平台", "公钥", "交易对", "策略", "数量", "价差", "状态", "操作"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 7) {
                    return true;
                }
                return false;
            }
        };
        table1.setModel(model);
        table1.setRowHeight(25);
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table1.setDefaultRenderer(Object.class, r);
        table1.getColumnModel().getColumn(7).setCellRenderer(new AccountButtonRender());
        table1.getColumnModel().getColumn(7).setCellEditor(new AccountButtonEditor(new JTextField()));
    }

    public void reload() {
        this.init();
    }

    private int checkSelected() {
        if (table1.getRowCount() == 0) return 0;
        int row = table1.getSelectedRow();
        if (row < 0) {
            TaskController.showMessage(this, "请选择一个任务");
            return 0;
        }
        key = (String) table1.getValueAt(row, 1);
        if (StringUtil.isEmpty(key)) {
            return 0;
        }
        return row;
    }

    private void btnAddAccountMousePressed(MouseEvent e) {
        CoinSettingPanel panel = CoinSettingPanel.getInstance();
        panel.preInit(platform.getName(), "");
        if (!panel.isVisible()) {
            FrameUtil.open2(panel, platform.getName(), new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    reload();
                }
            });
        }
    }

    private void btnDeleteAccountMousePressed(MouseEvent e) {
        int row = checkSelected();
        if (row == 0) return;
        ConfigItem ci = ConfigResolver.getConfig(platform.getName());
        if (ci == null) return;
        java.util.List<TaskParams> params = ci.configs;
        TaskParams temp = null;
        for (TaskParams p : params) {
            if (p.accessKey.toLowerCase().equals(key.toLowerCase())) {
                temp = p;
            }
        }
        if (temp != null) {
            //先停止任务
            TaskController.pause(this, platform.getName(), key);
            //移除
            ci.configs.remove(temp);
            //UI更新
            if (model != null) {
                model.removeRow(row);
                model.fireTableDataChanged();
            }
        }
        ConfigResolver.saveConfig(platform.getName(), ci);
    }

    private void btnStartMousePressed(MouseEvent e) {
        int row = checkSelected();
        if (row == 0) return;
        TaskParams params = config.configs.get(row);
        if (params != null) {
            TaskController.start(this, platform.getName(), key, params.authCode);
        }
    }

    private void btnPauseMousePressed(MouseEvent e) {
        if (checkSelected() <= 0) return;
        TaskController.pause(this, platform.getName(), key);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        btnAddAccount = new JButton();
        btnDeleteAccount = new JButton();
        btnStart = new JButton();
        btnPause = new JButton();

        //======== this ========
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                thisComponentHidden(e);
            }
            @Override
            public void componentShown(ComponentEvent e) {
                thisComponentShown(e);
            }
        });
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
            ) {
                boolean[] columnEditable = new boolean[] {
                    false, false, false, false, false, false, false
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return columnEditable[columnIndex];
                }
            });
            scrollPane1.setViewportView(table1);
        }
        add(scrollPane1);
        scrollPane1.setBounds(10, 55, 730, 380);

        //---- btnAddAccount ----
        btnAddAccount.setText("\u65b0\u589e");
        btnAddAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btnAddAccountMousePressed(e);
            }
        });
        add(btnAddAccount);
        btnAddAccount.setBounds(185, 15, 85, btnAddAccount.getPreferredSize().height);

        //---- btnDeleteAccount ----
        btnDeleteAccount.setText("\u5220\u9664");
        btnDeleteAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btnDeleteAccountMousePressed(e);
            }
        });
        add(btnDeleteAccount);
        btnDeleteAccount.setBounds(275, 15, 85, btnDeleteAccount.getPreferredSize().height);

        //---- btnStart ----
        btnStart.setText("\u542f\u52a8");
        btnStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btnStartMousePressed(e);
            }
        });
        add(btnStart);
        btnStart.setBounds(375, 15, 85, btnStart.getPreferredSize().height);

        //---- btnPause ----
        btnPause.setText("\u6682\u505c");
        btnPause.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                btnPauseMousePressed(e);
            }
        });
        add(btnPause);
        btnPause.setBounds(465, 15, 85, btnPause.getPreferredSize().height);

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
    private JButton btnStart;
    private JButton btnPause;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
