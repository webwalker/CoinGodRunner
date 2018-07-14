package com.webwalker.runner;

import com.webwalker.core.config.ConfigResolver;
import com.webwalker.runner.panel.MainPanel;

import javax.swing.*;

public class CoinGodUIRunner {
    public static void main(String[] args) {
        //ConfigResolver.setDebug(true);

        try {
            //UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
            //UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("");
        //com.sun.awt.AWTUtilities.setWindowOpacity(panel, 0.6f); //半透明
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 480);
        //panel.setPreferredSize(new Dimension(600,500));
        //panel.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("CoinGod V1.0.4");
        frame.setIconImage(new ImageIcon("/img/icon.jpg").getImage());

        frame.setContentPane(new MainPanel());
        frame.setVisible(true);
    }
}
