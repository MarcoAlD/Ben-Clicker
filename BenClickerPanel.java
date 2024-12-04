import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BenClickerPanel extends JPanel {
    // Instance variables
    private JPanel clickPanel;
    private JButton ainaButton, coraButton, benButton, openGeneralShopButton, closeShopButton, openBenShopButton, openCoraShopButton, openAinaShopButton, upgradeBoinValueButton, upgradeAutoValueButton, upgradeBoinMultiplierButton, benPartyHatButton, benGlassesButton, benMouseEarsButton, buyCoraButton, coraPartyHatButton, coraGlassesButton, coraDogEarsButton, buyAinaButton, ainaPartyHatButton, ainaGlassesButton, ainaCatEarsButton, equipBenPartyHatButton, unequipBenPartyHatButton, equipBenGlassesButton, unequipBenGlassesButton, equipBenMouseEarsButton, unequipBenMouseEarsButton, equipCoraPartyHatButton, unequipCoraPartyHatButton, equipCoraGlassesButton, unequipCoraGlassesButton, equipCoraDogEarsButton, unequipCoraDogEarsButton, equipAinaPartyHatButton, unequipAinaPartyHatButton, equipAinaGlassesButton, unequipAinaGlassesButton, equipAinaCatEarsButton, unequipAinaCatEarsButton;
    private JLabel autoValueLabel, nextLevelLabel, boinValueLabel, boinMultiplierLabel, levelLabel, totalBoinsLabel;
    private int autoValue = 0, totalBoins = 0, boinValue = 1, boinMultiplier = 1, level = 1, nextLevelValue = (int) Math.pow(3, level) * 1000, upgradeBoinValue = (int) Math.pow(3, boinValue) * 100, upgradeAutoValue = (int) Math.pow(3, autoValue) * 100, upgradeBoinMultiplier = (int) Math.pow(3, boinMultiplier) * 100;
    private boolean multiplierApplied = false, autoClickerRunning = false;
    private Timer autoClickerTimer;

    // Constructor

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public BenClickerPanel() {
        this.setLayout(new BorderLayout());
        this.add(titleScreenPanel(), BorderLayout.CENTER);
    }

    /*
     * Title screen panel where the user clicks play and the game starts
     * @return JPanel
     */

    @SuppressWarnings("Convert2Lambda")
    public JPanel titleScreenPanel() {
        JPanel titleScreenPanel = new JPanel();
        // add a title screen where the user clicks play and the game starts
        titleScreenPanel.setLayout(new BoxLayout(titleScreenPanel, BoxLayout.Y_AXIS));
        JLabel titleScreenLabel = new JLabel("Welcome to Ben Clicker!");
        titleScreenLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleScreenLabel.setFont(titleScreenLabel.getFont().deriveFont(50.0f));
        titleScreenPanel.add(titleScreenLabel);
        JButton playButton = new JButton("Play");
        playButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        titleScreenPanel.add(Box.createVerticalGlue());
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titleScreenPanel.setVisible(false);
                add(createClickPanel(), BorderLayout.CENTER);
                add(statsPanel(), BorderLayout.SOUTH);
                add(totalBoinsPanel(), BorderLayout.NORTH);
                add(shopPanel(), BorderLayout.EAST);
            }
        });
        playButton.setFont(playButton.getFont().deriveFont(30.0f));
        titleScreenPanel.add(playButton);
        titleScreenPanel.add(Box.createVerticalGlue());
        JLabel credits = new JLabel("Ben Clicker by Marco, Art provided by Mar");
        credits.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        credits.setFont(credits.getFont().deriveFont(20.0f));
        titleScreenPanel.add(credits);
        titleScreenPanel.setBackground(Color.PINK);
        return titleScreenPanel;
}

/*
 * Click panel where you can click on Ben to get boins
 * @return JPanel
 */

private JPanel createClickPanel() {
    clickPanel = new JPanel();
    clickPanel.setLayout(new BorderLayout());
    
    ImageIcon ben = new ImageIcon("images/ben.png");
    benButton = new JButton(ben);
    benButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    benButton.setBorderPainted(false);
    benButton.setContentAreaFilled(false);
    benButton.setFocusPainted(false);
    benButton.setOpaque(false);
    benButton.addActionListener(new BenListener());
    clickPanel.add(benButton, BorderLayout.CENTER);
    
    return clickPanel;
}

/*
 * Stats panel to display the level, next level, boin value, boin multiplier, and auto value
 * @return JPanel
 */

