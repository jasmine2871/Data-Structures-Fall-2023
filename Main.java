import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		//Map<Integer, List<Integer>> operationRuns = new HashMap<>();
		//ArrayList<Integer> operationRunList = new ArrayList<Integer>();
		Stack<Integer> stack = new Stack<Integer>();
		Queue<Integer> queue = new LinkedList<Integer>();
		// CITATION
		// "((a,b) -> b - a)" max to min from Stack Overflow by apadana
		// https://stackoverflow.com/questions/11003155/change-priorityqueue-to-max-priorityqueue
		PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>((a,b) -> b - a); 
		boolean isStack = true;
		boolean isQueue = true;
		boolean isPriority = true;
		int structureCount = 0;
		
		
		int numOfOperations = Integer.parseInt(scan.nextLine());
		
		for (int i = 0; i < numOfOperations; i++) {
			String[] line = scan.nextLine().split(" ");
			int operation = Integer.parseInt(line[0]);
			int number = 0;
			try {
				number = Integer.parseInt(line[1]);
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("impossible");
			}
			
			if (operation ==  1) { // add to structure
				stack.push(number);
				queue.add(number);
				priorityQueue.add(number);
			} else if (operation == 2) { // remove from structure
				int stackRemove = 0;
				int queueRemove = 0;
				int priQueueRemove = 0;
				
				if (!stack.empty()){
					stackRemove = stack.pop();
				}	
				
				if (!queue.isEmpty()) {
					queueRemove = queue.poll();
				}
				
				if (!priorityQueue.isEmpty()) {
					priQueueRemove = priorityQueue.poll().intValue();
				}
				
				
				if (stackRemove != number) {
					isStack = false;
				} 
				
				if (queueRemove != number) {
					isQueue = false;
				} 
				
				if (priQueueRemove != number) {
					isPriority = false;
				} 
				
//				System.out.println("STACK: " + stackRemove);
//				System.out.println("QUEUE: " + queueRemove);
//				System.out.println("PRI QUEUE: " + priQueueRemove);
				
			}
			
		}
		
		if (isStack) {
			structureCount += 1;
		} 
		
		if (isQueue) {
			structureCount += 1;
		} 
		
		if (isPriority) {
			structureCount += 1;
		}
		
		// System.out.println("STRUCTURE COUNT: " + structureCount);
		
		
		siasBox(structureCount, isStack, isQueue, isPriority);
		
		
		scan.close();

	}
	
	public static void siasBox(int structureCount, boolean stackCheck, boolean queueCheck, boolean priQueueCheck) {
		
		
		if (structureCount == 0) {
			System.out.println("impossible");
			return;
		} else if (structureCount > 1) {
			System.out.println("not sure");
			return;
		} else if (structureCount == 1) {
			// check which one it is
			if (stackCheck) {
				System.out.println("stack");
				return;
			} else if (queueCheck) {
				System.out.println("queue");
				return;
			} else if (priQueueCheck) {
				System.out.println("priority queue");
				return;
			}
		}
		
		
	}
}
