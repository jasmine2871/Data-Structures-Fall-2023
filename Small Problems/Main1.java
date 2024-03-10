import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// me when I take safe steps *O*
		Scanner scan = new Scanner(System.in);
		int steps = scan.nextInt();
		int useableSteps = scan.nextInt();
		
		// The output should end with a newline
		steps(steps, useableSteps);
		
		scan.close();
	}
	
	public static void steps(int steps, int useableSteps) {
		// need an arg that keeps track of how many 1s we have in the str
		// useableSteps is how many 1s we want in the str
		safeSteps(steps, "", 0, useableSteps);
	}
	
	// recursive weee
	public static void safeSteps(int steps, String str, int count, int useableSteps) {
		// base case should include useableSteps
		// when str has enough useableSteps, print, otherwise do not
		// and keep finding the patterns
		//     * steps != length of str, steps is the amount of chars we need left in str
		if ((steps == 0) && (count == useableSteps)) {
			System.out.println(str);
			return;
		} else if (steps > 0){
			// minus steps to know that str is the length we want
			safeSteps(steps - 1, str + "0", count, useableSteps);
			// add the amount of 1s we want to str
			safeSteps(steps - 1, str + "1", count + 1, useableSteps);
		}
		
		return;
	}

}