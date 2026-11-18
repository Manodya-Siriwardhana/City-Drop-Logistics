public class ParcelScheduler 
{
    private DSAHeap heap;
    private DSAHashTable customerTable;
    private DSAGraphs graph;
    private String warehouse;

    public ParcelScheduler(DSAHashTable customerTable, DSAGraphs graph, String warehouse) 
    {
        this.customerTable = customerTable;
        this.graph = graph;
        this.warehouse = warehouse;
        this.heap = new DSAHeap(50);  // intial size
    }

    public void scheduleDelivery(String customerId, String customerHub) 
    {
        try {
            //  Get customer info
            DSAHashCustomer customer = (DSAHashCustomer) customerTable.get(customerId);
            int priority = customer.getPriority();

            //  Get shortest path from warehouse to customer's hub
            int deliveryTime = graph.getShortestPathTime(warehouse, customerHub);

            // Create DeliveryRequest
            DeliveryRequest req = new DeliveryRequest(customerId, deliveryTime, priority);

            //  Insert into heap using score (as int priority)
            heap.add((int) req.getScore(), req);
            System.out.println("Scheduled: " + req);
        } catch (Exception e) {
            System.out.println("Error scheduling delivery: " + e.getMessage());
        }
    }

    public void processTopRequest() 
    {
        DSAHeapEntry top = heap.remove();
        if (top != null) 
        {
            System.out.println("Processing: " + top.getValue());
        }
    }

    public void showAllRequests() 
    {
        System.out.println("\nCurrent Scheduled Deliveries:");
        heap.display();
    }
}

