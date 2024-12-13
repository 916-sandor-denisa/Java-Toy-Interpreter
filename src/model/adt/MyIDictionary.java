package model.adt;

import exceptions.MyADTException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K, V> {
    void insert(K key, V value);
    void remove(K key) throws MyADTException;
    void put(K id, V val);
    boolean contains(K key);
    V get(K key) throws MyADTException;
    Set<K> getKeys();
    List<V> getValues();
    Map<K, V> getDictionary();
    String toString();
    MyIDictionary<K, V> clone();
}


