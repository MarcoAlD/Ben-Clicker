
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClickerGamePanel2 extends JPanel {

    private int totalClicks = 0, clickValue = 1, clickMultiplier = 1, autoValue = 0;
    private JButton clickButton, upgradeAutoButton, upgradeClickButton, upgradeMultiplierButton, ainaButton, coraButton;
    private JLabel upgradeLabel, autoLabel, clickLabel;

    public ClickerGamePanel2() {
        this.setLayout(new BorderLayout(100, 0));

        this.add(clickerPanel(), BorderLayout.CENTER);
        this.add(statsPanel(), BorderLayout.WEST);
        this.add(shopPanel(), BorderLayout.EAST);
    }

    private JPanel clickerPanel() {
        JPanel clickerPanel = new JPanel();
        clickerPanel.setLayout(new BorderLayout());

        clickButton = new JButton("Click Me!");
        clickerPanel.add(clickButton, BorderLayout.CENTER);

        ainaButton = new JButton("Aina");
        clickerPanel.add(ainaButton, BorderLayout.EAST);

        coraButton = new JButton("Cora");
        clickerPanel.add(coraButton, BorderLayout.WEST);

        return clickerPanel;
    }

    private JPanel statsPanel() {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));

        clickLabel = new JLabel("Total Clicks: " + totalClicks);
        statsPanel.add(clickLabel);
        upgradeLabel = new JLabel("Click Value: " + clickValue);
        statsPanel.add(upgradeLabel);
        autoLabel = new JLabel("Auto Clicker: " + autoValue);
        statsPanel.add(autoLabel);

        return statsPanel;
    }

    private JPanel shopPanel() {
        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(new BoxLayout(shopPanel, BoxLayout.Y_AXIS));

        upgradeClickButton = new JButton("Upgrade Click Value: " + clickValue);
        shopPanel.add(upgradeClickButton);

        upgradeMultiplierButton = new JButton("Upgrade Click Multiplier: " + clickMultiplier);
        shopPanel.add(upgradeMultiplierButton);

        upgradeAutoButton = new JButton("Upgrade Auto Clicker: " + autoValue);
        shopPanel.add(upgradeAutoButton);

        return shopPanel;
    }
}
