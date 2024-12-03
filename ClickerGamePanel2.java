
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
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

    private JPanel shopPanel, inventoryPanel, benInventoryPanel, coraInventoryPanel, ainaInventoryPanel;
    private int totalClicks = 0, coraDogEarsCost = 2000, coraPartyHatCost = 8000, mouseEarsCost = 1000, partyHatCost = 5000, clickValue = 12000, clickMultiplier = 1, autoValue = 0, upgradeClickCost = 150, level = 1, nextLevelValue = (int) Math.pow(3, level) * 100, upgradeMultiplierCost = 1500, ainaCost = 3000, coraCost = 12000, autoClickerCost = 3000;
    private JButton openBenInventoryButton, openCoraInventoryButton, openAinaInventoryButton, openBenShopButton, closeBenShopButton, openCoraShopButton, closeCoraShopButton, openAinaShopButton, closeAinaShopButton, clickButton, coraDogEarsButton, coraPartyHatButton, mouseEarsButton, openInventoryButton, closeInventoryButton, upgradeAutoButton, upgradeClickButton, upgradeMultiplierButton, ainaButton, coraButton, openShopButton, closeShopButton, addAinaButton, addCoraButton, partyHatButton;
    private JLabel autoLabel, clickLabel, levelLabel, nextLevelLabel, multiplierLabel, clickValueLabel;
    private boolean multiplierApplied = false, autoClickerRunning = false;
    private Timer autoClickerTimer;
    private Map<String, Boolean> benInventory, coraInventory, ainaInventory;

    public ClickerGamePanel2() {
        this.setLayout(new BorderLayout(100, 0));

        shopPanel = new JPanel();
        inventoryPanel = new JPanel(new BorderLayout());
        benInventoryPanel = new JPanel();
        coraInventoryPanel = new JPanel();
        ainaInventoryPanel = new JPanel();
        benInventory = new HashMap<>();
        coraInventory = new HashMap<>();
        // ainaInventory = new HashMap<>();

        this.add(clickerPanel(), BorderLayout.CENTER);
        this.add(inventoryPanel, BorderLayout.WEST);
        this.add(shopPanel, BorderLayout.EAST);
        this.add(statsPanel(), BorderLayout.SOUTH);

        initializeShop();
        initializeInventory();
    }

    @SuppressWarnings("Convert2Lambda")
    public void initializeShop() {
        shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));
        openShopButton = new JButton("Open General Shop");
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
                // shopPanel.add(partyHatButton);
                // shopPanel.add(mouseEarsButton);
                // shopPanel.add(coraPartyHatButton);
                // shopPanel.add(coraDogEarsButton);
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
                shopPanel.add(openBenInventoryButton);
                shopPanel.add(openCoraInventoryButton);
                // shopPanel.add(openAinaInventoryButton);
                shopPanel.add(openBenShopButton);
                shopPanel.add(openCoraShopButton);
                shopPanel.revalidate();
                shopPanel.repaint();
            }
        });

        openBenShopButton = new JButton("Open Ben's Shop");
        openBenShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPanel.removeAll();
                shopPanel.add(closeBenShopButton);
                shopPanel.add(partyHatButton);
                shopPanel.add(mouseEarsButton);
                shopPanel.revalidate();
                shopPanel.repaint();
            }
        });

        closeBenShopButton = new JButton("Close Ben's Shop");
        closeBenShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPanel.removeAll();
                shopPanel.add(openShopButton);
                shopPanel.add(openBenInventoryButton);
                shopPanel.add(openCoraInventoryButton);
                // shopPanel.add(openAinaInventoryButton);
                shopPanel.add(openBenShopButton);
                shopPanel.add(openCoraShopButton);
                shopPanel.revalidate();
                shopPanel.repaint();
            }
        });

        openCoraShopButton = new JButton("Open Cora's Shop");
        openCoraShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPanel.removeAll();
                shopPanel.add(closeCoraShopButton);
                shopPanel.add(coraPartyHatButton);
                shopPanel.add(coraDogEarsButton);
                shopPanel.revalidate();
                shopPanel.repaint();
            }
        });
        openCoraShopButton.setEnabled(false);

        closeCoraShopButton = new JButton("Close Cora's Shop");
        closeCoraShopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPanel.removeAll();
                shopPanel.add(openShopButton);
                shopPanel.add(openBenInventoryButton);
                shopPanel.add(openCoraInventoryButton);
                // shopPanel.add(openAinaInventoryButton);
                shopPanel.add(openBenShopButton);
                shopPanel.add(openCoraShopButton);
                shopPanel.revalidate();
                shopPanel.repaint();
            }
        });

        openBenInventoryButton = new JButton("Open Ben's Inventory");
        openBenInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryPanel.removeAll();
                benInventoryPanel.removeAll();
                for (String item : benInventory.keySet()) {
                    JButton itemButton = new JButton(item + (benInventory.get(item) ? " (Equipped)" : " (Unequipped)"));
                    itemButton.addActionListener(new InventoryItemListener(item, benInventory));
                    benInventoryPanel.add(itemButton);
                }
                benInventoryPanel.add(closeInventoryButton);
                inventoryPanel.add(benInventoryPanel, BorderLayout.CENTER);
                inventoryPanel.revalidate();
                inventoryPanel.repaint();
            }
        });

        openCoraInventoryButton = new JButton("Open Cora's Inventory");
        openCoraInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryPanel.removeAll();
                coraInventoryPanel.removeAll();
                for (String item : coraInventory.keySet()) {
                    JButton itemButton = new JButton(item + (coraInventory.get(item) ? " (Equipped)" : " (Unequipped)"));
                    itemButton.addActionListener(new InventoryItemListener(item, coraInventory));
                    coraInventoryPanel.add(itemButton);
                }
                coraInventoryPanel.add(closeInventoryButton);
                inventoryPanel.add(coraInventoryPanel, BorderLayout.CENTER);
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

        partyHatButton = new JButton("Buy Ben's Party Hat? (Cost: " + partyHatCost + ")");
        partyHatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (totalClicks >= partyHatCost) {
                    totalClicks -= partyHatCost;
                    JOptionPane.showMessageDialog(null, "Party Hat for Ben has been purchased! Check your inventory!");
                    clickLabel.setText("Total Clicks: " + totalClicks);
                    benInventory.put("Ben's Party Hat", false);
                    partyHatButton.setEnabled(false);
                    
                }
            }
        });

        mouseEarsButton = new JButton("Buy Ben's Mouse Ears? (Cost: " + mouseEarsCost + ")");
        mouseEarsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (totalClicks >= mouseEarsCost) {
                    totalClicks -= mouseEarsCost;
                    JOptionPane.showMessageDialog(null, "Mouse Ears for Ben has been purchased! Check your inventory!");
                    clickLabel.setText("Total Clicks: " + totalClicks);
                    benInventory.put("Ben's Mouse Ears", false);
                    mouseEarsButton.setEnabled(false);
                }
            }
        });

        coraPartyHatButton = new JButton("Buy Cora's Party Hat? (Cost: " + coraPartyHatCost + ")");
        coraPartyHatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (totalClicks >= coraPartyHatCost) {
                    totalClicks -= coraPartyHatCost;
                    JOptionPane.showMessageDialog(null, "Party Hat for Cora has been purchased! Check your inventory!");
                    clickLabel.setText("Total Clicks: " + totalClicks);
                    coraInventory.put("Cora's Party Hat", false);
                    coraPartyHatButton.setEnabled(false);
                }
            }
        });

        coraDogEarsButton = new JButton("Buy Cora's Dog Ears? (Cost: " + coraDogEarsCost + ")");
        coraDogEarsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (totalClicks >= coraDogEarsCost) {
                    totalClicks -= coraDogEarsCost;
                    JOptionPane.showMessageDialog(null, "Dog Ears for Cora has been purchased! Check your inventory!");
                    clickLabel.setText("Total Clicks: " + totalClicks);
                    coraInventory.put("Cora's Dog Ears", false);
                    coraDogEarsButton.setEnabled(false);
                }
            }
        });

        shopPanel.add(openShopButton);
        shopPanel.add(openBenInventoryButton);
        shopPanel.add(openCoraInventoryButton);
        // shopPanel.add(openAinaInventoryButton);
        shopPanel.add(openBenShopButton);
        shopPanel.add(openCoraShopButton);
    }

    public void initializeInventory() {
        benInventoryPanel.setLayout(new GridLayout(0, 1));
        coraInventoryPanel.setLayout(new GridLayout(0, 1));
        ainaInventoryPanel.setLayout(new GridLayout(0, 1));
    }

    public JPanel clickerPanel() {
        JPanel clickerPanel = new JPanel();
        clickerPanel.setLayout(new BorderLayout());

        ImageIcon ben = new ImageIcon("images/ben.png");
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
        ainaButton = new JButton(aina);
        ainaButton.setBorderPainted(false);
        ainaButton.setContentAreaFilled(false);
        ainaButton.setFocusPainted(false);
        ainaButton.setOpaque(false);
        ainaButton.setVisible(false);
        clickerPanel.add(ainaButton, BorderLayout.EAST);

        ImageIcon cora = new ImageIcon("images/cora.png");
        coraButton = new JButton(cora);
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

    @SuppressWarnings("Convert2Lambda")
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
                openCoraShopButton.setEnabled(true);
    
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
        private Map<String, Boolean> inventory;

        public InventoryItemListener(String item, Map<String, Boolean> inventory) {
            this.item = item;
            this.inventory = inventory;
        }
    
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isEquipped = inventory.get(item);
            if (isEquipped) {
                int response = JOptionPane.showConfirmDialog(null, "Do you want to unequip the " + item + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.NO_OPTION) {
                    return; // Do nothing if the user chooses not to unequip the item
                }
                if (response == JOptionPane.YES_OPTION) {
                    inventory.put(item, false);
                }
            } else {
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
                inventory.put(item, true);
            }
            ((JButton) e.getSource()).setText(item + (inventory.get(item) ? " (Equipped)" : " (Unequipped)"));
            updateEquippedItems(inventory);
        }
    }
    
    private void updateEquippedItems(Map<String, Boolean> inventory) {
        String equippedItem = null;

        for (Map.Entry<String, Boolean> entry : inventory.entrySet()) {
            if (entry.getValue()) {
                equippedItem = entry.getKey();
                break;
            }
        }

        switch (equippedItem) {
            case "Ben's Party Hat":
                partyHatButton.setVisible(false);
                JOptionPane.showMessageDialog(null, "Ben is now wearing a party hat! +5 Click Value!");
                clickValue += 5;
                clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
                ImageIcon partyHat = new ImageIcon("images/ben_partyHat.png");
                clickButton.setIcon(partyHat);
                break;

            case "Ben's Mouse Ears":
                mouseEarsButton.setVisible(false);
                JOptionPane.showMessageDialog(null, "Ben is now wearing mouse ears! +2 Click Value!");
                clickValue += 2;
                clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
                ImageIcon mouseEars = new ImageIcon("images/ben_mouseEars.png");
                clickButton.setIcon(mouseEars);
                break;

            case "Cora's Party Hat":
                coraPartyHatButton.setVisible(false);
                JOptionPane.showMessageDialog(null, "Cora is now wearing a party hat! +5 Click Value!");
                clickValue += 5;
                clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
                ImageIcon coraPartyHat = new ImageIcon("images/cora_partyHat.png");
                clickButton.setIcon(coraPartyHat);
                break;
            case "Cora's Dog Ears":
                coraDogEarsButton.setVisible(false);
                JOptionPane.showMessageDialog(null, "Cora is now wearing dog ears! +2 Click Value!");
                clickValue += 2;
                clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
                ImageIcon coraDogEars = new ImageIcon("images/cora_dogEars.png");
                coraButton.setIcon(coraDogEars);
                break;
            default:
                for (Map.Entry<String, Boolean> entry : inventory.entrySet()) {
                    if (entry.getKey().equals("Ben's Party Hat") && !entry.getValue()) {
                        partyHatButton.setVisible(true);
                        clickValue -= 5;
                        clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
                    } else if (entry.getKey().equals("Ben's Mouse Ears") && !entry.getValue()) {
                        mouseEarsButton.setVisible(true);
                        clickValue -= 2;
                        clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
                    } else if (entry.getKey().equals("Cora's Party Hat") && !entry.getValue()) {
                        coraPartyHatButton.setVisible(true);
                        clickValue -= 5;
                        clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
                    } else if (entry.getKey().equals("Cora's Dog Ears") && !entry.getValue()) {
                        coraDogEarsButton.setVisible(true);
                        clickValue -= 2;
                        clickValueLabel.setText("Click Value: " + clickValue + "(" + clickValue * clickMultiplier + ")");
                    }
                }
                clickButton.setIcon(new ImageIcon("images/ben.png"));
                coraButton.setIcon(new ImageIcon("images/cora.png"));
                break;
        }
    }
}

