import java.util.Arrays;
import java.util.Scanner;

// Don't forget to delete package name and rename class to Main
// and have file name be Main.java 


public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int weightCapacity = Integer.parseInt(scan.nextLine());
		int totalHippos = Integer.parseInt(scan.nextLine());
		String[] hippoWeights = scan.nextLine().split(" ");
		
		if (weightCapacity == 0 || totalHippos == 0) {
			System.out.print(0);
		} else {
			int[] hippoWeights_int = new int[totalHippos];
			
			for (int i = 0; i < hippoWeights.length; i++) {
				hippoWeights_int[i] = Integer.parseInt(hippoWeights[i]);
			}
			
			Arrays.sort(hippoWeights_int);
			
//			System.out.println("Hippo Weights: ");
//			for (int hippoLBS : hippoWeights_int) {
//				System.out.println(hippoLBS);
//			}
			
			int danceHippos = dancingHippos(weightCapacity, totalHippos, hippoWeights_int);
			System.out.print(danceHippos);
		}
		
		
		
//		System.out.println("Weight Capacity: " + weightCapacity);
//		System.out.println("Hippos: " + totalHippos);
//		System.out.println("Hippo Weights: ");
//		for (int hippoLBS : hippoWeights_int) {
//			System.out.println(hippoLBS);
//		}
		
		
		
		scan.close();
		// store as BST or an array? Or am I thinking too much..?
		

	}
	
	public static int dancingHippos(int weight, int hippos, int[] hippoWeights) {
		int hippoOnTrampoline = 0;
		int currentCapacity = 0;
		
		for(int i = 0; i < hippoWeights.length; i++) {
			currentCapacity += hippoWeights[i];
			if (currentCapacity > weight) {
				return hippoOnTrampoline;
			} else {
				hippoOnTrampoline += 1;
			}
		}
		
		return hippoOnTrampoline;
	}
	
	
	

}
