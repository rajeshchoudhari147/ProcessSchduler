import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Experiment 
{
	public static long testDE(Runqueue queue) 
	{
		long startTime, endTime;
		startTime = System.nanoTime();
		while (queue.dequeue() != "") {}
		endTime = System.nanoTime();
		return endTime - startTime;
		//System.out.println("dequeue time for array:" + sumTime);
	}
	
	public static long testPT(Runqueue queue, Set<String> processes) 
	{
		long startTime, endTime;
		startTime = System.nanoTime();
		for(String process : processes) 
		{
			queue.precedingProcessTime(process);
		}
		endTime = System.nanoTime();
		return endTime - startTime;
	}
	
	//A total of 10 groups of commands, for each group, return 3 times
	public static void testEN(ArrayList <HashMap <String, Integer> > commands) 
	{
		long sumTime = 0;
		long sumDETime = 0;
		long sumPTTime = 0;
		long startTime, endTime;
		for(HashMap<String, Integer> lines : commands)
		{
			Runqueue queue = new OrderedArrayRQ();
			startTime = System.nanoTime();
			for(String key : lines.keySet()) 
			{
				queue.enqueue(key, lines.get(key));
			}
			endTime = System.nanoTime();
			sumTime = endTime - startTime;
			sumPTTime = testPT(queue, lines.keySet());
			sumDETime += testDE(queue);
			
		}
		
		System.out.println("Average run time for Array: " + sumTime / commands.size() + 
				" DE Time: " + sumDETime/commands.size() + " PT Time: " + sumPTTime/commands.size());
		
		sumTime = 0;
		sumDETime = 0;
		sumPTTime = 0;
		for(HashMap<String, Integer> lines : commands) 
		{
			Runqueue queue = new OrderedLinkedListRQ();
			startTime = System.nanoTime();
			for(String key : lines.keySet()) 
			{
				queue.enqueue(key, lines.get(key));
			}
			endTime = System.nanoTime();
			sumTime = endTime - startTime;
			sumPTTime = testPT(queue, lines.keySet());
			sumDETime += testDE(queue);
		}
		
		System.out.println("Average run time for Linkedlist: " + sumTime / commands.size() + 
				" DE Time: " + sumDETime/commands.size() + " PT Time: " + sumPTTime/commands.size());		
		sumTime = 0;
		sumDETime = 0;
		sumPTTime = 0;
		for(HashMap<String, Integer> lines : commands)
		{
			Runqueue queue = new BinarySearchTreeRQ();
			startTime = System.nanoTime();
			for(String key : lines.keySet()) 
			{
				queue.enqueue(key, lines.get(key));
			}
			endTime = System.nanoTime();
			sumTime = endTime - startTime;
			sumPTTime = testPT(queue, lines.keySet());
			sumDETime += testDE(queue);
		}
		
		System.out.println("Average run time for BST: " + sumTime / commands.size() + 
				" DE Time:" + sumDETime/commands.size() + " PT Time:" + sumPTTime/commands.size());
	}
	
	public static ArrayList< HashMap<String, Integer> > generateENCommand(ArrayList<String> filename) throws IOException 
	{
		ArrayList< HashMap<String, Integer> > result = new ArrayList<>();
		for(int i = 0; i < filename.size(); ++i) 
		{
			BufferedReader input = new BufferedReader(new FileReader(filename.get(i)));
			HashMap<String, Integer> lines = new HashMap<>();
			String line;
			while((line = input.readLine()) != null) 
			{
				String[] items = line.split(",");
				lines.put(items[0], Integer.parseInt(items[1]));
			}
			input.close();
			result.add(lines);
		}
		return result;
	}
	public static String generateENfile( int n, int round) throws IOException 
	{
		BufferedReader input = new BufferedReader(new FileReader("processes.txt"));
		PrintWriter output = new PrintWriter(new FileWriter("generation/Experiment" + n + "_" + round +".txt"), true);
		ArrayList<String> lines = new ArrayList<>();
		String line;
		while((line = input.readLine()) != null)
		{
			lines.add(line);
		}
		
		input.close();
		Random r = new Random(System.nanoTime());
		for(int i = 0; i < n; ++i) 
		{
			int index = r.nextInt(lines.size());
			output.println(lines.get(index));
			lines.remove(index);
		}
		output.close();
		return "generation/Experiment" + n + "_" + round +".txt";
	}
	
	public static void main(String args[]) throws IOException 
	{
		long startTime=System.currentTimeMillis();  //ms
		int[]  complexityParams = new int[] { 10, 50, 100, 500, 1000, 2000, 5000};
		for(int n : complexityParams) 
		{
			System.out.println("\nStart of Runqueue of size: " + n);
			ArrayList<String> filename = new ArrayList<String>();
			for(int i = 0; i < 20; ++i) 
			{
				filename.add(generateENfile(n, i));
			}
			
			ArrayList< HashMap<String, Integer> > commands = generateENCommand(filename);
			testEN(commands);
		}
		//System.out.println(System.currentTimeMillis() - startTime);
	}
}