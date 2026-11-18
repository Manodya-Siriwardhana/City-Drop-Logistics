public class DSAQueues 
{
    public DSALinkedList queue;

    public DSAQueues()
    {
        queue = new DSALinkedList(); 
    }

    public void enqueue(Object data)
    {
        queue.insertLast(data);
    }

    public Object dequeue()
    {
        return queue.removeFirst();
    }

    public Object peek()
    {
        return queue.peekFirst();
    }

    public boolean isEmpty()
    {
        return queue.isEmpty(); 
    }
}