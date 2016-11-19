package training.collection.impl;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by oleksij.onysymchuk@gmail on 19.11.2016.
 */
public class MyArrayListImpl<E> implements List<E> {
    private final static String ERROR_NEGATIVE_CAPACITY = "Initial capacity can not be under zero!";
    private final static String INDEX_OUT_OF_BOUNDS = "Index is out of bounds. Index = %d.";
    private final static String DESTINATION_ARRAY_UNSUFFICIENT_CAPACITY =
            "Destination array has unsufficient capacity. List size = %d, array length = %d.";
    private final static String NO_PLACE_LEFT =
            "There is no place left in list to add element(s). Size = %d.";
    private static final String TO_LESS_FROM = "toIndex can not be less then fromIndex.";

    private final static int DEFAULT_CAPACITY = 10;

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
        this(DEFAULT_CAPACITY);
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
            if (array[i] == null) {
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
        return new MyIterator();
    }

    @Override
    public E[] toArray() {
        Object[] result = new Object[size];
        System.arraycopy(array, 0, result, 0, size);
        return (E[]) result;
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
            if (newArrayLength < capacity) {
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
                if (obj.equals(array[index])) {
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
        int oldSize = size;
        ensureCapacity(size + quantityToAdd);
        selfArrayCopy(index, index + quantityToAdd, size - index);
        collection.forEach(el -> array[index + (size++) - oldSize] = el);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (collection.contains(iterator.next())) {
                iterator.remove();
            }
        }
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
        System.out.println(Arrays.toString(array));
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        size = 0;
        System.out.println(Arrays.toString(array));
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
                if (obj.equals(array[index])) {
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
        return new MyListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new MyListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        int elementNumberToCopy = toIndex - fromIndex;
        if (elementNumberToCopy < 1) {
            throw new IllegalArgumentException(TO_LESS_FROM);
        }
        checkIndex(fromIndex);
        checkIndex(toIndex-1);
        MyArrayListImpl<E> subList = new MyArrayListImpl<E>(elementNumberToCopy);
        System.arraycopy(array, fromIndex, subList.array, 0, elementNumberToCopy);
        subList.size = elementNumberToCopy;
        return subList;
    }

    public void trimToSize() {
        if (size < array.length) {
            Object[] newArray = new Object[size];
            System.arraycopy(array, 0, newArray, 0, size);
            array = newArray;
        }
    }

    /**
     * ! ! ! FOR TEST PURPOSES ONLY ! ! !
     *
     * @return Inner private array object
     */
    E[] getInnerArray() {
        return (E[]) array;
    }

    private class MyListIterator extends MyIterator implements ListIterator<E> {


        public MyListIterator(int currentIndex) {
            checkIndex(currentIndex);
            this.current = currentIndex;
        }

        @Override
        public boolean hasPrevious() {
            return current > 0;
        }

        @Override
        public E previous() {
            if (current < 1) {
                throw new NoSuchElementException();
            }
            current--;
            lastReturned = current;
            return (E) array[lastReturned];
        }

        @Override
        public int nextIndex() {
            return current;
        }

        @Override
        public int previousIndex() {
            return current - 1;
        }

        @Override
        public void set(E obj) {
            if (lastReturned < 0)
                throw new IllegalStateException();
            try {
                MyArrayListImpl.this.set(lastReturned, obj);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(E obj) {
            try {
                MyArrayListImpl.this.add(current, obj);
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
            current++;
            lastReturned = -1;
        }
    }

    private class MyIterator implements Iterator<E> {
        /**
         * Next element to be returned index
         */
        int current;
        /**
         * Last returned element index
         */
        int lastReturned = -1;


        public boolean hasNext() {
            return current != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            int i = current;
            if (i >= size)
                throw new NoSuchElementException();
            if (i >= MyArrayListImpl.this.array.length) {
                throw new ConcurrentModificationException();
            }
            current = i + 1;
            lastReturned = i;
            return (E) MyArrayListImpl.this.array[lastReturned];
        }

        public void remove() {
            if (lastReturned < 0)
                throw new IllegalStateException();
            try {
                MyArrayListImpl.this.remove(lastReturned);
                current = lastReturned;
                lastReturned = -1;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof List)) return false;

        List<?> that = (List<?>) obj;

        if (size != that.size()) return false;

        for (int i = 0; i < size(); i++) {
            E el = this.get(i);
            if (el == null) if (that.get(i) != null) {
                return false;
            } else {
                continue;
            }

            if (!this.get(i).equals(that.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        final E[] arrayFinalReference = (E[]) array;
        for (int i = 0; i < size; i++) {
            action.accept(arrayFinalReference[i]);
        }
    }
}