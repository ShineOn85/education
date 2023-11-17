package my.list.array;

import my.list.MyList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;


public class MyListArray<T> implements MyList<T> {
    private int size = 0;
    private Object[] array = new Object[10];

    public MyListArray(int size) {
        array = new Object[size];
    }

    public MyListArray() {
    }

    public boolean add(T t) {
        if (size == array.length)
            array = Arrays.copyOf(array, array.length * 3 / 2);
        array[size] = t;
        size++;
        return true;
    }

    public void add(int index, T t) {
        if (index > size || index < 0) throw new IndexOutOfBoundsException();
        if (array.length > size) {
            System.arraycopy(array, index, array, index + 1, size - index);
            array[index] = t;
        } else {
            Object[] newArray = new Object[array.length * 3 / 2];
            System.arraycopy(array, 0, newArray, 0, index);
            System.arraycopy(array, index, newArray, index + 1, size - index);
            newArray[index] = t;
            array = newArray;
        }
        size++;
    }

    public boolean addAll(MyList<? extends T> anotherArray) {
        if (anotherArray == null) return false;
        System.arraycopy(anotherArray.toArray(), 0, array, size, anotherArray.size());
        size += anotherArray.size();
        return true;
    }

    /*public void remove(int index){
        if (index >= 0 && index < size) {
            System.arraycopy(array, 0, array, 0, index);
            System.arraycopy(array, index + 1, array, index, array.length - index - 1);
            size--;
        }
    }*/

    public boolean remove(int index) {
        if (index < 0 && index > size)
            throw new IndexOutOfBoundsException();

        for (int i = index; i < size - 1; i++)
            array[i] = array[i + 1];
        size--;
        return true;
    }

    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(t))
                remove(i);
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i < size; i++)
            array[i] = null;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 && index >= size)
            throw new IndexOutOfBoundsException();

        return (T) array[index];
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOfRange(array, 0, size));
        /*T[] arrayCopy = (T[]) Arrays.copyOf(array, size);
        return Arrays.toString(arrayCopy);*/
    }


    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> comparator) {
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (comparator.compare((T) array[i], (T) array[j]) > 0) {
                    T temp = (T) array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
    }

    public void trim() {
        Object[] newArray = new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyListArray<?> that = (MyListArray<?>) o;
        this.trim();
        that.trim();
        return size == that.size && Arrays.equals(array, that.array);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }
}
