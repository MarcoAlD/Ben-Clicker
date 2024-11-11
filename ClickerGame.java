import java.awt.Dimension;
import javax.swing.JFrame;

public class ClickerGame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clicker Game");
        frame.setPreferredSize(new Dimension(1000, 850));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ClickerGamePanel());
        frame.pack();
        frame.setVisible(true);
    }
}