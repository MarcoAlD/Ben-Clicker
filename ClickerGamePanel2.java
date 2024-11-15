
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public final class ClickerGamePanel2 extends JPanel {

    private int totalClicks = 0, clickValue = 12000, clickMultiplier = 1, autoValue = 0, upgradeClickCost = 150, level = 1, nextLevelValue = (int) Math.pow(3, level) * 100, upgradeMultiplierCost = 1500, ainaCost = 3000, coraCost = 12000;
    private JButton clickButton, upgradeAutoButton, upgradeClickButton, upgradeMultiplierButton, ainaButton, coraButton, openShopButton, closeShopButton, addAinaButton, addCoraButton;
    private JLabel upgradeLabel, autoLabel, clickLabel, levelLabel, nextLevelLabel, multiplierLabel;
    private boolean multiplierApplied = false, autoClickerRunning = false;
    private Timer autoClickerTimer;

    public ClickerGamePanel2() {
        this.setLayout(new BorderLayout(100, 0));

        this.add(clickerPanel(), BorderLayout.CENTER);
        this.add(statsPanel(), BorderLayout.WEST);
        this.add(shopPanel(), BorderLayout.EAST);
    }

    public JPanel clickerPanel() {
        JPanel clickerPanel = new JPanel();
        clickerPanel.setLayout(new BorderLayout());

        ImageIcon ben = new ImageIcon("images/ben.png");
        Image benImage = ben.getImage();
        Image newBen = benImage.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        clickButton = new JButton(new ImageIcon(newBen));
        clickButton.setBorderPainted(false);
        clickButton.setContentAreaFilled(false);
        clickButton.setFocusPainted(false);
        clickButton.setOpaque(false);
        clickButton.addActionListener(new ClickListener());
        clickerPanel.add(clickButton, BorderLayout.CENTER);

        clickLabel = new JLabel("Total Clicks: " + totalClicks);
        clickerPanel.add(clickLabel, BorderLayout.NORTH);

        ImageIcon aina = new ImageIcon("images/aina.png");
        Image ainaImage = aina.getImage();
        Image newAina = ainaImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ainaButton = new JButton(new ImageIcon(newAina));
        ainaButton.setBorderPainted(false);
        ainaButton.setContentAreaFilled(false);
        ainaButton.setFocusPainted(false);
        ainaButton.setOpaque(false);
        ainaButton.setVisible(false);
        clickerPanel.add(ainaButton, BorderLayout.EAST);

        ImageIcon cora = new ImageIcon("images/cora.png");
        Image coraImage = cora.getImage();
        Image newCora = coraImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        coraButton = new JButton(new ImageIcon(newCora));
        coraButton.setBorderPainted(false);
        coraButton.setContentAreaFilled(false);
        coraButton.setFocusPainted(false);
        coraButton.setOpaque(false);
        coraButton.setVisible(false);
        clickerPanel.add(coraButton, BorderLayout.WEST);

        return clickerPanel;
    }

    public JPanel statsPanel() {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));

        levelLabel = new JLabel("Level: " + level);
        statsPanel.add(levelLabel);

        int nextLevel = nextLevelValue - totalClicks;
        nextLevelLabel = new JLabel("Next Level: " + nextLevel);
        statsPanel.add(nextLevelLabel);

        upgradeLabel = new JLabel("Click Value: " + clickValue);
        statsPanel.add(upgradeLabel);

        multiplierLabel = new JLabel("Click Multiplier: " + clickMultiplier);
        statsPanel.add(multiplierLabel);

        autoLabel = new JLabel("Auto Clicker: " + autoValue);
        statsPanel.add(autoLabel);

        return statsPanel;
    }

    @SuppressWarnings("Convert2Lambda")
    public JPanel shopPanel() {
        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));

        openShopButton = new JButton("Open Shop");
        openShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPanel.removeAll();
                shopPanel.add(closeShopButton);
                shopPanel.add(upgradeClickButton);
                shopPanel.add(upgradeMultiplierButton);
                shopPanel.add(upgradeAutoButton);
                shopPanel.add(addAinaButton);
                shopPanel.add(addCoraButton);
                shopPanel.revalidate();
                shopPanel.repaint();
            }
        });
        shopPanel.add(openShopButton);

        closeShopButton = new JButton("Close Shop");
        closeShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPanel.removeAll();
                shopPanel.add(openShopButton);
                shopPanel.revalidate();
                shopPanel.repaint();
            }
        });

        upgradeClickButton = new JButton("Upgrade Click Value (Cost: " + upgradeClickCost + ")");
        upgradeClickButton.addActionListener(new UpgradeClickListener());

        upgradeMultiplierButton = new JButton("Upgrade Click Multiplier (Cost: " + upgradeMultiplierCost + ")");
        upgradeMultiplierButton.addActionListener(new MultiplierListener());

        upgradeAutoButton = new JButton("Upgrade Auto Clicker: " + autoValue);

        addAinaButton = new JButton("Add Aina? (Cost: " + ainaCost + ")");
        addAinaButton.addActionListener(new autoAinaListener());

        addCoraButton = new JButton("Add Cora? (Cost: " + coraCost + ")");
        addCoraButton.addActionListener(new autoCoraListener());

        return shopPanel;
    }

    public void setTotalClicks(int totalClicks) {
        this.totalClicks = totalClicks;
        clickLabel.setText("Total Clicks: " + totalClicks);
    }

    public void setClickValue(int clickValue) {
        this.clickValue = clickValue;
        upgradeLabel.setText("Click Value: " + clickValue);
    }

    public void setAutoValue(int autoValue) {
        this.autoValue = autoValue;
        autoLabel.setText("Auto Clicker: " + autoValue);
    }

    public void setMultiplier(int clickMultiplier) {
        this.clickMultiplier = clickMultiplier;
        multiplierLabel.setText("Click Multiplier: " + clickMultiplier);
    }

    public void levelUp() {
        level++;
        levelLabel.setText("Level: " + level);
        untilNextLevel();
    }

    public void untilNextLevel() {
        nextLevelValue = (int) Math.pow(2, level) * 100;
        int nextLevel = nextLevelValue - totalClicks;
        nextLevelLabel.setText("Next Level: " + nextLevel);
    }

    public void checkLevel() {
        if (level % 5 == 0 && !multiplierApplied) {
            clickMultiplier++;
            multiplierLabel.setText("Click Multiplier: " + clickMultiplier);
            multiplierApplied = true;
        }
        else if (level % 5 != 0) {
            multiplierApplied = false;
        }
        if (level % 10 == 0 && !multiplierApplied) {
            autoValue++;
            autoLabel.setText("Auto Clicker: " + autoValue);
            multiplierApplied = true;
        }
        else if (level % 10 != 0) {
            multiplierApplied = false;
        }
    }

    public void startAutoClicker() {
        autoClickerTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTotalClicks(totalClicks + autoValue);
                untilNextLevel();
    
                if (totalClicks >= nextLevelValue) {
                    levelUp();
                }
                checkLevel();
            }
        });
        autoClickerTimer.start();
        autoClickerRunning = true;
    }

    private class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setTotalClicks(totalClicks + clickValue * clickMultiplier);
            untilNextLevel();
            if(totalClicks >= nextLevelValue) {
                levelUp();
            }
            checkLevel();
        }
    }

    private class UpgradeClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalClicks >= upgradeClickCost) {
                setTotalClicks(totalClicks - upgradeClickCost);
                setClickValue(clickValue + 1);
                upgradeClickCost += Math.pow(upgradeClickCost, 1.1);
                upgradeClickButton.setText("Upgrade Click Value (Cost: " + upgradeClickCost + ")");
            }
        }
    }

    private class MultiplierListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalClicks >= upgradeMultiplierCost) {
                setTotalClicks(totalClicks - upgradeMultiplierCost);
                setMultiplier(clickMultiplier + 1);
                upgradeMultiplierCost += Math.pow(upgradeMultiplierCost, 1.2);
                upgradeMultiplierButton.setText("Upgrade Click Multiplier (Cost: " + upgradeMultiplierCost + ")");
            }
        }
    }

    private class autoAinaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalClicks >= ainaCost) {
                ainaButton.setVisible(true);
                setTotalClicks(totalClicks - ainaCost);
                setAutoValue(autoValue + 1);
                autoLabel.setText("Auto Clicker: " + autoValue);
    
                if (!autoClickerRunning) {
                    startAutoClicker();
                }
            }
        }

}
    private class autoCoraListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalClicks >= coraCost) {
                coraButton.setVisible(true);
                setTotalClicks(totalClicks - coraCost);
                setAutoValue(autoValue + 5);
                autoLabel.setText("Auto Clicker: " + autoValue);
    
                if (!autoClickerRunning) {
                    startAutoClicker();
                }
            }
        }
    }
}
