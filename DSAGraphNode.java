// Class representing a node (vertex) in a graph
public class DSAGraphNode
{
    // Fields for the node's label, visited status, and adjacency list
    private String label;   
    private boolean visited;
    private DSALinkedList adjList;

    // Constructor: initializes the node with a label and an empty adjacency list
    public DSAGraphNode(String inLabel) 
    {
        label = inLabel;
        visited = false;
        adjList = new DSALinkedList();
    }

    // Getter method for the label of the node
    public String getLabel() 
    {
        return label;
    }

    // Getter method to check if the node has been visited
    public boolean getVisited()
    {
        return visited;
    }

    // Setter method to mark the node as visited or not
    public void setVisited(boolean v)
    {
        visited = v;
    }

    // Returns the adjacency list containing neighbouring nodes
    public DSALinkedList getEdges()
    {
        return adjList; 
    }
}