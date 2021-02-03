package backend.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, I> {

    Optional<I> insert(T t);

    void update(T t);

    void delete(T t);

    Optional<T> get(I id);

    List<T> getAll();

}
