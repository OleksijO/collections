package training.collection;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by user on 25.11.2016.
 */
public class MyTreeSet<E> implements Set<E> {
    int size = 0;
    public Node<E> root = null;

    static class Node<E> {
        Node<E> parent = null;
        Node<E> left = null;
        Node<E> right = null;
        E data;

        public Node(E data, Node<E> parent, Node<E> left, Node<E> right) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.data = data;
        }

        public void assignToParentInsteadMe(Node<E> newNode) {

            if (parent.left == this) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
            if (newNode != null) {
                newNode.parent = parent;
            }

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

                if (current != root) {
                    if (current.right == null) {
                        current.assignToParentInsteadMe(current.left);
                    } else if (current.left == null) {
                        current.assignToParentInsteadMe(current.right);
                    } else {
                        Node<E> curr = current.right;
                        while (curr != null) {
                            if (curr.left != null) {
                                curr = curr.left;
                            } else {
                                current.data = curr.data;
                                curr.assignToParentInsteadMe(curr.right);
                                break;
                            }
                        }
                    }
                } else {
                    if (current.left == null && current.right == null) {
                        root = null;
                    } else if (current.right == null) {
                        root = current.left;
                        root.parent = null;
                    } else if (current.left == null) {
                        root = current.right;
                        root.parent = null;
                    } else {
                        Node<E> curr = current.right;
                        while (curr != null) {
                            if (curr.left != null) {
                                curr = curr.left;
                            } else {
                                current.data = curr.data;
                                curr.assignToParentInsteadMe(curr.right);
                                break;
                            }
                        }
                    }
                }
                size--;
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
