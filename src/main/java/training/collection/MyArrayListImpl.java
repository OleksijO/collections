package training.collection;

import java.util.*;
import java.util.function.Consumer;

/**
 * Variation of ArrayList implementation.
 *
 * @author oleksij.onysymchuk@gmail.com
 */
public class MyArrayListImpl<E> extends MyAbstractList<E> {
    private final static String ERROR_NEGATIVE_CAPACITY = "Initial capacity can not be under zero!";
    private final static String DESTINATION_ARRAY_UNSUFFICIENT_CAPACITY =
            "Destination array has unsufficient capacity. List size = %d, array length = %d.";

    private final static int DEFAULT_CAPACITY = 10;


    /**
     * Inner values container
     */
    private Object array[];


    public MyArrayListImpl(int initialCapacity) {
        checkNonNegative(initialCapacity, ERROR_NEGATIVE_CAPACITY);
        array = new Object[initialCapacity];
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
        if (obj == null) {
            for (int i = 0; i < size; i++) {
                if (array[i] == null) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (obj.equals(array[i])) {
                    return true;
                }
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

    public void ensureCapacity(int capacity) {
        modCount++;
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
        modCount++;
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
        Objects.requireNonNull(collection);
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
        modCount++;
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
        modCount++;
        int oldSize = size;
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (!collection.contains(iterator.next())) {
                iterator.remove();
            }
        }
        return size != oldSize;
    }

    @Override
    public void clear() {
        modCount++;
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) array[index];
    }



    @Override
    public E set(int index, E element) {
        checkIndex(index);
        modCount++;
        array[index] = element;
        return (E) array[index];
    }

    @Override
    public void add(int index, E element) {
        ensureCapacity(size + 1);
        selfArrayCopy(index, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        modCount++;
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
        return new MySubList<E>(this, fromIndex, toIndex);
    }


    @Override
    public List<E> clone() {
        MyArrayListImpl<E> newList = null;
        try {
            newList = (MyArrayListImpl<E>) super.clone();
        } catch (CloneNotSupportedException ignored) {
        }
        System.arraycopy(array, 0, newList.array, 0, size);
        newList.modCount = 0;
        return newList;
    }

    public void trimToSize() {
        modCount++;
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
            checkForComodification();
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
            checkForComodification();
            try {
                MyArrayListImpl.this.set(lastReturned, obj);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(E obj) {
            checkForComodification();
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
        /**
         * Stores modification counter state when instance of iterator has created
         */
        int expectedModCount = modCount;

        public MyIterator() {
        }

        private MyIterator(int current) {
            this.current = current;
        }

        public boolean hasNext() {
            return current != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            checkForComodification();
            if (current >= size)
                throw new NoSuchElementException();
            if (current >= MyArrayListImpl.this.array.length) {
                throw new ConcurrentModificationException();
            }
            lastReturned = current;
            current++;
            return (E) MyArrayListImpl.this.array[lastReturned];
        }

        public void remove() {
            if (lastReturned < 0)
                throw new IllegalStateException();
            checkForComodification();
            try {
                MyArrayListImpl.this.remove(lastReturned);
                current = lastReturned;
                lastReturned = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }

    private class MySubList<E> implements List<E> {
        private MyArrayListImpl parentList;
        private int offset;
        private int subSize;
        private int modSubCount;

        MySubList(MyArrayListImpl<E> parentList,
                  int fromIndex, int toIndex) {
            this.parentList = parentList;
            this.offset = fromIndex;
            this.subSize = toIndex - fromIndex;
            this.modSubCount = MyArrayListImpl.this.modCount;
        }

        @Override
        public E get(int index) {
            checkIndex(index + offset);
            return (E) parentList.get(offset + index);
        }

        @Override
        public int size() {
            return subSize;
        }

        @Override
        public boolean isEmpty() {
            return subSize != 0;
        }

        @Override
        public boolean contains(Object o) {
            //TODO
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(int index, E obj) {
            checkIndex(offset + index);
            checkForComodification();
            parentList.add(offset + index, obj);
            modSubCount = modCount;
            subSize++;
        }

        @Override
        public E set(int index, E obj) {
            checkIndex(offset + index);
            checkForComodification();
            E old = (E) parentList.get(offset + index);
            parentList.set(offset + index, obj);
            return old;
        }

        @Override
        public E remove(int index) {
            checkIndex(offset + index);
            checkForComodification();
            E removedEl = (E) parentList.remove(offset + index);
            modSubCount = modCount;
            subSize--;
            return removedEl;
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
            return new MySubListListIterator(offset);
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

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            checkIndex(index + offset);
            int collectionSize = c.size();
            if (collectionSize == 0) {
                return false;
            }
            checkForComodification();
            parentList.addAll(offset + index, c);
            modSubCount = modCount;
            subSize += collectionSize;
            return true;
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
            //TODO
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            return addAll(subSize, collection);
        }

        final void checkForComodification() {
            if (modCount != modSubCount)
                throw new ConcurrentModificationException();
        }

        @Override
        public Iterator<E> iterator() {
            return new MySubListListIterator(offset);
        }

        @Override
        public Object[] toArray() {
            //TODO
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            //TODO
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(E e) {
            parentList.add(offset + subSize, e);
            modSubCount = modCount;
            return true;
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


        private class MySubListListIterator implements ListIterator<E> {
            private MyListIterator parentIterator;
            private int current = 0;

            private MySubListListIterator(int offset) {
                parentIterator = new MyListIterator(offset);
            }

            @Override
            public boolean hasNext() {
                return parentIterator.hasNext() && current < subSize;
            }

            @Override
            public E next() {
                current++;
                return (E) parentIterator.next();
            }

            @Override
            public boolean hasPrevious() {
                return parentIterator.hasPrevious() && current > 0;
            }

            @Override
            public E previous() {
                current--;
                return (E) parentIterator.previous();
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
            public void remove() {
                subSize--;
                parentIterator.remove();
            }

            @Override
            public void set(E e) {
                //todo
            }

            @Override
            public void add(E e) {
                //todo
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
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            builder.append(get(i));
            if (i < size - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
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