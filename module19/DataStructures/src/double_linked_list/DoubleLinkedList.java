package double_linked_list;

public class DoubleLinkedList
{
    private ListItem head;
    private ListItem tail;

    public ListItem getHeadElement()
    {
        return head;
    }

    public ListItem getTailElement()
    {
        return tail;
    }

    public ListItem popHeadElement()
    {
        ListItem item = head;
        if (head != null) {
            head = head.getNext();
            if (head != null) {
                head.setPrev(null);
            }
            item.setNext(null);
        }
        return item;
    }

    public ListItem popTailElement()
    {
        ListItem item = tail;
        if (tail != null) {
            tail = tail.getPrev();
            if (tail != null) {
                tail.setNext(null);
            }
            item.setPrev(null);
        }
        return item;
    }

    public void removeHeadElement()
    {
        if (head != null) {
            ListItem next = head.getNext();
            if (next == null) {
                head = null;
                tail = null;
            } else {
                next.setPrev(null);
                head.setNext(null);
                head = next;
            }
        }
    }

    public void removeTailElement()
    {
        if (tail != null) {
            ListItem prev = tail.getPrev();
            if (prev == null) {
                tail = null;
                head = null;
            } else {
                prev.setNext(null);
                tail.setPrev(null);
                tail = prev;
            }
        }
    }

    public void addToHead(ListItem item)
    {
        if (head == null && tail == null) {
            head = item;
            tail = item;
        } else {
            item.setNext(head);
            head.setPrev(item);
            head = item;
        }
    }

    public void addToTail(ListItem item)
    {
        if (head == null && tail == null) {
            head = item;
            tail = item;
        } else {
            item.setPrev(tail);
            tail.setNext(item);
            tail = item;
        }
    }

    public void print() {
        System.out.print("===> All List: ");
        if (head == null || tail == null) {
            System.out.println(" Список пуст! \n");
        } else {
            ListItem current = head;
            while (current.getNext() != null) {
                System.out.print(current.getData() + " ");
                current = current.getNext();
            }
            System.out.print(current.getData() + "\n");
        }
    }
}