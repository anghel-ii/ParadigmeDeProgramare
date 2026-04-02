package adt;

import exceptions.MyException;
import model.values.Value;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface MyIHeap<K, V>  {
    
    boolean isDefined(K key);
    
    void update(K key, V value);
    
    V lookup(K key);
    
    Set<K> keys();
    
    V remove(K key);
    
    int allocate(V value);

    void setContent(Map<K, V> newMap);

    Map<Integer,V> getContent();

}
