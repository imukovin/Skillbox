package single_linked_list;

public class ListItem<T>
{
    private T data;
    private ListItem next;

    public ListItem(T data)
    {
        this.data = data;
    }

    public T getData()
    {
        return data;
    }

    public void setNext(ListItem item)
    {
        next = item;
    }

    public ListItem getNext()
    {
        return next;
    }
}