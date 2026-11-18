import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DeliveryFileSorter 
{
    public static void sortDeliveriesFromCSV(String filename) 
    {
        try {
            // Count lines to determine array size
            int count = 0;
            BufferedReader counter = new BufferedReader(new FileReader(filename));
            while (counter.readLine() != null) 
            {
                count++;
            }
            counter.close();

            if (count == 0) 
            {
                System.out.println("No deliveries found in " + filename);
                return;
            }

            // Load deliveries into array
            DSAHeapEntry[] deliveries = new DSAHeapEntry[count];
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            int index = 0;

            while ((line = br.readLine()) != null) 
            {
                String[] parts = line.split(",", 4);
                if (parts.length == 4) 
                {
                    String customerId = parts[0].trim();
                    int priorityLevel = Integer.parseInt(parts[1].trim());
                    int deliveryTime = Integer.parseInt(parts[2].trim());
                    double score = Double.parseDouble(parts[3].trim());
                    DeliveryRequest req = new DeliveryRequest(customerId, deliveryTime, priorityLevel);
                    deliveries[index] = new DSAHeapEntry((int) score, req);
                    index++;
                }
            }
            br.close();

            // Create a copy for Quick Sort to avoid modifying the original
            DSAHeapEntry[] deliveriesCopy = copyArray(deliveries);

            // Measure and sort with Merge Sort
            long mergeSortStart = System.nanoTime();
            DeliverySorts.mergeSort(deliveries);
            long mergeSortEnd = System.nanoTime();
            double mergeSortTimeMs = (mergeSortEnd - mergeSortStart) / 1_000.0;

            System.out.println("\n=== Deliveries Sorted by Merge Sort ===");
            DeliverySorts.displaySorted(deliveries);

            // Measure and sort with Quick Sort (median-of-three)
            long quickSortStart = System.nanoTime();
            DeliverySorts.quickSortMedian3(deliveriesCopy);
            long quickSortEnd = System.nanoTime();
            double quickSortTimeMs = (quickSortEnd - quickSortStart) / 1_000.0;

            System.out.println("\n=== Deliveries Sorted by Quick Sort (Median-of-Three) ===");
            DeliverySorts.displaySorted(deliveriesCopy);

            // Display execution times
            System.out.println("\n=== Sorting Execution Times ===");
            System.out.printf("%-20s %-10s %10s\n", "Algorithm", "Size", "Time (ms)");
            System.out.println("----------------------------------------");
            System.out.printf("%-20s %-10d %10.3f\n", "MergeSort", count, mergeSortTimeMs);
            System.out.printf("%-20s %-10d %10.3f\n", "QuickSortMedian3", count, quickSortTimeMs);

        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid data format in " + filename + ": " + e.getMessage());
        }
    }

    private static DSAHeapEntry[] copyArray(DSAHeapEntry[] original) 
    {
        DSAHeapEntry[] copy = new DSAHeapEntry[original.length];
        for (int i = 0; i < original.length; i++) 
        {
            if (original[i] != null) 
            {
                DeliveryRequest origReq = (DeliveryRequest) original[i].getValue();
                DeliveryRequest newReq = new DeliveryRequest(origReq.getCustomerId(), origReq.getDeliveryTime(), origReq.getPriorityLevel());
                copy[i] = new DSAHeapEntry(original[i].getPriority(), newReq);
            }
        }
        return copy;
    }
}