public JPanel statsPanel() {
    JPanel statsPanel = new JPanel();
    statsPanel.setLayout(new FlowLayout());

    levelLabel = new JLabel("Level: " + level);
    levelLabel.setFont(getFont().deriveFont(20.0f));
    statsPanel.add(levelLabel);

    int nextLevel = nextLevelValue - totalBoins;
    nextLevelLabel = new JLabel("Until Next Level: " + nextLevel);
    nextLevelLabel.setFont(getFont().deriveFont(20.0f));
    statsPanel.add(nextLevelLabel);

    boinValueLabel = new JLabel("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
    boinValueLabel.setFont(getFont().deriveFont(20.0f));
    statsPanel.add(boinValueLabel);

    boinMultiplierLabel = new JLabel("Boin Multiplier: " + boinMultiplier);
    boinMultiplierLabel.setFont(getFont().deriveFont(20.0f));
    statsPanel.add(boinMultiplierLabel);

    autoValueLabel = new JLabel("Auto Value: " + autoValue);
    autoValueLabel.setFont(getFont().deriveFont(20.0f));
    statsPanel.add(autoValueLabel);

    return statsPanel;
}

/*
 * Total boins panel to display the total amount of boins you have
 * @return JPanel
 */

public JPanel totalBoinsPanel() {
    JPanel totalBoinsPanel = new JPanel();
    totalBoinsPanel.setLayout(new FlowLayout());
    totalBoinsLabel = new JLabel("Total Boins: " + totalBoins);
    totalBoinsLabel.setFont(getFont().deriveFont(40.0f));
    totalBoinsPanel.add(totalBoinsLabel);
    return totalBoinsPanel;
}

/*
 * This is the shop panel where you can buy upgrades, outfits, and more!
 * The shop panel is split into 4 sections: General Shop, Ben's Shop, Cora's Shop, and Aina's Shop
 * Each section has its own buttons to buy upgrades and outfits
 * The general shop has upgrades for boin value, auto value, and boin multiplier
 * Ben's shop has outfits for Ben
 * Cora's shop has outfits for Cora
 * Aina's shop has outfits for Aina
 * Each outfit gives you a different amount of boins per click
 * Each outfit can be equipped and unequipped
 * @return JPanel
 */

    @SuppressWarnings("Convert2Lambda")
    public JPanel shopPanel() {
    JPanel shopPanel = new JPanel();
    shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));

    openGeneralShopButton = new JButton("Open General Shop");
    openGeneralShopButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    openGeneralShopButton.setFont(openGeneralShopButton.getFont().deriveFont(20.0f));
    openGeneralShopButton.setBackground(Color.PINK);
    openGeneralShopButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Welcome to the general shop! Here you can spend your boins on upgrades and more!");
            shopPanel.removeAll();
            shopPanel.add(closeShopButton);
            shopPanel.add(upgradeBoinValueButton);
            shopPanel.add(upgradeAutoValueButton);
            shopPanel.add(upgradeBoinMultiplierButton);
            shopPanel.revalidate();
            shopPanel.repaint();
        }
    });
    shopPanel.add(openGeneralShopButton);

    closeShopButton = new JButton("Close Shop");
    closeShopButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    closeShopButton.setFont(closeShopButton.getFont().deriveFont(20.0f));
    closeShopButton.setBackground(Color.PINK);
    closeShopButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopPanel.removeAll();
            shopPanel.add(openGeneralShopButton);
            shopPanel.add(openBenShopButton);
            shopPanel.add(openCoraShopButton);
            shopPanel.add(openAinaShopButton);
            shopPanel.revalidate();
            shopPanel.repaint();
        }
    });

    openBenShopButton = new JButton("Open Ben's Shop");
    openBenShopButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    openBenShopButton.setFont(openBenShopButton.getFont().deriveFont(20.0f));
    openBenShopButton.setBackground(Color.PINK);
    openBenShopButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Welcome to Ben's shop! Here you can spend your boins on outfits and more!");
            shopPanel.removeAll();
            shopPanel.add(closeShopButton);
            shopPanel.add(benPartyHatButton);
            shopPanel.add(benGlassesButton);
            shopPanel.add(benMouseEarsButton);
            shopPanel.add(equipBenPartyHatButton);
            shopPanel.add(unequipBenPartyHatButton);
            shopPanel.add(equipBenGlassesButton);
            shopPanel.add(unequipBenGlassesButton);
            shopPanel.add(equipBenMouseEarsButton);
            shopPanel.add(unequipBenMouseEarsButton);
            shopPanel.revalidate();
            shopPanel.repaint();
        }
    });

    openCoraShopButton = new JButton("Open Cora's Shop");
    openCoraShopButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    openCoraShopButton.setFont(openCoraShopButton.getFont().deriveFont(20.0f));
    openCoraShopButton.setBackground(Color.PINK);
    openCoraShopButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Welcome to Cora's shop! Here you can spend your boins on outfits and more!");
            shopPanel.removeAll();
            shopPanel.add(closeShopButton);
            shopPanel.add(buyCoraButton);
            shopPanel.add(coraPartyHatButton);
            shopPanel.add(coraGlassesButton);
            shopPanel.add(coraDogEarsButton);
            shopPanel.add(equipCoraPartyHatButton);
            shopPanel.add(unequipCoraPartyHatButton);
            shopPanel.add(equipCoraGlassesButton);
            shopPanel.add(unequipCoraGlassesButton);
            shopPanel.add(equipCoraDogEarsButton);
            shopPanel.add(unequipCoraDogEarsButton);
            shopPanel.revalidate();
            shopPanel.repaint();
        }
    });

    openAinaShopButton = new JButton("Open Aina's Shop");
    openAinaShopButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    openAinaShopButton.setFont(openAinaShopButton.getFont().deriveFont(20.0f));
    openAinaShopButton.setBackground(Color.PINK);
    openAinaShopButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Welcome to Aina's shop! Here you can spend your boins on outfits and more!");
            shopPanel.removeAll();
            shopPanel.add(closeShopButton);
            shopPanel.add(buyAinaButton);
            shopPanel.add(ainaPartyHatButton);
            shopPanel.add(ainaGlassesButton);
            shopPanel.add(ainaCatEarsButton);
            shopPanel.add(equipAinaPartyHatButton);
            shopPanel.add(unequipAinaPartyHatButton);
            shopPanel.add(equipAinaGlassesButton);
            shopPanel.add(unequipAinaGlassesButton);
            shopPanel.add(equipAinaCatEarsButton);
            shopPanel.add(unequipAinaCatEarsButton);
            shopPanel.revalidate();
            shopPanel.repaint();
        }
    });

    upgradeBoinValueButton = new JButton("Upgrade Boin Value: " + upgradeBoinValue + " Boins");
    upgradeBoinValueButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    upgradeBoinValueButton.setFont(upgradeBoinValueButton.getFont().deriveFont(20.0f));
    upgradeBoinValueButton.setBackground(Color.PINK);
    upgradeBoinValueButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= upgradeBoinValue) {
                totalBoins -= upgradeBoinValue;
                boinValue++;
                boinValueLabel.setText("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                upgradeBoinValue = (int) Math.pow(3, boinValue) * 100;
                upgradeBoinValueButton.setText("Upgrade Boin Value: " + upgradeBoinValue + " Boins");
                JOptionPane.showMessageDialog(null, "You've upgraded your boin value! It now gives you " + boinValue + " boins per click!");
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }
        }
    });

    upgradeAutoValueButton = new JButton("Upgrade Auto Value: Buy a auto clicker first!");
    upgradeAutoValueButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    upgradeAutoValueButton.setFont(upgradeAutoValueButton.getFont().deriveFont(20.0f));
    upgradeAutoValueButton.setBackground(Color.PINK);
    upgradeAutoValueButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= upgradeAutoValue) {
                totalBoins -= upgradeAutoValue;
                autoValue++;
                upgradeAutoValue = (int) Math.pow(3, autoValue) * 100;
                upgradeAutoValueButton.setText("Upgrade Auto Value: " + upgradeAutoValue + " Boins");
                autoValueLabel.setText("Auto Value: " + autoValue);
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've upgraded your auto value! It now gives you " + autoValue + " boins per click!");
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }
        }
    });
    upgradeAutoValueButton.setEnabled(false);

    upgradeBoinMultiplierButton = new JButton("Upgrade Boin Multiplier: " + upgradeBoinMultiplier + " Boins");
    upgradeBoinMultiplierButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    upgradeBoinMultiplierButton.setFont(upgradeBoinMultiplierButton.getFont().deriveFont(20.0f));
    upgradeBoinMultiplierButton.setBackground(Color.PINK);
    upgradeBoinMultiplierButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= upgradeBoinMultiplier) {
                totalBoins -= upgradeBoinMultiplier;
                boinMultiplier++;
                upgradeBoinMultiplier = (int) Math.pow(3, boinMultiplier) * 100;
                upgradeBoinMultiplierButton.setText("Upgrade Boin Multiplier: " + upgradeBoinMultiplier + " Boins");
                boinMultiplierLabel.setText("Boin Multiplier: " + boinMultiplier);
                boinValueLabel.setText("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've upgraded your boin multiplier! It now gives you " + boinMultiplier + " times the boins per click!");
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }
        }
    });

    benPartyHatButton = new JButton("Buy Ben's Party Hat? 2000 Boins");
    benPartyHatButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    benPartyHatButton.setFont(getFont().deriveFont(20.0f));
    benPartyHatButton.setBackground(Color.PINK);
    benPartyHatButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= 2000) {
                totalBoins -= 2000;
                boinValue += 2;
                boinValueLabel.setText("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
                benButton.setIcon(new ImageIcon("images/ben_partyHat.png"));
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've bought Ben's party hat! It now gives you " + boinValue + " boins per click!");
                unequipBenPartyHatButton.setVisible(true);
                benPartyHatButton.setEnabled(false);
                shopPanel.revalidate();
                shopPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }
        }
    });

    benGlassesButton = new JButton("Buy Ben's Glasses? 5000 Boins");
    benGlassesButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    benGlassesButton.setFont(getFont().deriveFont(20.0f));
    benGlassesButton.setBackground(Color.PINK);
    benGlassesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= 5000) {
                totalBoins -= 5000;
                boinValue += 5;
                boinValueLabel.setText("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
                benButton.setIcon(new ImageIcon("images/ben_glasses.png"));
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've bought Ben's glasses! It now gives you " + boinValue + " boins per click!");
                unequipBenGlassesButton.setVisible(true);
                benGlassesButton.setEnabled(false);
                shopPanel.revalidate();
                shopPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }
        }
    });

    benMouseEarsButton = new JButton("Buy Ben's Mouse Ears? 10000 Boins");
    benMouseEarsButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    benMouseEarsButton.setFont(getFont().deriveFont(20.0f));
    benMouseEarsButton.setBackground(Color.PINK);
    benMouseEarsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= 10000) {
                totalBoins -= 10000;
                boinValue += 10;
                boinValueLabel.setText("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
                benButton.setIcon(new ImageIcon("images/ben_mouseEars.png"));
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've bought Ben's mouse ears! It now gives you " + boinValue + " boins per click!");
                unequipBenMouseEarsButton.setVisible(true);
                benMouseEarsButton.setEnabled(false);
                shopPanel.revalidate();
                shopPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }
        }
    });

    buyCoraButton = new JButton("Buy Cora? 12000 Boins");
    buyCoraButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    buyCoraButton.setFont(getFont().deriveFont(20.0f));
    buyCoraButton.setBackground(Color.PINK);
    buyCoraButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= 12000) {
                totalBoins -= 12000;
                autoValue += 3;
                autoValueLabel.setText("Auto Value: " + autoValue);
                upgradeAutoValueButton.setEnabled(true);
                upgradeAutoValueButton.setText("Upgrade Auto Value: " + upgradeAutoValue + " Boins");
                // benButton.setIcon(new ImageIcon("images/cora.png"));
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've bought Cora! She now gives you " + autoValue + " boins per click!");
                
                coraButton = new JButton(new ImageIcon("images/cora.png"));
                coraButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // animations
                    }
                });
                coraButton.setHorizontalAlignment(JButton.CENTER);
                coraButton.setBorderPainted(false);
                coraButton.setContentAreaFilled(false);
                coraButton.setFocusPainted(false);
                coraButton.setOpaque(false);
                clickPanel.add(coraButton, BorderLayout.WEST);
                clickPanel.revalidate();
                clickPanel.repaint();
                
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }

            if(!autoClickerRunning) {
                startAutoClicker();
            }
        }
    });

    coraPartyHatButton = new JButton("Buy Cora's Party Hat? 3000 Boins");
    coraPartyHatButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    coraPartyHatButton.setFont(getFont().deriveFont(20.0f));
    coraPartyHatButton.setBackground(Color.PINK);
    coraPartyHatButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= 3000) {
                totalBoins -= 3000;
                autoValue += 3;
                autoValueLabel.setText("Auto Value: " + autoValue);
                coraButton.setIcon(new ImageIcon("images/cora_partyHat.png"));
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've bought Cora's party hat! She now gives you " + autoValue + " boins per click!");
                unequipCoraPartyHatButton.setVisible(true);
                coraPartyHatButton.setEnabled(false);
                shopPanel.revalidate();
                shopPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }
        }
    });

    coraGlassesButton = new JButton("Buy Cora's Glasses? 5000 Boins");
    coraGlassesButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    coraGlassesButton.setFont(getFont().deriveFont(20.0f));
    coraGlassesButton.setBackground(Color.PINK);
    coraGlassesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= 5000) {
                totalBoins -= 5000;
                autoValue += 5;
                autoValueLabel.setText("Auto Value: " + autoValue);
                coraButton.setIcon(new ImageIcon("images/cora_glasses.png"));
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've bought Cora's glasses! She now gives you " + autoValue + " boins per click!");
                unequipCoraGlassesButton.setVisible(true);
                coraGlassesButton.setEnabled(false);
                shopPanel.revalidate();
                shopPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }
        }
    });

    coraDogEarsButton = new JButton("Buy Cora's Dog Ears? 8000 Boins");
    coraDogEarsButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    coraDogEarsButton.setFont(getFont().deriveFont(20.0f));
    coraDogEarsButton.setBackground(Color.PINK);
    coraDogEarsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= 8000) {
                totalBoins -= 8000;
                autoValue += 8;
                autoValueLabel.setText("Auto Value: " + autoValue);
                coraButton.setIcon(new ImageIcon("images/cora_dogEars.png"));
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've bought Cora's dog ears! She now gives you " + autoValue + " boins per click!");
                unequipCoraDogEarsButton.setVisible(true);
                coraDogEarsButton.setEnabled(false);
                shopPanel.revalidate();
                shopPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }
        }
    });

    buyAinaButton = new JButton("Buy Aina? 2000 Boins");
    buyAinaButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    buyAinaButton.setFont(getFont().deriveFont(20.0f));
    buyAinaButton.setBackground(Color.PINK);
    buyAinaButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= 2000) {
                totalBoins -= 2000;
                autoValue += 1;
                upgradeAutoValueButton.setEnabled(true);
                upgradeAutoValueButton.setText("Upgrade Auto Value: " + upgradeAutoValue + " Boins");
                autoValueLabel.setText("Auto Value: " + autoValue);
                // benButton.setIcon(new ImageIcon("images/aina.png"));
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've bought Aina! She now gives you " + autoValue + " boins per click!");
                
                ainaButton = new JButton(new ImageIcon("images/aina.png"));
                ainaButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // animations
                    }
                });
                ainaButton.setHorizontalAlignment(JButton.CENTER);
                ainaButton.setBorderPainted(false);
                ainaButton.setContentAreaFilled(false);
                ainaButton.setFocusPainted(false);
                ainaButton.setOpaque(false);
                clickPanel.add(ainaButton, BorderLayout.EAST);
                clickPanel.revalidate();
                clickPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }

            if(!autoClickerRunning) {
                startAutoClicker();
            }
        }
    });

    ainaPartyHatButton = new JButton("Buy Aina's Party Hat? 4000 Boins");
    ainaPartyHatButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    ainaPartyHatButton.setFont(getFont().deriveFont(20.0f));
    ainaPartyHatButton.setBackground(Color.PINK);
    ainaPartyHatButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= 4000) {
                totalBoins -= 4000;
                autoValue += 4;
                autoValueLabel.setText("Auto Value: " + autoValue);
                ainaButton.setIcon(new ImageIcon("images/aina_partyHat.png"));
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've bought Aina's party hat! She now gives you " + autoValue + " boins per click!");
                unequipAinaPartyHatButton.setVisible(true);
                ainaPartyHatButton.setEnabled(false);
                shopPanel.revalidate();
                shopPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }
        }
    });

    ainaGlassesButton = new JButton("Buy Aina's Glasses? 8000 Boins");
    ainaGlassesButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    ainaGlassesButton.setFont(getFont().deriveFont(20.0f));
    ainaGlassesButton.setBackground(Color.PINK);
    ainaGlassesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= 8000) {
                totalBoins -= 8000;
                autoValue += 8;
                autoValueLabel.setText("Auto Value: " + autoValue);
                ainaButton.setIcon(new ImageIcon("images/aina_glasses.png"));
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've bought Aina's glasses! She now gives you " + autoValue + " boins per click!");
                unequipAinaGlassesButton.setVisible(true);
                ainaGlassesButton.setEnabled(false);
                shopPanel.revalidate();
                shopPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }
        }
    });

    ainaCatEarsButton = new JButton("Buy Aina's Cat Ears? 10000 Boins");
    ainaCatEarsButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    ainaCatEarsButton.setFont(getFont().deriveFont(20.0f));
    ainaCatEarsButton.setBackground(Color.PINK);
    ainaCatEarsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (totalBoins >= 10000) {
                totalBoins -= 10000;
                autoValue += 10;
                autoValueLabel.setText("Auto Value: " + autoValue);
                ainaButton.setIcon(new ImageIcon("images/aina_catEars.png"));
                totalBoinsLabel.setText("Total Boins: " + totalBoins);
                JOptionPane.showMessageDialog(null, "You've bought Aina's cat ears! She now gives you " + autoValue + " boins per click!");
                unequipAinaCatEarsButton.setVisible(true);
                ainaCatEarsButton.setEnabled(false);
                shopPanel.revalidate();
                shopPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough boins!");
            }
        }
    });

    equipBenPartyHatButton = new JButton("Equip Ben's Party Hat");
    equipBenPartyHatButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    equipBenPartyHatButton.setFont(getFont().deriveFont(20.0f));
    equipBenPartyHatButton.setBackground(Color.PINK);
    equipBenPartyHatButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            equipBenPartyHatButton.setVisible(false);
            benButton.setIcon(new ImageIcon("images/ben_partyHat.png"));
            JOptionPane.showMessageDialog(null, "You've equipped Ben's party hat!");
            boinValue += 2;
            unequipBenPartyHatButton.setVisible(true);
            boinValueLabel.setText("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
            shopPanel.revalidate();
            shopPanel.repaint();
        }
    });
    equipBenPartyHatButton.setVisible(false);

    unequipBenPartyHatButton = new JButton("Unequip Ben's Party Hat");
    unequipBenPartyHatButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    unequipBenPartyHatButton.setFont(getFont().deriveFont(20.0f));
    unequipBenPartyHatButton.setBackground(Color.PINK);
    unequipBenPartyHatButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            unequipBenPartyHatButton.setVisible(false);
            boinValue -= 2;
            boinValueLabel.setText("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
            benButton.setIcon(new ImageIcon("images/ben.png"));
            JOptionPane.showMessageDialog(null, "You've unequipped Ben's party hat!");
            equipBenPartyHatButton.setVisible(true);
            shopPanel.revalidate();
            shopPanel.repaint();
        }
    });
    unequipBenPartyHatButton.setVisible(false);

    equipBenGlassesButton = new JButton("Equip Ben's Glasses");
    equipBenGlassesButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    equipBenGlassesButton.setFont(getFont().deriveFont(20.0f));
    equipBenGlassesButton.setBackground(Color.PINK);
    equipBenGlassesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            equipBenGlassesButton.setVisible(false);
            benButton.setIcon(new ImageIcon("images/ben_glasses.png"));
            JOptionPane.showMessageDialog(null, "You've equipped Ben's glasses!");
            boinValue += 5;
            unequipBenGlassesButton.setVisible(true);
            boinValueLabel.setText("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
            revalidate();
            repaint();
        }
    });
    equipBenGlassesButton.setVisible(false);

    unequipBenGlassesButton = new JButton("Unequip Ben's Glasses");
    unequipBenGlassesButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    unequipBenGlassesButton.setFont(getFont().deriveFont(20.0f));
    unequipBenGlassesButton.setBackground(Color.PINK);
    unequipBenGlassesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            unequipBenGlassesButton.setVisible(false);
            benButton.setIcon(new ImageIcon("images/ben.png"));
            JOptionPane.showMessageDialog(null, "You've unequipped Ben's glasses!");
            boinValue -= 5;
            boinValueLabel.setText("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
            equipBenGlassesButton.setVisible(true);
            revalidate();
            repaint();
        }
    });
    unequipBenGlassesButton.setVisible(false);

    equipBenMouseEarsButton = new JButton("Equip Ben's Mouse Ears");
    equipBenMouseEarsButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    equipBenMouseEarsButton.setFont(getFont().deriveFont(20.0f));
    equipBenMouseEarsButton.setBackground(Color.PINK);
    equipBenMouseEarsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            equipBenMouseEarsButton.setVisible(false);
            benButton.setIcon(new ImageIcon("images/ben_mouseEars.png"));
            JOptionPane.showMessageDialog(null, "You've equipped Ben's mouse ears!");
            boinValue += 10;
            unequipBenMouseEarsButton.setVisible(true);
            boinValueLabel.setText("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
            revalidate();
            repaint();
        }
    });
    equipBenMouseEarsButton.setVisible(false);

    unequipBenMouseEarsButton = new JButton("Unequip Ben's Mouse Ears");
    unequipBenMouseEarsButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    unequipBenMouseEarsButton.setFont(getFont().deriveFont(20.0f));
    unequipBenMouseEarsButton.setBackground(Color.PINK);
    unequipBenMouseEarsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            unequipBenMouseEarsButton.setVisible(false);
            benButton.setIcon(new ImageIcon("images/ben.png"));
            JOptionPane.showMessageDialog(null, "You've unequipped Ben's mouse ears!");
            boinValue -= 10;
            boinValueLabel.setText("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
            equipBenMouseEarsButton.setVisible(true);
            revalidate();
            repaint();
        }
    });
    unequipBenMouseEarsButton.setVisible(false);

    equipCoraPartyHatButton = new JButton("Equip Cora's Party Hat");
    equipCoraPartyHatButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    equipCoraPartyHatButton.setFont(getFont().deriveFont(20.0f));
    equipCoraPartyHatButton.setBackground(Color.PINK);
    equipCoraPartyHatButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            equipCoraPartyHatButton.setVisible(false);
            coraButton.setIcon(new ImageIcon("images/cora_partyHat.png"));
            JOptionPane.showMessageDialog(null, "You've equipped Cora's party hat!");
            autoValue += 3;
            unequipCoraPartyHatButton.setVisible(true);
            autoValueLabel.setText("Auto Value: " + autoValue);
            revalidate();
            repaint();
        }
    });
    equipCoraPartyHatButton.setVisible(false);
    unequipCoraPartyHatButton = new JButton("Unequip Cora's Party Hat");
    unequipCoraPartyHatButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    unequipCoraPartyHatButton.setFont(getFont().deriveFont(20.0f));
    unequipCoraPartyHatButton.setBackground(Color.PINK);
    unequipCoraPartyHatButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            unequipCoraPartyHatButton.setVisible(false);
            coraButton.setIcon(new ImageIcon("images/cora.png"));
            JOptionPane.showMessageDialog(null, "You've unequipped Cora's party hat!");
            autoValue -= 3;
            autoValueLabel.setText("Auto Value: " + autoValue);
            equipCoraPartyHatButton.setVisible(true);
            revalidate();
            repaint();
        }
    });
    unequipCoraPartyHatButton.setVisible(false);

    equipCoraGlassesButton = new JButton("Equip Cora's Glasses");
    equipCoraGlassesButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    equipCoraGlassesButton.setFont(getFont().deriveFont(20.0f));
    equipCoraGlassesButton.setBackground(Color.PINK);
    equipCoraGlassesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            equipCoraGlassesButton.setVisible(false);
            coraButton.setIcon(new ImageIcon("images/cora_glasses.png"));
            JOptionPane.showMessageDialog(null, "You've equipped Cora's glasses!");
            autoValue += 5;
            unequipCoraGlassesButton.setVisible(true);
            autoValueLabel.setText("Auto Value: " + autoValue);
            revalidate();
            repaint();
        }
    });
    equipCoraGlassesButton.setVisible(false);

    unequipCoraGlassesButton = new JButton("Unequip Cora's Glasses");
    unequipCoraGlassesButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    unequipCoraGlassesButton.setFont(getFont().deriveFont(20.0f));
    unequipCoraGlassesButton.setBackground(Color.PINK);
    unequipCoraGlassesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            unequipCoraGlassesButton.setVisible(false);
            coraButton.setIcon(new ImageIcon("images/cora.png"));
            JOptionPane.showMessageDialog(null, "You've unequipped Cora's glasses!");
            autoValue -= 5;
            autoValueLabel.setText("Auto Value: " + autoValue);
            equipCoraGlassesButton.setVisible(true);
            revalidate();
            repaint();
        }
    });
    unequipCoraGlassesButton.setVisible(false);

    equipCoraDogEarsButton = new JButton("Equip Cora's Dog Ears");
    equipCoraDogEarsButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    equipCoraDogEarsButton.setFont(getFont().deriveFont(20.0f));
    equipCoraDogEarsButton.setBackground(Color.PINK);
    equipCoraDogEarsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            coraDogEarsButton.setVisible(false);
            coraButton.setIcon(new ImageIcon("images/cora_dogEars.png"));
            JOptionPane.showMessageDialog(null, "You've equipped Cora's dog ears!");
            autoValue += 8;
            unequipCoraDogEarsButton.setVisible(true);
            autoValueLabel.setText("Auto Value: " + autoValue);
            revalidate();
            repaint();
        }
    });
    equipCoraDogEarsButton.setVisible(false);

    unequipCoraDogEarsButton = new JButton("Unequip Cora's Dog Ears");
    unequipCoraDogEarsButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    unequipCoraDogEarsButton.setFont(getFont().deriveFont(20.0f));
    unequipCoraDogEarsButton.setBackground(Color.PINK);
    unequipCoraDogEarsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            unequipCoraDogEarsButton.setVisible(false);
            coraButton.setIcon(new ImageIcon("images/cora.png"));
            JOptionPane.showMessageDialog(null, "You've unequipped Cora's dog ears!");
            autoValue -= 8;
            autoValueLabel.setText("Auto Value: " + autoValue);
            equipCoraDogEarsButton.setVisible(true);
            revalidate();
            repaint();
        }
    });
    unequipCoraDogEarsButton.setVisible(false);

    equipAinaPartyHatButton = new JButton("Equip Aina's Party Hat");
    equipAinaPartyHatButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    equipAinaPartyHatButton.setFont(getFont().deriveFont(20.0f));
    equipAinaPartyHatButton.setBackground(Color.PINK);
    equipAinaPartyHatButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            equipAinaPartyHatButton.setVisible(false);
            ainaButton.setIcon(new ImageIcon("images/aina_partyHat.png"));
            JOptionPane.showMessageDialog(null, "You've equipped Aina's party hat!");
            autoValue += 4;
            unequipAinaPartyHatButton.setVisible(true);
            autoValueLabel.setText("Auto Value: " + autoValue);
            revalidate();
            repaint();
        }
    });
    equipAinaPartyHatButton.setVisible(false);

    unequipAinaPartyHatButton = new JButton("Unequip Aina's Party Hat");
    unequipAinaPartyHatButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    unequipAinaPartyHatButton.setFont(getFont().deriveFont(20.0f));
    unequipAinaPartyHatButton.setBackground(Color.PINK);
    unequipAinaPartyHatButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            unequipAinaPartyHatButton.setVisible(false);
            ainaButton.setIcon(new ImageIcon("images/aina.png"));
            JOptionPane.showMessageDialog(null, "You've unequipped Aina's party hat!");
            autoValue -= 4;
            autoValueLabel.setText("Auto Value: " + autoValue);
            equipAinaPartyHatButton.setVisible(true);
            revalidate();
            repaint();
        }
    });
    unequipAinaPartyHatButton.setVisible(false);

    equipAinaGlassesButton = new JButton("Equip Aina's Glasses");
    equipAinaGlassesButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    equipAinaGlassesButton.setFont(getFont().deriveFont(20.0f));
    equipAinaGlassesButton.setBackground(Color.PINK);
    equipAinaGlassesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            equipAinaGlassesButton.setVisible(false);
            ainaButton.setIcon(new ImageIcon("images/aina_glasses.png"));
            JOptionPane.showMessageDialog(null, "You've equipped Aina's glasses!");
            autoValue += 8;
            unequipAinaGlassesButton.setVisible(true);
            autoValueLabel.setText("Auto Value: " + autoValue);
            revalidate();
            repaint();
        }
    });
    equipAinaGlassesButton.setVisible(false);

    unequipAinaGlassesButton = new JButton("Unequip Aina's Glasses");
    unequipAinaGlassesButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    unequipAinaGlassesButton.setFont(getFont().deriveFont(20.0f));
    unequipAinaGlassesButton.setBackground(Color.PINK);
    unequipAinaGlassesButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            unequipAinaGlassesButton.setVisible(false);
            ainaButton.setIcon(new ImageIcon("images/aina.png"));
            JOptionPane.showMessageDialog(null, "You've unequipped Aina's glasses!");
            autoValue -= 8;
            autoValueLabel.setText("Auto Value: " + autoValue);
            equipAinaGlassesButton.setVisible(true);
            revalidate();
            repaint();
        }
    });
    unequipAinaGlassesButton.setVisible(false);

    equipAinaCatEarsButton = new JButton("Equip Aina's Cat Ears");
    equipAinaCatEarsButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    equipAinaCatEarsButton.setFont(getFont().deriveFont(20.0f));
    equipAinaCatEarsButton.setBackground(Color.PINK);
    equipAinaCatEarsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            equipAinaCatEarsButton.setVisible(false);
            ainaButton.setIcon(new ImageIcon("images/aina_cat_ears.png"));
            JOptionPane.showMessageDialog(null, "You've equipped Aina's cat ears!");
            autoValue += 10;
            unequipAinaCatEarsButton.setVisible(true);
            autoValueLabel.setText("Auto Value: " + autoValue);
            revalidate();
            repaint();
        }
    });
    equipAinaCatEarsButton.setVisible(false);

    unequipAinaCatEarsButton = new JButton("Unequip Aina's Cat Ears");
    unequipAinaCatEarsButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
    unequipAinaCatEarsButton.setFont(getFont().deriveFont(20.0f));
    unequipAinaCatEarsButton.setBackground(Color.PINK);
    unequipAinaCatEarsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            unequipAinaCatEarsButton.setVisible(false);
            ainaButton.setIcon(new ImageIcon("images/aina.png"));
            JOptionPane.showMessageDialog(null, "You've unequipped Aina's cat ears!");
            autoValue -= 10;
            autoValueLabel.setText("Auto Value: " + autoValue);
            equipAinaCatEarsButton.setVisible(true);
            revalidate();
            repaint();
        }
    });
    unequipAinaCatEarsButton.setVisible(false);

    shopPanel.add(openGeneralShopButton);
    shopPanel.add(openBenShopButton);
    shopPanel.add(openCoraShopButton);
    shopPanel.add(openAinaShopButton);

    return shopPanel;
}

