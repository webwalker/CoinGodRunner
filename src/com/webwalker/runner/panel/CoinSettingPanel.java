/*
 * Created by JFormDesigner on Mon Jul 09 22:00:39 CST 2018
 */

package com.webwalker.runner.panel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.border.*;

import com.webwalker.adapter.controller.AbsController;
import com.webwalker.adapter.controller.coinbig.model.SymbolsResult;
import com.webwalker.adapter.strategy.StrategyFactory;
import com.webwalker.core.auth.AuthResolver;
import com.webwalker.core.config.*;
import com.webwalker.core.utility.*;
import com.webwalker.runner.task.TaskController;

/**
 * @author xujian
 */
public class CoinSettingPanel extends JPanel {
    private String platform;
    private AbsController controller;
    private Timer timer;
    private TaskParams params;
    private List<String> logs = new ArrayList<>();
    private volatile int logCount;
    private static Object lockObj = new Object();
    private static CoinSettingPanel instance = null;

    public static CoinSettingPanel getInstance() {
        if (instance == null) {
            instance = new CoinSettingPanel();
            instance.setVisible(false);
        }
        return instance;
    }

    public CoinSettingPanel() {
        initComponents();
    }

    public void preInit(String platform, String key) {
        this.platform = platform;
        this.params = null;
        if (!StringUtil.isEmpty(key)) {
            TaskParams params = TaskController.getTask(key);
            this.params = params;
            this.controller = StrategyFactory.getInstance().getController(platform, params);
        } else {
            this.controller = StrategyFactory.getInstance().getController(platform, null);
        }
    }

    public void reset() {
        TaskParams resetParam = new TaskParams();
        txtAccessKey.setText(resetParam.accessKey);
        txtSecretKey.setText(resetParam.secretKey);
        txtAuthCode.setText(resetParam.authCode);
        radioButton1.setSelected(true);
        cbStrategy.setSelectedItem("Last");
    }

