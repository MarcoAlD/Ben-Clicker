
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ClickerGamePanel extends JPanel {
    // Instance variables
    private int clickCount = 0, level = 1, clickValue = 100, autoClickValue = 0;
    private JButton clickButton, upgradeButton, autoClickButton, upgradeAutoButton, addAutoClickerButton, openShopButton, closeShopButton;
    private JLabel clickCountLabel, levelLabel, autoClickValueLabel, clickValueLabel, levelUpLabel;
    private ImageIcon[] animationFrames;
    private int currentFrame;
    private Timer animationTimer;
    
    public ClickerGamePanel() {
        this.setLayout(new BorderLayout(100, 0));

        this.add(clickerPanel(), BorderLayout.CENTER);
        this.add(displayPanel(), BorderLayout.NORTH);
        this.add(shopPanel(), BorderLayout.EAST);
        this.add(displayValuesPanel(), BorderLayout.WEST);

        // startAutoClicker();
        initializeAnimation();
    }

    private void initializeAnimation() {
        
        animationFrames = new ImageIcon[]{
            new ImageIcon("neb.png"),
            // new ImageIcon("ben.png"),
            new ImageIcon("nen.png"),
            new ImageIcon("beb.png")
        };
        currentFrame = 0;

        
        animationTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFrame = (currentFrame + 1) % animationFrames.length;
                clickButton.setIcon(animationFrames[currentFrame]);
            }
        });
    }

    public final JPanel clickerPanel() {
        JPanel clickerPanel = new JPanel();
        clickerPanel.setLayout(new GridLayout(2, 1));
        clickerPanel.setPreferredSize(new Dimension(400, 400));

        
        ImageIcon icon = new ImageIcon("ben.png");
        clickButton = new JButton(icon);
        clickButton.setBorder(BorderFactory.createEmptyBorder());
        clickButton.setContentAreaFilled(false);
        clickButton.addActionListener(new ClickerListener());
        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationTimer.start();
                Timer stopTimer = new Timer(400, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        animationTimer.stop();
                        clickButton.setIcon(new ImageIcon("ben.png"));
                    }
                });
                stopTimer.setRepeats(false);
                stopTimer.start();
            }
        });
        clickerPanel.add(clickButton);

        autoClickButton = new JButton("Auto Clicker: Off");
        autoClickButton.addActionListener(new AutoClickerListener());
        autoClickButton.setVisible(false);
        clickerPanel.add(autoClickButton);

        return clickerPanel;
    }

    public final JPanel displayPanel() {
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new FlowLayout());

        levelLabel = new JLabel("Level: " + level);
        displayPanel.add(levelLabel);

        clickCountLabel = new JLabel("Clicks: " + clickCount);
        displayPanel.add(clickCountLabel);

        return displayPanel;
    }

    public final JPanel displayValuesPanel() {
        JPanel displayValuesPanel = new JPanel();
        displayValuesPanel.setLayout(new BoxLayout(displayValuesPanel, BoxLayout.Y_AXIS));

        autoClickValueLabel = new JLabel("Auto Click Value: " + autoClickValue);
        displayValuesPanel.add(autoClickValueLabel);

        clickValueLabel = new JLabel("Click Value: " + clickValue);
        displayValuesPanel.add(clickValueLabel);

        levelUpLabel = new JLabel("Level Up: " + Math.pow(3, level) * 100);
        displayValuesPanel.add(levelUpLabel);

        return displayValuesPanel;
    }

    @SuppressWarnings("Convert2Lambda")
    public final JPanel shopPanel() {
        JPanel upgradePanel = new JPanel();
        upgradePanel.setLayout(new BoxLayout(upgradePanel, BoxLayout.Y_AXIS));

        openShopButton = new JButton("Open Shop");
        openShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeShopButton.setVisible(true);
                upgradeButton.setVisible(true);
                upgradeAutoButton.setVisible(true);
                addAutoClickerButton.setVisible(true);
                openShopButton.setVisible(false);
            }
        });
        
        upgradePanel.add(openShopButton);

        closeShopButton = new JButton("Close Shop");
        closeShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeShopButton.setVisible(false);
                upgradeButton.setVisible(false);
                upgradeAutoButton.setVisible(false);
                addAutoClickerButton.setVisible(false);
                openShopButton.setVisible(true);
            }
        });
        closeShopButton.setVisible(false);
        upgradePanel.add(closeShopButton);

        upgradeButton = new JButton("Upgrade Click Value (10 clicks)");
        upgradeButton.addActionListener(new UpgradeListener());
        upgradeButton.setVisible(false);
        upgradePanel.add(upgradeButton);

        upgradeAutoButton = new JButton("Upgrade Auto Click Value (20 clicks)");
        upgradeAutoButton.addActionListener(new UpgradeAutoListener());
        upgradeAutoButton.setVisible(false);
        upgradePanel.add(upgradeAutoButton);

        addAutoClickerButton = new JButton("Add the auto clicker (100 clicks)");
        addAutoClickerButton.addActionListener(new addAutoListener());
        addAutoClickerButton.setVisible(false);
        upgradePanel.add(addAutoClickerButton);

        return upgradePanel;
    }

    public void levelUp() {
        level++;
        levelLabel.setText("Level: " + level);
    
        int clicksForNextLevel = (int) (Math.pow(3, level) * 100);
        levelUpLabel.setText("Level Up: " + clicksForNextLevel);
    }

    public final void startAutoClicker() {
        @SuppressWarnings("Convert2Lambda")
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickCount += autoClickValue;
                clickCountLabel.setText("Clicks: " + clickCount);
                
                animationTimer.start();
                Timer stopTimer = new Timer(400, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        animationTimer.stop();
                        clickButton.setIcon(new ImageIcon("ben.png"));
                    }
                });
                stopTimer.setRepeats(false);
                stopTimer.start();
            }
        });
        timer.start();
    }

    private class ClickerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clickCount += clickValue;
            clickCountLabel.setText("Clicks: " + clickCount);
    
            int clicksForNextLevel = (int) (Math.pow(3, level) * 100);
            int remainingClicks = clicksForNextLevel - clickCount;
            levelUpLabel.setText("Level Up: " + remainingClicks);
    
            if (clickCount >= clicksForNextLevel) {
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
            if (autoClickButton.getText().equals("Auto Clicker: Off")) {
                autoClickValue += 1;
                autoClickButton.setText("Auto Clicker: On");
                autoClickValueLabel.setText("Auto Click Value: " + autoClickValue);
    
                Timer autoClickTimer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        clickCount += autoClickValue;
                        clickCountLabel.setText("Clicks: " + clickCount);
    
                        int clicksForNextLevel = (int) (Math.pow(3, level) * 100);
                        int remainingClicks = clicksForNextLevel - clickCount;
                        levelUpLabel.setText("Level Up: " + remainingClicks);
    
                        if (clickCount >= clicksForNextLevel) {
                            levelUp();
                        }
                    }
                });
                autoClickTimer.start();
            } else {
                autoClickButton.setText("Auto Clicker: Off");
                autoClickValue = 0;
            }
        }
    }

    private class addAutoListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (clickCount >= 100) {
                clickCount -= 100;
                autoClickButton.setVisible(true);
                addAutoClickerButton.setEnabled(false);
            }
        }
        
    }

}
