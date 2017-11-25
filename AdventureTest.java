import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AdventureTest {
	public static void main(String[] args) {
		
		/*Change the boolean to switch mode*/
		Adventure.SG_testingmode = //true;
				false;
		AdvCommand.SG_testing=// true;
				false;
		AdvMotionCommand.SG_testing =//true;
				false;
		
		System.out.println("\n\nTesting Small\n\n");
		testSmallAdventure();
		System.out.println("\n\nTesting Crowther\n\n");
		testCrowtherAdventure();
	}

	public static void testSmallAdventure() {
		try {
			System.out.println("________________TEST INITIATED NOW________________");
			System.out.println("________________IMPORTING COMMANDS FROM THE FILE SmallTest.txt____________");
			System.out.println("These are the input of the console window.");
			Scanner scan = new Scanner(new File("SmallTest.txt"));
			Adventure.setScanner(scan);
			Adventure.main(null);
		} catch (IOException e) {
			System.out
					.println("To test, place the SmallTest.txt file in your project folder");
		}
	}

	public static void testCrowtherAdventure() {
		try {
			System.out.println("________________TEST INITIATED NOW________________");
			System.out.println("________________IMPORTING COMMANDS FROM THE FILE CrowtherTest.txt____________");
			System.out.println("These are the input of the console window.");
			Scanner scan = new Scanner(new File("CrowtherTest.txt"));
			Adventure.setScanner(scan);
			Adventure.main(null);
		} catch (IOException e) {
			System.out
					.println("To test, place the CrowtherTest.txt file in your project folder");
		}
	}

}