/*
 * Exception in thread "AWT-EventQueue-0" java.lang.NullPointerException: Cannot invoke "String.hashCode()" because "equippedItem" is null
        at ClickerGamePanel2.updateEquippedItems(ClickerGamePanel2.java:548)
        at ClickerGamePanel2$InventoryItemListener.actionPerformed(ClickerGamePanel2.java:534)
        at java.desktop/javax.swing.AbstractButton.fireActionPerformed(AbstractButton.java:1972)
        at java.desktop/javax.swing.AbstractButton$Handler.actionPerformed(AbstractButton.java:2314)
        at java.desktop/javax.swing.DefaultButtonModel.fireActionPerformed(DefaultButtonModel.java:407)
        at java.desktop/javax.swing.DefaultButtonModel.setPressed(DefaultButtonModel.java:262)
        at java.desktop/javax.swing.plaf.basic.BasicButtonListener.mouseReleased(BasicButtonListener.java:279)
        at java.desktop/java.awt.Component.processMouseEvent(Component.java:6621)
        at java.desktop/javax.swing.JComponent.processMouseEvent(JComponent.java:3398)
        at java.desktop/java.awt.Component.processEvent(Component.java:6386)
        at java.desktop/java.awt.Container.processEvent(Container.java:2266)
        at java.desktop/java.awt.Component.dispatchEventImpl(Component.java:4996)
        at java.desktop/java.awt.Container.dispatchEventImpl(Container.java:2324)
        at java.desktop/java.awt.Component.dispatchEvent(Component.java:4828)
        at java.desktop/java.awt.LightweightDispatcher.retargetMouseEvent(Container.java:4948)
        at java.desktop/java.awt.LightweightDispatcher.processMouseEvent(Container.java:4575)
        at java.desktop/java.awt.LightweightDispatcher.dispatchEvent(Container.java:4516)
        at java.desktop/java.awt.Container.dispatchEventImpl(Container.java:2310)
        at java.desktop/java.awt.Window.dispatchEventImpl(Window.java:2780)
        at java.desktop/java.awt.Component.dispatchEvent(Component.java:4828)
        at java.desktop/java.awt.EventQueue.dispatchEventImpl(EventQueue.java:775)
        at java.desktop/java.awt.EventQueue$4.run(EventQueue.java:720)
        at java.desktop/java.awt.EventQueue$4.run(EventQueue.java:714)
        at java.base/java.security.AccessController.doPrivileged(AccessController.java:400)
        at java.base/java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:87)
        at java.base/java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:98)
        at java.desktop/java.awt.EventQueue$5.run(EventQueue.java:747)
        at java.desktop/java.awt.EventQueue$5.run(EventQueue.java:745)
        at java.base/java.security.AccessController.doPrivileged(AccessController.java:400)
        at java.base/java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:87)
        at java.desktop/java.awt.EventQueue.dispatchEvent(EventQueue.java:744)
        at java.desktop/java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:203)
        at java.desktop/java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:124)
        at java.desktop/java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java:113)
        at java.desktop/java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:109)
        at java.desktop/java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:101)
        at java.desktop/java.awt.EventDispatchThread.run(EventDispatchThread.java:90)
 */
