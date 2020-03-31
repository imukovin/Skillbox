package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import single_linked_list.ListItem;
import single_linked_list.SingleLinkedList;

import java.util.Arrays;
import java.util.LinkedList;

public class SingleLinkedListTest {
    private SingleLinkedList listTest;
    private SingleLinkedList listAnswer;

    @Before
    public void initTest() {
        listTest = new SingleLinkedList();
        listAnswer = new SingleLinkedList();
    }

    @After
    public void afterTest() {
        listTest = null;
        listAnswer = null;
    }

    @Test
    public void testPush() {
        LinkedList<String> expectedList = new LinkedList();
        expectedList.addFirst("Alex");
        listTest.push(new ListItem<String>("Alex"));
        assertEquals(expectedList.toString(), listTest.toString());
    }
}
