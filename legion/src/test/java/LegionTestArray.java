import legion.Legion;
import my.list.MyList;
import my.list.array.MyListArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static legion.Legion.kill;

public class LegionTestArray {
    MyList<Integer> myList = new MyListArray<>();
    MyList<Integer> actualList = new MyListArray<>();

    @Test
    public void test1(){
        myList.add(8);
        myList.add(13);
        Assertions.assertEquals(myList,kill(13,3,actualList));
    }
    @Test
    public void test2(){
        myList.add(1);
        myList.add(3);
        myList.add(4);
        myList.add(6);
        Assertions.assertEquals(myList, kill(8,5,actualList));
    }
    @Test
    public void test3(){
        myList.add(7);
        myList.add(13);
        myList.add(14);
        Assertions.assertEquals(myList, kill(15,4,actualList));
    }
}
