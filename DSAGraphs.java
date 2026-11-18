/*  Used same graph code used in praticals
 * But Modified by: Manodya Siriwardhana (22717311)
*/

public class DSAGraphs 
{
    private DSALinkedList vertices;
    private DSALinkedList labels = new DSALinkedList();  // Keeps order of added nodes


    public DSAGraphs()
    {
        vertices = new DSALinkedList();
    }

    public void addNode(String label) 
    {
        if (label == null || findNode(label) != null) 
        {
            System.out.println("Node with label '" + label + "' already exists or is invalid. Skipping.");
        } else 
        {
            vertices.insertLast(new DSAGraphNode(label));
            labels.insertLast(label);
        }
    }

    public void addEdge(String label1, String label2, int weight) 
    {
        DSAGraphNode node1 = findNode(label1);
        DSAGraphNode node2 = findNode(label2);

        if (node1 == null || node2 == null) 
        {
            System.out.println("One or both nodes not found. Cannot add edge.");
        } else if (isNeighbour(node1, label2)) 
        {
            System.out.println("Edge between '" + label1 + "' and '" + label2 + "' already exists. Skipping.");
        } else 
        {
            node1.getEdges().insertLast(new DSAEdge(node2, weight));
            node2.getEdges().insertLast(new DSAEdge(node1, weight));
        }
    }



