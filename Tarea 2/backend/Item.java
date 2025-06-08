package backend;

import java.util.List;

public class Item {
    private String question;
    private List<String> options;
    private int correctIndex;
    private ItemType type;
    private BloomLevel level;

    public Item(String question, List<String> options, int correctIndex, ItemType type, BloomLevel level) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
        this.type = type;
        this.level = level;
    }

    public String getQuestion() { return question; }
    public List<String> getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
    public ItemType getType() { return type; }
    public BloomLevel getLevel() { return level; }
}