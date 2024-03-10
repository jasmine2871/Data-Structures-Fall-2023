import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String[] scanIn = scan.nextLine().split(" ");
		int totalPassengers = Integer.parseInt(scanIn[0]);
		int yourIndexPos = Integer.parseInt(scanIn[1]);
		String[] passengers = scan.nextLine().split(" ");
		Queue<Integer> passengerQueue = new LinkedList<Integer>();
		int yourBoardingNum = Integer.parseInt(passengers[yourIndexPos]);
		// int boardingCount = 0;
		
		// O(N)
		for (int i = 0; i < passengers.length; i++) {
			passengerQueue.add(Integer.parseInt(passengers[i]));
		}
		
		airlineBoarding(passengerQueue, yourBoardingNum, yourIndexPos);
		
		scan.close();

	}
	
	public static void airlineBoarding(Queue<Integer> passengerQueue, int yourBoardingNum, int yourIndexPos) {
	    boolean boarded = false;
	    int currentPassenger = 0;
	    boolean hasLargerBehind = false;
	    int boardingCount = 0;

	    // now I need to keep in track of your location

	    while (!boarded && !passengerQueue.isEmpty()) {
	        currentPassenger = passengerQueue.remove();
	        yourIndexPos -= 1;
	        hasLargerBehind = false;
	        // O(N)
	        
	        // System.out.println("CHECK " + currentPassenger + " FOR LARGER VALUES IN " + passengerQueue);
	        for (int passenger : passengerQueue) {
	            if (passenger > currentPassenger) {
	                hasLargerBehind = true;
	            }
	        }

	        if ((yourIndexPos == -1) && (!hasLargerBehind)) {
	            System.out.println(boardingCount);
	            boarded = true;
	            return;
	        } else if ((yourIndexPos == -1) && (hasLargerBehind)) { 
	        	// if you are current passenger and there are larger ppl behind you
	            passengerQueue.add(currentPassenger);
	            yourIndexPos = passengerQueue.size() - 1;
	        } else if ((hasLargerBehind) && (yourIndexPos >= 0)) { // go to the back of the list
	            passengerQueue.add(currentPassenger);
	        } else {
	        	boardingCount += 1;
	        	// System.out.println("BOARDED: " + currentPassenger);
	        }
	        
//	        System.out.println("YOUR POSITION: " + yourIndexPos);
//	        System.out.println(passengerQueue);
//	        System.out.println("CURRENT PEOPLE BOARDED: " + boardingCount);
//	        System.out.println();
	        
	    }

	    // System.out.println("Error: Could not determine the correct boarding count.");
	}
	
}