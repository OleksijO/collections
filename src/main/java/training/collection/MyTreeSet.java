package training.collection;

import java.util.*;

/**
 * Created by user on 25.11.2016.
 */
public class MyTreeSet<E> implements Set<E> {
    int size = 0;
    public Node<E> root = null;

    private class Node<E> {
        Node<E> parent = null;
        Node<E> left = null;
        Node<E> right = null;
        E data;

        public Comparable<E> getData() {
            return (Comparable<E>) data;
        }

        public Node(E data, Node<E> parent, Node<E> left, Node<E> right) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.data = data;
        }
    }

    private Comparator<E> comparator = (o1, o2) -> ((Comparable<E>) o1).compareTo(o2);


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
        if (size == 0) {
            return false;
        }
        E valueToFind = (E) o;
        Node<E> current = root;
        while (current != null) {
            E currentValue = current.data;
            int compResult = comparator.compare(currentValue, valueToFind);
            if (compResult == 0) {
                return true;
            }
            if (compResult > 0) {
                if (current.left != null) {
                    current = current.left;
                    continue;
                } else {
                    return false;
                }
            } else {
                if (current.right != null) {
                    current = current.right;
                    continue;
                } else {
                    return false;
                }
            }
        }
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
        System.out.println(" adding " + e);
        if (size == 0) {
            root = new Node<>(e, null, null, null);
            size++;
            return true;
        }
        Node<E> current = root;
        while (current != null) {
            E currentValue = current.data;
            int compResult = comparator.compare(currentValue, e);
            if (compResult == 0) {
                return false;
            }
            if (compResult > 0) {
                if (current.left != null) {
                    current = current.left;
                    continue;
                } else {
                    addLeftToNode(current, e);
                    return true;
                }
            } else {
                if (current.right != null) {
                    current = current.right;
                    continue;
                } else {
                    addRightToNode(current, e);
                    return true;
                }
            }
        }
        return false;
    }

    private void addLeftToNode(Node<E> current, E e) {
        current.left = new Node<>(e, current, null, null);
        size++;
    }

    private void addRightToNode(Node<E> current, E e) {
        current.right = new Node<>(e, current, null, null);
        size++;
    }

    @Override
    public boolean remove(Object o) {
        System.out.println();
        System.out.println("Removing " + o + " ...");
        if (size == 0) {
            System.out.println("size==0, so nothing to remove. Return false.");
            return false;
        }
        Node<E> current = root;
        E valueToRemove = (E) o;
        System.out.println("beginning iteration");
        while (current != null) {

            E currentValue = current.data;
            System.out.println("current = " + currentValue);
            int compResult = comparator.compare(currentValue, valueToRemove);
            if (compResult == 0) {
                System.out.println("founded node to remove...");
                current.data = null;
                Node<E> oldRoot = root;
                root = null;
                size = 0;
                List<E> elements = new LinkedList<E>();
                addElementFromNodeToListrecursively(oldRoot, elements);
                System.out.println(elements);
                Collections.shuffle(elements);
                elements.forEach(this::add);
                System.out.println("size = " + size);
                return true;
            }
            if (compResult > 0) {
                if (current.left != null) {
                    current = current.left;
                    continue;
                } else {
                    return false;
                }
            } else {
                if (current.right != null) {
                    current = current.right;
                    continue;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private void addElementFromNodeToListrecursively(Node<E> current, List<E> elements) {
        if (current != null) {
            if (current.data != null) {
                elements.add(current.data);
            }
            addElementFromNodeToListrecursively(current.right, elements);
            addElementFromNodeToListrecursively(current.left, elements);

        }
    }

    /*

        // this implementation has a problem. Smaller El can get to right side after removing el above and greater
        // Solution: After removing balancing is necessary!!!

        private void removeNode(Node<E> current) {
            System.out.println("START DELETING. Begining iteration on last of tree");
            while (current != null) {
                System.out.println("current = " + current.data);
                if (current.right != null) {
                    System.out.println("right is not null, copying its value to current. ");
                    current.data = current.right.data;
                    System.out.println("now current = "+current.data);
                    current = current.right;
                    System.out.println("going right....");
                } else if (current.left != null) {
                    System.out.println("right=null, left is not null, copying its value to current. ");
                    current.data = current.left.data;
                    System.out.println("now current = "+current.data);
                    current = current.left;
                    System.out.println("going left....");
                } else {
                    System.out.println("right=null, left=null, deleting this node");
                    Node<E> parent = current.parent;
                    if (parent!=null) {
                        if (parent.left==current){
                            parent.left=null;
                        } else                     if (parent.right==current){
                            parent.right=null;
                        } else {
                            throw new RuntimeException("unexpected variant of else if cases");
                        }
                    }
                    current = null;
                }
            }
        }
    */
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
        size = 0;
        root = null;
    }
}
