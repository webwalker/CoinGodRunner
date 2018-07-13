package com.webwalker.runner.utility;

import javax.swing.*;

public class FrameUtil {
    public static void open(JPanel panel, String title){
        JFrame frame = new JFrame(title);
        frame.setSize(730, 505);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("/img/icon.jpg").getImage());
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