/*
 * This method sets the total boins.
 * @param totalBoins the total boins
 */

public void setTotalBoins(int totalBoins) {
    this.totalBoins = totalBoins;
    totalBoinsLabel.setText("Total Boins: " + totalBoins);
}

/*
 * This method sets the boin value.
 * @param boinValue the boin value
 */

public void setBoinValue(int boinValue) {
    this.boinValue = boinValue;
    boinValueLabel.setText("Boin Value: " + boinValue);
}

/*
 * This method sets the boin multiplier.
 * @param boinMultiplier the boin multiplier
 */

public void setBoinMultiplier(int boinMultiplier) {
    this.boinMultiplier = boinMultiplier;
    boinMultiplierLabel.setText("Boin Multiplier: " + boinMultiplier);
}

/*
 * This method sets the auto value.
 * @param autoValue the auto value
 */

public void setAutoValue(int autoValue) {
    this.autoValue = autoValue;
    autoValueLabel.setText("Auto Value: " + autoValue);
}

/*
 * This method starts the auto clicker.
 */

    @SuppressWarnings("Convert2Lambda")
    public void startAutoClicker() {
    autoClickerTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setTotalBoins(totalBoins + autoValue);
            untilNextLevel();
            if(totalBoins >= nextLevelValue) {
                levelUp();
            }
            checkLevel();
        }
    });
    autoClickerTimer.start();
    autoClickerRunning = true;
}

