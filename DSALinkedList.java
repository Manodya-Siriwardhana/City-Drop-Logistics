/*  Used same LinkedList code used in praticals
 * But Modified by: Manodya Siriwardhana (22717311)
*/
import java.util.NoSuchElementException;

public class DSALinkedList
{
    public class DSAListNode
    {
        public Object value;
        public DSAListNode next;
        public DSAListNode prev;

        public DSAListNode(Object inValue)
        {
            this.value = inValue; 
            this.next = null;
            this.prev = null;
        }
    }

    public DSAListNode head;
    public DSAListNode tail;

    public DSALinkedList()
    {
        head = null;
        tail = null;
    }

    public boolean isEmpty()
    {
        return head == null; 
    }

    public void insertFirst(Object value)
    {
        DSAListNode newNode = new DSAListNode(value);

        if(isEmpty())
        {
            head = newNode;
            tail = newNode;
        }else
        {
            newNode.next = head; 
            head.prev = newNode;
            head = newNode;
        }
    }

    public void insertLast(Object value)
    {
        DSAListNode newNode = new DSAListNode(value);

        if (isEmpty())
        {
            head = newNode;
            tail = newNode;
        }else
        {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public Object peekFirst()
    {
        if (isEmpty()) 
        {
            throw new RuntimeException("List is empty. Cannot peek first.");
        }
        return head.value;
    }

    // peek the last item
    public Object peekLast()
    {
        if (isEmpty()) 
        {
            throw new RuntimeException("List is empty. Cannot peek last.");
        }
        return tail.value;
    }

    public Object removeFirst()
    {
        if (isEmpty()) 
        {
            throw new RuntimeException("List is empty. Cannot remove first item");
        }

        Object nodeValue = head.value;

        if (head == tail) 
        {
            head = null; 
            tail = null;
        } else 
        {
            head = head.next;
            head.prev = null;
        }
        
        return nodeValue;
    }

    public Object removeLast()
    {
        if (isEmpty()) 
        {
            throw new RuntimeException("List is empty. Cannot remove last item");
        }

        Object nodeValue = tail.value;

        if (head == tail) 
        {
            head = tail = null;
        } else 
        {
            tail = tail.prev;
            tail.next = null;
        }
        return nodeValue;
    }

    public void removeNode(Object value) 
    {
        if (head == null) 
        {
            throw new NoSuchElementException("List is empty");
        }
    
        DSAGraphNode targetNode = (DSAGraphNode) value;
    
        // Special case: head
        DSAGraphNode headNode = (DSAGraphNode) head.value;
        if (headNode.getLabel().equals(targetNode.getLabel())) 
        {
            head = head.next;
            if (head == null) 
            {
                tail = null;
            }
            return;
        }
    
        DSAListNode prev = head;
        DSAListNode curr = head.next;
    
        while (curr != null) 
        {
            DSAGraphNode currNode = (DSAGraphNode) curr.value;
            if (currNode.getLabel().equals(targetNode.getLabel())) 
            {
                prev.next = curr.next;
                if (curr == tail) 
                {
                    tail = prev;
                }
                return;
            }
            prev = curr;
            curr = curr.next;
        }
    
        throw new NoSuchElementException("Value not found in list");
    }
    
    
    
    
    public void display() 
    {
        DSAListNode curNode = head;
        System.out.print("List: ");
        while (curNode != null) 
        {
            System.out.print(curNode.value + " ");
            curNode = curNode.next;
        }
        System.out.println();
    }

    public int getCount() 
    {
        int count = 0;
        DSAListNode curr = head;
        while (curr != null) 
        {
            count++;
            curr = curr.next;
        }
        return count;
    }
}