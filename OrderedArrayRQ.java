import java.io.PrintWriter;
import java.lang.String;

public class OrderedArrayRQ implements Runqueue {

	private Proc[] array;
	private int size;
	private int cap;
	private int startPosition;
    /**
     * Constructs empty queue
     */
    public OrderedArrayRQ() 
	{
        // Implement Me
    	cap = 128;
    	size = 0;
    	array = new Proc[cap];
    	startPosition = 0;

    }  // end of OrderedArrayRQ()


    @Override
    public void enqueue(String procLabel, int vt) 
	{
    	if(size == cap) 
		{
    		cap *= 2;
    		Proc[] new_array = new Proc[cap];
    		for(int i = 0; i < size; ++i) 
			{
    			new_array[i] = array[startPosition + i];
    		}
    		startPosition = 0;
    		array = new_array;
    	} 
		else if (startPosition + size == cap) 
		{
    		for(int i = 0; i < size; ++i) 
			{
    			array[i] = array[startPosition + 1];
    		}
    		startPosition = 0;
    	}
    	 	
    	for(int i = size-1; i >= 0; --i) 
		{
    		if(vt < array[startPosition + i].vt) 
			{
    			array[startPosition + i + 1] = array[startPosition + i];
    		} else 
			{
    			array[startPosition + i + 1] = new Proc(procLabel, vt);
    			++size;
    			return;
    		}
    	}
    	array[startPosition] = new Proc(procLabel, vt);
    	++size;
		
    } // end of enqueue()


    @Override
    public String dequeue() 
	{
        // Implement me
    	if(size == 0) 
		{
    		return "";
    	}
    	String result = array[startPosition].procLabel;
    	array[startPosition] = null;
    	++startPosition;
    	--size;
        return result; // placeholder,modify this
		
    } // end of dequeue()


    @Override
    public boolean findProcess(String procLabel)
	{
        // Implement me
    	for(int i = 0; i < size; ++i) 
		{
    		if(array[startPosition + i].procLabel.equals(procLabel)) 
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
    	for(int i = 0; i < size; ++i) 
		{
    		if(array[startPosition + i].procLabel.equals(procLabel)) 
			{
    			for(int j = i + 1; j < size; ++j) 
				{
    				array[startPosition + j-1].procLabel = array[startPosition + j].procLabel;
    				array[startPosition + j-1].vt = array[startPosition + j].vt;
    			}
    			--size;
    			return true;
    		}
    	}
        return false; // placeholder, modify this
		
    } // end of removeProcess()


    @Override
    public int precedingProcessTime(String procLabel) 
	{
        // Implement me
    	int sum_vt = 0;
    	for(int i = 0; i < size; ++i) 
		{
    		if(array[startPosition + i].procLabel.equals(procLabel)) 
			{
    			return sum_vt;
    		} else 
			{
    			sum_vt += array[startPosition + i].vt;
    		}
    	}
        return -1; // placeholder, modify this
		
    }// end of precedingProcessTime()


    @Override
    public int succeedingProcessTime(String procLabel) 
	{
        // Implement me
    	for(int i = 0; i < size; ++i) 
		{
    		if(array[startPosition + i].procLabel.equals(procLabel)) 
			{
    			int sum_vt = 0;
    			for(int j = i+1; j < size; ++j) 
				{
    				sum_vt += array[startPosition + j].vt;
    			}
    			return sum_vt;
    		}
    	}
        return -1; // placeholder, modify this
		
    } // end of precedingProcessTime()


    @Override
    public void printAllProcesses(PrintWriter os) 
	{
        //Implement me
    	for(int i = 0; i < size; ++i) 
		{
    		if(i != 0) 
			{
    			os.print(' ');
    		}
    		os.print(array[startPosition + i].procLabel);
    	}
    	os.println();
    } // end of printAllProcesses()
    
    private class Proc 
	{
		String procLabel;
    	int vt;
		
    	public Proc() 
		{
    		procLabel = "";
    		vt = -1;
    	}
		
    	public Proc(String procLabel, int vt) 
		{
    		this.procLabel = procLabel;
    		this.vt = vt;
    	}
    	
    }

} // end of class OrderedArrayRQ
