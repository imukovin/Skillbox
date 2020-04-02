package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import single_linked_list.ListItem;
import single_linked_list.SingleLinkedList;

import java.util.NoSuchElementException;

public class SingleLinkedListTest {
    private SingleLinkedList<String> listTest;

    @Before
    public void initTest() {
        listTest = new SingleLinkedList<>();
    }

    @After
    public void afterTest() {
        listTest = null;
    }

    @Test
    public void testPush() {
        int expectedSize = 3;
        listTest.push(new ListItem<String>("All"));
        listTest.push(new ListItem<String>("Alll"));
        listTest.push(new ListItem<String>("Allll"));
        assertEquals(expectedSize, listTest.getSize());
    }

    @Test
    public void testPop() {
        int expectedSize = 1;
        listTest.push(new ListItem<String>("All"));
        listTest.push(new ListItem<String>("Alll"));
        listTest.pop();
        assertEquals(expectedSize, listTest.getSize());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveTop() {
        int expectedSize = 0;
        listTest.push(new ListItem<String>("Alll"));
        listTest.removeTop();
        assertEquals(expectedSize, listTest.getSize());

        listTest.removeTop();
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveLast() {
        listTest = new SingleLinkedList<>();
        listTest.push(new ListItem<String>("All"));
        listTest.push(new ListItem<String>("Alll"));
        listTest.push(new ListItem<String>("Allll"));
        listTest.push(new ListItem<String>("Alllll"));
        for (int i = 3; i >= 0; i--) {
            listTest.removeLast();
            assertEquals(i, listTest.getSize());
        }
        listTest.removeLast();
        Assert.fail();
    }
}
