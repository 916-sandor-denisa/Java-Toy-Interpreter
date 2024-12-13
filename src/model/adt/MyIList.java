package model.adt;

import java.util.List;

public interface MyIList<T> {

    void add(T element);
    List<T> getAll();
    int size();
    T get(int index);
    void remove(T element);
    String toString();
}
