package adt;

import java.util.Set;

public interface MyIDictionary<K,V> {
    boolean isDefined(K key);
    void update(K key, V value);
    V lookup(K key);
    Set<K> keys();
}
