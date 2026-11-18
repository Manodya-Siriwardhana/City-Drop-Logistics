public class DSAStacks 
{
    public DSALinkedList stack;

    public DSAStacks()
    {
        stack = new DSALinkedList();
    }

    public void push(Object data)
    {
        stack.insertFirst(data);
    }

    public Object pop()
    {
        return stack.removeFirst();
    }

    public Object peek()
    {
        return stack.peekFirst();
    }


    public boolean isEmpty()
    {
        return stack.isEmpty(); 
    }
}
