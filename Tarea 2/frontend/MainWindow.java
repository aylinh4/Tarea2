package frontend;

import backend.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MainWindow extends JFrame {
    private final TestManager testManager = new TestManager();
    private final ItemPanel itemPanel = new ItemPanel();
    private final SummaryPanel summaryPanel = new SummaryPanel();
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private int currentIndex = 0;

    public MainWindow() {
        setTitle("Sistema de Pruebas - Taxonomía de Bloom");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JButton loadButton = new JButton("Cargar archivo de prueba");
        loadButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    testManager.loadItems(file);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al cargar el archivo: " + ex.getMessage());
                }
            }
        });

        testManager.onItemsLoaded.subscribe(items -> {
            currentIndex = 0;
            updateItem(items);
        });

        add(loadButton, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void updateItem(List<Item> items) {
        Item item = items.get(currentIndex);
        int saved = testManager.getUserAnswers().getOrDefault(currentIndex, -1);
        itemPanel.loadItem(item, saved);

        JButton backButton = new JButton("Volver atrás");
        JButton nextButton = new JButton(currentIndex == items.size() - 1 ? "Enviar respuestas" : "Siguiente");

        backButton.setEnabled(currentIndex > 0);
        backButton.addActionListener(e -> {
            saveCurrentAnswer();
            currentIndex--;
            updateItem(items);
        });

        nextButton.addActionListener(e -> {
            saveCurrentAnswer();
            if (currentIndex == items.size() - 1) {
                summaryPanel.setSummary(
                        testManager.getStatsByType(),
                        testManager.getStatsByLevel()
                );
                showSummary();
            } else {
                currentIndex++;
                updateItem(items);
            }
        });

        JPanel navPanel = new JPanel();
        navPanel.add(backButton);
        navPanel.add(nextButton);

        mainPanel.removeAll();
        mainPanel.add(itemPanel, BorderLayout.CENTER);
        mainPanel.add(navPanel, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void saveCurrentAnswer() {
        int answer = itemPanel.getSelectedIndex();
        if (answer >= 0) {
            testManager.answer(currentIndex, answer);
        }
    }

    private void showSummary() {
        JButton reviewButton = new JButton("Revisar respuestas");
        reviewButton.addActionListener(e -> {
            currentIndex = 0;
            updateReview(testManager.getItems());
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(reviewButton);

        mainPanel.removeAll();
        mainPanel.add(summaryPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void updateReview(List<Item> items) {
        Item item = items.get(currentIndex);
        int selected = testManager.getUserAnswers().getOrDefault(currentIndex, -1);
        itemPanel.loadItem(item, selected);

        JButton backButton = new JButton("Volver atrás");
        JButton nextButton = new JButton("Siguiente");
        JButton resumeButton = new JButton("Resumen");

        backButton.setEnabled(currentIndex > 0);
        backButton.addActionListener(e -> {
            currentIndex--;
            updateReview(items);
        });

        nextButton.setEnabled(currentIndex < items.size() - 1);
        nextButton.addActionListener(e -> {
            currentIndex++;
            updateReview(items);
        });

        resumeButton.addActionListener(e -> showSummary());

        JPanel navPanel = new JPanel();
        navPanel.add(backButton);
        navPanel.add(nextButton);
        navPanel.add(resumeButton);

        mainPanel.removeAll();
        mainPanel.add(itemPanel, BorderLayout.CENTER);
        mainPanel.add(navPanel, BorderLayout.SOUTH);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}