/*
 * This method has the user level up.
 */

public void levelUp() {
    level++;
    levelLabel.setText("Level: " + level);
    untilNextLevel();
}

/*
 * This method calculates the boins until the next level.
 */

public void untilNextLevel() {
    nextLevelValue = (int) Math.pow(3, level) * 1000;
    int nextLevel = nextLevelValue - totalBoins;
    nextLevelLabel.setText("Until Next Level: " + nextLevel);
}

/*
 * This method checks the level.
 */

public void checkLevel() {
    if (level % 5 == 0 && !multiplierApplied) { 
        JOptionPane.showMessageDialog(null, "You've reached a milestone! Your boin multiplier has increased by 1!");
        boinMultiplier++;
        boinMultiplierLabel.setText("Boin Multiplier: " + boinMultiplier);
        boinValueLabel.setText("Boin Value: " + boinValue + "(" + boinValue * boinMultiplier + ")");
        multiplierApplied = true;
    }
    if (level % 10 == 0 && !multiplierApplied) {
        JOptionPane.showMessageDialog(null, "You've reached a milestone! Your auto value has increased by 1!");
        autoValue++;
        autoValueLabel.setText("Auto Value: " + autoValue);
        multiplierApplied = true;
    }
}

/*
 * This method is the Ben listener and it has the code for the clicker button.
 * @param e the action event
 */

private class BenListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        setTotalBoins(totalBoins + boinValue * boinMultiplier);
        untilNextLevel();
        if(totalBoins >= nextLevelValue) {
            levelUp();
        }
        checkLevel();
    }

}
}