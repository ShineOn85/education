package my.list;

import java.util.Comparator;
import java.util.List;

public interface MyList<T> {
    boolean add(T t);
    void add(int index, T t);
    boolean addAll(MyList<? extends T> anotherList);
    boolean remove(int index);
    boolean remove(T t);
    T get(int index);
    int size();
    boolean isEmpty();
    void clear();
    void sort(Comparator<? super T> comparator);
    Object[] toArray();

}
