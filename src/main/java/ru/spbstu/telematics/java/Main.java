package ru.spbstu.telematics.java;

import java.util.Iterator;
import java.util.Map;

    public class Main {
        public static void main(String[] args) {
            MyHashMap map = new MyHashMap();
            map.put(1, "one");
            map.put(2, "two");
            map.put(3, "three");
            map.put(4, "four");
            map.put(5, "five");
            map.put(6, "six");
            Iterator<Map.Entry<Integer, String>> i = map.entryIterator();
            System.out.println(i.next());

        }
    }

