package backend;

import java.util.*;
import java.io.*;

public class TestManager {
    private final List<Item> items = new ArrayList<>();
    private final Map<Integer, Integer> userAnswers = new HashMap<>();
    public final EventManager<List<Item>> onItemsLoaded = new EventManager<>();

    public void loadItems(File file) throws IOException {
        items.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] parts = line.split("\\|");
                if (parts.length != 6) {
                    throw new IOException("Formato incorrecto en la línea " + lineNumber + ": se esperaban 6 partes");
                }
                String question = parts[0].trim();
                if (question.isEmpty()) {
                    throw new IOException("Pregunta vacía en la línea " + lineNumber);
                }
                List<String> options = Arrays.asList(parts[1].split(";"));
                if (options.isEmpty()) {
                    throw new IOException("Opciones vacías en la línea " + lineNumber);
                }
                int correct;
                try {
                    correct = Integer.parseInt(parts[2].trim());
                    if (correct < 0 || correct >= options.size()) {
                        throw new IOException("Índice correcto inválido en la línea " + lineNumber);
                    }
                } catch (NumberFormatException e) {
                    throw new IOException("Índice correcto no es un número en la línea " + lineNumber);
                }
                ItemType type;
                try {
                    type = ItemType.valueOf(parts[3].trim());
                    if (type == ItemType.ESSAY) {
                        throw new IOException("Tipo ESSAY no soportado en la línea " + lineNumber);
                    }
                    if (type == ItemType.TRUE_FALSE && options.size() != 2) {
                        throw new IOException("Pregunta TRUE_FALSE debe tener exactamente 2 opciones en la línea " + lineNumber);
                    }
                } catch (IllegalArgumentException e) {
                    throw new IOException("Tipo de ítem inválido en la línea " + lineNumber);
                }
                BloomLevel level;
                try {
                    level = BloomLevel.valueOf(parts[4].trim());
                } catch (IllegalArgumentException e) {
                    throw new IOException("Nivel de Bloom inválido en la línea " + lineNumber);
                }
                int estimatedTime;
                try {
                    estimatedTime = Integer.parseInt(parts[5].trim());
                    if (estimatedTime < 0) {
                        throw new IOException("Tiempo estimado negativo en la línea " + lineNumber);
                    }
                } catch (NumberFormatException e) {
                    throw new IOException("Tiempo estimado no es un número en la línea " + lineNumber);
                }
                items.add(new Item(question, options, correct, type, level, estimatedTime));
            }
            if (items.isEmpty()) {
                throw new IOException("El archivo no contiene ítems válidos");
            }
        }
        onItemsLoaded.notifyAll(items);
    }

    public List<Item> getItems() { return items; }

    public int getTotalEstimatedTime() {
        return items.stream().mapToInt(Item::getEstimatedTime).sum();
    }

    public void answer(int index, int answerIndex) {
        userAnswers.put(index, answerIndex);
    }

    public boolean isCorrect(int index) {
        return userAnswers.getOrDefault(index, -1) == items.get(index).getCorrectIndex();
    }

    public Map<String, Double> getStatsByType() {
        Map<ItemType, Integer> total = new HashMap<>();
        Map<ItemType, Integer> correct = new HashMap<>();

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            total.put(item.getType(), total.getOrDefault(item.getType(), 0) + 1);
            if (isCorrect(i)) {
                correct.put(item.getType(), correct.getOrDefault(item.getType(), 0) + 1);
            }
        }

        Map<String, Double> stats = new LinkedHashMap<>();
        for (ItemType type : total.keySet()) {
            double percentage = (100.0 * correct.getOrDefault(type, 0)) / total.get(type);
            stats.put(type.name(), percentage);
        }

        return stats;
    }

    public Map<String, Double> getStatsByLevel() {
        Map<BloomLevel, Integer> total = new HashMap<>();
        Map<BloomLevel, Integer> correct = new HashMap<>();

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            total.put(item.getLevel(), total.getOrDefault(item.getLevel(), 0) + 1);
            if (isCorrect(i)) {
                correct.put(item.getLevel(), correct.getOrDefault(item.getLevel(), 0) + 1);
            }
        }

        Map<String, Double> stats = new LinkedHashMap<>();
        for (BloomLevel level : total.keySet()) {
            double percentage = (100.0 * correct.getOrDefault(level, 0)) / total.get(level);
            stats.put(level.name(), percentage);
        }

        return stats;
    }

    public Map<Integer, Integer> getUserAnswers() {
        return userAnswers;
    }
}