package single_linked_list;

public class ListItem<T>
{
    private T data;
    private ListItem<T> next;

    public ListItem(T data)
    {
        this.data = data;
    }

    public T getData()
    {
        return data;
    }

    public void setNext(ListItem<T> item)
    {
        next = item;
    }

    public ListItem<T> getNext()
    {
        return next;
    }
}