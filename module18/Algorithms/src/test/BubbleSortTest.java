package test;

import bubble_sort.BubbleSort;
import org.junit.*;
import static org.junit.Assert.*;

public class BubbleSortTest {
    private static final int NUM_OF_TESTS = 3;
    private int[][] test = {
            {4, 3, 5, 1, 3, 2, 9},
            {45, 8, 78, 1, 0, -5, 45, 77},
            {-1, 0, -77, 155, -3, 2}
    };
    private int[][] answers = {
            {1, 2, 3, 3, 4, 5, 9},
            {-5, 0, 1, 8, 45, 45, 77, 78},
            {-77, -3, -1, 0, 2, 155}
    };

    @Test
    public void testSort() {
        for (int i = 0; i < NUM_OF_TESTS; i++) {
            assertArrayEquals(answers[i], BubbleSort.sort(test[i]));
        }
    }
}
