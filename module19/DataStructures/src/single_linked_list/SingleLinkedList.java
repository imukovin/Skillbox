package single_linked_list;

import java.util.NoSuchElementException;

public class SingleLinkedList<T>
{
    private ListItem<T> top;
    private int size;

    public SingleLinkedList() {
        size = 0;
    }

    public void push(ListItem<T> item)
    {
        if(top != null) {
            item.setNext(top);
        }
        top = item;
        size++;
    }

    public T pop()
    {
        ListItem<T> item = top;
        if(top != null)
        {
            top = top.getNext();
            item.setNext(null);
            size--;
        }
        return item.getData();
    }

    public void removeTop()
    {
        if(top != null) {
            top = top.getNext();
            size--;
        } else {
            throw new NoSuchElementException("No such element!");
        }
    }

    public void removeLast() {
        ListItem<T> pred = null, current = top;
        if (top == null) {
            throw new NoSuchElementException("No such element!");
        }
        while (current.getNext() != null) {
            pred = current;
            current = current.getNext();
        }
        if (pred != null) {
            pred.setNext(null);
        } else {
            top = null;
        }
        size--;
    }

    public void print() {
        System.out.print("All list: ");
        ListItem<T> current = top;
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

    public int getSize() {
        return size;
    }
}