import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ClickerGame extends JPanel{
    private final int DELAY = 100;

    private int XOffset = 0;
    private int YOffset = 0;
    private int xOffalt = getWidth();
    
    public void paintComponent(Graphics g) {
        // add GUI elements here
        super.paintComponent(g);
        
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clicker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ClickerGame());
        frame.pack();
        frame.setVisible(true);
    }

    public ClickerGame() {
        int initWidth = 350;
        int initHeight = 250;
        setPreferredSize(new Dimension(initWidth, initHeight));
        this.setDoubleBuffered(true);

        startTimer();
    }


    private void startTimer() {
        ActionListener timerListener = new TimerListener();
        Timer timer = new Timer(DELAY, timerListener);
        timer.start();
    }

    // TimerListener class, repaints the panel every frame
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }
}