package training.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by user on 25.11.2016.
 */
public class MyTreeSet<E> implements Set<E> {
    int size=0;
    Node<E> root=null;

    private class Node<E>{
        Node<E> left=null;
        Node<E> right=null;
        E data;

        public Comparable<E> getData(){
            return (Comparable<E>) data;
        }

        public Node(E obj, Node<E> left, Node<E> right) {
            this.left = left;
            this.right = right;
            this.data = data;
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size!=0;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        if (size==0){
            root = new Node<E>(e,null,null);
        } else {
            Comparable<E> value = (Comparable<E>) e;
            Node<E> current=root;
            while (true){
                if (value.compareTo(current.getData())
            }
        }


        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}
