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
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\|");
                if (parts.length != 5) continue;
                String question = parts[0];
                List<String> options = Arrays.asList(parts[1].split(";"));
                int correct = Integer.parseInt(parts[2]);
                ItemType type = ItemType.valueOf(parts[3]);
                BloomLevel level = BloomLevel.valueOf(parts[4]);
                items.add(new Item(question, options, correct, type, level));
            }
        }
        onItemsLoaded.notifyAll(items);
    }

    public List<Item> getItems() { return items; }

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