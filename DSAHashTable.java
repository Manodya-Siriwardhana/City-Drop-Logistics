/*  Used same HashTable code used in praticals
 * But Modified by: Manodya Siriwardhana (22717311)
*/

import java.io.*;

public class DSAHashTable 
{
    private DSAHashEntry[] hashTable;
    private int count;
    private static final int USED = 1;
    private static final int DELETED = -1;
    private static final int NEVER_USED = 0;
    private static final double UPPER_LOAD_FACTOR = 0.7;
    private static final double LOWER_LOAD_FACTOR = 0.3;

    public DSAHashTable(int size) 
    {
        int actualSize = nextPrime(size);
        hashTable = new DSAHashEntry[actualSize];
        for (int i = 0; i < actualSize; i++) 
        {
            hashTable[i] = new DSAHashEntry();
        }
        count = 0;
    }

    private int hash(String key) 
    {
        int hashVal = 0;
        for (int i = 0; i < key.length(); i++) 
        {
            hashVal = (31 * hashVal + key.charAt(i)) % hashTable.length;
        }
        return hashVal;
    }

    public void put(String key, Object value) 
    {
        if (getLoadFactor() > UPPER_LOAD_FACTOR) 
        {
            resize(nextPrime(hashTable.length * 2));
        }

        int hashIdx = hash(key);
        int origIdx = hashIdx;
        int firstDeleted = -1;
        boolean inserted = false;
        boolean updated = false;
        boolean giveUp = false;

        while (!inserted && !updated && !giveUp) {
            int state = hashTable[hashIdx].getState();
            if (state != USED) {
                if (state == DELETED && firstDeleted == -1) 
                {
                    firstDeleted = hashIdx;
                } else 
                {
                    int insertIdx;
                    if (firstDeleted != -1) 
                    {
                        insertIdx = firstDeleted;
                    } else 
                    {
                        insertIdx = hashIdx;
                    }
                    hashTable[insertIdx] = new DSAHashEntry(key, value);
                    count++;
                    inserted = true;
                }
            } else if (hashTable[hashIdx].getKey().equals(key)) 
            {
                hashTable[hashIdx].setValue(value);
                updated = true;
            }

            if (!inserted && !updated) 
            {
                hashIdx = (hashIdx + 1) % hashTable.length;
                if (hashIdx == origIdx) 
                {
                    giveUp = true;
                }
            }
        }

        if (inserted || updated) {
            saveToCSV("customers.csv");
        } else {
            throw new RuntimeException("Hash table is full");
        }
    }

    public Object get(String key) 
    {
        int idx = findIndex(key);
        if (idx == -1) 
        {
            return null; 
        }
        return hashTable[idx].getValue();
    }

    public boolean remove(String key) 
    {
        int idx = findIndex(key);
        if (idx == -1) 
        {
            return false;
        }
            hashTable[idx].setState(DELETED);
            count--;

            if (getLoadFactor() < LOWER_LOAD_FACTOR && hashTable.length > 11) 
            {
                resize(hashTable.length / 2);
            }
            saveToCSV("customers.csv");
            return true; 
        
    }

    private int findIndex(String key) 
    {
        int hashIdx = hash(key);
        int origIdx = hashIdx;
        boolean giveUp = false;

        while (!giveUp) 
        {
            int state = hashTable[hashIdx].getState();
            if (state == NEVER_USED) 
            {
                return -1;
            } else if (state == USED && hashTable[hashIdx].getKey().equals(key)) 
            {
                return hashIdx;
            } else 
            {
                hashIdx = (hashIdx + 1) % hashTable.length;
                if (hashIdx == origIdx) 
                {
                    giveUp = true;
                }
            }
        }
        return -1;
    }

    private void resize(int newSize) 
    {
        int actualSize = nextPrime(newSize);
        DSAHashEntry[] oldTable = hashTable;
        hashTable = new DSAHashEntry[actualSize];
        count = 0;

        for (int i = 0; i < actualSize; i++) 
        {
            hashTable[i] = new DSAHashEntry();
        }

        for (DSAHashEntry entry : oldTable) 
        {
            if (entry.getState() == USED) 
            {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    private int nextPrime(int n) 
    {
        while (!isPrime(n))
        { 
            n++;
        }
        return n;
    }

    private boolean isPrime(int n) 
    {
        if (n < 2)
        { 
            return false;
        }
        if (n % 2 == 0 && n != 2)
        { 
            return false;
        }
        for (int i = 3; i <= Math.sqrt(n); i += 2) 
        {
            if (n % i == 0)
            { 
                return false;
            }
        }
        return true;
    }

    public double getLoadFactor() 
    {
        return (double) count / hashTable.length;
    }

    public void saveToCSV(String filename) 
    {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) 
        {
            for (DSAHashEntry entry : hashTable) 
            {
                if (entry.getState() == 1) 
                { // 1 = USED
                    // Safe cast: we assume all stored values are Customer objects
                    DSAHashCustomer customer = (DSAHashCustomer) entry.getValue();
                    pw.println(customer.toCSV());
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    public void loadFromCSV(String filename) 
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                DSAHashCustomer customer = DSAHashCustomer.fromCSV(line);
                put(customer.getId(), customer);
            }
        } catch (IOException e) {
            System.err.println("Error reading from CSV: " + e.getMessage());
        }
    }

    public void display() 
    {
        for (int i = 0; i < hashTable.length; i++) 
        {
            DSAHashEntry entry = hashTable[i];
            System.out.print("[" + i + "]: ");
            switch (entry.getState()) 
            {
                case USED:
                    System.out.println(entry.getKey() + " => " + entry.getValue());
                    break;
                case DELETED:
                    System.out.println("<deleted>");
                    break;
                case NEVER_USED:
                    System.out.println("<empty>");
                    break;
            }
        }
    }
}
