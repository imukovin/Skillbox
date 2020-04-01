package suffix_tree;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private String fragment;
    private ArrayList<Integer> nextNodes;
    private int position;

    public Node() {
        nextNodes = new ArrayList<>();
    }

    public Node(String fragment, int position)
    {
        this.fragment = fragment;
        nextNodes = new ArrayList<>();
        this.position = position;
    }

    public String getFragment()
    {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public List<Integer> getNextNodes()
    {
        return nextNodes;
    }
}