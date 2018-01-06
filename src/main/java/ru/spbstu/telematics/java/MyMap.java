package ru.spbstu.telematics.java;
import java.util.Iterator;

public interface MyMap<K, V> {
    void clear();
    boolean containsKey(Object key);
    boolean containsValue(Object value);
    V get(Object key);
    boolean isEmpty();
    V put(K key, V value);
    V remove(Object key);
    int size();
    Iterator entryIterator();
    interface SimpleEntry<K, V> {
        boolean equals(Object o);
        K getKey();
        V getValue();
        int hashCode();
        V setValue(V value);
    }
}
