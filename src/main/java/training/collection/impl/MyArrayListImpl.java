package training.collection.impl;

import training.collection.MyArrayList;

import java.util.*;

/**
 * Created by oleksij.onysymchuk@gmail on 19.11.2016.
 */
public class MyArrayListImpl<E> implements MyArrayList<E> {
    private final static String ERROR_NEGATIVE_CAPACITY = "Initial capacity can not be under zero!";
    private final static String INDEX_OUT_OF_BOUNDS = "Index is out of bounds. Index = %d.";
    private final static String DESTINATION_ARRAY_UNSUFFICIENT_CAPACITY =
            "Destination array has unsufficient capacity. List size = %d, array length = %d.";
    private final static String NO_PLACE_LEFT =
            "There is no place left in list to add element(s). Size = %d.";
    private final static int initCapacity = 16;
    private int size = 0;
    private Object array[];

    public MyArrayListImpl(int initialCapacity) {
        checkNonNegative(initialCapacity, ERROR_NEGATIVE_CAPACITY);
        array = new Object[initialCapacity];
    }

    private void checkNonNegative(int number, String errorMessage) {
        if (number < 0) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public MyArrayListImpl() {
        this(initCapacity);
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
    public boolean contains(Object obj) {
        for (int i = 0; i < size; i++) {
            if (array[i]==null) {
                return true;
            }
        }
        for (int i = 0; i < size; i++) {
            if (array[i].equals(obj)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(array, 0, result, 0, size);
        return result;
    }

    @Override
    public <T> T[] toArray(T[] destinationArray) {
        Objects.requireNonNull(destinationArray);
        if (destinationArray.length < size) {
            throw new IllegalArgumentException(
                    String.format(DESTINATION_ARRAY_UNSUFFICIENT_CAPACITY, size, array.length));
        }
        System.arraycopy(array, 0, destinationArray, 0, size);
        return destinationArray;
    }

    @Override
    public boolean add(E obj) {
        ensureCapacity(size + 1);
        array[size] = obj;
        size++;
        return true;
    }

    private void ensureCapacity(int capacity) {
        checkNonNegative(capacity, String.format(NO_PLACE_LEFT, size));
        if (capacity > array.length) {
            int newArrayLength = (array.length * 3) / 2 + 1;
            if (newArrayLength < array.length) {
                newArrayLength = capacity;
            }
            Object newArray[] = new Object[newArrayLength];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    @Override
    public boolean remove(Object obj) {
        if (obj == null) {
            for (int index = 0; index < size; index++) {
                if (array[index] == null) {
                    removeWithoutChecks(index);
                    return true;
                }
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (array[index].equals(obj)) {
                    removeWithoutChecks(index);
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object el : collection) {
            if (!contains(el)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean addAll(Collection<? extends E> collection) {
        int quantityToAdd = collection.size();
        ensureCapacity(size + quantityToAdd);
        collection.forEach(el -> array[size++] = el);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        int quantityToAdd = collection.size();
        ensureCapacity(size + quantityToAdd);
        selfArrayCopy(index, index + quantityToAdd, quantityToAdd);
        collection.forEach(el -> array[index - quantityToAdd + size++] = el);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        collection.forEach(el -> {
            remove(el);
            size--;
        });
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        Object[] sameElements = Arrays.stream(array).filter(collection::contains).toArray();
        int oldSize = size;
        clear();
        System.arraycopy(sameElements, 0, array, 0, sameElements.length);
        size = sameElements.length;
        return size != oldSize;
    }

    @Override
    public void clear() {
        Arrays.stream(array).forEach(el -> el = null);
        size = 0;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) array[index];
    }

    private void checkIndex(int index) {
        if (index >= size) {
            throw new IllegalArgumentException(String.format(INDEX_OUT_OF_BOUNDS, index));
        }
        checkNonNegative(index, String.format(INDEX_OUT_OF_BOUNDS, index));
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        array[index] = element;
        return (E) array[index];
    }

    @Override
    public void add(int index, E element) {
        ensureCapacity(size + 1);
        selfArrayCopy(index, index + 1, size - index - 1);
        array[index] = element;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E element = (E) array[index];
        removeWithoutChecks(index);
        return element;
    }

    private void removeWithoutChecks(int index) {
        if (index < size - 1) {
            selfArrayCopy(index + 1, index, size - index - 1);
        }
        array[size - 1] = null;
        size--;
    }

    private void selfArrayCopy(int from, int to, int quantity) {
        if (from > to) {
            System.arraycopy(array, from, array, to, quantity);
        } else if (from < to) {
            Object newArray[] = new Object[array.length];
            System.arraycopy(array, 0, newArray, 0, from + 1);
            System.arraycopy(array, from, newArray, to, quantity);
            array = newArray;
        }
    }

    @Override
    public int indexOf(Object obj) {
        int index = size - 1;
        if (obj == null) {
            for (; index >= 0; index--) {
                if (array[index] == null) {
                    return index;
                }
            }
        } else {
            for (; index >= 0; index--) {
                if (array[index].equals(obj)) {
                    return index;
                }
            }
        }
        return index;
    }

    @Override
    public int lastIndexOf(Object obj) {
        return indexOf(obj);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new MyListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        int elementNumberToCopy = toIndex - fromIndex + 1;
        MyArrayListImpl<E> subList = new MyArrayListImpl<E>(elementNumberToCopy);
        System.arraycopy(array, fromIndex, subList.array, 0, elementNumberToCopy);
        return subList;
    }

    @Override
    public void trimToSize() {
        if (size < array.length) {
            Object[] newArray = new Object[size];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    private class MyListIterator implements ListIterator<E> {
        int currentIndex;

        public MyListIterator(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        public MyListIterator() {
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size - 1;
        }

        @Override
        public E next() {
            return (E) array[++currentIndex];
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        @Override
        public E previous() {
            return (E) array[--currentIndex];
        }

        @Override
        public int nextIndex() {
            return currentIndex + 1;
        }

        @Override
        public int previousIndex() {
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            MyArrayListImpl.this.remove(currentIndex);
        }

        @Override
        public void set(E e) {
            array[currentIndex] = e;
        }

        @Override
        public void add(E e) {
            MyArrayListImpl.this.add(currentIndex, e);
        }
    }

    private class MyIterator implements Iterator<E> {
        private final MyListIterator listIterator;

        private MyIterator() {
            this.listIterator = new MyListIterator();
        }

        @Override
        public boolean hasNext() {
           return listIterator.hasNext();
        }

        @Override
        public E next() {
           return listIterator.next();
        }

        @Override
        public void remove() {
            listIterator.remove();
        }
    }
}