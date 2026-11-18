public class DSAHashEntry 
{
    private String key;
    private Object value;
    private int state; // 0 = never used, 1 = used, -1 = formerly used

    public DSAHashEntry() 
    {
        this.key = "";
        this.value = null;
        this.state = 0;
    }

    public DSAHashEntry(String inKey, Object inValue) 
    {
        this.key = inKey;
        this.value = inValue;
        this.state = 1;
    }

    // Getters and setters
    public String getKey() 
    { 
        return key; 
    }
    public Object getValue() 
    { 
        return value; 
    }
    public int getState() 
    { 
        return state; 
    }
    public void setKey(String key) 
    { 
        this.key = key; 
    }
    public void setValue(Object value) 
    { 
        this.value = value; 
    }
    public void setState(int state) 
    { 
        this.state = state; 
    }
}
