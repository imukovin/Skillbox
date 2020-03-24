import array_max_value.ArrayMaxValue;
import binary_search.BinarySearch;
import merge_sort.MergeSort;
import quick_sort.QuickSort;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Loader {
    public static void main(String[] args) {
        System.out.println("ArrayMaxValueRes: " +
                ArrayMaxValue.getMaxValue(new int[]{-1, 0, -4}));

        ArrayList<String> arr = getList();
        int pos = new BinarySearch(arr).search("Ale");
        System.out.println("BinarySearchRes: " + pos);

        int[] a = new int[]{5, 7, 1, 2, 3, 8, 0, 10};
        QuickSort.sort(a);
        System.out.println("QuickSort: " + Arrays.toString(a));

        a = new int[]{5, 7, 1, 2, 3, 8, 0, 10};
        MergeSort.mergeSort(a);
        System.out.println("MergeSort: " + Arrays.toString(a));


    }

    private static ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Ale");
        list.add("Cwert");
        list.add("Frtttt");
        list.add("Gtewd");
        list.add("Ittrtrt");
        list.add("Serttt");
        list.add("Tutu");
        return list;
    }
}
