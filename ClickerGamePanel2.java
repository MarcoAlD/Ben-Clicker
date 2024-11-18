
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public final class ClickerGamePanel2 extends JPanel {

    private JPanel shopPanel, inventoryPanel;
    private int totalClicks = 0, partyHatCost = 5000, clickValue = 1, clickMultiplier = 1, autoValue = 0, upgradeClickCost = 150, level = 1, nextLevelValue = (int) Math.pow(3, level) * 100, upgradeMultiplierCost = 1500, ainaCost = 3000, coraCost = 12000, autoClickerCost = 3000;
    private JButton clickButton, openInventoryButton, upgradeAutoButton, upgradeClickButton, upgradeMultiplierButton, ainaButton, coraButton, openShopButton, closeShopButton, addAinaButton, addCoraButton, partyHatButton;
    private JLabel autoLabel, clickLabel, levelLabel, nextLevelLabel, multiplierLabel, clickValueLabel;
    private boolean multiplierApplied = false, autoClickerRunning = false;
    private Timer autoClickerTimer;
    private Map<String, Boolean> inventory;

    public ClickerGamePanel2() {
        this.setLayout(new BorderLayout(100, 0));

        shopPanel = new JPanel();
        inventoryPanel = new JPanel();
        inventory = new HashMap<>();

        this.add(clickerPanel(), BorderLayout.CENTER);
        this.add(inventoryPanel, BorderLayout.WEST);
        this.add(shopPanel, BorderLayout.EAST);
        this.add(statsPanel(), BorderLayout.SOUTH);

        initializeShop();
        initializeInventory();
    }

    public void initializeShop() {
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
                // shopPanel.add(addCoraButton);
                shopPanel.add(partyHatButton);
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
                shopPanel.add(openInventoryButton);
                shopPanel.revalidate();
                shopPanel.repaint();
            }
        });

        openInventoryButton = new JButton("Open Inventory");
        openInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryPanel.removeAll();
                for (String item : inventory.keySet()) {
                    JButton itemButton = new JButton(item + (inventory.get(item) ? " (Equipped)" : " (Unequipped)"));
                    itemButton.addActionListener(new InventoryItemListener(item));
                    inventoryPanel.add(itemButton);
                }
                inventoryPanel.revalidate();
                inventoryPanel.repaint();
            }
        });

        upgradeClickButton = new JButton("Upgrade Click Value (Cost: " + upgradeClickCost + ")");
        upgradeClickButton.addActionListener(new UpgradeClickListener());

        upgradeMultiplierButton = new JButton("Upgrade Click Multiplier (Cost: " + upgradeMultiplierCost + ")");
        upgradeMultiplierButton.addActionListener(new MultiplierListener());

        upgradeAutoButton = new JButton("Upgrade Auto Clicker (Cost: " + autoClickerCost + ")");
        upgradeAutoButton.addActionListener(new UpgradeAutoListener());

        addAinaButton = new JButton("Add Aina? (Cost: " + ainaCost + ")");
        addAinaButton.addActionListener(new AutoAinaListener());

        partyHatButton = new JButton("Buy Party Hat (Cost: " + partyHatCost + ")");
        partyHatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (totalClicks >= partyHatCost) {
                    totalClicks -= partyHatCost;
                    inventory.put("Party Hat", false);
                    partyHatButton.setEnabled(false);
                }
            }
        });

        shopPanel.add(openShopButton);
        shopPanel.add(openInventoryButton);
    }

    public void initializeInventory() {
        inventoryPanel.setLayout(new GridLayout(0, 1));
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

        clickValueLabel = new JLabel("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
        statsPanel.add(clickValueLabel);

        multiplierLabel = new JLabel("Click Multiplier: " + clickMultiplier);
        statsPanel.add(multiplierLabel);

        autoLabel = new JLabel("Auto Clicker: " + autoValue);
        statsPanel.add(autoLabel);

        return statsPanel;
    }

    // @SuppressWarnings("Convert2Lambda")
    // public JPanel shopPanel() {
    //     JPanel shopPanel = new JPanel();
    //     shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));

    //     openShopButton = new JButton("Open Shop");
    //     openShopButton.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             shopPanel.removeAll();
    //             shopPanel.add(closeShopButton);
    //             shopPanel.add(upgradeClickButton);
    //             shopPanel.add(upgradeMultiplierButton);
    //             shopPanel.add(upgradeAutoButton);
    //             shopPanel.add(addAinaButton);
    //             shopPanel.add(addCoraButton);
    //             shopPanel.add(partyHatButton);
    //             shopPanel.revalidate();
    //             shopPanel.repaint();
    //         }
    //     });
    //     shopPanel.add(openShopButton);

    //     closeShopButton = new JButton("Close Shop");
    //     closeShopButton.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             shopPanel.removeAll();
    //             shopPanel.add(openShopButton);
    //             shopPanel.add(openInventoryButton);
    //             shopPanel.revalidate();
    //             shopPanel.repaint();
    //         }
    //     });

    //     openInventoryButton = new JButton("Open Inventory");


    //     upgradeClickButton = new JButton("Upgrade Click Value (Cost: " + upgradeClickCost + ")");
    //     upgradeClickButton.addActionListener(new UpgradeClickListener());

    //     upgradeMultiplierButton = new JButton("Upgrade Click Multiplier (Cost: " + upgradeMultiplierCost + ")");
    //     upgradeMultiplierButton.addActionListener(new MultiplierListener());

    //     upgradeAutoButton = new JButton("Upgrade Auto Clicker (Cost: " + autoClickerCost + ")");
    //     upgradeAutoButton.addActionListener(new UpgradeAutoListener());

    //     addAinaButton = new JButton("Add Aina? (Cost: " + ainaCost + ")");
    //     addAinaButton.addActionListener(new AutoAinaListener());

    //     addCoraButton = new JButton("Add Cora? (Cost: " + coraCost + ")");
    //     addCoraButton.addActionListener(new AutoCoraListener());

    //     partyHatButton = new JButton("Give Ben a Party Hat? (Cost: " + partyHatCost + ")");
    //     partyHatButton.addActionListener(new PartyHatListener());


    //     return shopPanel;
    // }

    public void setTotalClicks(int totalClicks) {
        this.totalClicks = totalClicks;
        clickLabel.setText("Total Clicks: " + totalClicks);
    }

    public void setClickValue(int clickValue) {
        this.clickValue = clickValue;
        clickValueLabel.setText("Click Value: " + clickValue);
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
        nextLevelValue = (int) Math.pow(3, level) * 100;
        int nextLevel = nextLevelValue - totalClicks;
        nextLevelLabel.setText("Next Level: " + nextLevel);
    }

    public void checkLevel() {
        if (level % 5 == 0 && !multiplierApplied) {
            JOptionPane.showMessageDialog(null, "You've reached a milestone! Click Multiplier increased by 1!");
            clickMultiplier++;
            multiplierLabel.setText("Click Multiplier: " + clickMultiplier);
            clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
            multiplierApplied = true;
        }
        if (level % 10 == 0 && !multiplierApplied) {
            JOptionPane.showMessageDialog(null, "You've reached a milestone! Auto Clicker increased by 1!");
            autoValue++;
            autoLabel.setText("Auto Clicker: " + autoValue);
            multiplierApplied = true;
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
                upgradeClickCost *= 2;
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
                upgradeMultiplierCost *= 2;
                upgradeMultiplierButton.setText("Upgrade Click Multiplier (Cost: " + upgradeMultiplierCost + ")");
            }
        }
    }

    private class AutoAinaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalClicks >= ainaCost) {
                ainaButton.setVisible(true);
                setTotalClicks(totalClicks - ainaCost);
                setAutoValue(autoValue + 1);
                autoLabel.setText("Auto Clicker: " + autoValue);
                addAinaButton.setVisible(false);
    
                if (!autoClickerRunning) {
                    startAutoClicker();
                }
            }
        }

}
    private class AutoCoraListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalClicks >= coraCost) {
                coraButton.setVisible(true);
                setTotalClicks(totalClicks - coraCost);
                setAutoValue(autoValue + 5);
                autoLabel.setText("Auto Clicker: " + autoValue);
                addCoraButton.setVisible(false);
    
                if (!autoClickerRunning) {
                    startAutoClicker();
                }
            }
        }
    }

    private class UpgradeAutoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalClicks >= autoClickerCost) {
                setTotalClicks(totalClicks - autoClickerCost);
                setAutoValue(autoValue + 1);
                autoClickerCost *= 2;
                upgradeAutoButton.setText("Upgrade Auto Clicker (Cost: " + autoClickerCost + ")");
            }
        }
    }

    private class PartyHatListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Ben is now wearing a party hat! +5 Click Value!");
            partyHatButton.setVisible(false);
            ImageIcon partyHat = new ImageIcon("images/ben_partyHat.png");
            Image partyHatImage = partyHat.getImage();
            Image newPartyHat = partyHatImage.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
            clickButton.setIcon(new ImageIcon(newPartyHat));
        }
    }

    private class InventoryItemListener implements ActionListener {
        private String item;

        public InventoryItemListener(String item) {
            this.item = item;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isEquipped = inventory.get(item);
            inventory.put(item, !isEquipped);
            ((JButton) e.getSource()).setText(item + (inventory.get(item) ? " (Equipped)" : " (Unequipped)"));
            updateEquippedItems();
        }
    }

    public void updateEquippedItems() {
        if (inventory.getOrDefault("Party Hat", false)) {
            partyHatButton.setVisible(false);
            JOptionPane.showMessageDialog(null, "Ben is now wearing a party hat! +5 Click Value!");
            clickValue += 5;
            clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
            ImageIcon partyHat = new ImageIcon("images/ben_partyHat.png");
            Image partyHatImage = partyHat.getImage();
            Image newPartyHat = partyHatImage.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
            clickButton.setIcon(new ImageIcon(newPartyHat));
        }
        else {
            partyHatButton.setVisible(true);
            clickValue -= 5;
            ImageIcon ben = new ImageIcon("images/ben.png");
            Image benImage = ben.getImage();
            Image newBen = benImage.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
            clickButton.setIcon(new ImageIcon(newBen));
        }
    }
}
