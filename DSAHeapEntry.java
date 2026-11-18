public class DSAHeapEntry 
{
    private int priority;
    private Object value;

    public DSAHeapEntry(int inPriority, Object inValue) 
    {
        priority = inPriority;
        value = inValue;
    }

    public int getPriority() 
    {
        return priority;
    }

    public void setPriority(int inPriority) 
    {
        priority = inPriority;
    }

    public Object getValue() 
    {
        return value;
    }

    public void setValue(Object inValue) 
    {
        value = inValue;
    }
}
