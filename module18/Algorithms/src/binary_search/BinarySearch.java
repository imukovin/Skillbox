package binary_search;

import java.util.ArrayList;

public class BinarySearch
{
    private ArrayList<String> list;

    public BinarySearch(ArrayList<String> list)
    {
        this.list = list;
    }

    public int search(String query)
    {
        return search(query, 0, list.size() - 1);
    }

    private int search(String query, int from, int to)
    {
        int middle = (from + to) / 2;
        int compare = list.get(middle).compareTo(query);
        if (compare == 0) {
            return middle;
        } else {
            if (from != to) {
                if (compare < 0) {
                    return search(query, middle + 1, list.size() - 1);
                } else {
                    return search(query, from, middle - 1);
                }
            }
        }
        return -1;
    }
}
