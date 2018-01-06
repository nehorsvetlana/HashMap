package ru.spbstu.telematics.java;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import ru.spbstu.telematics.java.MyHashMap;

public class MyHashMapTest {
    MyHashMap map = new MyHashMap();

    @Test
    public void emptyHashMap() {
        boolean expRes = true;
        boolean res = map.isEmpty();
        assertEquals(res, expRes);
    }

    @Test
    public void containsValidKey() {
        map.put(1, "first");
        boolean expRes = true;
        boolean res = map.containsKey(1);
        assertEquals(expRes, res);
    }

    @Test
    public void containsInvalidKey() {
        map.put(1, "first");
        boolean expRes = false;
        boolean res = map.containsKey("atata");
        assertEquals(expRes, res);
    }

    @Test
    public void containsNullKey() {
        map.put(null, "first");
        boolean expRes = true;
        boolean res = map.containsKey(null);
        assertEquals(expRes, res);
    }

    @Test
    public void getValueOfAvailableKey() {
        map.put(1, "first");
        String expRes = "first";
        String res = (String) map.get(1);
        assertEquals(expRes, res);
    }

    @Test
    public void getValueOfNotAvailableKey() {
        map.put(1, "first");
        String expRes = null;
        String res = (String) map.get(2);
        assertEquals(expRes, res);
    }

    @Test
    public void getValueOfNullKey() {
        map.put(null, null);
        String expRes = null;
        String res = (String) map.get(null);
        assertEquals(expRes, res);
    }

    @Test
    public void clearSuccessful() {
        map.put(1, "first");
        map.put(2, "second");
        map.put(3, "third");

        int expSize = 0;
        map.clear();
        int size = map.size();
        assertEquals(size, expSize);
    }

    @Test
    public void containsValidValue() {
        map.put(1, "first");
        boolean expRes = true;
        boolean res = map.containsValue("first");
        assertEquals(expRes, res);
    }

    @Test
    public void containsInvalidValue() {
        map.put(1, "first");
        boolean expRes = false;
        boolean res = map.containsValue("second");
        assertEquals(expRes, res);
    }

    @Test
    public void containsNullValue() {
        map.put(1, null);
        boolean expRes = true;
        boolean res = map.containsValue(null);
        assertEquals(expRes, res);
    }

    @Test
    public void putNewElement() {
        Object expRes = null;
        Object res = map.put(1, "first");
        assertEquals(expRes, res);
    }

    @Test
    public void putExistingElement() {
        map.put(1, "first");
        String expRes = "first";
        String res = (String) map.put(1, "second");
        assertEquals(expRes, res);
    }

    @Test
    public void removeElement() {
        map.put(1, "first");
        int expSize = 0;
        map.remove(1);
        int size = map.size();
        assertEquals(expSize, size);
    }

    @Test
    public void removeElementWithNullKey() {
        map.put(null, "first");
        String expRes = "first";
        String res = (String) map.remove(null);
        assertEquals(expRes, res);
    }

}