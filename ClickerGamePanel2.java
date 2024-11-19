
import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
    private int totalClicks = 0, mouseEarsCost = 1000, partyHatCost = 5000, clickValue = 300, clickMultiplier = 1, autoValue = 0, upgradeClickCost = 150, level = 1, nextLevelValue = (int) Math.pow(3, level) * 100, upgradeMultiplierCost = 1500, ainaCost = 3000, coraCost = 12000, autoClickerCost = 3000;
    private JButton clickButton, mouseEarsButton, openInventoryButton, closeInventoryButton, upgradeAutoButton, upgradeClickButton, upgradeMultiplierButton, ainaButton, coraButton, openShopButton, closeShopButton, addAinaButton, addCoraButton, partyHatButton;
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
                shopPanel.add(addCoraButton);
                shopPanel.add(partyHatButton);
                shopPanel.add(mouseEarsButton);
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
                inventoryPanel.add(closeInventoryButton);
                inventoryPanel.revalidate();
                inventoryPanel.repaint();
            }
        });

        closeInventoryButton = new JButton("Close Inventory");
        closeInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryPanel.removeAll();
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

        addCoraButton = new JButton("Add Cora? (Cost: " + coraCost + ")");
        addCoraButton.addActionListener(new AutoCoraListener());

        partyHatButton = new JButton("Buy Party Hat (Cost: " + partyHatCost + ")");
        partyHatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (totalClicks >= partyHatCost) {
                    totalClicks -= partyHatCost;
                    JOptionPane.showMessageDialog(null, "Party Hat for Ben has been purchased! Check your inventory!");
                    clickLabel.setText("Total Clicks: " + totalClicks);
                    inventory.put("Party Hat", false);
                    partyHatButton.setEnabled(false);
                }
            }
        });

        mouseEarsButton = new JButton("Buy Mouse Ears (Cost: " + mouseEarsCost + ")");
        mouseEarsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (totalClicks >= mouseEarsCost) {
                    totalClicks -= mouseEarsCost;
                    JOptionPane.showMessageDialog(null, "Mouse Ears for Ben has been purchased! Check your inventory!");
                    clickLabel.setText("Total Clicks: " + totalClicks);
                    inventory.put("Mouse Ears", false);
                    mouseEarsButton.setEnabled(false);
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
        // Image benImage = ben.getImage();
        // Image newBen = benImage.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
        // clickButton = new JButton(new ImageIcon(newBen));
        clickButton = new JButton(ben);
        clickButton.setHorizontalAlignment(JButton.CENTER);
        clickButton.setBorderPainted(false);
        clickButton.setContentAreaFilled(false);
        clickButton.setFocusPainted(false);
        clickButton.setOpaque(false);
        clickButton.addActionListener(new ClickListener());
        clickerPanel.add(clickButton, BorderLayout.CENTER);

        clickLabel = new JLabel("Total Clicks: " + totalClicks);
        clickLabel.setHorizontalAlignment(JLabel.CENTER);
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
        statsPanel.setLayout(new FlowLayout());

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

    public void setTotalClicks(int totalClicks) {
        this.totalClicks = totalClicks;
        clickLabel.setText("Total Clicks: " + totalClicks);
    }

    public void setClickValue(int clickValue) {
        this.clickValue = clickValue;
        clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
    }

    public void setAutoValue(int autoValue) {
        this.autoValue = autoValue;
        autoLabel.setText("Auto Clicker: " + autoValue);
    }

    public void setMultiplier(int clickMultiplier) {
        this.clickMultiplier = clickMultiplier;
        multiplierLabel.setText("Click Multiplier: " + clickMultiplier);
        clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
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

    private class InventoryItemListener implements ActionListener {
        private String item;
    
        public InventoryItemListener(String item) {
            this.item = item;
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isEquipped = inventory.get(item);
            if (!isEquipped) {
                // Check if any item is currently equipped
                boolean anyItemEquipped = inventory.values().stream().anyMatch(equipped -> equipped);
                if (anyItemEquipped) {
                    int response = JOptionPane.showConfirmDialog(null, "An item is already equipped. Do you want to unequip it first?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.NO_OPTION) {
                        return; // Do nothing if the user chooses not to unequip the current item
                    }
                    if (response == JOptionPane.YES_OPTION) {
                        // Unequip the currently equipped item
                        for (Map.Entry<String, Boolean> entry : inventory.entrySet()) {
                            if (entry.getValue()) {
                                inventory.put(entry.getKey(), false);
                                break;
                            }
                        }
                    }
                }
            }
            inventory.put(item, !isEquipped);
            ((JButton) e.getSource()).setText(item + (inventory.get(item) ? " (Equipped)" : " (Unequipped)"));
            updateEquippedItems();
        }
    }
    
    private void updateEquippedItems() {
        String equippedItem = null;
    
        for (Map.Entry<String, Boolean> entry : inventory.entrySet()) {
            if (entry.getValue()) {
                equippedItem = entry.getKey();
                break;
            }
        }
    
        switch (equippedItem) {
            case "Party Hat":
                partyHatButton.setVisible(false);
                JOptionPane.showMessageDialog(null, "Ben is now wearing a party hat! +5 Click Value!");
                clickValue += 5;
                clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
                ImageIcon partyHat = new ImageIcon("images/ben_partyHat.png");
                clickButton.setIcon(partyHat);
                break;
    
            case "Mouse Ears":
                mouseEarsButton.setVisible(false);
                JOptionPane.showMessageDialog(null, "Ben is now wearing mouse ears! +2 Click Value!");
                clickValue += 2;
                clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
                ImageIcon mouseEars = new ImageIcon("images/ben_mouseEars.png");
                clickButton.setIcon(mouseEars);
                break;
    
            case null:
                if (inventory.containsKey("Party Hat")) {
                    partyHatButton.setVisible(true);
                    clickValue -= 5;
                    clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
                }
                if (inventory.containsKey("Mouse Ears")) {
                    mouseEarsButton.setVisible(true);
                    clickValue -= 2;
                    clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
                }
                ImageIcon ben = new ImageIcon("images/ben.png");
                clickButton.setIcon(ben);
                break;
    
            default:
                ImageIcon benDefault = new ImageIcon("images/ben.png");
                clickButton.setIcon(benDefault);
                break;
        }
    }
}
