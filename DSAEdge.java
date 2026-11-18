public class DSAEdge 
{
    public DSAGraphNode destination;
    public int weight;

    public DSAEdge(DSAGraphNode node, int weight) 
    {
        this.destination = node;
        this.weight = weight;
    }

     public DSAGraphNode getDestination() 
     {
        return destination;
     }

    // Setter for destination
    public void setDestination(DSAGraphNode destination) 
    {
        this.destination = destination;
    }

    // Getter for weight
    public int getWeight() 
    {
        return weight;
    }

    // Setter for weight
    public void setWeight(int weight) 
    {
        this.weight = weight;
    }
}