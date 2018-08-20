package com.mygdx.game.utils;

import java.util.HashMap;
import java.util.Map;

public class DoubleKeyMap<K, V> {

    private HashMap<DoubleKey<K>, MapEntry<K, V>> values;

    public DoubleKeyMap() {
        values = new HashMap<DoubleKey<K>, MapEntry<K, V>>();
    }

    public void put(K key1, K key2, V value) {
        DoubleKey<K> key = new DoubleKey<K>(key1, key2);
        MapEntry<K, V> entry = new MapEntry<K, V>(key, value);
        values.put(key, entry);
    }

    public V get(K o1, K o2) {
        V value;

        DoubleKey<K> key = new DoubleKey<K>(o1, o2);
        MapEntry<K, V> entry = values.get(key);
        value = entry.getValue();

        return value;
    }

    private static class MapEntry<K, V> implements Map.Entry<DoubleKey<K>, V> {

        private DoubleKey<K> doubleKey;
        private V value;

        public MapEntry() {
        }

        public MapEntry(DoubleKey<K> key) {
            this.doubleKey = key;
        }

        public MapEntry(DoubleKey<K> doubleKey, V value) {
            this.doubleKey = doubleKey;
            this.value = value;
        }

        @Override
        public DoubleKey<K> getKey() {
            return doubleKey;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    public static class DoubleKey<V> {

        private V key1;
        private V key2;

        public DoubleKey(V key1, V key2) {
            this.key1 = key1;
            this.key2 = key2;
        }

        public V getKey1() {
            return key1;
        }

        public V getKey2() {
            return key2;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof DoubleKeyMap.DoubleKey)) return false;

            DoubleKey other = (DoubleKey) obj;
            if (key1.equals(other.key1) && key2.equals(other.key2)) return true;
            if (key1.equals(other.key2) && key2.equals(other.key1)) return true;

            return false;
        }

        @Override
        public int hashCode() {
            return key1.hashCode() + key2.hashCode();
        }
    }
}
