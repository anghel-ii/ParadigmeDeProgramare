package adt;

import exceptions.MyException;
import model.values.Value;

import java.util.*;

public class MyHeap<V> implements MyIHeap<Integer, V> {

    private Map<Integer, V> map = new HashMap<>();
    private int freeLocation = 1;


    @Override
    public int allocate(V value) {
        map.put(freeLocation, value);
        int usedLocation = freeLocation;
        freeLocation++;
        return usedLocation;
    }





    @Override
    public boolean isDefined(Integer key) {
        return map.containsKey(key);
    }

    @Override
    public void update(Integer key, V value) {
        map.put(key, value);
    }

    @Override
    public V lookup(Integer key) {
        if(!map.containsKey(key)) throw new MyException("Key not defined" + key);
        return map.get(key);
    }


    @Override
    public Set<Integer> keys() {
        return map.keySet();
    }

    @Override
    public V remove(Integer key) {
        return map.remove(key);
    }

    @Override
    public Map<Integer, V> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<Integer, V> newMap) {
        this.map = newMap;
    }


    @Override
    public String toString() {
        return map.toString();
    }
}
