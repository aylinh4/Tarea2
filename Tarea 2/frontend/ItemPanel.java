package frontend;

import backend.Item;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ItemPanel extends JPanel {
    private final JLabel questionLabel = new JLabel();
    private final ButtonGroup group = new ButtonGroup();
    private final JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
    private int selectedIndex = -1;

    public ItemPanel() {
        setLayout(new BorderLayout());
        add(questionLabel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
    }

    public void loadItem(Item item, int selected) {
        questionLabel.setText("<html><h3>" + item.getQuestion() + "</h3></html>");
        optionsPanel.removeAll();
        group.clearSelection();

        List<String> options = item.getOptions();
        for (int i = 0; i < options.size(); i++) {
            JRadioButton radio = new JRadioButton(options.get(i));
            int finalI = i;
            radio.addActionListener(e -> selectedIndex = finalI);
            group.add(radio);
            optionsPanel.add(radio);
            if (i == selected) {
                radio.setSelected(true);
                selectedIndex = i;
            }
        }
        revalidate();
        repaint();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}