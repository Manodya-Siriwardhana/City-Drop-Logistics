public class DSAHashCustomer 
{
    private String id;
    private String name;
    private String address;
    private int priority;
    private String status;

    public DSAHashCustomer(String id, String name, String address, int priority, String status) 
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.priority = priority;
        this.status = status;
    }

    public String getId() 
    { 
        return id; 
    }
    public String getName() 
    { 
        return name; 
    }
    public String getAddress() 
    { 
        return address; 
    }
    public int getPriority() 
    { 
        return priority; 
    }
    public String getStatus() 
    { 
        return status; 
    }
    public void setStatus(String status) 
    { 
        this.status = status; 
    }

    
    public String toString() 
    {
        return String.format("Customer[ID = %s, Name = %s, Address = %s, Priority = %d, Status = %s]",
                id, name, address, priority, status);
    }

    public String toCSV() 
    {
        return id + "," + name + "," + address + "," + priority + "," + status;
    }

    public static DSAHashCustomer fromCSV(String line) 
    {
        String[] parts = line.split(",", 5);
        return new DSAHashCustomer(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4]);
    }
}


