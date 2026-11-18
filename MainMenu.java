import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu 
{
    
    private static final String CUSTOMER_CSV = "customers.csv";
    private static final String DELIVERY_CSV = "deliveries.csv";

    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);

    
        DSAGraphs graph = new DSAGraphs();
        DSAHashTable customerTable = new DSAHashTable(50);
        DSAHeap deliveryHeap = new DSAHeap(50);

        customerTable.loadFromCSV(CUSTOMER_CSV);

        boolean exit = false;
        while (!exit) 
        {
            try 
            {
                System.out.println("\n=== City Drop Logistic MENU ===");
                System.out.println("1. Graph Operations");
                System.out.println("2. Customer Operations");
                System.out.println("3. Delivery Scheduling");
                System.out.println("4. Sorting Operations");
                System.out.println("0. Exit");
                System.out.print("Choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) 
                {
                    case 1:
                        graphSubMenu(sc, graph);
                        break;
                    case 2:
                        customerSubMenu(sc, customerTable);
                        break;
                    case 3:
                        deliverySubMenu(sc, graph, customerTable, deliveryHeap);
                        break;
                    case 4:
                        sortingSubMenu(sc);
                        break;
                    case 0:
                        clearDeliveryCSV("deliveries.csv");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid selection.");
                }
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }
        sc.close();
        System.out.println("Goodbye!");
    }


    // Submenu: Graph Operations
    private static void graphSubMenu(Scanner sc, DSAGraphs graph) 
    {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- GRAPH OPERATIONS ---");
            System.out.println("1. Add Hub");
            System.out.println("2. Add Road (Edge)");
            System.out.println("3. Delete Hub");
            System.out.println("4. Delete Road (Edge)");
            System.out.println("5. Display Graph");
            System.out.println("6. Detect Cycle from Hub");
            System.out.println("7. Run BFS (Show Levels)");
            System.out.println("8. Run DFS (Show Path)");
            System.out.println("9. Display Shortest Path Time");
            System.out.println("10. Load Graph");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) 
            {
                case 1:
                    System.out.print("Enter hub label: ");
                    String node = sc.nextLine();
                    graph.addNode(node);
                    System.out.println("Node " + node + " Added");
                    break;
                case 2:
                    System.out.print("Enter first hub: ");
                    String h1 = sc.nextLine();
                    System.out.print("Enter second hub: ");
                    String h2 = sc.nextLine();
                    System.out.print("Enter travel time: ");
                    int time = sc.nextInt(); 
                    if(time >= 0)
                    {
                        sc.nextLine();
                        graph.addEdge(h1, h2, time);
                        System.out.println("Added edge between " + h1 + " and " + h2 + " time between = " +time);
                    }
                    else
                    {
                        System.out.println("time should be a positive.");
                    }
                    break;
                case 3:
                    System.out.print("Enter hub to delete: ");
                    graph.deleteNode(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Enter first hub: ");
                    String fromHub = sc.nextLine();
                    System.out.print("Enter second hub: ");
                    String toHub = sc.nextLine();
                    graph.deleteEdge(fromHub, toHub);
                break;
                case 5:
                    if (graph.getVertexCount() == 0) 
                    {
                        System.out.println("Graph is empty. Nothing to display.");
                    } else 
                    {
                        graph.displayAsList();
                    }
                    break;
                case 6:
                    System.out.print("Enter hub to check for cycles: ");
                    graph.hasCycleFromNode(sc.nextLine());
                    break;
                case 7:
                    System.out.print("Enter starting hub for BFS: ");
                    graph.breadthFirstSearch(sc.nextLine());
                    break;
                case 8:
                    System.out.print("Enter starting hub for DFS: ");
                    graph.depthFirstSearch(sc.nextLine());
                    break;
                case 9:
                    System.out.print("Enter origin hub: ");
                    String origin = sc.nextLine();
                    System.out.print("Enter destination hub: ");
                    String dest = sc.nextLine();
                    int destime = graph.getShortestPathTime(origin, dest);
                    if(graph.findNode(origin) != null  &&  graph.findNode(dest) != null)
                    {
                        if(destime == 999999)
                        {
                            System.out.println("No path exists between '" + origin + "' and '" + dest + "'.");
                            return;
                        }
                        else
                        {
                            System.out.println("Shortest path time: " +graph.getShortestPathTime(origin, dest));
                            return;
                        }
                    }
                    System.out.println("One or Both nodes not found.");
                    break;
                case 10:
                    loadDefaultGraph(graph);
                    System.out.println("Loded default graph.");
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // Submenu: Customer Operations
    private static void customerSubMenu(Scanner sc, DSAHashTable table) 
    {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- CUSTOMER OPERATIONS ---");
            System.out.println("1. Add Customer");
            System.out.println("2. Find Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. Display All");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) 
            {
                case 1:
                    addCustomerInteractive(sc, table);
                    break;
                case 2:
                    System.out.print("Enter customer ID: ");
                    String searchId = sc.nextLine();
                    Object result = table.get(searchId);
                    
                    if (result == null) 
                    {
                        System.out.println("Customer not found.");
                    } else 
                    {
                        System.out.println(result);
                    }
                    break;

                case 3:
                    System.out.print("Enter customer ID to delete: ");
                    String deleteId = sc.nextLine();
                    if (table.remove(deleteId)) 
                    {
                        System.out.println("Customer deleted.");
                    } else 
                    {
                        System.out.println("Customer ID not found. Nothing deleted.");
                    }
                    break;
                case 4:
                    table.display();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // Submenu: Delivery Scheduling
    private static void deliverySubMenu(Scanner sc, DSAGraphs graph, DSAHashTable table, DSAHeap heap) 
    {
        boolean back = false;
        while (!back) 
        {
            System.out.println("\n--- DELIVERY SCHEDULING ---");
            System.out.println("1. Schedule Delivery");
            System.out.println("2. Process Top Delivery");
            System.out.println("3. Show Scheduled Deliveries");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) 
            {
                case 1:
                    System.out.print("Enter customer ID: ");
                    String custId = sc.nextLine();
                    System.out.print("Enter origin hub: ");
                    String origin = sc.nextLine();
                    System.out.print("Enter destination hub: ");
                    String dest = sc.nextLine();

                    try {
                        if (graph.findNode(origin) == null || graph.findNode(dest) == null) 
                        {
                            System.out.println("One or both hubs not found.");
                        } else if (isCustomerInHeap(heap, custId)) 
                        {
                            System.out.println("Customer already has a scheduled delivery in progress.");
                        } else {
                            int deliveryTime = graph.getShortestPathTime(origin, dest);
                            if(deliveryTime == 999999)
                            {
                                System.out.println("No path exists between '" + origin + "' and '" + dest + "'. Cannot schedule delivery.");
                            }
                            else
                            {
                                DSAHashCustomer customer = (DSAHashCustomer) table.get(custId);
                                int priority = customer.getPriority();

                                DeliveryRequest req = new DeliveryRequest(custId, deliveryTime, priority);
                                heap.add((int) req.getScore(), req);

                                saveDeliveryToCSV(req, "deliveries.csv");

                                System.out.println("Scheduled: " + req);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 2:
                    DSAHeapEntry top = heap.remove();
                    if (top != null) {
                        DeliveryRequest req = (DeliveryRequest) top.getValue();
                        System.out.println("Processing: " + req);

                        //  Update customer status
                        try {
                            DSAHashCustomer c = (DSAHashCustomer) table.get(req.getCustomerId());
                            c.setStatus("Delivered");
                        } catch (Exception e) {
                            System.out.println("Error updating customer status.");
                        }

                        //  Overwrite the CSV with remaining heap entries
                        saveEntireDeliveryHeapToCSV(heap, "deliveries.csv");
                    } else {
                        System.out.println("Heap is empty.");
                    }
                break;


                case 3:
                    System.out.println("Scheduled Deliveries:");
                    heap.display();
                    break;
                case 0:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void sortingSubMenu(Scanner sc) 
    {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- SORTING OPERATIONS ---");
            System.out.println("1. Sort Deliveries from CSV");
            System.out.println("2. Run Sorting Performance Tests");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Sorting deliveries from " + DELIVERY_CSV + "...");
                    DeliveryFileSorter.sortDeliveriesFromCSV(DELIVERY_CSV);
                    break;
                case 2:
                    System.out.println("Running sorting performance tests...");
                    DeliveryDatasetTester.SortResult[] results = DeliveryDatasetTester.runTests();
                    DeliveryDatasetTester.displayResults(results);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // Helper to add a customer
    private static void addCustomerInteractive(Scanner sc, DSAHashTable table) 
    {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Address: ");
        String address = sc.nextLine();

        int priority = 0;
        while (priority < 1 || priority > 5) 
        {
            System.out.print("Enter Priority (1-5): ");
            if (sc.hasNextInt()) 
            {
                priority = sc.nextInt();
            }
            sc.nextLine();
        }

        System.out.print("Enter Delivery Status (Delivered / Delayed / In Transit): ");
        String status = sc.nextLine();

        DSAHashCustomer customer = new DSAHashCustomer(id, name, address, priority, status);
        if(table.get(id) != null)
        {
            System.out.println("Customer ID already exist.");
            return;
        }
        table.put(id, customer);
        System.out.println("Customer added.");
    }

    private static void saveDeliveryToCSV(DeliveryRequest req, String filename) 
    {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename, true))) 
        {
            pw.println(req.getCustomerId() + "," + req.getPriorityLevel() + "," + req.getDeliveryTime() + "," + req.getScore());
        } catch (IOException e) 
        {
            System.out.println("Error saving to deliveries.csv: " + e.getMessage());
        }
    }

    private static void saveEntireDeliveryHeapToCSV(DSAHeap heap, String filename) 
    {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) 
        {
            DSAHeapEntry[] entries = heap.getHeapCopy();
            for (int i = 0; i < entries.length; i++) 
            {
                DeliveryRequest req = (DeliveryRequest) entries[i].getValue();
                pw.println(req.getCustomerId() + "," + req.getPriorityLevel() + "," + req.getDeliveryTime() + "," + req.getScore());
            }
            System.out.println("Updated " + filename + " after processing.");
        } catch (IOException e) {
            System.out.println("Error writing to " + filename + ": " + e.getMessage());
        }
    }

    private static boolean isCustomerInHeap(DSAHeap heap, String customerId) 
    {
        DSAHeapEntry[] entries = heap.getHeapCopy();
        for (int i = 0; i < entries.length; i++) 
        {
            DeliveryRequest req = (DeliveryRequest) entries[i].getValue();
            if (req.getCustomerId().equals(customerId)) 
            {
                return true;
            }
        }
            return false;
    }



    private static void loadDefaultGraph(DSAGraphs graph) 
    {
        try {
            // Add nodes
            graph.addNode("A");
            graph.addNode("B");
            graph.addNode("C");
            graph.addNode("D");
            graph.addNode("E");
            graph.addNode("F");
            graph.addNode("G");
            graph.addNode("H");
            graph.addNode("J");

            // Add edges
            graph.addEdge("A", "B", 3);
            graph.addEdge("A", "C", 5);
            graph.addEdge("B", "D", 4);
            graph.addEdge("B", "G", 2);
            graph.addEdge("C", "E", 4);
            graph.addEdge("D", "F", 3);
            graph.addEdge("E", "F", 6);
            graph.addEdge("G", "D", 7);
            graph.addEdge("F", "H", 5);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An Unexpected Error Occurred");
        }
    }


    private static void clearDeliveryCSV(String filename) 
    {
        try 
        {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            writer.close();  // Overwrites the file with nothing
            System.out.println("Delivery file '" + filename + "' has been cleared.");
        } catch (IOException e) 
        {
            System.out.println("Error clearing delivery file: " + e.getMessage());
        }
    }

}

