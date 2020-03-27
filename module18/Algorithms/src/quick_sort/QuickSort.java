package quick_sort;

import java.util.Arrays;

public class QuickSort
{
    public static void sort(int[] array)
    {
        if(array.length <= 1) {
            return;
        }
        sort(array, 0, array.length - 1);
    }

    private static void sort(int[] array, int from, int to)
    {
        if(from < to)
        {
            int pivot = partition(array, from, to);
            sort(array, from, pivot - 1);
            sort(array, pivot + 1, to);
        }
    }

    private static int partition(int[] array, int from, int to)
    {
        int pivot = array[from];
        while (from < to) {
            while (array[from] < pivot) {
                from++;
            }
            while (array[to] > pivot) {
                to--;
            }
            if (array[from] != array[to]) {
                int temp = array[from];
                array[from] = array[to];
                array[to] = temp;
            } else {
                to--;
            }
        }
        return from;
    }

}
