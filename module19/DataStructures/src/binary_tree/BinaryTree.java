package binary_tree;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree
{
    private Node root;

    public void addNode(String data)
    {
        if (root == null) {
            root = new Node(data);
        } else {
            Node node = new Node(data);
            Node current = root;
            Node prev = null;
            while (true) {
                prev = current;
                if (data.length() < prev.getData().length()) {
                    current = current.getLeft();
                    if (current == null) {
                        prev.setLeft(node);
                        return;
                    }
                } else {
                    current = current.getRight();
                    if (current == null) {
                        prev.setRight(node);
                        return;
                    }
                }
            }
        }
    }

    public List<Node> searchNodes(String data)
    {
        Node current = root;
        ArrayList<Node> list = new ArrayList<>();
        while (current != null) {
            if (current.getData().length() > data.length()) {
                current = current.getLeft();
            } else if (current.getData().length() == data.length()) {
                list.add(current);
                current = current.getRight();
            } else {
                current = current.getRight();
            }
        }
        return list;
    }

    public void print(Node node, String type) {
        if (node != null) {
            node.printNode(node.getData().length(), type);
            print(node.getLeft(), "left");
            print(node.getRight(), "right");
        }
    }

    public Node getRoot() {
        return root;
    }
}