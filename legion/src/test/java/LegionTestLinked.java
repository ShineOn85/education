import legion.Legion;
import my.list.MyList;
import my.list.array.MyListArray;
import my.list.linked.MyListLinked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LegionTestLinked {
    private Legion legion;
    MyList<Integer> myList = new MyListLinked<>();

    @BeforeEach
    public void createLegion(){
        legion = new Legion();
    }

    @Test
    public void test4(){
        myList.add(8);
        myList.add(13);
        Assertions.assertEquals(myList,legion.kill(13,3,new MyListLinked<>()));
    }
    @Test
    public void test5(){
        myList.add(1);
        myList.add(3);
        myList.add(4);
        myList.add(6);
        Assertions.assertEquals(myList,legion.kill(8,5,new MyListLinked<>()));
    }
    @Test
    public void test6(){
        myList.add(7);
        myList.add(13);
        myList.add(14);
        Assertions.assertEquals(myList,legion.kill(15,4,new MyListLinked<>()));
    }
}
