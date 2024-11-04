
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ClickerGamePanel extends JPanel {
    // Instance variables
    private int clickCount = 0, level = 1, clickValue = 1, autoClickValue = 1;
    private JButton clickButton, upgradeButton, autoClickButton, upgradeAutoButton;
    private JLabel clickCountLabel, levelLabel, autoClickValueLabel, clickValueLabel;
    
    public ClickerGamePanel() {
        this.setLayout(new BorderLayout(100, 100));

        this.add(clickerPanel(), BorderLayout.CENTER);
        this.add(displayPanel(), BorderLayout.NORTH);
        this.add(upgradePanel(), BorderLayout.EAST);
        this.add(displayValuesPanel(), BorderLayout.WEST);
    }

    public JPanel clickerPanel() {
        JPanel clickerPanel = new JPanel();
        clickerPanel.setLayout(new GridLayout(2, 1, 0, 500));
        clickerPanel.setPreferredSize(new Dimension(400, 400));
        
        clickButton = new JButton("Click Me!");
        clickButton.addActionListener(new ClickerListener());
        clickerPanel.add(clickButton);

        autoClickButton = new JButton("Auto Clicker (100 clicks)");
        autoClickButton.addActionListener(new AutoClickerListener());
        clickerPanel.add(autoClickButton);

        return clickerPanel;
    }

    public JPanel displayPanel() {
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new FlowLayout());

        levelLabel = new JLabel("Level: " + level);
        displayPanel.add(levelLabel);

        clickCountLabel = new JLabel("Clicks: " + clickCount);
        displayPanel.add(clickCountLabel);

        return displayPanel;
    }

    public JPanel displayValuesPanel() {
        JPanel displayValuesPanel = new JPanel();
        displayValuesPanel.setLayout(new BoxLayout(displayValuesPanel, BoxLayout.Y_AXIS));

        autoClickValueLabel = new JLabel("Auto Click Value: " + autoClickValue);
        displayValuesPanel.add(autoClickValueLabel);

        clickValueLabel = new JLabel("Click Value: " + clickValue);
        displayValuesPanel.add(clickValueLabel);

        return displayValuesPanel;
    }

    public JPanel upgradePanel() {
        JPanel upgradePanel = new JPanel();
        upgradePanel.setLayout(new BoxLayout(upgradePanel, BoxLayout.Y_AXIS));

        upgradeButton = new JButton("Upgrade Click Value (10 clicks)");
        upgradeButton.addActionListener(new UpgradeListener());
        upgradePanel.add(upgradeButton);

        upgradeAutoButton = new JButton("Upgrade Auto Click Value (20 clicks)");
        upgradeAutoButton.addActionListener(new UpgradeAutoListener());
        upgradePanel.add(upgradeAutoButton);

        return upgradePanel;
    }

    public void levelUp() {
        level++;
        levelLabel.setText("Level: " + level);
    }

    private class ClickerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            clickCount += clickValue;
            clickCountLabel.setText("Clicks: " + clickCount);

            if (clickCount >= level * 10) {
                levelUp();
            }
        }
        
        
    }

    private class UpgradeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (clickCount >= 10) {
                clickCount -= 10;
                clickValue++;
                clickCountLabel.setText("Clicks: " + clickCount);
                clickValueLabel.setText("Click Value: " + clickValue);
            }
        }
        
    }

    private class UpgradeAutoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (clickCount >= 20) {
                clickCount -= 20;
                autoClickValue++;
                autoClickValueLabel.setText("Auto Click Value: " + autoClickValue);
            }
        }
        
    }

    private class AutoClickerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (clickCount >= 100) {
                clickCount -= 100;
                // Use the timer library to create an auto-clicker that clicks every second
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        clickCount += autoClickValue;
                        clickCountLabel.setText("Clicks: " + clickCount);
                    }
                });
                timer.start();
            }
        }
        
    }

}
