/*  Used same Heap code used in praticals
 * But Modified by: Manodya Siriwardhana (22717311)
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DSAHeap 
{
    // Array to store heap entries
    private DSAHeapEntry[] heap;
    // Number of elements currently in the heap
    private int count;

    // Constructor to initialize heap with given size
    public DSAHeap(int initialSize) 
    {
        heap = new DSAHeapEntry[initialSize];
        count = 0;
    }

    // Load heap data from a CSV file
    public void loadFromCSV(String filename) throws IOException
    {
        try 
        {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;

            // Read each line from the CSV
            while ((line = br.readLine()) != null) 
            {
                String[] parts = line.split(",");

                // Check if line has at least priority and value
                if (parts.length >= 2) 
                {
                    int priority = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    this.add(priority, name);
                }
            }
            br.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }


    // Add an element to the heap
    public void add(int priority, Object value) 
    {
        // Expand heap array if it's full
        if (count >= heap.length) 
        {
            expandHeap();
        }
        heap[count] = new DSAHeapEntry(priority, value);
        trickleUp(count);
        count++;
    }

     // Remove the root (highest priority) from the heap
    public DSAHeapEntry remove() 
    {
        if (count == 0) 
        {
            System.out.println("Heap is empty.");
            return null;
        }
        DSAHeapEntry root = heap[0];
        heap[0] = heap[count - 1];
        count--;
        trickleDown(0);
        return root;
    }


    public void display() 
    {
        // Header
        System.out.println("\n=== Scheduled Deliveries ===");
        System.out.printf("%-12s %-10s %-12s %-15s\n",
                "Customer ID", "Priority", "Time ", "Score");
        System.out.println("-------------------------------------------------");

        // Entries
        for (int i = 0; i < count; i++) 
        {
            DeliveryRequest req = (DeliveryRequest) heap[i].getValue();
            System.out.printf("%-12s %-10d %-12d %-15.2f\n",
                    req.getCustomerId(),
                    req.getPriorityLevel(),
                    req.getDeliveryTime(),
                    req.getScore()); // Score is in heap priority
        }
    }

    // Helper method to restore heap order upwards
    private void trickleUp(int index) 
    {
        int curIdx = index;
        int parentIdx = (curIdx - 1) / 2;

        // Swap with parent until heap order is restored
        while (curIdx > 0 && heap[curIdx].getPriority() > heap[parentIdx].getPriority()) 
        {
            DSAHeapEntry temp = heap[parentIdx];
            heap[parentIdx] = heap[curIdx];
            heap[curIdx] = temp;
            curIdx = parentIdx;
            parentIdx = (curIdx - 1) / 2;
        }
    }

    // Helper method to restore heap order downwards
    private void trickleDown(int index) 
    {
        int leftIdx, rightIdx, largeIdx;
        boolean done = false;

        // Loop while node has children
        while (!done && (index < count / 2)) 
        {
            leftIdx = index * 2 + 1;
            rightIdx = leftIdx + 1;      // index * 2 + 2;

            // Determine which child has higher priority
            if (rightIdx < count && heap[rightIdx].getPriority() > heap[leftIdx].getPriority()) 
            {
                largeIdx = rightIdx;
            }
            else
            {
                largeIdx = leftIdx;
            }

            // If child has higher priority, swap
            if (heap[largeIdx].getPriority() > heap[index].getPriority()) 
            {
                DSAHeapEntry temp = heap[index];
                heap[index] = heap[largeIdx];
                heap[largeIdx] = temp;
                index = largeIdx;
            } 
            else 
            {
                done = true;
            }
        }
    }

    // Double the size of the heap array
    private void expandHeap() 
    {
        int newSize = heap.length * 2;
        DSAHeapEntry[] newHeap = new DSAHeapEntry[newSize];
        for (int i = 0; i < heap.length; i++) 
        {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    // Return a copy of the heap array (only the used portion)
    public DSAHeapEntry[] getHeapCopy() 
    {
        DSAHeapEntry[] copy = new DSAHeapEntry[count];
        for (int i = 0; i < count; i++) 
        {
            copy[i] = heap[i];
        }
        return copy;
    }

    // Return the number of elements in the heap
    public int getCount() 
    {
        return count;
    }
} 
