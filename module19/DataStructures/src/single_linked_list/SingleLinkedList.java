package single_linked_list;

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
        }
    }

    public void removeLast() {
        ListItem pred = null, current = null;
        current = top;
        while (current.getNext() != null) {
            pred = current;
            current = current.getNext();
        }
        if (pred != null) {
            pred.setNext(null);
        } else {
            top = null;
        }
    }

    public void print() {
        ListItem current = top;
        while (current.getNext() != null) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
        System.out.print(current.getData() + "\n");
    }
}