package training.collection;

import java.util.*;

/**
 * Variation of ArrayList implementation.
 *
 * @author oleksij.onysymchuk@gmail.com
 */
abstract class MyAbstractList<E> implements List<E> {

    final static String INDEX_OUT_OF_BOUNDS = "Index is out of bounds. Index = %d.";

    final static String NO_PLACE_LEFT =
            "There is no place left in list to add element(s). Size = %d.";
    static final String TO_LESS_FROM = "toIndex can not be less then fromIndex.";
    /**
     * Effective size of this list
     */
   int size = 0;

    /**
     * Concurrent modification counter for iterators
     */
    int modCount = 0;

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

    void checkIndex(int index) {
        if (index >= size) {
            throw new IllegalArgumentException(String.format(INDEX_OUT_OF_BOUNDS, index));
        }
        checkNonNegative(index, String.format(INDEX_OUT_OF_BOUNDS, index));
    }

    void checkNonNegative(int number, String errorMessage) {
        if (number < 0) {
            throw new IllegalArgumentException(errorMessage);
        }
    }


    protected class MySubList<E> implements List<E> {
        private MyAbstractList parentList;
        private int offset;
        private int subSize;
        private int modSubCount;

        MySubList(MyAbstractList parentList,
                  int fromIndex, int toIndex) {
            this.parentList = parentList;
            this.offset = fromIndex;
            this.subSize = toIndex - fromIndex;
            this.modSubCount = modCount;
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
            //TODO
            throw new UnsupportedOperationException();
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



    }

}
