package adt;

import exceptions.MyException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K,V> implements MyIDictionary<K,V> {

    private final Map<K,V> map = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        return map.containsKey(key);
    }

    @Override
    public void update(K key, V value) {
        map.put(key, value);
    }

    @Override
    public V lookup(K key) {
        if (!map.containsKey(key)) throw new MyException("Key not defined: " + key);
        return map.get(key);
    }

    @Override
    public Set<K> keys() {
        return map.keySet();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
