import legion.Legion;
import my.list.MyList;
import my.list.linked.MyListLinked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LegionTestLinked {

    MyList<Integer> myList = new MyListLinked<>();
    MyList<Integer> actualList = new MyListLinked<>();

    @Test
    public void test4(){
        myList.add(8);
        myList.add(13);
        Assertions.assertEquals(myList,Legion.kill(13,3,actualList));
    }
    @Test
    public void test5(){
        myList.add(1);
        myList.add(3);
        myList.add(4);
        myList.add(6);
        Assertions.assertEquals(myList,Legion.kill(8,5,actualList));
    }
    @Test
    public void test6(){
        myList.add(7);
        myList.add(13);
        myList.add(14);
        Assertions.assertEquals(myList,Legion.kill(15,4,actualList));
    }
}
