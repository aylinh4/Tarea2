package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class SummaryPanel extends JPanel {
    private final JTextArea summaryText = new JTextArea();

    public SummaryPanel() {
        setLayout(new BorderLayout());
        summaryText.setEditable(false);
        add(new JScrollPane(summaryText), BorderLayout.CENTER);
    }

    public void setSummary(Map<String, Double> byType, Map<String, Double> byLevel) {
        StringBuilder sb = new StringBuilder();
        sb.append("Estadísticas por tipo de ítem:\n");
        byType.forEach((k, v) -> sb.append(k).append(": ").append(String.format("%.2f%%", v)).append("\n"));

        sb.append("\nEstadísticas por nivel de Bloom:\n");
        byLevel.forEach((k, v) -> sb.append(k).append(": ").append(String.format("%.2f%%", v)).append("\n"));

        summaryText.setText(sb.toString());
    }
}