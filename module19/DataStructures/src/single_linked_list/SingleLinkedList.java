package single_linked_list;

import java.util.NoSuchElementException;

public class SingleLinkedList
{
    private ListItem top;

    public void push(ListItem item)
    {
        if(top != null) {
            item.setNext(top);
        }
        top = item;
    }

    public ListItem pop()
    {
        ListItem item = top;
        if(top != null)
        {
            top = top.getNext();
            item.setNext(null);
        }
        return item;
    }

    public void removeTop()
    {
        if(top != null) {
            top = top.getNext();
        } else {
            throw new NoSuchElementException("No such element!");
        }
    }

    public void removeLast() {
        ListItem pred = null, current = top;
        try {
            while (current.getNext() != null) {
                pred = current;
                current = current.getNext();
            }
            if (pred != null) {
                pred.setNext(null);
            } else {
                top = null;
            }
        } catch (NullPointerException ex) {
            throw new NoSuchElementException("No such element!");
        }
    }

    public void print() {
        System.out.print("All list: ");
        ListItem current = top;
        if (current == null) {
            System.out.println("Список пуст!");
        } else {
            while (current.getNext() != null) {
                System.out.print(current.getData() + " ");
                current = current.getNext();
            }
            System.out.print(current.getData() + "\n");
        }
    }

    @Override
    public String toString() {
        ListItem current = top;
        String s = "";
        while (current.getNext() != null) {
            s = s + current.getData() + " " + current.getNext().toString() + " ";
            current = current.getNext();
        }
        //s = s + current.getData() + " " + current.getNext().toString() + " ";

        return s;
    }
}