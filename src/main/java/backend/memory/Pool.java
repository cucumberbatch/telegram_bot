package backend.memory;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {

    public interface IPoolObjectFactory<T> {
        T create();
    }

    private IPoolObjectFactory<T> factory;
    private List<T> elements;

    public Pool(IPoolObjectFactory<T> factory) {
        this.elements = new ArrayList<>();
        this.factory  = factory;
    }

    public T get() {
        return elements.isEmpty() ? factory.create() : elements.get(0);
    }

    public void put(T element) {
        this.elements.add(element);
    }
}
