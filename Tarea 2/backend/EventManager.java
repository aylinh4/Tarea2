package backend;

import java.util.ArrayList;
import java.util.function.Consumer;

public class EventManager<T> {
    private final ArrayList<Consumer<T>> listeners = new ArrayList<>();

    public void subscribe(Consumer<T> listener) {
        listeners.add(listener);
    }

    public void notifyAll(T event) {
        for (Consumer<T> listener : listeners) {
            listener.accept(event);
        }
    }
}