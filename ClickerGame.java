/*
 * This project is a clicker game that has upgrades, auto-clickers, and a prestige system.
 * @author Marco A Duarte
 * @version 1.0
 */

import java.awt.Dimension;
import javax.swing.JFrame;

public class ClickerGame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clicker Game");
        frame.setPreferredSize(new Dimension(1000, 850));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new BenClickerPanel());
        frame.pack();
        frame.setVisible(true);
    }
}