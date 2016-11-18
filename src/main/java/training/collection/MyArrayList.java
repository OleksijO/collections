package training.collection;

import java.util.*;

/**
 * Created by oleksij.onysymchuk@gmail on 19.11.2016.
 */
public class MyArrayList<E> implements List<E> {
    private final static String ERROR_NEGATIVE_CAPACITY = "Initial capacity can not be under zero!";
    private int size = 0;
    private Object array[];

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(ERROR_NEGATIVE_CAPACITY);
        }
        array = new Object[initialCapacity];
    }

    public MyArrayList() {
        this(16);
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
    public boolean contains(Object o) {
        //TODO
        throw new UnsupportedOperationException();
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
    public <T> T[] toArray(T[] a) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        Arrays.stream(array).forEach(el -> el = null);
        size = 0;
    }

    @Override
    public E get(int index) {
        //TODO check bound
        return (E) array[index];
    }

    @Override
    public E set(int index, E element) {
        //TODO check bound
        array[index] = element;
        return (E) array[index];
    }

    @Override
    public void add(int index, E element) {
        //TODO

    }

    @Override
    public E remove(int index) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        //TODO
        throw new UnsupportedOperationException();
    }
}
