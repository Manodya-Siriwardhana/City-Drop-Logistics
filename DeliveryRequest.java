public class DeliveryRequest 
{
    private String customerId;
    private int deliveryTime;
    private int priorityLevel;
    private double score;

    public DeliveryRequest(String customerId, int deliveryTime, int priorityLevel) 
    {
        this.customerId = customerId;
        this.deliveryTime = deliveryTime;
        this.priorityLevel = priorityLevel;
        this.score = computeScore(priorityLevel, deliveryTime);
    }

    private double computeScore(int p, int t) 
    {
        if(t == 0)
        {
            return 0;
        }
        return (6 - p) + 1000.0 / t;
    }

    public double getScore() 
    {
        return score;
    }

    public String getCustomerId() 
    {
        return customerId;
    }

    public int getDeliveryTime() 
    {
        return deliveryTime;
    }

    public int getPriorityLevel() 
    {
        return priorityLevel;
    }


    public String toString() 
    {
        return String.format("CustomerID: %s | P: %d | T: %d | Priority Score: %.2f",
                customerId, priorityLevel, deliveryTime, score);
    }
}

