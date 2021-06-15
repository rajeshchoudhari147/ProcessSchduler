import java.io.PrintWriter;
import java.lang.String;

public class BinarySearchTreeRQ implements Runqueue 
{
	//Root node of the BST
	private Proc root; 
    /**
     * Constructs empty queue
     */
    public BinarySearchTreeRQ() 
	{
        // Implement Me
    	root = null;
    }  // end of BinarySearchTreeRQ()


    /*
     * add the a node into a tree
     * @param node the new node
     * @param parent the root the the tree
     * @return the new root
     * */
    private Proc enqueue(Proc parent, Proc node) 
	{
    	if(parent == null) 
		{
    		return node;
    	}
    	if(node.vt < parent.vt) 
		{
    		if(parent.left == null) 
			{
    			parent.left = node;
    		}
			else 
			{
    		    parent.left = enqueue(parent.left, node);
    		}
    		parent.left.parent = parent;
    		return parent;
    	} 
		else 
		{
    		if(parent.right == null) 
			{
    			parent.right = node;
    		} 
			else 
			{
    			parent.right = enqueue(parent.right, node);
    		}
    		parent.right.parent = parent;
    	}
    	return parent;
    }
	
    @Override
    public void enqueue(String procLabel, int vt) 
	{
        // Implement me	
    	Proc curr = new Proc(procLabel, vt);
    	
    	if(root == null) 
		{
    		root = curr;
    		return;
    	}
    	enqueue(root, curr);
    	
    } // end of enqueue()


    @Override
    public String dequeue() 
	{
        // Implement me
    	if(root == null) 
		{
    		return "";
    	}
    	Proc temp;
    	for(temp = root; temp.left != null; temp = temp.left) 
		{
			
    	}
    	Proc parent = temp.parent;
    	String result = temp.procLabel;
    	
    	if(parent == null) 
		{ // Delete the root node
    		if(root.right != null) 
			{
    			root.right.parent = null;
    		}
    		root = root.right;
    	} 
		else 
		{
    		parent.left = temp.right;
    		if(temp.right != null) 
			{
    			temp.right.parent = parent;
    		}
    	}
		
    	temp.parent = null;
    	temp.left = null;
    	temp.right = null;
        return result; // placeholder, modify this
		
    } // end of dequeue()


    /*
     * Find a process in the tree
     * @param node the root of the tree
     * @param procLabel the label to find
     * @return if found, the node with procLabel, null if not found
     * */
    public Proc findProcess(Proc node, String procLabel) 
	{
    	if(node == null) 
		{
    		return null;
    	}
    	if(node.procLabel.contentEquals(procLabel)) 
		{
    		return node;
    	}
    	Proc left = findProcess(node.left, procLabel);
    	if(left != null) 
		{
    		return left;
    	} 
		else 
		{
    		return findProcess(node.right, procLabel);
    	}
    }
	
    @Override
    public boolean findProcess(String procLabel) 
	{
        // Implement me
    	return findProcess(root, procLabel) != null;
		
    } // end of findProcess()


