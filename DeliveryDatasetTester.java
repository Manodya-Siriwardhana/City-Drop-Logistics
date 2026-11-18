public class DeliveryDatasetTester 
{
    public static class SortResult 
    {
        String algorithm;
        int size;
        String condition;
        long timeNano;

        public SortResult(String algorithm, int size, String condition, long timeNano) {
            this.algorithm = algorithm;
            this.size = size;
            this.condition = condition;
            this.timeNano = timeNano;
        }


        public String toString() 
        {
            // Convert nanoseconds to micro seconds with 3 decimal places
            double timeMs = timeNano / 1_000.0;
            return String.format("%-20s %-10d %-20s %10.3f", 
                algorithm, size, condition, timeMs);
        }
    }

     // Runs sorting tests on various dataset sizes and conditions
    public static SortResult[] runTests() 
    {
        int[] sizes = {100, 500, 1000}; // Dataset Sizes
        String[] conditions = {"random", "nearly_sorted", "reversed"}; // Data conditions
        String[] algorithms = {"MergeSort", "QuickSortMedian3"};  // Algorithms to test
        SortResult[] results = new SortResult[sizes.length * conditions.length * algorithms.length];
        int resultIdx = 0;

         // Loop through all combinations
        for (int size : sizes) {
            for (String condition : conditions) 
            {
                DSAHeapEntry[] dataset = generateDataset(size, condition);

                for (String algorithm : algorithms) 
                {
                    DSAHeapEntry[] dataCopy = copyArray(dataset);
                    long startTime = System.nanoTime();

                    if (algorithm.equals("MergeSort")) 
                    {
                        DeliverySorts.mergeSort(dataCopy);
                    } else if (algorithm.equals("QuickSortMedian3")) 
                    {
                        DeliverySorts.quickSortMedian3(dataCopy);
                    }

                    long endTime = System.nanoTime();
                    results[resultIdx] = new SortResult(algorithm, size, condition, endTime - startTime);
                    resultIdx++;

                    if (!isSorted(dataCopy)) 
                    {
                        System.out.println("Error: " + algorithm + " failed to sort " + condition + " dataset of size " + size);
                    }
                }
            }
        }
        return results;
    }

    // Generates synthetic test data for a given size and condition
    private static DSAHeapEntry[] generateDataset(int size, String type) 
    {
        DSAHeapEntry[] dataset = new DSAHeapEntry[size];

        if (type.equals("random")) 
        {
            // Random delivery times
            for (int i = 0; i < size; i++) 
            {
                int deliveryTime = (int) Math.round(Math.random() * 999) + 1; // 1 to 1000 minutes
                DeliveryRequest req = new DeliveryRequest("CUST" + i, deliveryTime, 3);
                dataset[i] = new DSAHeapEntry((int) req.getScore(), req);
            }
        } else if (type.equals("nearly_sorted")) 
        {
            for (int i = 0; i < size; i++) 
            {
                int deliveryTime = i + 1;
                if (i % 10 == 0 && i > 0) 
                {
                    deliveryTime += (int) Math.round(Math.random() * 10);
                }
                DeliveryRequest req = new DeliveryRequest("CUST" + i, deliveryTime, 3);
                dataset[i] = new DSAHeapEntry((int) req.getScore(), req);
            }
        } else if (type.equals("reversed")) 
        {
             // Descending order
            for (int i = 0; i < size; i++) 
            {
                int deliveryTime = size - i;
                DeliveryRequest req = new DeliveryRequest("CUST" + i, deliveryTime, 3);
                dataset[i] = new DSAHeapEntry((int) req.getScore(), req);
            }
        } else {
            throw new IllegalArgumentException("Invalid dataset type: " + type);
        }
        return dataset;
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
                copy[i] = new DSAHeapEntry(origReq.getPriorityLevel(), newReq);
            }
        }
        return copy;
    }

    // Verifies that the array is sorted by delivery time (ascending order)
    private static boolean isSorted(DSAHeapEntry[] A) 
    {
        for (int i = 0; i < A.length - 1; i++) {
            if (A[i] != null && A[i + 1] != null) 
            {
                DeliveryRequest reqI = (DeliveryRequest) A[i].getValue();
                DeliveryRequest reqIPlus1 = (DeliveryRequest) A[i + 1].getValue();
                if (reqI.getDeliveryTime() > reqIPlus1.getDeliveryTime()) 
                {
                    return false;
                }
            }
        }
        return true;
    }

    // Displays the collected results in a formatted table
    public static void displayResults(SortResult[] results) 
    {
        System.out.println("\n=== Sorting Performance Results ===");

        System.out.printf("%-20s %-10s %-20s %10s\n", 
            "Algorithm", "Size", "Condition", "Time (ms)");
        System.out.println("----------------------------------------------------------------");
        for (SortResult result : results)
        {
            System.out.println(result.toString());
        }
    }
}
