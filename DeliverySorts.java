/*  Used same Advance Sorting code used in praticals
 * But Modified by: Manodya Siriwardhana (22717311)
*/

public class DeliverySorts 
{
    // Merge Sort for DSAHeapEntry array based on deliveryTime
    public static void mergeSort(DSAHeapEntry[] A) 
    {
        mergeSortRecurse(A, 0, A.length - 1);
    }

    private static void mergeSortRecurse(DSAHeapEntry[] A, int leftIdx, int rightIdx) 
    {
        if (leftIdx < rightIdx) 
        {
            int midIdx = (leftIdx + rightIdx) / 2;
            mergeSortRecurse(A, leftIdx, midIdx);
            mergeSortRecurse(A, midIdx + 1, rightIdx);
            merge(A, leftIdx, midIdx, rightIdx);
        }
    }

    private static void merge(DSAHeapEntry[] A, int leftIdx, int midIdx, int rightIdx) 
    {
        DSAHeapEntry[] temp = new DSAHeapEntry[rightIdx - leftIdx + 1];
        int i = leftIdx, j = midIdx + 1, k = 0;

        while (i <= midIdx && j <= rightIdx) {
            DeliveryRequest reqI = (DeliveryRequest) A[i].getValue();
            DeliveryRequest reqJ = (DeliveryRequest) A[j].getValue();
            if (reqI.getDeliveryTime() <= reqJ.getDeliveryTime()) 
            {
                temp[k] = A[i];
                i++;
                k++;
            } else 
            {
                temp[k] = A[j];
                j++;
                k++;
            }
        }

        while (i <= midIdx) 
        {
            temp[k] = A[i];
            i++;
            k++;
        }
        while (j <= rightIdx) 
        {
            temp[k] = A[j];
            j++;
            k++;
        }

        for (int t = 0; t < temp.length; t++) 
        {
            A[leftIdx + t] = temp[t];
        }
    }

    // Quick Sort with median-of-three pivot for DSAHeapEntry array
    public static void quickSortMedian3(DSAHeapEntry[] A) 
    {
        quickSortMedian3Recurse(A, 0, A.length - 1);
    }

    private static void quickSortMedian3Recurse(DSAHeapEntry[] A, int leftIdx, int rightIdx) 
    {
        if (leftIdx < rightIdx) 
        {
            int midIdx = (leftIdx + rightIdx) / 2;
            int pivotIdx = medianOfThree(A, leftIdx, midIdx, rightIdx);
            int newPivotIdx = doPartitioning(A, leftIdx, rightIdx, pivotIdx);
            quickSortMedian3Recurse(A, leftIdx, newPivotIdx - 1);
            quickSortMedian3Recurse(A, newPivotIdx + 1, rightIdx);
        }
    }

    private static int medianOfThree(DSAHeapEntry[] A, int a, int b, int c) 
    {
        DeliveryRequest reqA = (DeliveryRequest) A[a].getValue();
        DeliveryRequest reqB = (DeliveryRequest) A[b].getValue();
        DeliveryRequest reqC = (DeliveryRequest) A[c].getValue();
        int valA = reqA.getDeliveryTime();
        int valB = reqB.getDeliveryTime();
        int valC = reqC.getDeliveryTime();
        if ((valA < valB && valB < valC) || (valC < valB && valB < valA))
        { 
            return b;
        }
        else if ((valB < valA && valA < valC) || (valC < valA && valA < valB)) 
        {
            return a;
        }
        else return c;
    }

    private static int doPartitioning(DSAHeapEntry[] A, int leftIdx, int rightIdx, int pivotIdx) 
    {
        DeliveryRequest pivotReq = (DeliveryRequest) A[pivotIdx].getValue();
        int pivotVal = pivotReq.getDeliveryTime();
        swap(A, pivotIdx, rightIdx);
        int storeIdx = leftIdx;

        for (int i = leftIdx; i < rightIdx; i++) 
        {
            DeliveryRequest reqI = (DeliveryRequest) A[i].getValue();
            if (reqI.getDeliveryTime() < pivotVal) 
            {
                swap(A, i, storeIdx);
                storeIdx++;
            }
        }

        swap(A, storeIdx, rightIdx);
        return storeIdx;
    }

    // Swap utility for DSAHeapEntry
    public static void swap(DSAHeapEntry[] A, int idx1, int idx2) 
    {
        DSAHeapEntry temp = A[idx1];
        A[idx1] = A[idx2];
        A[idx2] = temp;
    }

    // Display sorted array
    public static void displaySorted(DSAHeapEntry[] A) 
    {
        if (A == null || A.length == 0) 
        {
            System.out.println("No delivery records to display.");
            return;
        }
        for (int i = 0; i < A.length; i++) 
        {
            if (A[i] != null) 
            {
                DeliveryRequest req = (DeliveryRequest) A[i].getValue();
                System.out.println("Customer ID: " + req.getCustomerId() + ", Delivery Time: " + req.getDeliveryTime());
            }
        }
    }
}

