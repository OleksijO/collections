package training.collection;

import java.util.*;

/**
 * Created by oleksij.onysymchuk@gmail on 30.11.2016.
 */
public class MyHashMap<K, V> implements Map<K, V> {
    private MyEntry<K, V>[] array;

    //private final double loadFactor;
    private int size = 0;

    public MyHashMap() {
        //loadFactor = 0.75;
        array = (MyEntry<K, V>[]) new MyEntry[16];
    }

    static class MyEntry<K, V> implements Entry<K, V> {
        K key;
        V value;
        MyEntry<K, V> next;
        int hash;

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }

        MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
            hash = key.hashCode();
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int bucket = calcBucket(key.hashCode(), array);
        if (array[bucket] == null) {
            return false;
        }
        MyEntry<K, V> current = array[bucket];
        while (current != null) {
            if (current.key.equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (MyEntry<K, V> el : array) {
            if (el != null) {
                MyEntry<K, V> current = el;
                while (current != null) {
                    if (current.value.equals(value)) {
                        return true;
                    }
                    current = current.next;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
       if (size==0){
           return null;
       }
        int bucket = calcBucket(key.hashCode(), array);
        if (array[bucket] == null) {
            return null;
        } else {
            MyEntry<K, V> current = array[bucket];
            while (current != null) {
                if (current.key.equals(key)){
                    return current.value;
                }
                current = current.next;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        MyEntry<K, V> newEntry = new MyEntry<K, V>(key, value);
        if (size == array.length) {
            expand();
        }
        int bucket = calcBucket(key.hashCode(), array);
        if (array[bucket] == null) {
            array[bucket] = newEntry;
            size++;
            return null;
        }
        MyEntry<K, V> current = array[bucket];
        MyEntry<K, V> previous = current;
        while (current != null) {
            if (current.key.equals(key)) {
                V old = current.value;
                current.value = value;
                return old;
            }
            previous = current;
            current = current.next;
        }
        previous.next = newEntry;
        size++;
        return null;
    }

    private void expand() {
        MyEntry<K, V>[] newArray = (MyEntry<K, V>[]) new MyEntry[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                MyEntry<K, V> current = array[i];
                MyEntry<K, V> previous = current;
                while (current != null) {
                    placeEntryToArray(current, newArray);
                    previous = current;
                    current = current.next;
                    previous.next = null;
                }
            }
        }
        array = newArray;
    }

    private void placeEntryToArray(MyEntry<K, V> entry, MyEntry<K, V>[] array) {
        int bucket = calcBucket(entry, array);
        if (array[bucket] == null) {
            array[bucket] = entry;
        } else {
            MyEntry<K, V> current = array[bucket];
            MyEntry<K, V> previous = current;
            while (current != null) {
                previous = current;
                current = current.next;
            }
            previous.next = entry;
        }
    }

    int calcBucket(MyEntry<K, V> entry, MyEntry<K, V>[] array) {
        return Math.abs(entry.hash % array.length);
    }

    int calcBucket(int hash, MyEntry<K, V>[] array) {
        return Math.abs(hash % array.length);
    }

    @Override
    public V remove(Object key) {
        int bucket = calcBucket(key.hashCode(), array);
        if (array[bucket] == null) {
            return null;
        } else {
            MyEntry<K, V> current = array[bucket];
            MyEntry<K, V> previous = current;
            while (current != null) {
                if (current.key.equals(key)) {
                    if (previous != current) {
                        previous.next = current.next;
                    } else {
                        array[bucket] = current.next;
                    }
                    size--;
                    return current.value;
                }

                previous = current;
                current = current.next;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Objects.requireNonNull(m);
        m.entrySet().forEach(entry-> put(entry.getKey(),entry.getValue()));
    }

    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                MyEntry<K, V> current = array[i];
                while (current != null) {
                    set.add(current.key);
                    current = current.next;
                }
            }
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<V>(size);
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                MyEntry<K, V> current = array[i];
                while (current != null) {
                    values.add(current.value);
                    current = current.next;
                }
            }
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                MyEntry<K, V> current = array[i];
                while (current != null) {
                    set.add(new MyEntry<>(current.key, current.value));
                    current = current.next;
                }
            }
        }
        return set;
    }
}