    private void init() {
        getConfig();
        Logger.setCallback(new ILogCallback<String>() {
            @Override
            public void action(String key, String log) {
                synchronized (lockObj) {
                    //只显示跟自己有关的交易日志
                    if (params != null && params.accessKey.toLowerCase().equals(key.toLowerCase())) {
                        logs.add(log);
                        if (logCount > 100) {
                            logs.remove(0);
                        }

                        txtLog.setText(txtLog.getText() + "\r\n" + log);
                        txtLog.setSelectionStart(txtLog.getText().length());
                        JScrollBar vertical = scrollPane2.getVerticalScrollBar();
                        vertical.setValue(vertical.getMaximum());

                        logCount++;
                    }
                }
            }
        });
        timer = TimeUtil.executeAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (params != null) {
                    checkAuthCode(params.authCode);
                }
            }
        }, 1000);
        txtLog.setText("");
        txtRemark.setCaretPosition(0);
    }

    private void getSymbols(String symbol, ICallback<List<String>> callback) {
        if (symbol.length() == 0) return;
        controller.symbol(new ICallback<SymbolsResult>() {
            @Override
            public void action(SymbolsResult result) {
                List<String> list = result.data;
                List<String> matches = new ArrayList<>();
                for (String s : list) {
                    if (s.endsWith(symbol)) {
                        matches.add(s);
                    }
                }
                callback.action(matches);
            }
        });
    }

    private boolean saveConfig() {
        TaskParams item = new TaskParams();
        item.accessKey = txtAccessKey.getText();
        item.secretKey = txtSecretKey.getText();
        item.authCode = txtAuthCode.getText();
        item.symbolIndex = radioButton1.isSelected() ? 0 : 0;
        if (cbSymbol.getSelectedItem() != null) {
            item.symbolValue = cbSymbol.getSelectedItem().toString();
        }
        if (cbStrategy.getSelectedItem() != null) {
            item.strategyType = cbStrategy.getSelectedItem().toString();
        }
        item.depthNum = StringUtil.getInt(txtDepthNum.getText());
        item.avgValue = StringUtil.getDouble(txtAvgValue.getText());

        item.limitOrder = rbLimitOrder.isSelected() ? 1 : 0;
        item.amount = StringUtil.getDouble(txtAmount.getText());
        item.priceDiff = StringUtil.getDouble(txtPriceDiff.getText());
        item.robotTime = StringUtil.getLong(txtRobotTime.getText());
        item.pauseTime = StringUtil.getLong(txtPauseTime.getText());
        item.expireTime = StringUtil.getInt(txtExpireTime.getText());
        item.partSell = rbAutoSell.isSelected() ? 1 : 0;
        if (!check(item)) {
            return false;
        }
        //新增时使用
        params = item;
        controller.setParams(params);

        //找出原有的更新掉
        ConfigItem c = ConfigResolver.getConfig(platform);
        boolean existed = false;
        if (c != null) {
            TaskParams temp = null;
            for (TaskParams p : c.configs) {
                if (p.accessKey.toLowerCase().equals(item.accessKey.toLowerCase())) {
                    temp = p;
                    existed = true;
                }
            }
            if (temp != null) {
                c.configs.remove(temp);
                c.configs.add(item);
            }
        }
        if (c == null) {
            c = new ConfigItem();
            c.configs = new ArrayList<>();
            c.configs.add(item);
        } else if (!existed) {
            c.configs.add(item);
        }
        ConfigResolver.saveConfig(platform, c);
        return true;
    }

    private void showMessage(String params) {
        JOptionPane.showMessageDialog(this, "错误: " + params);
    }

    private boolean check(TaskParams item) {
        if (StringUtil.isEmpty(item.accessKey)) {
            showMessage("accessKey");
            return false;
        }
        if (StringUtil.isEmpty(item.secretKey)) {
            showMessage("secretKey");
            return false;
        }
        if (StringUtil.isEmpty(item.authCode)) {
            showMessage("authCode");
            return false;
        }
        if (StringUtil.isEmpty(item.symbolValue)) {
            showMessage("交易对");
            return false;
        }
        if (item.amount <= 0) {
            showMessage("amount");
            return false;
        }
        if (item.robotTime <= 0) {
            showMessage("robotTime");
            return false;
        }
        if (item.expireTime < 6) {
            showMessage("订单撤销周期需大于5秒");
            return false;
        }
        return true;
    }

    private void getConfig() {
        if (params == null) return;
        txtAccessKey.setText(params.accessKey);
        txtSecretKey.setText(params.secretKey);
        txtAuthCode.setText(params.authCode);
        if (params.symbolIndex == 0) {
            radioButton1.setSelected(true);
        }

        cbStrategy.setSelectedItem(params.strategyType);
        if (params.strategyType.toLowerCase().equals(StrategyType.Avg.getType())) {
            panelAvg.setVisible(true);
        } else {
            panelAvg.setVisible(false);
        }

        txtDepthNum.setText(params.depthNum + "");
        txtAvgValue.setText(params.avgValue + "");
        if (params.limitOrder()) {
            rbLimitOrder.setSelected(true);
        } else {
            rbMarketOrder.setSelected(true);
        }
        txtAmount.setText(params.amount + "");
        txtPriceDiff.setText(params.priceDiff + "");
        txtRobotTime.setText(params.robotTime + "");
        txtPauseTime.setText(params.pauseTime + "");
        txtExpireTime.setText(params.expireTime + "");

        if (params.partSell == 1) {
            rbAutoSell.setSelected(true);
        } else {
            rbAutoSell.setSelected(false);
        }
    }

    private boolean checkAuthCode(String authCode) {
        if (StringUtil.isEmpty(authCode)) {
            lTrial.setText("");
            lTrial.setVisible(false);
            return false;
        }
        long expireTime = AuthResolver.getExpireTime(authCode);
        long leftTime = expireTime - System.currentTimeMillis();
        if (leftTime < 0) {
            lTrial.setText("公测版已过期");
            lTrial.setVisible(true);
            return false;
        }
        long hours = leftTime / (24 * 60 * 60 * 1000);
        if (hours < 365) { //大于的为终身会员
            lTrial.setText("公测版:" + TimeUtil.calcuTimeSub(leftTime));
            lTrial.setVisible(true);
        } else {
            lTrial.setText("");
        }
        return true;
    }

    private void radioButton1ItemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            TaskParams param = new TaskParams();
            param.accessKey = txtAccessKey.getText();
            controller.setParams(param);
            getSymbols(radioButton1.getText(), new ICallback<List<String>>() {
                @Override
                public void action(List<String> o) {
                    for (String s : o) {
                        cbSymbol.addItem(s);
                    }
                    if (params != null && !StringUtil.isEmpty(params.symbolValue)) {
                        cbSymbol.setSelectedItem(params.symbolValue);
                    }
                }
            });
        }
    }

    private void btnStartMousePressed(MouseEvent e) {
        if (saveConfig()) {
            TaskController.start(this, platform, params.accessKey, params.authCode);
        }
    }

    private void btnPauseMousePressed(MouseEvent e) {
        TaskController.pause(this, platform, params.accessKey);
        TaskController.showMessage(this, "机器人已停止!");
    }

    private void txtAuthCodeKeyPressed(KeyEvent e) {
        if (params != null) {
            params.authCode = txtAuthCode.getText();
            checkAuthCode(params.authCode);
        }
    }

    private void thisComponentAdded(ContainerEvent e) {

    }

    private void thisComponentHidden(ComponentEvent e) {
    }

    private void thisComponentShown(ComponentEvent e) {
        reset();
        init();
    }

    private void cbStrategyItemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String item = (String) e.getItem();
            if (item.toLowerCase().equals(StrategyType.Avg.getType())) {
                panelAvg.setVisible(true);
            } else {
                panelAvg.setVisible(false);
            }
        }
    }

    private void btnSaveMousePressed(MouseEvent e) {
        if (saveConfig()) {
            TaskController.showMessage(this, "保存成功");
            MultiAccountPanel.getInstance(platform).reload();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        tabbedPane1 = new JTabbedPane();
        panel2 = new JPanel();
        panel4 = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        txtAccessKey = new JTextField();
        label7 = new JLabel();
        txtAuthCode = new JTextField();
        txtSecretKey = new JTextField();
        panel5 = new JPanel();
        radioButton1 = new JRadioButton();
        radioButton2 = new JRadioButton();
        radioButton3 = new JRadioButton();
        radioButton4 = new JRadioButton();
        label3 = new JLabel();
        cbSymbol = new JComboBox();
        btnStart = new JButton();
        btnPause = new JButton();
        panel6 = new JPanel();
        label4 = new JLabel();
        label5 = new JLabel();
        txtAmount = new JTextField();
        rbLimitOrder = new JRadioButton();
        rbMarketOrder = new JRadioButton();
        label10 = new JLabel();
        label11 = new JLabel();
        txtPriceDiff = new JTextField();
        label12 = new JLabel();
        txtRobotTime = new JTextField();
        label13 = new JLabel();
        txtExpireTime = new JTextField();
        label14 = new JLabel();
        rbAutoSell = new JRadioButton();
        label15 = new JLabel();
        txtPauseTime = new JTextField();
        label17 = new JLabel();
        txtDepthNum2 = new JTextField();
        label19 = new JLabel();
        txtAvgValue2 = new JTextField();
        label20 = new JLabel();
        panel7 = new JPanel();
        label6 = new JLabel();
        cbStrategy = new JComboBox<>();
        panelAvg = new JPanel();
        label8 = new JLabel();
        txtDepthNum = new JTextField();
        label9 = new JLabel();
        txtAvgValue = new JTextField();
        lTrial = new JLabel();
        btnSave = new JButton();
        panel1 = new JPanel();
        scrollPane2 = new JScrollPane();
        txtLog = new JTextPane();
        panel3 = new JPanel();
        scrollPane1 = new JScrollPane();
        txtRemark = new JTextPane();

        //======== this ========
        setPreferredSize(new Dimension(720, 445));
        setMinimumSize(new Dimension(720, 445));
        setMaximumSize(new Dimension(720, 445));
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

        //======== tabbedPane1 ========
        {
            tabbedPane1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
            tabbedPane1.setAutoscrolls(true);
            tabbedPane1.setMinimumSize(new Dimension(715, 445));
            tabbedPane1.setPreferredSize(new Dimension(715, 445));

            //======== panel2 ========
            {
                panel2.setMinimumSize(new Dimension(715, 445));
                panel2.setLayout(null);

                //======== panel4 ========
                {
                    panel4.setBorder(new TitledBorder("API\u8bbe\u7f6e"));

                    //---- label1 ----
                    label1.setText("AccessKey");
                    label1.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- label2 ----
                    label2.setText("SecretKey");
                    label2.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- txtAccessKey ----
                    txtAccessKey.setHorizontalAlignment(SwingConstants.LEFT);
                    txtAccessKey.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

                    //---- label7 ----
                    label7.setText("Auth Code");
                    label7.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- txtAuthCode ----
                    txtAuthCode.setHorizontalAlignment(SwingConstants.LEFT);
                    txtAuthCode.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            txtAuthCodeKeyPressed(e);
                        }
                    });

                    //---- txtSecretKey ----
                    txtSecretKey.setHorizontalAlignment(SwingConstants.LEFT);
                    txtSecretKey.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

                    GroupLayout panel4Layout = new GroupLayout(panel4);
                    panel4.setLayout(panel4Layout);
                    panel4Layout.setHorizontalGroup(
                            panel4Layout.createParallelGroup()
                                    .addGroup(panel4Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(panel4Layout.createParallelGroup()
                                                    .addGroup(panel4Layout.createSequentialGroup()
                                                            .addGroup(panel4Layout.createParallelGroup()
                                                                    .addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addGroup(panel4Layout.createParallelGroup()
                                                                    .addComponent(txtAccessKey, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(txtAuthCode, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
                                                            .addContainerGap(12, Short.MAX_VALUE))
                                                    .addGroup(panel4Layout.createSequentialGroup()
                                                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addComponent(txtSecretKey, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
                                                            .addContainerGap(12, Short.MAX_VALUE))))
                    );
                    panel4Layout.linkSize(SwingConstants.HORIZONTAL, new Component[]{txtAccessKey, txtAuthCode});
                    panel4Layout.setVerticalGroup(
                            panel4Layout.createParallelGroup()
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
                                            .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtAccessKey, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(panel4Layout.createParallelGroup()
                                                    .addComponent(label2, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtSecretKey, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                            .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(label7, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtAuthCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGap(14, 14, 14))
                    );
                    panel4Layout.linkSize(SwingConstants.VERTICAL, new Component[]{txtAccessKey, txtAuthCode});
                }
                panel2.add(panel4);
                panel4.setBounds(20, 5, 335, 140);

                //======== panel5 ========
                {
                    panel5.setBorder(new TitledBorder("\u4ea4\u6613\u5bf9\u8bbe\u7f6e"));

                    //---- radioButton1 ----
                    radioButton1.setText("USDT");
                    radioButton1.addItemListener(e -> radioButton1ItemStateChanged(e));

                    //---- radioButton2 ----
                    radioButton2.setText("BTC");

                    //---- radioButton3 ----
                    radioButton3.setText("ETH");

                    //---- radioButton4 ----
                    radioButton4.setText("BCH");

                    //---- label3 ----
                    label3.setText("  \u5b50\u5e01\u79cd\u9009\u62e9");
                    label3.setHorizontalAlignment(SwingConstants.LEFT);

                    GroupLayout panel5Layout = new GroupLayout(panel5);
                    panel5.setLayout(panel5Layout);
                    panel5Layout.setHorizontalGroup(
                            panel5Layout.createParallelGroup()
                                    .addGroup(panel5Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(panel5Layout.createParallelGroup()
                                                    .addGroup(panel5Layout.createSequentialGroup()
                                                            .addComponent(radioButton1, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(radioButton2, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(radioButton3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(radioButton4, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(panel5Layout.createSequentialGroup()
                                                            .addComponent(label3, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(cbSymbol, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)))
                                            .addContainerGap(31, Short.MAX_VALUE))
                    );
                    panel5Layout.linkSize(SwingConstants.HORIZONTAL, new Component[]{radioButton1, radioButton2, radioButton3, radioButton4});
                    panel5Layout.setVerticalGroup(
                            panel5Layout.createParallelGroup()
                                    .addGroup(panel5Layout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addGroup(panel5Layout.createParallelGroup()
                                                    .addComponent(radioButton4)
                                                    .addComponent(radioButton3)
                                                    .addComponent(radioButton2)
                                                    .addComponent(radioButton1))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(cbSymbol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                                            .addContainerGap(13, Short.MAX_VALUE))
                    );
                    panel5Layout.linkSize(SwingConstants.VERTICAL, new Component[]{radioButton1, radioButton2, radioButton3, radioButton4});
                }
                panel2.add(panel5);
                panel5.setBounds(20, 155, 335, 115);

                //---- btnStart ----
                btnStart.setText("\u542f\u52a8");
                btnStart.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        btnStartMousePressed(e);
                    }
                });
                panel2.add(btnStart);
                btnStart.setBounds(190, 400, 100, 40);

                //---- btnPause ----
                btnPause.setText("\u6682\u505c");
                btnPause.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        btnPauseMousePressed(e);
                    }
                });
                panel2.add(btnPause);
                btnPause.setBounds(305, 400, 100, 40);

                //======== panel6 ========
                {
                    panel6.setBorder(new TitledBorder("\u5176\u4ed6\u53c2\u6570\u8bbe\u7f6e"));

                    //---- label4 ----
                    label4.setText("\u5355\u7b14\u4ea4\u6613\u6570\u91cf:");
                    label4.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- label5 ----
                    label5.setText("\u4ea4\u6613\u7c7b\u578b:");
                    label5.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- txtAmount ----
                    txtAmount.setText("0.01");

                    //---- rbLimitOrder ----
                    rbLimitOrder.setText("\u9650\u4ef7\u5355");
                    rbLimitOrder.setSelected(true);

                    //---- rbMarketOrder ----
                    rbMarketOrder.setText("\u5e02\u4ef7\u5355");
                    rbMarketOrder.setEnabled(false);

                    //---- label10 ----
                    label10.setText("\u4ef7\u5dee\u8bbe\u7f6e:");
                    label10.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- label11 ----
                    label11.setText("\u4e70\u5165\u4ef7-\u5356\u51fa\u4ef7=");
                    label11.setHorizontalAlignment(SwingConstants.CENTER);

                    //---- txtPriceDiff ----
                    txtPriceDiff.setText("0.01");
                    txtPriceDiff.setHorizontalAlignment(SwingConstants.CENTER);

                    //---- label12 ----
                    label12.setText("\u4f5c\u4e1a\u5468\u671f/s:");
                    label12.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- txtRobotTime ----
                    txtRobotTime.setText("10");

                    //---- label13 ----
                    label13.setText("\u8ba2\u5355\u64a4\u9500\u5468\u671f/s:");
                    label13.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- txtExpireTime ----
                    txtExpireTime.setText("30");

                    //---- label14 ----
                    label14.setText("\u90e8\u5206\u6210\u4ea4\u5355\u81ea\u52a8\u5356\u51fa:");
                    label14.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- rbAutoSell ----
                    rbAutoSell.setText("\u662f");
                    rbAutoSell.setSelected(true);

                    //---- label15 ----
                    label15.setText("\u4ea4\u6613\u5ef6\u65f6\u5468\u671f/s:");
                    label15.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- txtPauseTime ----
                    txtPauseTime.setText("3");

                    //---- label17 ----
                    label17.setText("\u505c\u6b62\u6316\u77ff:");
                    label17.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- txtDepthNum2 ----
                    txtDepthNum2.setText("5");
                    txtDepthNum2.setHorizontalAlignment(SwingConstants.CENTER);
                    txtDepthNum2.setEditable(false);
                    txtDepthNum2.setEnabled(false);

                    //---- label19 ----
                    label19.setText("\u5206\u949f\u51fa\u9519");
                    label19.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- txtAvgValue2 ----
                    txtAvgValue2.setHorizontalAlignment(SwingConstants.CENTER);
                    txtAvgValue2.setText("30");
                    txtAvgValue2.setEditable(false);
                    txtAvgValue2.setEnabled(false);

                    //---- label20 ----
                    label20.setText("\u6b21");
                    label20.setHorizontalAlignment(SwingConstants.LEFT);

                    GroupLayout panel6Layout = new GroupLayout(panel6);
                    panel6.setLayout(panel6Layout);
                    panel6Layout.setHorizontalGroup(
                            panel6Layout.createParallelGroup()
                                    .addGroup(panel6Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(panel6Layout.createParallelGroup()
                                                    .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                                                            .addGroup(panel6Layout.createParallelGroup()
                                                                    .addComponent(label5, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(label4, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                                                            .addGroup(panel6Layout.createParallelGroup()
                                                                    .addComponent(txtAmount, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
                                                                    .addGroup(panel6Layout.createSequentialGroup()
                                                                            .addComponent(rbLimitOrder, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                            .addComponent(rbMarketOrder)))
                                                            .addGap(20, 20, 20))
                                                    .addGroup(panel6Layout.createSequentialGroup()
                                                            .addGroup(panel6Layout.createParallelGroup()
                                                                    .addComponent(label17, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                                    .addGroup(panel6Layout.createSequentialGroup()
                                                                            .addGroup(panel6Layout.createParallelGroup()
                                                                                    .addComponent(label14, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                                                                                    .addComponent(label12, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                                                                                    .addComponent(label13, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                                                                                    .addComponent(label15, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
                                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                            .addGroup(panel6Layout.createParallelGroup()
                                                                                    .addComponent(txtRobotTime)
                                                                                    .addGroup(panel6Layout.createSequentialGroup()
                                                                                            .addGap(1, 1, 1)
                                                                                            .addComponent(txtPauseTime, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
                                                                                    .addComponent(txtExpireTime)
                                                                                    .addGroup(panel6Layout.createSequentialGroup()
                                                                                            .addComponent(txtDepthNum2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                            .addComponent(label19)
                                                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                                            .addComponent(txtAvgValue2, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                                                                            .addGap(1, 1, 1)
                                                                                            .addComponent(label20, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                                                                    .addComponent(rbAutoSell)))
                                                                    .addGroup(panel6Layout.createSequentialGroup()
                                                                            .addComponent(label10, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                                            .addGap(58, 58, 58)
                                                                            .addComponent(label11, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                            .addComponent(txtPriceDiff, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))
                                                            .addGap(0, 0, Short.MAX_VALUE))))
                    );
                    panel6Layout.linkSize(SwingConstants.HORIZONTAL, new Component[]{txtExpireTime, txtPauseTime, txtRobotTime});
                    panel6Layout.setVerticalGroup(
                            panel6Layout.createParallelGroup()
                                    .addGroup(panel6Layout.createSequentialGroup()
                                            .addGap(15, 15, 15)
                                            .addComponent(label5, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                            .addGap(10, 10, 10)
                                            .addGroup(panel6Layout.createParallelGroup()
                                                    .addComponent(label4, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGap(14, 14, 14)
                                            .addGroup(panel6Layout.createParallelGroup()
                                                    .addComponent(label10, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label11, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtPriceDiff, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(panel6Layout.createParallelGroup()
                                                    .addComponent(label12, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtRobotTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addGap(12, 12, 12)
                                            .addGroup(panel6Layout.createParallelGroup()
                                                    .addComponent(label15, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtPauseTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(panel6Layout.createParallelGroup()
                                                    .addComponent(label13, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtExpireTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(panel6Layout.createParallelGroup()
                                                    .addComponent(label14, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(rbAutoSell))
                                            .addGap(18, 18, 18)
                                            .addGroup(panel6Layout.createParallelGroup()
                                                    .addComponent(label17, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtDepthNum2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label19, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtAvgValue2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label20, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                                            .addContainerGap(32, Short.MAX_VALUE))
                                    .addGroup(GroupLayout.Alignment.TRAILING, panel6Layout.createSequentialGroup()
                                            .addContainerGap(15, Short.MAX_VALUE)
                                            .addGroup(panel6Layout.createParallelGroup()
                                                    .addComponent(rbMarketOrder)
                                                    .addComponent(rbLimitOrder))
                                            .addGap(320, 320, 320))
                    );
                    panel6Layout.linkSize(SwingConstants.VERTICAL, new Component[]{txtExpireTime, txtPauseTime, txtRobotTime});
                }
                panel2.add(panel6);
                panel6.setBounds(370, 5, 335, 385);

                //======== panel7 ========
                {
                    panel7.setBorder(new TitledBorder("\u4ea4\u6613\u7b56\u7565\u8bbe\u7f6e"));

                    //---- label6 ----
                    label6.setText("\u7b56\u7565\u9009\u62e9");
                    label6.setHorizontalAlignment(SwingConstants.LEFT);

                    //---- cbStrategy ----
                    cbStrategy.setModel(new DefaultComboBoxModel<>(new String[]{
                            "Avg",
                            "Last",
                            "Bid",
                            "Mid"
                    }));
                    cbStrategy.addItemListener(e -> cbStrategyItemStateChanged(e));

                    //======== panelAvg ========
                    {
                        panelAvg.setVisible(false);
                        panelAvg.setLayout(null);

                        //---- label8 ----
                        label8.setText("\u6df1\u5ea6\u4e2a\u6570");
                        label8.setHorizontalAlignment(SwingConstants.LEFT);
                        panelAvg.add(label8);
                        label8.setBounds(10, 5, label8.getPreferredSize().width, 28);

                        //---- txtDepthNum ----
                        txtDepthNum.setText("5");
                        txtDepthNum.setHorizontalAlignment(SwingConstants.CENTER);
                        panelAvg.add(txtDepthNum);
                        txtDepthNum.setBounds(90, 5, 49, txtDepthNum.getPreferredSize().height);

                        //---- label9 ----
                        label9.setText("\u6df1\u5ea6\u6761\u4ef6");
                        label9.setHorizontalAlignment(SwingConstants.LEFT);
                        panelAvg.add(label9);
                        label9.setBounds(150, 5, label9.getPreferredSize().width, 28);

                        //---- txtAvgValue ----
                        txtAvgValue.setHorizontalAlignment(SwingConstants.CENTER);
                        txtAvgValue.setText("0.009");
                        panelAvg.add(txtAvgValue);
                        txtAvgValue.setBounds(210, 5, 69, txtAvgValue.getPreferredSize().height);
                    }

                    GroupLayout panel7Layout = new GroupLayout(panel7);
                    panel7.setLayout(panel7Layout);
                    panel7Layout.setHorizontalGroup(
                            panel7Layout.createParallelGroup()
                                    .addGroup(panel7Layout.createSequentialGroup()
                                            .addGroup(panel7Layout.createParallelGroup()
                                                    .addGroup(panel7Layout.createSequentialGroup()
                                                            .addGap(15, 15, 15)
                                                            .addComponent(label6, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addComponent(cbStrategy, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(panel7Layout.createSequentialGroup()
                                                            .addContainerGap()
                                                            .addComponent(panelAvg, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)))
                                            .addContainerGap(30, Short.MAX_VALUE))
                    );
                    panel7Layout.setVerticalGroup(
                            panel7Layout.createParallelGroup()
                                    .addGroup(panel7Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(label6, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cbStrategy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(panelAvg, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                            .addContainerGap(52, Short.MAX_VALUE))
                    );
                }
                panel2.add(panel7);
                panel7.setBounds(20, 280, 335, 110);

                //---- lTrial ----
                lTrial.setText("-----------------");
                lTrial.setForeground(Color.red);
                lTrial.setHorizontalAlignment(SwingConstants.RIGHT);
                lTrial.setVisible(false);
                panel2.add(lTrial);
                lTrial.setBounds(485, 405, 190, 28);

                //---- btnSave ----
                btnSave.setText("\u4fdd\u5b58");
                btnSave.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        btnSaveMousePressed(e);
                    }
                });
                panel2.add(btnSave);
                btnSave.setBounds(420, 400, 100, 40);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for (int i = 0; i < panel2.getComponentCount(); i++) {
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
            tabbedPane1.addTab("\u53c2\u6570\u8bbe\u7f6e", panel2);

            //======== panel1 ========
            {
                panel1.setLayout(null);

                //======== scrollPane2 ========
                {

                    //---- txtLog ----
                    txtLog.setBackground(Color.black);
                    txtLog.setForeground(Color.green);
                    txtLog.setFont(new Font(".SF NS Text", Font.PLAIN, 12));
                    txtLog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    scrollPane2.setViewportView(txtLog);
                }
                panel1.add(scrollPane2);
                scrollPane2.setBounds(0, 0, 715, 445);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for (int i = 0; i < panel1.getComponentCount(); i++) {
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
            tabbedPane1.addTab("\u8fd0\u884c\u72b6\u6001", panel1);

            //======== panel3 ========
            {
                panel3.setMinimumSize(new Dimension(715, 445));
                panel3.setPreferredSize(new Dimension(715, 445));
                panel3.setLayout(null);

                //======== scrollPane1 ========
                {

                    //---- txtRemark ----
                    txtRemark.setText("\u8f6f\u4ef6\u7279\u6027\uff1a\n1\u3001\u63d0\u4f9b\u591a\u79cd\u7b56\u7565, \u65b9\u4fbf\u4e0d\u540c\u884c\u60c5\u4f7f\u7528\n2\u3001\u5bf9\u78b0\u4ea4\u6613\u65f6, \u5f53\u4e70\u5165\u4e00\u90e8\u5206, \u5356\u51fa\u5168\u91cf\u5931\u8d25\u65f6, \u8ba2\u5355\u5065\u5eb7\u68c0\u67e5\u7a0b\u5e8f\u4f1a\u6839\u636e\u8bbe\u5b9a\u7684\u5468\u671f\u68c0\u67e5, \u6709\u6210\u4ea4\u90e8\u5206\u7684\u5219\u4ee5\u884c\u60c5\u4ef7\u5356\u51fa, \u5feb\u901f\u8d44\u91d1\u56de\u6d41, \u6ca1\u6709\u6210\u4ea4\u7684\u5219\u53d6\u6d88\u6389\n3\u3001\u5bf9\u540c\u4e00\u4e2a\u4ea4\u6613\u6240\u3001\u591a\u4e2a\u8d26\u53f7\u7684\u60c5\u51b5\uff0c\u76ee\u524d\u9700\u8981\u591a\u5f00(\u53ef\u4ee5\u65e0\u9650\u5f00)\uff0c\u540e\u7eed\u589e\u52a0\u591a\u4efb\u52a1\u7ba1\u7406\n4\u3001\u4fee\u6539\u914d\u7f6e\u540e\uff0c\u8bf7\u5148\u6682\u505c\u4ea4\u6613\uff0c\u7136\u540e\u91cd\u65b0\u542f\u52a8\u751f\u6548\n\n\u53c2\u6570\u8bf4\u660e\uff1a\n1\u3001\u7b56\u7565\u8bbe\u7f6e\n(1) \u7b56\u7565\u7c7b\u578b\navg\uff1a\u6839\u636e\u4e70\u76d8\u3001\u4e70\u76d8\u76d8\u53e3\u6df1\u5ea6\u7684\u5747\u4ef7\u4e3a\u57fa\u51c6\u6765\u5224\u65ad\u662f\u5426\u8fbe\u5230\u4e0b\u5355\u6761\u4ef6(\u5f53\u76d8\u53e3\u5356\u51fa\u6302\u5355\u5747\u4ef7\u5c0f\u4e8e\u76d8\u53e3\u4e70\u5165\u6302\u5355\u5747\u4ef7\u65f6),\u76f8\u6bd4mid\u591a\u4e86\u4e00\u4e2a\u4ef7\u5dee\u5224\u65ad\n\nLast\uff1a\u4ee5\u6700\u65b0\u6210\u4ea4\u4ef7\u4e3a\u57fa\u51c6\u6765\u8ba1\u7b97\u4e70\u5356\u4ef7\u683c\uff0c\u5982\u6700\u65b0\u6210\u4ea4\u4ef7\u4e3a1234.05\uff0c\u8bbe\u7f6e\u4ef7\u5dee\u4e3a0.01\uff0c\u5219\u4e70\u5165\u4ef7\u548c\u5356\u51fa\u4ef7\u5206\u522b\u4e3a1234.06\u548c1234.04\uff0c\u5373\u4e70\u5165\u4ef7\u5927\u4e8e\u5356\u51fa\u4ef7\uff0c\u4e5f\u53ef\u8bbe\u7f6e\u4e3a-0.01\uff0c\u5219\u6b64\u65f6\u4e70\u5165\u4ef7\u548c\u5356\u51fa\u4ef7\u5206\u522b\u4e3a1234.04\u548c1234.06\uff08\u5efa\u8bae\u884c\u60c5\u7a33\u5b9a\u65f6\u91c7\u7528\uff1a\u56e0\u4e3a\u5f53\u5356\u5355\u63025\u5143\uff0c\u4e70\u5355\u63024\u5143\u3002\u82e5\u6700\u65b0\u4ef7\u4e3a5\uff0c\u90a3\u4e48\u4e0b5\u7684\u4e70\u5355\uff0c\u518d\u4e0b\u4e2a5\u7684\u5356\u5355\uff0c\u522b\u4eba5\u7684\u5356\u5355\u5148\u6392\u961f\uff0c\u6240\u4ee5\u4f60\u4e70\u8fdb\u4e86\u522b\u4eba\u7684\uff0c\u4f46\u5374\u5356\u4e0d\u51fa\u53bb\uff09\n\nBid\uff1a\u4ee5\u5356\u4e00\u4e3a\u4e70\u5165\u4ef7\uff0c\u4ee5\u4e70\u4e00\u4e3a\u5356\u51fa\u4ef7\u8fdb\u884c\u4ea4\u6613\uff0c\u53ea\u80fd\u9ad8\u4e70\u4f4e\u5356\uff0c\u4f46\u80fd\u5feb\u901f\u6210\u4ea4, \u4e0d\u540c\u7684\u4ea4\u6613\u5bf9\u9700\u8981\u8bbe\u5b9a\u4e0d\u540c\u7684\u503c(\u5bf9\u4e8eBTC_USDT\u800c\u8a00\uff0c\u5efa\u8bae\u8be5\u503c\u8bbe\u4e3a0.01)\uff0c\u5982\u4e70\u4e00\u4ef71234.01 \u5356\u4e00\u4ef71234.02\uff0c\u5982\u679c\u8bbe\u7f6e\u4ef7\u5dee\u4e3a0.01\uff0c\u5219\u6ee1\u8db3\u6761\u4ef6\u6267\u884c\u53cc\u5411\u4e0b\u5355\uff0c\u4e70\u5165\u4ef7\u548c\u5356\u51fa\u4ef7\u5206\u522b\u4e3a1234.02\u548c1234.01\uff0c\u5373\u53ef\u9a6c\u4e0a\u6210\u4ea4\uff0c\u8be5\u503c\u8bbe\u5b9a\u8d8a\u5927\u6761\u4ef6\u8d8a\u80fd\u6ee1\u8db3\uff0c\u6210\u4ea4\u6982\u7387\u8d8a\u5927\uff0c\u4f46\u4f1a\u4e8f\u635f\u4e00\u5b9a\u7684\u4ef7\u5dee\uff0c\u53cd\u4e4b\u4ea6\u7136\u3002\n\nMid\uff1a\u4ee5\u76d8\u53e3\u4e2d\u95f4\u4ef7\u4e3a\u57fa\u51c6\u6765\u8ba1\u7b97\u4e70\u5356\u4ef7\u683c\uff0c\u5982\u4e70\u4e00\u4ef71234.01,\u5356\u4e00\u4ef71234.06, \u4e2d\u95f4\u4ef7\u4e3a(1234.01+1234.06)/2=1234.035, \u5982\u679c\u8bbe\u7f6e\u4ef7\u5dee\u4e3a0.01,\u5219\u4e70\u5165\u4ef7\u4e3a1234.045,\u5356\u51fa\u4ef7\u4e3a1234.025\t\t\t\t\t\t\n\n(2) Avg\u6df1\u5ea6\u4e2a\u6570\uff1a\u7528\u4e8e\u8ba1\u7b97\u76d8\u53e3\u6302\u5355\u5747\u4ef7\u6240\u7528\u4e2a\u6570, 1-20\u4e4b\u95f4(coinbig\u57281-18)\t\n(3) Avg\u6df1\u5ea6\u6761\u4ef6\uff1a\u5f53\u76d8\u53e3\u5356\u51fa\u6302\u5355\u5747\u4ef7\u5c0f\u4e8e\u76d8\u53e3\u4e70\u5165\u6302\u5355\u5747\u4ef7(1+\u6df1\u5ea6\u6761\u4ef6)%\u500d\u65f6, \u53d1\u51fa\u4ea4\u6613\u6307\u4ee4\uff08\u4e0d\u540c\u4ea4\u6613\u5bf9\u9700\u8981\u9488\u5bf9\u5e01\u7279\u6027\u81ea\u884c\u8ba1\u7b97AVG_VALUE\u6bd4\u7387\uff09\n\u76d8\u53e3\u5356\u51fa\u6302\u5355\u5747\u4ef7\u4e3a11000, \u4e70\u5165\u6302\u5355\u5747\u4ef7\u4e3a9900, 11000>9900*1.015=10048.5, \u4ef7\u5dee\u592a\u5927\u4e0d\u53d1\u9001\u4ea4\u6613\u6307\u4ee4\u3002\n\u76d8\u53e3\u5356\u51fa\u6302\u5355\u5747\u4ef7\u4e3a10000, \u4e70\u5165\u6302\u5355\u5747\u4ef7\u4e3a9900, 10000<9900*1.015=10048.5, \u4ef7\u5dee\u5c0f\u53ef\u53d1\u9001\u4ea4\u6613\u6307\u4ee4\n\n2\u3001\u4ea4\u6613\u7c7b\u578b\uff1a\u9650\u4ef7\u5355\u6839\u636e\u4e0a\u9762\u7684\u7b56\u7565+\u4ef7\u5dee\u5f97\u51fa\uff0c\u5e02\u4ef7\u5355\u5219\u6839\u636e\u5e02\u573a\u884c\u60c5\n3\u3001\u5355\u7b14\u4ea4\u6613\u6570\u91cf\uff1a\u5e01\u6570\u91cf\t\n4\u3001\u4ef7\u5dee\u8bbe\u7f6e\n   \u4ef7\u5dee=\u4e70\u5165\u4e0b\u5355\u4ef7\u683c-\u5356\u51fa\u4e0b\u5355\u4ef7\u683c, \u82e5\u8981\u5feb\u901f\u6210\u4ea4\u5efa\u8bae\u9ad8\u4e70\u4f4e\u5356, \u8be5\u503c\u4e3a\u6b63\u65f6\u9ad8\u4e70\u4f4e\u5356, \u4e3a0\u65f6\u4e70\u5165\u548c\u5356\u51fa\u4ef7\u683c\u76f8\u540c, \u4e3a\u8d1f\u65f6\u4f4e\u4e70\u9ad8\u5356\n5\u3001\u4f5c\u4e1a\u5468\u671f\uff1a\u6bcf\u9694\u591a\u5c11\u79d2\u8fdb\u884c\u4e00\u6b21\u64ae\u5408\u4ea4\u6613\n6\u3001\u4ea4\u6613\u5ef6\u65f6\uff1a\u4e70\u5165\u3001\u5356\u51fa\u4ea4\u6613\u4e2d\u95f4\u7684\u5ef6\u65f6\uff0c\u7528\u4e8e\u4ea4\u6613\u6240API\u8c03\u7528\u9891\u7387\u9650\u5236\n7\u3001\u8ba2\u5355\u53d6\u6d88\u5468\u671f\n   \u8ddf\u968f\u4f5c\u4e1a\u5468\u671f\u5b9a\u65f6\u68c0\u67e5((\u7709\u7b14\u8ba2\u5355\u8d85\u8fc7\u8be5\u65f6\u6548\u8bbe\u5b9a\u5219\u4e3a\u5931\u6548\u8ba2\u5355))\n   \u5df2\u4e0b\u5355\u672a\u6210\u4ea4\u7684\u5931\u6548\u8ba2\u5355\u81ea\u52a8\u53d6\u6d88\n   \u5df2\u4e0b\u5355\u53ea\u6210\u4ea4\u4e86\u4e00\u90e8\u5206\u7684\u5931\u6548\u8ba2\u5355\u81ea\u52a8\u53d6\u6d88\n8\u3001\u90e8\u5206\u6210\u4ea4\u5355\u81ea\u52a8\u5356\u51fa\uff1a\u4e70\u5165\u4e86\u90e8\u5206, \u5168\u91cf\u5356\u51fa\u5931\u8d25\u65f6, \u662f\u5426\u81ea\u52a8\u5356\u51fa\uff0c\u5feb\u901f\u56de\u6d41\u8d44\u91d1\n9\u3001\u505c\u6b62\u6316\u77ff\uff1a\u51e0\u5206\u949f\u4e4b\u5185\u51fa\u9519\u6b21\u6570\u8fbe\u5230\u591a\u5c11, \u505c\u6b62\u6316\u77ff\n10\u3001\u4e0d\u6316\u77ff\u65f6\uff0c\u9700\u8981\u624b\u5de5\u6682\u505c\n\n\u64cd\u4f5c\u5efa\u8bae\uff1a\n1\u3001\u5e01\u4ef7\u6025\u901f\u4e0b\u8dcc\u65f6, \u5c3d\u91cf\u4e0d\u8981\u5237\n2\u3001\u5c0f\u989d\u5feb\u901f\u6210\u4ea4\n3\u3001\u9002\u5f53\u7684\u4ea4\u6613\u7b56\u7565\n4\u3001\u81ea\u52a8\u6b62\u635f\n5\u3001\u9009\u62e9\u6bd4\u8f83\u7a33\u7684\u4ea4\u6613\u5bf9,\u6ce2\u52a8\u5c0f\u98ce\u9669\u5c0f\n\n");
                    scrollPane1.setViewportView(txtRemark);
                }
                panel3.add(scrollPane1);
                scrollPane1.setBounds(5, 0, 705, 445);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for (int i = 0; i < panel3.getComponentCount(); i++) {
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

            tabbedPane1.setSelectedIndex(0);
        }
        add(tabbedPane1);
        tabbedPane1.setBounds(0, 0, 720, 490);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < getComponentCount(); i++) {
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

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(radioButton1);
        buttonGroup1.add(radioButton2);
        buttonGroup1.add(radioButton3);
        buttonGroup1.add(radioButton4);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTabbedPane tabbedPane1;
    private JPanel panel2;
    private JPanel panel4;
    private JLabel label1;
    private JLabel label2;
    private JTextField txtAccessKey;
    private JLabel label7;
    private JTextField txtAuthCode;
    private JTextField txtSecretKey;
    private JPanel panel5;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JLabel label3;
    private JComboBox cbSymbol;
    private JButton btnStart;
    private JButton btnPause;
    private JPanel panel6;
    private JLabel label4;
    private JLabel label5;
    private JTextField txtAmount;
    private JRadioButton rbLimitOrder;
    private JRadioButton rbMarketOrder;
    private JLabel label10;
    private JLabel label11;
    private JTextField txtPriceDiff;
    private JLabel label12;
    private JTextField txtRobotTime;
    private JLabel label13;
    private JTextField txtExpireTime;
    private JLabel label14;
    private JRadioButton rbAutoSell;
    private JLabel label15;
    private JTextField txtPauseTime;
    private JLabel label17;
    private JTextField txtDepthNum2;
    private JLabel label19;
    private JTextField txtAvgValue2;
    private JLabel label20;
    private JPanel panel7;
    private JLabel label6;
    private JComboBox<String> cbStrategy;
    private JPanel panelAvg;
    private JLabel label8;
    private JTextField txtDepthNum;
    private JLabel label9;
    private JTextField txtAvgValue;
    private JLabel lTrial;
    private JButton btnSave;
    private JPanel panel1;
    private JScrollPane scrollPane2;
    private JTextPane txtLog;
    private JPanel panel3;
    private JScrollPane scrollPane1;
    private JTextPane txtRemark;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
