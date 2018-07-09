import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        //com.sun.awt.AWTUtilities.setWindowOpacity(panel, 0.6f); //半透明
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 480);
        //panel.setPreferredSize(new Dimension(600,500));
        //panel.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("CoinGod V1.0.1");
        frame.setIconImage(new ImageIcon("/img/icon.jpg").getImage());

        frame.setContentPane(new MainPanel());
        frame.setVisible(true);
    }
}
