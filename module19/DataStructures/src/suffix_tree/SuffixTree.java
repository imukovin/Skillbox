package suffix_tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuffixTree
{
    private String text;
    private ArrayList<Node> nodes;
    private Node root;
    ArrayList<Integer> positions = new ArrayList<>();

    public SuffixTree(String text)
    {
        this.text = text;
        nodes = new ArrayList<>();
        build();
    }

    private void build()
    {
        text = text + "$";
        nodes.add(new Node());
        for (int i = 0; i < text.length(); i++) {
            addSuffix(text.substring(i), i);
        }
    }

    private void addSuffix(String suf, int pos) {
        int n = 0;
        int i = 0;
        while (i < suf.length()) {
            char b = suf.charAt(i);
            List<Integer> children = nodes.get(n).getNextNodes();
            int x2 = 0;
            int n2;
            while (true) {
                if (x2 == children.size()) {
                    n2 = nodes.size();
                    Node temp = new Node();
                    temp.setFragment(suf.substring(i));
                    temp.setPosition(pos);
                    nodes.add(temp);
                    children.add(n2);
                    return;
                }
                n2 = children.get(x2);
                if (nodes.get(n2).getFragment().charAt(0) == b) break;
                x2++;
            }
            String sub2 = nodes.get(n2).getFragment();
            int j = 0;
            while (j < sub2.length()) {
                if (suf.charAt(i + j) != sub2.charAt(j)) {
                    int n3 = n2;
                    n2 = nodes.size();
                    Node temp = new Node();
                    temp.setFragment(sub2.substring(0, j));
                    temp.setPosition(pos);
                    temp.getNextNodes().add(n3);
                    nodes.add(temp);
                    nodes.get(n3).setFragment(sub2.substring(j));
                    nodes.get(n).getNextNodes().set(x2, n2);
                    break;
                }
                j++;
            }
            i += j;
            n = n2;
        }
    }

    public int search(String query)
    {
        List<Integer> children = nodes.get(0).getNextNodes();
        int pos = -1;
        for (int i = 0; i < query.length() - 1; i++) {
            for (int j = 0; j < children.size() - 1; j++) {
                if (!nodes.get(children.get(j)).getFragment().contains("$")) {
                    if (nodes.get(children.get(j)).getFragment().equals(Character.toString(query.charAt(i)))) {
                        if (pos == -1) {
                            pos = nodes.get(children.get(j)).getPosition();
                        }
                        children = nodes.get(children.get(j)).getNextNodes();
                        break;
                    } else {
                        pos = -1;
                    }
                } else {
                    if (nodes.get(children.get(j)).getFragment().replace("$", "").equals(query.substring(i))) {
                        if (pos == -1) {
                            pos = nodes.get(children.get(j)).getPosition();
                        }
                        return pos;
                    }
                    if (nodes.get(children.get(j)).getFragment().charAt(0) == query.substring(i).charAt(0)) {
                        if (pos == -1) {
                            pos = nodes.get(children.get(j)).getPosition();
                        }
                        return pos;
                    }
                }
            }
        }

        return pos;
    }

    public void print() {
        if (nodes.isEmpty()) {
            System.out.println("Дерево пустое!");
            return;
        }
        printTree(0, "");
    }

    private void printTree(int n, String pre) {
        List<Integer> children = nodes.get(n).getNextNodes();
        if (children.isEmpty()) {
            System.out.println("- " + nodes.get(n).getFragment());
            return;
        }
        System.out.println("┐ " + nodes.get(n).getFragment());
        for (int i = 0; i < children.size() - 1; i++) {
            Integer c = children.get(i);
            System.out.print(pre + "├─");
            printTree(c, pre + "│ ");
        }
        System.out.print(pre + "└─");
        printTree(children.get(children.size() - 1), pre + "  ");
    }
}