package my.list.linked;

import my.list.MyList;

import java.util.Comparator;
import java.util.Objects;

public class MyListLinked<T> implements MyList<T> {
    private int size;
    private Node firstNode;
    private Node lastNode;

    class Node {
        Node nextNode;
        Node previousNode;
        T t;

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(t, node.t);
        }

        @Override
        public int hashCode() {
            return Objects.hash(t);
        }

        Node(T t) {
            this.t = t;
        }

        T get() {
            return t;
        }
    }

    @Override
    public boolean add(T t) {
        Node node = new Node(t);
        if (firstNode == null) {
            firstNode = node;
            lastNode = firstNode;
        } else {
            lastNode.nextNode = node;
            node.previousNode = lastNode;
            lastNode = node;
        }
        size++;
        return true;
    }

    @Override
    public void add(int index, T t) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addAll(MyList<? extends T> anotherList) {
        Object[] array = anotherList.toArray();
        for (Object o : array){
            add((T)o);
        }
        return true;
    }

    @Override
    public boolean remove(int index) {
        if (index > size - 1 || index < 0)
            throw new IndexOutOfBoundsException();

        if (index == 0) {
            firstNode = firstNode.nextNode;
            size--;
            return true;
        }
        if (index == size - 1){
            Node n = lastNode.previousNode;
            n.nextNode = null;
            lastNode = n;

        }
        Node n;
        if (size / 2 >= index) {
            n = firstNode;
            for (int i = 0; i < index - 1; i++) {
                n = n.nextNode;
            }
        } else {
            n = lastNode;
            for (int i = size; i > index; i--) {
                n = n.previousNode;
            }
        }
        n.nextNode = n.nextNode.nextNode;
        if (n.nextNode != null)
            n.nextNode.previousNode = n;
        size--;
        return true;
    }

    @Override
    public boolean remove(T t) {
        Node n = firstNode;
        for (int i = 0; i < size; i++) {
            if (n.get().equals(t)) {
                remove(i);
                return true;
            }
            n = n.nextNode;
        }
        return false;


    }

    @Override
    public T get(int index) {
        if (firstNode == null || index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Такого элемента не существует");
        }
        Node n = firstNode;
        for (int i = 0; i < index; i++) {
            n = n.nextNode;
        }
        return n.t;
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
    public void clear() {
        Node n = firstNode;
        for (int i = 0; i < size; i++) {
            Node next = n.nextNode;
            n.t = null;
            n.previousNode = null;
            n.nextNode = null;
            n = next;
        }
        size = 0;
    }

    @Override
    public void sort(Comparator<? super T> comparator) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyListLinked<?> that = (MyListLinked<?>) o;
        return size == that.size && Objects.equals(firstNode, that.firstNode) && Objects.equals(lastNode, that.lastNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, firstNode, lastNode);
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node n = firstNode;
        for (int i = 0; i < size; i++) {
            array[i] = n.get();
            n = n.nextNode;
        }
        return array;
    }

    @Override
    public String toString() {
        if (this.isEmpty()) return "[]";
        StringBuilder list = new StringBuilder("[");
        Node node = firstNode;
        for (int i = 0; i < size; i++) {
            list.append(node == null ? "null" : node.t).append(", ");
            node = node.nextNode;
        }
        return list.toString().trim().replaceAll(",$", "") + ']';
    }
}
