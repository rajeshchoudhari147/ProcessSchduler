import java.io.PrintWriter;
import java.lang.String;

public class OrderedLinkedListRQ implements Runqueue 
{
	private Proc head;
    /**
     * Constructs empty linked list
     */
    public OrderedLinkedListRQ() 
	{
        // Implement me
    	head = null;

    }  // end of OrderedLinkedList()


    @Override
    public void enqueue(String procLabel, int vt) 
	{
        // Implement me
    	Proc current = new Proc(procLabel, vt);
    	if(head == null) 
		{
    		head = current;
    		return;
    	}
    	Proc prev = null;
    	for(Proc temp = head; temp != null; temp = temp.next) 
		{
    		if(temp.vt <= vt) 
			{
    			prev = temp;
    		} 
			else 
			{
    			current.next = temp;
    			if(prev != null) 
				{
    				prev.next = current;
    			} 
				else 
				{
    				head = current;
    			}
    			return;
    		}
    	}
    	prev.next = current;
		
    } // end of enqueue()


    @Override
    public String dequeue() 
	{
        // Implement me
    	String result = "";
    	if(head != null) 
		{
    		result = head.procLabel;
    		head = head.next;
    	}
        return result; // placeholder, modify this
		
    } // end of dequeue()


    @Override
    public boolean findProcess(String procLabel) 
	{
        // Implement me
    	for(Proc temp = head; temp != null; temp = temp.next) 
		{
    		if(temp.procLabel.equals(procLabel)) 
			{
    			return true;
    		}
    	}
        return false; // placeholder, modify this
		
    } // end of findProcess()


    @Override
    public boolean removeProcess(String procLabel) 
	{
        // Implement me
    	if(head == null) 
		{
    		return false;
    	}
    	
    	Proc prev = null;
    	for(Proc temp = head; temp != null; temp = temp.next) 
		{
    		if(temp.procLabel.equals(procLabel)) 
			{
    			if(prev == null) 
				{ // the find one is the head
    				head = head.next;
    			} 
				else 
				{
    				prev.next = temp.next;
    			}
    			return true;
    		} 
			else 
			{
    			prev = temp;
    		}
    	}
        return false; // placeholder, modify this
		
    } // End of removeProcess()


    @Override
    public int precedingProcessTime(String procLabel) 
	{
        // Implement me
    	int sum = 0;
    	for(Proc temp = head; temp != null; temp = temp.next) 
		{
    		if(temp.procLabel.equals(procLabel)) 
			{
    			return sum;
    		} 
			else 
			{
    			sum += temp.vt;
    		}
    	}
        return -1; // placeholder, modify this
		
    } // end of precedingProcessTime()


    @Override
    public int succeedingProcessTime(String procLabel) 
	{
        // Implement me
    	for(Proc temp = head; temp != null; temp = temp.next) 
		{
    		if(temp.procLabel.equals(procLabel)) 
			{
    			int sum = 0;
    			for(Proc t = temp.next; t != null; t = t.next) 
				{
    				sum += t.vt;
    			}
    			return sum;
    		}
    	}
        return -1; // placeholder, modify this
		
    } // end of precedingProcessTime()


    @Override
    public void printAllProcesses(PrintWriter os) 
	{
        //Implement me
    	for(Proc temp = head; temp != null; temp = temp.next) 
		{
    		if(temp != head) 
			{
    			os.print(' ');
    		}
    		os.print(temp.procLabel);
    	}
    	os.println();

    } // end of printAllProcess()
    
    private class Proc 
	{
		String procLabel;
    	int vt;
    	Proc next;
		
    	public Proc(String procLabel, int vt) 
		{
    		this.procLabel = procLabel;
    		this.vt = vt;
    		this.next = null;
    	}
    }

} // end of class OrderedLinkedListRQ