    @Override
    public boolean removeProcess(String procLabel) 
	{
        // Implement me
    	Proc curr = findProcess(root, procLabel);
    	if(curr == null) 
		{
            return false;
    	}
    	
    	//try to delete curr node
		Proc temp = null;
		if (curr.left != null) 
		{
			// Traverse to the rightmost leaf of the left child
			for (temp = curr.left.right; temp != null && temp.right != null; temp = temp.right) 
			{
				
			}
			if (temp == null) 
			{
				Proc delnode = curr.left;
				curr.procLabel = curr.left.procLabel;
				curr.vt = curr.left.vt;
				curr.left = curr.left.left;
				
				if(curr.left != null) 
				{
					curr.left.parent = curr;
				}
				
				delnode.parent = null;
				delnode.left = null;
				delnode.right = null;
			}
			else 
			{
				curr.procLabel = temp.procLabel;
				curr.vt = temp.vt;
				temp.parent.right = temp.left;
				
				if(temp.left != null) 
				{
					temp.left.parent = temp.parent;
				}
				
				temp.parent = null;
				temp.left = null;
				temp.right = null;
			}
		} 
		else if (curr.right == null)
		{ // Right null, left null
			if(curr.parent == null) 
			{
				root = null;
			} 
			else 
			{
				if(curr.parent.right == curr) 
				{
					curr.parent.right = null;
				} 
				else 
				{
					curr.parent.left = null;
				}
			}
		} 
		else 
		{ // Left null, but the right is not
			if(curr.parent == null) 
			{
				root = curr.right;
				curr.right.parent = null;
			} 
			else 
			{ 
				if(curr.parent.right == curr) 
				{
					curr.parent.right = curr.right;					
				} 
				else 
				{
					curr.parent.left = curr.right;
				}
				curr.right.parent = curr.parent;
			}
		}

		return true;
    } // end of removeProcess()

    private Proc getPrev(Proc node) 
	{
    	Proc prev = null;
        if (node == null)
		{
            return null;
		}

        if (node.left == null) 
		{
            prev = node;
            for (; prev.parent != null && prev.parent.left == prev; prev = prev.parent) 
			{
				
			}
            prev = prev.parent;
        } 
		else 
		{
            prev = node.left;
            for (Proc p = prev.right; p != null; prev = p, p = p.right) 
			{
				
			}
        }
        return prev;
    }

    private Proc getNext(Proc node) 
	{
    	Proc next = null;
    	if (node == null)
		{
            return null;
		}

        if (node.right == null) 
		{
            for (next = node; next.parent != null && next.parent.right == next; next = next.parent) 
			{
				
			}
            next = next.parent;
        } 
		else 
		{
        	next = node.right;
            for (Proc p = next.left; p != null; next = p, p = p.left) 
			{
				
			}
        }
        return next;
    }
    
    @Override
    public int precedingProcessTime(String procLabel) 
	{	
    	Proc curr = findProcess(root, procLabel);
    	if(curr == null) 
		{
    		return -1;
    	}
    	int sum = 0;
    	while(true) { //sum every previous node vt
    		Proc prev = this.getPrev(curr);
    		if(prev == null) 
			{
    			return sum;
    		}
    		sum = sum +	prev.vt;
    		curr = prev;
    	}
		
    } // end of precedingProcessTime()


    @Override
    public int succeedingProcessTime(String procLabel)
	{
        // Implement me
    	Proc curr = findProcess(root, procLabel);
    	if(curr == null) 
		{
    		return -1;
    	}
    	int sum = 0;
    	while(true) 
		{
    		Proc next = this.getNext(curr);
    		if(next == null) 
			{
    			return sum;
    		}
    		sum = sum + next.vt;
    		curr = next;
    	}
		
    } // end of precedingProcessTime()


    private boolean printAllProcesses(Proc node, PrintWriter os, boolean flag) 
	{
    	if(node == null) 
		{
    		return flag;
    	}
    	flag = printAllProcesses(node.left, os, flag);
    	
		if(!flag) 
		{
    		os.print(' ');
    	}
		
    	os.print(node.procLabel);
    	flag = false;
    	printAllProcesses(node.right, os, false);
    	return false;
    }
	
    @Override
    public void printAllProcesses(PrintWriter os) 
	{
        // Implement me
    	printAllProcesses(root, os, true);
    	os.println();
    	
    } // end of printAllProcess()
    
    private class Proc 
	{
		String procLabel;
    	int vt;
    	Proc parent;
    	Proc left; //left child
    	Proc right; //right child
		
    	public Proc(String procLabel, int vt) 
		{
    		this.procLabel = procLabel;
    		this.vt = vt;
    		parent = null;
    		left = null;
    		right = null;
    	}	
    }

} // end of class BinarySearchTreeRQ