package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import single_linked_list.ListItem;
import single_linked_list.SingleLinkedList;

public class SingleLinkedListTest {
    private SingleLinkedList listTest;

    @Before
    public void initTest() {
        listTest = new SingleLinkedList();
    }

    @After
    public void afterTest() {
        listTest = null;
    }

    @Test
    public void testPush() {
        int expectedSize = 3;
        listTest.push(new ListItem("All"));
        listTest.push(new ListItem("Alll"));
        listTest.push(new ListItem("Allll"));
        assertEquals(expectedSize, listTest.getSize());
    }

    @Test
    public void testPop() {
        int expectedSize = 1;
        listTest.push(new ListItem("All"));
        listTest.push(new ListItem("Alll"));
        listTest.pop();
        assertEquals(expectedSize, listTest.getSize());
    }

    @Test
    public void testRemoveTop() {
        int expectedSize = 0;
        listTest.push(new ListItem("Alll"));
        listTest.removeTop();
        assertEquals(expectedSize, listTest.getSize());
    }

    @Test
    public void testRemoveLast() {
        int expectedSize = 2;
        listTest.push(new ListItem("All"));
        listTest.push(new ListItem("Alll"));
        listTest.push(new ListItem("Allll"));
        listTest.push(new ListItem("Alllll"));
        listTest.removeLast();
        listTest.removeLast();
        assertEquals(expectedSize, listTest.getSize());
    }
}