    private boolean isNeighbour(DSAGraphNode node, String label) 
    {
        DSALinkedList.DSAListNode curr = node.getEdges().head;
        while (curr != null) 
        {
            DSAEdge edge = (DSAEdge) curr.value;
            if (edge.getDestination().getLabel().equals(label)) 
            {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public DSAGraphNode findNode(String label) 
    {
        DSALinkedList.DSAListNode curr = vertices.head;
        while (curr != null) 
        {
            DSAGraphNode node = (DSAGraphNode) curr.value;
            if (node.getLabel().equals(label)) 
            {
                return node;
            }
            curr = curr.next;
        }
        return null;
    }

    public void deleteNode(String label) 
    {
        DSAGraphNode node = findNode(label);
        if (node != null) 
        {
            // Remove edges pointing to the node from other vertices
            DSALinkedList.DSAListNode curr = vertices.head;
            while (curr != null) 
            {
                DSAGraphNode currentNode = (DSAGraphNode) curr.value;

                if (!currentNode.getLabel().equals(label)) 
                {
                    DSALinkedList.DSAListNode adjCurr = currentNode.getEdges().head;
                    DSALinkedList.DSAListNode prevAdj = null;

                    while (adjCurr != null) 
                    {
                        DSAEdge edge = (DSAEdge) adjCurr.value;
                        DSAGraphNode destination = edge.getDestination();
                        DSALinkedList.DSAListNode nextAdj = adjCurr.next;

                        if (destination.getLabel().equals(label)) 
                        {
                            if (prevAdj == null) 
                            {
                                currentNode.getEdges().head = nextAdj;
                            } 
                            else 
                            {
                                prevAdj.next = nextAdj;
                            }
                        } 
                        else 
                        {
                            prevAdj = adjCurr;
                        }
                        adjCurr = nextAdj;
                    }
                }

                curr = curr.next;
            }

            // Remove node from vertices
            vertices.removeNode(node);

            // Remove all label matches from labels list (no break)
            DSALinkedList.DSAListNode labelCurr = labels.head;
            DSALinkedList.DSAListNode prevLabel = null;

            while (labelCurr != null)
            {
                DSALinkedList.DSAListNode nextLabel = labelCurr.next;

                if (labelCurr.value.equals(label))
                {
                    if (prevLabel == null)
                    {
                        labels.head = nextLabel;
                    }
                    else
                    {
                        prevLabel.next = nextLabel;
                    }
                }
                else
                {
                    prevLabel = labelCurr;
                }

                labelCurr = nextLabel;
            }

            System.out.println("Node '" + label + "' deleted.");
        } 
        else 
        {
            System.out.println("Node with label '" + label + "' not found.");
        }
    }



    public void deleteEdge(String label1, String label2) 
    {
        DSAGraphNode node1 = findNode(label1);
        DSAGraphNode node2 = findNode(label2);

        if (node1 == null || node2 == null) 
        {
            System.out.println("One or both nodes not found.");
            return;
        }

        boolean removedFrom1 = false;
        boolean removedFrom2 = false;

        // Remove all edges from node1 to node2
        DSALinkedList.DSAListNode curr = node1.getEdges().head;
        DSALinkedList.DSAListNode prev = null;
        while (curr != null) 
        {
            DSAEdge edge = (DSAEdge) curr.value;
            DSALinkedList.DSAListNode next = curr.next;

            if (edge.getDestination().getLabel().equals(label2)) 
            {
                if (prev == null) 
                {
                    node1.getEdges().head = next;
                } 
                else 
                {
                    prev.next = next;
                }
                removedFrom1 = true;
                // Don't move prev forward because current is removed
            } 
            else 
            {
                prev = curr;
            }
            curr = next;
        }

        // Remove all edges from node2 to node1
        curr = node2.getEdges().head;
        prev = null;
        while (curr != null) 
        {
            DSAEdge edge = (DSAEdge) curr.value;
            DSALinkedList.DSAListNode next = curr.next;

            if (edge.getDestination().getLabel().equals(label1)) 
            {
                if (prev == null) 
                {
                    node2.getEdges().head = next;
                } 
                else 
                {
                    prev.next = next;
                }
                removedFrom2 = true;
            } 
            else 
            {
                prev = curr;
            }
            curr = next;
        }

        if (removedFrom1 && removedFrom2) 
        {
            System.out.println("Edge between '" + label1 + "' and '" + label2 + "' successfully deleted.");
        } 
        else 
        {
            System.out.println("Edge between '" + label1 + "' and '" + label2 + "' not found.");
        }
    }


    
    public void displayAsList()
    {
        // Start with the head of the vertices linked list
        DSALinkedList.DSAListNode curr = vertices.head; 
        while (curr != null)
        {
            // Get the current graph node
            DSAGraphNode node = (DSAGraphNode) curr.value;
            System.out.print(node.getLabel() + " -> ");

            // Get the head of the adjacency (neighbour) list for this node
            DSALinkedList.DSAListNode adjCurr = node.getEdges().head; 
            
            // Loop through all adjacent nodes (neighbours)
            while(adjCurr != null)
            {
                DSAEdge edge = (DSAEdge) adjCurr.value; 
                System.out.print(edge.destination.getLabel() + "(" + edge.weight + "min) ");
                adjCurr = adjCurr.next; 
            }
            System.out.println();
            curr = curr.next;
        }
    }

    public int getVertexCount()
    {
        return vertices.getCount(); // Assuming vertices is a DSALinkedList
    }



    public void breadthFirstSearch(String startLabel) 
    {
        DSAQueues nodeQueue = new DSAQueues();
        DSAQueues levelQueue = new DSAQueues();

        // Reset visited flags
        DSALinkedList.DSAListNode curr = vertices.head;
        while (curr != null) 
        {
            ((DSAGraphNode) curr.value).setVisited(false);
            curr = curr.next;
        }

        DSAGraphNode start = findNode(startLabel);
        if (start == null) 
        {
            System.out.println("Start node '" + startLabel + "' not found.");
            return;
        }

        start.setVisited(true);
        nodeQueue.enqueue(start);
        levelQueue.enqueue(0);

        System.out.println("BFS from '" + startLabel + "':");
        System.out.println("Hub\tLevel");

        while (!nodeQueue.isEmpty()) 
        {
            DSAGraphNode currentNode = (DSAGraphNode) nodeQueue.dequeue();
            int currentLevel = (int) levelQueue.dequeue();

            System.out.println(currentNode.getLabel() + "\t" + currentLevel);

            DSALinkedList sortedAdj = getSortedAdjacencyList(currentNode);
            DSALinkedList.DSAListNode adjCurr = sortedAdj.head;

            while (adjCurr != null) 
            {
                DSAGraphNode neighbor = (DSAGraphNode) adjCurr.value;
                if (!neighbor.getVisited()) 
                {
                    neighbor.setVisited(true);
                    nodeQueue.enqueue(neighbor);
                    levelQueue.enqueue(currentLevel + 1);
                }

                    adjCurr = adjCurr.next;
            }
        }

            // Check for unreachable nodes
            curr = vertices.head;
            while (curr != null) 
            {
                DSAGraphNode node = (DSAGraphNode) curr.value;
                if (!node.getVisited()) 
                {
                    System.out.println(node.getLabel() + "\tUnreachable");
                }
                curr = curr.next;
            }
    }

    private DSALinkedList getSortedAdjacencyList(DSAGraphNode node) 
    {
        int size = 0;
        DSALinkedList.DSAListNode curr = node.getEdges().head;
        while (curr != null) {
            size++;
            curr = curr.next;
        }
        String[] labels = new String[size];
        DSAGraphNode[] nodes = new DSAGraphNode[size];
        int i = 0;
        curr = node.getEdges().head;
        while (curr != null) {
            DSAEdge edge = (DSAEdge) curr.value;
            labels[i] = edge.getDestination().getLabel();
            nodes[i] = edge.getDestination();
            i++;
            curr = curr.next;
        }
        for (i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) 
            {
                if (labels[j].compareTo(labels[j + 1]) > 0) 
                {
                    String tempLabel = labels[j];
                    labels[j] = labels[j + 1];
                    labels[j + 1] = tempLabel;
                    DSAGraphNode tempNode = nodes[j];
                    nodes[j] = nodes[j + 1];
                    nodes[j + 1] = tempNode;
                }
            }
        }
        DSALinkedList sortedList = new DSALinkedList();
        for (i = 0; i < size; i++) 
        {
            sortedList.insertLast(nodes[i]);
        }
        return sortedList;
    }



    public boolean hasCycleFromNode(String startLabel) 
    {
        // Reset visited flags for all nodes
        DSALinkedList.DSAListNode curr = vertices.head;
        while (curr != null) {
            ((DSAGraphNode) curr.value).setVisited(false);
            curr = curr.next;
        }

        // Find start node
        DSAGraphNode startNode = findNode(startLabel);
        if (startNode == null) {
            System.out.println("Node with label '" + startLabel + "' not found.");
            return false;
        }

        DSAStacks pathStack = new DSAStacks();
        DSALinkedList pathList = new DSALinkedList(); // Track path for printing

        if (!startNode.getVisited()) {
            if (dfsCycle(startNode, null, pathStack, pathList,startLabel)) 
            {
                return true; // Cycle found and printed
            }
        }

        System.out.println("No cycles found from node '" + startLabel + "'.");
        return false;
    }




    private boolean dfsCycle(DSAGraphNode currentNode, DSAGraphNode parentNode, DSAStacks pathStack, DSALinkedList pathList, String startLabel)
    {
        currentNode.setVisited(true);
        pathStack.push(currentNode);
        pathList.insertLast(currentNode);

        // Count neighbors first 
        int count = 0;
        DSALinkedList.DSAListNode temp = currentNode.getEdges().head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }

        // Create array and fill with neighbors
        DSAGraphNode[] neighbors = new DSAGraphNode[count];
        int i = 0;
        DSALinkedList.DSAListNode adjCurrent = currentNode.getEdges().head;
        while (adjCurrent != null) {
            DSAEdge edge = (DSAEdge) adjCurrent.value;
            neighbors[i++] = edge.getDestination();
            adjCurrent = adjCurrent.next;
        }

        // Sort neighbors alphabetically
        for (int x = 0; x < neighbors.length - 1; x++) {
            for (int y = x + 1; y < neighbors.length; y++) {
                if (neighbors[x].getLabel().compareTo(neighbors[y].getLabel()) > 0) {
                    DSAGraphNode tempNode = neighbors[x];
                    neighbors[x] = neighbors[y];
                    neighbors[y] = tempNode;
                }
            }
        }

        // DFS traversal in alphabetical order
        for (DSAGraphNode neighbor : neighbors) 
        {
            if (!neighbor.getVisited()) 
            {
                if (dfsCycle(neighbor, currentNode, pathStack, pathList, startLabel)) {
                    return true;
                }
            } else if (neighbor != parentNode && neighbor.getLabel().equals(startLabel)) {
                printCycle(pathList, neighbor);
                return true;
            }
        }

        pathStack.pop();
        pathList.removeLast();
        return false;
    }




    private void printCycle(DSALinkedList pathList, DSAGraphNode startNode)
    {
        System.out.print("Cycle detected: ");

        // Find where the cycle starts 
        DSALinkedList.DSAListNode curr = pathList.head;
        DSALinkedList.DSAListNode cycleStartNode = null;

        while (curr != null)
        {
            if (curr.value == startNode) 
            {
                cycleStartNode = curr; // update pointer to last seen
            }
            curr = curr.next;
        }

        // Print from cycleStartNode to the end
        curr = cycleStartNode;
        while (curr != null)
        {
            DSAGraphNode node = (DSAGraphNode) curr.value;
            System.out.print(node.getLabel() + " -> ");
            curr = curr.next;
        }

        // Close the loop
        System.out.println(startNode.getLabel());
    }



    public int getShortestPathTime(String fromLabel, String toLabel) 
    {
        int INF = 999999;

        // Initialize distances and visited list
        DSALinkedList distances = new DSALinkedList();
        DSALinkedList visited = new DSALinkedList();
        DSALinkedList.DSAListNode curr = vertices.head;

        while (curr != null) 
        {
            DSAGraphNode node = (DSAGraphNode) curr.value;
            int dist;
            if (node.getLabel().equals(fromLabel)) 
            {
                dist = 0;
            } else 
            {
                dist = INF;
            }

            distances.insertLast(new Object[]{node, dist});
            curr = curr.next;
        }

        // Main loop — keep trying to process next closest unvisited node
        boolean finished = false;
        while (!finished) 
        {
            DSAGraphNode u = null;
            int minDist = INF;

            // Find the unvisited node with the smallest distance
            curr = distances.head;
            while (curr != null) 
            {
                Object[] pair = (Object[]) curr.value;
                DSAGraphNode node = (DSAGraphNode) pair[0];
                int dist = (int) pair[1];

                if (!visitedContains(visited, node) && dist < minDist) 
                {
                    u = node;
                    minDist = dist;
                }
                curr = curr.next;
            }

            // If no such node found, end the loop
            if (u == null || minDist == INF) 
            {
                finished = true;
            }
            else
            {
                visited.insertLast(u);

                DSALinkedList adjList = getSortedAdjacencyList(u);
                DSALinkedList.DSAListNode adjCurr = adjList.head;

                while (adjCurr != null) 
                {
                    DSAGraphNode v = (DSAGraphNode) adjCurr.value;
                    int weight = getEdgeWeight(u, v);

                    int distU = getDistance(distances, u);
                    int distV = getDistance(distances, v);

                    if (!visitedContains(visited, v) && distU + weight < distV) 
                    {
                        updateDistance(distances, v, distU + weight);
                    }

                    adjCurr = adjCurr.next;
                }
            }
        }

        // Step 3: Return final distance to destination
        DSAGraphNode target = findNode(toLabel);
        return getDistance(distances, target);
    }


    private boolean visitedContains(DSALinkedList visited, DSAGraphNode node) 
    {
        DSALinkedList.DSAListNode curr = visited.head;
        while (curr != null) 
        {
            if (curr.value == node)
            {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }


    private int getEdgeWeight(DSAGraphNode from, DSAGraphNode to) 
    {
        DSALinkedList.DSAListNode curr = from.getEdges().head;
        while (curr != null) {
            DSAEdge edge = (DSAEdge) curr.value;
            if (edge.getDestination() == to) 
            {
                return edge.getWeight();
            }
            curr = curr.next;
        }
        return 999999;
    }

    private int getDistance(DSALinkedList distances, DSAGraphNode node) 
    {
        DSALinkedList.DSAListNode curr = distances.head;
        while (curr != null) 
        {
            Object[] pair = (Object[]) curr.value;
            if (pair[0] == node) 
            {
                return (int) pair[1];
            }
            curr = curr.next;
        }
        return 999999;
    }

    private void updateDistance(DSALinkedList distances, DSAGraphNode node, int newDist) 
    {
        DSALinkedList.DSAListNode curr = distances.head;
        while (curr != null) {
            Object[] pair = (Object[]) curr.value;
            if (pair[0] == node) 
            {
                pair[1] = newDist;
                return;
            }
            curr = curr.next;
        }
    }



    public void depthFirstSearch(String startLabel) 
    {
        DSAGraphNode start = findNode(startLabel);
        if (start == null) 
        {
            System.out.println("Start node '" + startLabel + "' not found.");
            return;
        }

        // Reset all visited flags
        DSALinkedList.DSAListNode curr = vertices.head;
        while (curr != null) 
        {
            ((DSAGraphNode) curr.value).setVisited(false);
            curr = curr.next;
        }

        DSAStacks stack = new DSAStacks();

        System.out.println("Depth First Traversal from '" + startLabel + "':");

        // Push start node onto stack
        stack.push(start);

        // Traverse until stack is empty
        while (!stack.isEmpty()) 
        {
            DSAGraphNode currentNode = (DSAGraphNode) stack.pop();

            if (!currentNode.getVisited()) 
            {
                currentNode.setVisited(true);
                System.out.print(currentNode.getLabel() + " ");

                // Push neighbors in reverse sorted order
                DSALinkedList sortedAdj = getSortedAdjacencyList(currentNode);
                DSALinkedList.DSAListNode adjCurr = sortedAdj.tail; // start from tail to push in reverse
                while (adjCurr != null) 
                {
                    DSAGraphNode neighbor = (DSAGraphNode) adjCurr.value;
                    if (!neighbor.getVisited()) 
                    {
                        stack.push(neighbor);
                    }
                    adjCurr = adjCurr.prev;
                }
            }
        }

        System.out.println();
    }

}


/* References

 * Dijkstra Algroithmn reference
 * https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/
 * https://www.freecodecamp.org/news/dijkstras-shortest-path-algorithm-visual-introduction/
 * https://youtu.be/bZkzH5x0SKU?feature=shared
 * 
 * DFS Cycle Detection reference 
 * https://www.geeksforgeeks.org/detect-cycle-undirected-graph/
 * https://www.w3schools.com/dsa/dsa_algo_graphs_cycledetection.php
 
*/

