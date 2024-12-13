package model.adt;

import exceptions.MyADTException;
import exceptions.MyExpressionException;
import model.type.IType;

import java.util.*;

public class MyDictionary<K, V> implements MyIDictionary<K,V> {
    private Map<K, V> map;

    public MyDictionary() {
        map = new HashMap<K, V>();
    }

    @Override
    public void insert(K key, V value) {
        this.map.put(key, value);
    }

    @Override
    public void remove(K key) throws MyADTException {
        if (!contains(key)){
            throw new MyADTException("Key not found!\n");
        }
        this.map.remove(key);
    }

    @Override
    public void put(K id, V val) {
        this.map.replace(id, val);
    }

    @Override
    public boolean contains(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public V get(K key) throws MyADTException {
        if (!contains(key)){
            throw new MyADTException("Key not found!\n");
        }
        return this.map.get(key);
    }

    @Override
    public Set<K> getKeys() {
        return map.keySet();
    }

    @Override
    public List<V> getValues() {
        return new ArrayList<>(map.values());
    }

    public Map<K, V> getDictionary() {
        return map;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (K key : map.keySet()) {
            sb.append(key).append("-> ").append(map.get(key)).append("\n");
        }
        return "My dictionary contains:\n" + sb.toString();
    }

    @Override
    public  MyIDictionary<K, V> clone() {
        MyIDictionary<K, V> newTypeEnv = new MyDictionary<>();
        for(Map.Entry<K, V> entry: this.getDictionary().entrySet()) {
            newTypeEnv.insert(entry.getKey(), entry.getValue());
        }
        return newTypeEnv;
    }
}
