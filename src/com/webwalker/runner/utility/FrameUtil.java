package com.webwalker.runner.utility;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameUtil {
    public static void open(JPanel panel, String title) {
        panel.setVisible(true);
        JFrame frame = new JFrame(title);
        frame.setSize(730, 505);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("/img/icon.jpg").getImage());
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    public static void open2(JPanel panel, String title) {
        open2(panel, title, null);
    }

    public static void open2(JPanel panel, String title, WindowAdapter adapter) {
        JFrame frame = new JFrame(title);
        frame.setSize(730, 505);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("/img/icon.jpg").getImage());
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                panel.setVisible(false);
                if (adapter != null) {
                    adapter.windowClosing(e);
                }
            }

            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                panel.setVisible(true);
                if (adapter != null) {
                    adapter.windowOpened(e);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                if (adapter != null) {
                    adapter.windowClosed(e);
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
                super.windowIconified(e);
                if (adapter != null) {
                    adapter.windowIconified(e);
                }
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                super.windowDeiconified(e);
                if (adapter != null) {
                    adapter.windowDeiconified(e);
                }
            }

            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                if (adapter != null) {
                    adapter.windowActivated(e);
                }
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                super.windowDeactivated(e);
                if (adapter != null) {
                    adapter.windowDeactivated(e);
                }
            }

            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
                if (adapter != null) {
                    adapter.windowStateChanged(e);
                }
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                super.windowGainedFocus(e);
                if (adapter != null) {
                    adapter.windowGainedFocus(e);
                }
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                super.windowLostFocus(e);
                if (adapter != null) {
                    adapter.windowLostFocus(e);
                }
            }
        });
    }
}
