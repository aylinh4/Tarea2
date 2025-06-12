package frontend;

import backend.Item;
import backend.ItemType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ItemPanel extends JPanel {
    private final JLabel questionLabel = new JLabel();
    private final ButtonGroup group = new ButtonGroup();
    private final JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
    private final JLabel resultLabel = new JLabel();
    private int selectedIndex = -1;

    public ItemPanel() {
        setLayout(new BorderLayout());
        add(questionLabel, BorderLayout.NORTH);
        add(optionsPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
        resultLabel.setForeground(Color.BLUE);
    }

    public void loadItem(Item item, int selected, boolean isReviewMode) {
        questionLabel.setText("<html>" + item.getQuestion() + "</html>");
        optionsPanel.removeAll();
        group.clearSelection();
        resultLabel.setText("");

        List<String> options = item.getOptions();
        if (item.getType() == ItemType.TRUE_FALSE) {
            options = List.of("Verdadero", "Falso");
        }

        for (int i = 0; i < options.size(); i++) {
            JRadioButton radio = new JRadioButton(options.get(i));
            radio.setEnabled(!isReviewMode);
            int finalI = i;
            radio.addActionListener(e -> selectedIndex = finalI);
            group.add(radio);
            optionsPanel.add(radio);
            if (i == selected) {
                radio.setSelected(true);
                selectedIndex = i;
            }
            if (isReviewMode) {
                if (i == item.getCorrectIndex()) {
                    radio.setForeground(Color.GREEN);
                } else if (i == selected && selected != item.getCorrectIndex()) {
                    radio.setForeground(Color.RED);
                }
            }
        }

        if (isReviewMode) {
            String result = selected == item.getCorrectIndex() ? "Correcta" : "Incorrecta";
            resultLabel.setText("Respuesta: " + result);
        }

        revalidate();
        repaint();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}