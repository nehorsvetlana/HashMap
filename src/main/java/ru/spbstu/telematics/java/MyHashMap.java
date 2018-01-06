package ru.spbstu.telematics.java;

import java.util.Iterator;

public class MyHashMap<K, V> implements MyMap<K, V> {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    public SimpleEntry<K, V>[] table;
    private int size = 0;
    private float loadFactor;

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, float loadFactor)
            throws IllegalArgumentException {
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: "
                    + loadFactor);
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: "
                    + initialCapacity);

        this.loadFactor = loadFactor;
        table = new SimpleEntry[initialCapacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public static class SimpleEntry<K, V> implements MyMap.SimpleEntry<K, V> {
        public K key;
        public V value;
        SimpleEntry<K, V> next;

        SimpleEntry(K key, V value, SimpleEntry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        //@Override
        public K getKey() {
            return key;
        }

        //@Override
        public V getValue() {
            return value;
        }

        public int hashCode() {
            return (key == null ? 0 : key.hashCode())
                    ^ (value == null ? 0 : value.hashCode());
        }

        //@Override
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return (V) oldValue;
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    public V get(Object key) {
        if (key == null) {
            return getForNullKey();
        }
        int bucketIndex = hashPosition(key, table.length);
        SimpleEntry<K, V> entry = table[bucketIndex];
        while (entry != null) {
            if (key.equals(entry.key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    private V getForNullKey() {
        for (SimpleEntry<K, V> e = table[0]; e != null; e = e.next) {
            if (e.key == null)
                return e.value;
        }
        return null;
    }

    public int hashPosition(Object key, int length) {
        return Math.abs(key.hashCode()) % length;
    }

    @SuppressWarnings("unchecked")
    public V put(K key, V value) {
        if (key == null) {
            return putForNullKey(value);
        }
        int bucketIndex = hashPosition(key, table.length);
        SimpleEntry<K, V> currentEntry = table[bucketIndex];
        for (SimpleEntry<K, V> e = currentEntry; e != null; e = e.next) {
            if (key.equals(e.key)) {
                Object oldValue = e.value;
                e.value = value;
                return (V) oldValue;
            }
        }
        table[bucketIndex] = new SimpleEntry<K, V>(key, value, currentEntry);
        size++;
        if (size * loadFactor >= table.length) {
            transfer();
        }
        return null;
    }

    private V putForNullKey(V value) {
        SimpleEntry<K, V> currentEntry = table[0];
        for (SimpleEntry<K, V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                e.value = value;
                return (V) e.value;
            }
        }
        table[0] = new SimpleEntry<K, V>(null, value, currentEntry);
        size++;
        if (size * loadFactor > table.length) {
            transfer();
        }
        return null;
    }

    private void transfer() {
        @SuppressWarnings("unchecked")
        SimpleEntry<K, V>[] newTable = new SimpleEntry[table.length * 2];
        for (int i = 0; i < table.length; i++) {
            SimpleEntry<K, V> old = table[i];
            while (old != null) {
                int newIndex = hashPosition(old.key, newTable.length);
                SimpleEntry<K, V> entry = newTable[newIndex];
                newTable[newIndex] = new SimpleEntry<K, V>(old.key, old.value,
                        entry);
                old = old.next;
            }
        }
        table = newTable;
    }

    //@Override
    public void clear() {
        for (int i = 0; i < table.length; i++)
            table[i] = null;
        size = 0;
    }

   // @Override
    public boolean containsValue(Object value) {
        if (value == null) {
            return containsNullValue();
        }
        boolean res = false;
        for (int i = 0; i < table.length; i++) {
            for (SimpleEntry<K, V> e = table[i]; e != null; e = e.next) {
                if (e.value.equals(value)) {
                    res = true;
                }
            }
        }
        return res;
    }

    private boolean containsNullValue() {
        boolean res = false;
        for (int i = 0; i < table.length; i++)
            for (SimpleEntry<K, V> e = table[i]; e != null; e = e.next)
                if (e.value == null)
                    res = true;
        return res;
    }

   // @Override
    public V remove(Object key) {
        int bucketIndex;
        if (key == null) {
            bucketIndex = 0;
        } else {
            bucketIndex = hashPosition(key, table.length);
        }
        SimpleEntry<K, V> prev = table[bucketIndex];// ��� ��������� �� ��������
        // ��������
        SimpleEntry<K, V> e = prev;

        while (e != null) {
            if ((e.key == key) || (key != null && key.equals(e.key))) {
                size--;
                if (e == prev)
                    table[bucketIndex] = e.next;
                else
                    prev.next = e.next;
                return e.value;
            }
            prev = e;
            e = e.next;
        }
        return (V) (e == null ? null : e.value);
    }

    //@Override
    public String toString() {
        if (size() == 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            for (SimpleEntry<K, V> e = table[i]; e != null; e = e.next) {
                sb.append(e + ", ");
            }

        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        return "{" + sb.toString() + "}";
    }

    private SimpleEntry<K, V> getSimpleEntry(int index) {
        SimpleEntry<K, V> res = null;
        int step = 0;
        for (int i = 0; i < table.length; i++) {
            for (SimpleEntry<K, V> e = table[i]; e != null && step <= index; e = e.next) {
                res = e;
                step++;
            }
        }
        return res;
    }

    //@Override
    public Iterator<MyMap.SimpleEntry<K, V>> entryIterator() {

        return new Iterator<MyMap.SimpleEntry<K, V>>() {
            int index;

            //@Override
            public boolean hasNext() {
                return (index < size);
            }

           // @Override
            public SimpleEntry<K, V> next() {
                return getSimpleEntry(index++);
            }

            //@Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }
}