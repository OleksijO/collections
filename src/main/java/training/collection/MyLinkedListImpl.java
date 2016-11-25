package training.collection;

import java.util.*;

/**
 * Variation of LinkedList implementation.
 *
 * @author oleksij.onysymchuk@gmail.com
 */
public class MyLinkedListImpl<E> extends MyAbstractList<E> implements Deque<E> {
    private final static String DESTINATION_ARRAY_UNSUFFICIENT_CAPACITY =
            "Destination array has unsufficient capacity. List size = %d, array length = %d.";

    private Node<E> first = null;
    private Node<E> last = null;


    public MyLinkedListImpl() {
    }

    public MyLinkedListImpl(Collection<? extends E> collection) {

        addAll(collection);
    }


    private class Node<E> {
        E value;
        Node<E> next = null;
        Node<E> previous = null;

        Node(E value) {
            this.value = value;
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
    public boolean contains(Object obj) {
        Node<E> current = first;
        if (obj == null) {
            while (current != null) {
                if (current.value == null) {
                    return true;
                }
                current = current.next;
            }
        } else {
            for (Node<E> curr = first; curr != null; curr = curr.next)
                if (obj.equals(curr.value)) {
                    return true;
                }

        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyListIterator(0);
    }

    @Override
    public E[] toArray() {
        E[] array = (E[]) new Object[size];
        Node<E> current = first;
        for (int i = 0; i < size; i++) {
            array[i] = current.value;
            current = current.next;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        Objects.requireNonNull(array);
        if (array.length < size) {
            throw new IllegalArgumentException(
                    String.format(DESTINATION_ARRAY_UNSUFFICIENT_CAPACITY, size, array.length));
        }
        Node<E> current = first;
        for (int i = 0; i < size; i++) {
            array[i] = (T) current.value;
            current = current.next;
        }
        return array;
    }

    @Override
    public boolean add(E e) {
        modCount++;
        Node<E> newNode = new Node<>(e);
        if (size == 0) {
            setFirst(newNode);
            setLast(newNode);
        } else {
            connectNodes(last, newNode);
            setLast(newNode);
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object obj) {
        modCount++;
        Node<E> current = first;
        if (obj == null) {
            while (current != null) {
                if (current.value == null) {
                    deleteNode(current);
                    return true;
                }
                current = current.next;
            }
        } else {
            while (current != null) {
                if (obj.equals(current.value)) {
                    deleteNode(current);
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    private void deleteNode(Node<E> node) {
        if (node == null) {
            throw new ConcurrentModificationException();
        }
        if (first == node) {
            setFirst(node.next);
        } else if (node == last) {
            setLast(node.previous);
        } else {
            node.next.previous = node.previous;
            node.previous.next = node.next;
        }
        size--;

    }

    private void setFirst(Node<E> node) {
        if (node == null) {
            first = null;
        } else {
            first = node;
            first.previous = null;
        }
    }

    private void setLast(Node<E> node) {
        if (node == null) {
            last = null;
        } else {
            last = node;
            last.next = null;
        }
    }


    @Override
    public boolean addAll(Collection<? extends E> collection) {
        modCount++;
        collection.forEach(this::add);
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        modCount++;
        checkIndex(index);
        MyLinkedListImpl<E> newChain = new MyLinkedListImpl<>(collection);
        size += collection.size();
        Node<E> current = getNodeByIndex(index);
        if (index == 0) {
            setFirst(newChain.first);
        } else {
            connectNodes(current.previous, newChain.first);
        }
        connectNodes(newChain.last, current);
        return true;
    }

    private Node<E> getNodeByIndex(int index) {
        int counter = 0;
        Node<E> current = first;
        while (counter < index) {
            current = current.next;
            counter++;
        }
        return current;
    }

    private void connectNodes(Node<E> node1, Node<E> node2) {
        Objects.requireNonNull(node1);
        Objects.requireNonNull(node2);
        node1.next = node2;
        node2.previous = node1;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        modCount++;
        Objects.requireNonNull(collection);
        Node<E> current = first;
        while (current != null) {
            if (collection.contains(current.value)) {
                deleteNode(current);
            }
            current = current.next;
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        modCount++;
        Node<E> current = first;
        while (current != null) {
            if (!collection.contains(current.value)) {
                deleteNode(current);
            }
            current = current.next;
        }
        return true;
    }

    @Override
    public void clear() {
        modCount++;
        size = 0;
        last = null;

        // Clearing all of the links between nodes is "unnecessary", but:
        // - helps a generational GC if the discarded nodes inhabit
        //   more than one generation
        // - is sure to free memory even if there is a reachable Iterator
        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            x.value = null;
            x.next = null;
            x.previous = null;
            x = next;
        }
        first = null;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        modCount++;
        Node<E> current = getNodeByIndex(index);
        E oldValue = current.value;
        current.value = element;
        return oldValue;
    }


    public E set1(int index, E element) {
        checkIndex(index);
        modCount++;
        Node<E> current = getNodeByIndex(index);
        try {
            return current.value;
        } finally {
            current.value = element;
        }

    }

    @Override
    public void add(int index, E element) {
        checkIndex(index);
        modCount++;
        Node<E> current = getNodeByIndex(index);
        addBeforeNode(current, element);

    }

    private void addBeforeNode(Node<E> current, E element) {
        Node<E> newNode = new Node<>(element);
        if (current == first) {
            connectNodes(newNode, first);
            setFirst(newNode);
        } else if (current == last) {
            connectNodes(last, newNode);
            setLast(newNode);
        } else {
            connectNodes(current.previous, newNode);
            connectNodes(newNode, current);
        }
        size++;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        modCount++;
        Node<E> current = getNodeByIndex(index);
        deleteNode(current);
        return current.value;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        Node<E> current = first;
        if (o == null) {
            while (index < size) {
                if (current == null) {
                    throw new ConcurrentModificationException();
                }
                if (current.value == null) {
                    return index;
                }
                current = current.next;
                index++;
            }
        } else {
            while (index < size) {
                if (current == null) {
                    throw new ConcurrentModificationException();
                }
                if (o.equals(current.value)) {
                    return index;
                }
                current = current.next;
                index++;
            }
        }
        if (current != null) {
            throw new ConcurrentModificationException();
        }
        return (index == size) ? -1 : index;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = size - 1;
        Node<E> current = last;
        if (o == null) {
            while (index >= 0) {
                if (current == null) {
                    throw new ConcurrentModificationException();
                }
                if (current.value == null) {
                    return index;
                }
                current = current.previous;
                index--;
            }
        } else {
            while (index >= 0) {
                if (current == null) {
                    throw new ConcurrentModificationException();
                }
                if (o.equals(current.value)) {
                    return index;
                }
                current = current.previous;
                index--;
            }
        }
        if (current != null) {
            throw new ConcurrentModificationException();
        }
        return index;

    }

    @Override
    public ListIterator<E> listIterator() {
        return new MyListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        checkIndex(index);
        return new MyListIterator(index);
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E pollFirst() {
        return poll();
    }

    @Override
    public E pollLast() {
        return (last == null) ? null : last.value;
    }

    @Override
    public E peekFirst() {
        return peek();
    }

    @Override
    public E peekLast() {
        if (last == null) {
            return null;
        }
        E value = last.value;
        deleteNode(last);
        return value;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public E remove() {
        return poll();
    }

    @Override
    public E poll() {
        if (first == null) {
            return null;
        }
        E value = first.value;
        deleteNode(first);
        return value;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return (first == null) ? null : first.value;
    }

    @Override
    public void push(E e) {
        addFirst(e);

    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            private final MyListIterator listIterator = new MyListIterator(size);

            public boolean hasNext() {
                return listIterator.hasPrevious();
            }

            public E next() {
                return listIterator.previous();
            }

            public void remove() {
                listIterator.remove();
            }
        };
    }


    public List<E> subList(int fromIndex, int toIndex) {
        return new MySubList<E>(this, fromIndex, toIndex);
    }

    /**
     * Returns the first element in this list.
     *
     * @return the first element in this list
     * @throws NoSuchElementException if this list is empty
     */
    public E getFirst() {

        if (first == null)
            throw new NoSuchElementException();
        return first.value;
    }

    /**
     * Returns the last element in this list.
     *
     * @return the last element in this list
     * @throws NoSuchElementException if this list is empty
     */
    public E getLast() {

        if (last == null)
            throw new NoSuchElementException();
        return last.value;
    }

    /**
     * Removes and returns the first element from this list.
     *
     * @return the first element from this list
     * @throws NoSuchElementException if this list is empty
     */
    public E removeFirst() {

        if (first == null)
            throw new NoSuchElementException();
        E value = first.value;
        deleteNode(first);
        return value;
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last element from this list
     * @throws NoSuchElementException if this list is empty
     */
    public E removeLast() {
        if (last == null)
            throw new NoSuchElementException();
        E value = last.value;
        deleteNode(last);
        return value;
    }

    /**
     * Inserts the specified element at the beginning of this list.
     *
     * @param e the element to add
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e the element to add
     */
    public void addLast(E e) {
        add(e);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof List)) return false;

        List<?> that = (List<?>) obj;

        if (size != that.size()) return false;
        Node<E> current = first;
        int index = 0;
        while (current != null) {
            E el = current.value;
            E thatEl = (E) that.get(index++);
            if (el == null) {
                if (thatEl != null) {
                    return false;
                } else {
                    current = current.next;
                    continue;
                }
            }
            if (!current.value.equals(thatEl)) {
                return false;
            }
            current = current.next;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        Node<E> current = first;
        while (current != null) {
            E el = current.value;
            hashCode = 31 * hashCode + (el == null ? 0 : el.hashCode());
        }
        return hashCode;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        int counter = 0;
        for (Node<E> current = first; current != null; current = current.next) {
            builder.append(current.value);
            if (counter < size - 1) {
                builder.append(", ");
            }
            counter++;
        }
        builder.append("]");
        return builder.toString();
    }

    private class MyListIterator implements ListIterator<E> {
        /**
         * Next node, which element to be returned index
         */
        Node<E> current;
        /**
         * Next element to be returned index
         */
        int currentIndex;
        /**
         * Last returned element node
         */
        Node<E> lastReturned = null;
        /**
         * Stores modification counter state when instance of iterator has created
         */
        int expectedModCount = modCount;

        public MyListIterator(int index) {
            if (index == size) {
                current = null;
            } else {
                checkIndex(index);
                current = getNodeByIndex(index);
            }
            this.currentIndex = index;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            checkForComodification();
            E valueToReturn = current.value;
            lastReturned = current;
            current = current.next;
            currentIndex++;
            return valueToReturn;
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        @Override
        public E previous() {
            checkForComodification();
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (current == null) {
                current = last;
            } else {
                current = current.previous;
            }
            lastReturned = current;
            currentIndex--;
            return lastReturned.value;
        }

        @Override
        public int nextIndex() {
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            checkForComodification();
            deleteNode(current);
            expectedModCount = modCount;
            current = current.next;
            lastReturned = null;
        }

        @Override
        public void set(E e) {
            checkForComodification();
            current.value = e;
        }

        @Override
        public void add(E element) {
            checkForComodification();
            addBeforeNode(current, element);
            lastReturned = null;
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}
