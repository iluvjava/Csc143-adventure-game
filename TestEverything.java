import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TestEverything {

	public static void main(String[] args) 
	{
//		regexAnalyzer();
//		System.out.println("-----------");
//		String[] roomnumber = {"7483 "," 3847374 ","3434","sedf ","45j"};
//		for(String s : roomnumber)
//		{
//			isRoomnumber(s);
//		}
//		
//		String[] seperator =
//			{"  ------- ","--","----===","sldkfjs;ldfj  asl;dkfj;ldsjkkdkdkd","-----","--------------","----- -----","------ "};
//		for(String s : seperator)
//		{
//			isSeperator(s);
//		}
//		System.out.println("----------Testing the motiontable method.-----------");
//		String[] testMT = {"WEST       2","DOWN       8/KEYS","   ","------"};
//		for(String s : testMT)
//		{
//			ismotionentry(s);
//		}
//		
//		testadvRoomReadfile_("SmallRooms.txt");
		
		
		//TestEverything.AdventureTest.testTheSettinnUp();
		
		
		
		//TestEverything.AdventureTest.testingReadingCommand();
		TestEverything.AdventureTest.finalTesting();
	}
	
	public static void printout(String s)
	{
		System.out.println(s);
	}

	
	/**
	 * 
	 */
	public static void developAdventureObject()
	{
		
	}
	
	
	public static void regexAnalyzer()
	{
		System.out.println(Pattern.matches("\\w+\\s*$","sadf jhjl"));
		System.out.println(Pattern.matches("[A-Z]+\\s*$","ADSF  "));
	}
	
	public static void isRoomnumber(String s)
	{
		System.out.println(AdvRoom.isRoomNUmber(s));
	}
	
	static void isSeperator(String arg)
	{
		System.out.print("The string:\" "+arg+"\"");
		System.out.println(AdvRoom.isSeperator(arg)?" Is a seperator.":" is not a seperator.");
	}
	
	static void ismotionentry(String s)
	{
		System.out.print("The input string: \""+s+"\"");
		boolean satisfied = AdvRoom.isMotionTable(s);
		System.out.println(satisfied?
				" Satisfied the conditions of a motiontable.":
					" Doesn't satisfied the condtions of a motion entry table.");
		if(satisfied)
		{
			System.out.println("Splitting the string we got: ");
			System.out.println(Arrays.toString(AdvRoom.motionTableSplit(s)));
		}
	}
	
	static void testadvRoomReadfile(String Filename)
	{
		println("--------------advRoom readfile test initiated------------");
		println("file: "+ Filename);
		try
		{
			File f= new File(Filename);
			Scanner sc = new Scanner(f);

			while(sc.hasNextLine())
			{
				println((AdvRoom.readFromFile_(sc)));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void print(Object s)
	{
		System.out.print(s.toString());
	}
	public static void println(Object s)
	{
		print(s+ "\n");
	}
	
	static void testadvRoomReadfile_(String Filename)
	{
		println("--------------AdvRoom Readfile Test Initiated v1.1------------");
		println("file: "+ Filename);
		try
		{
			File f= new File(Filename);
			Scanner sc = new Scanner(f);

			while(sc.hasNextLine())
			{
				println((AdvRoom.readFromFile(sc)));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static class AdventureTest
	{
		public static void testTheSettingUp_part(String s)
		{
			println("Testing the setting up process of all the files. ");
			Scanner[] ca = Adventure.checkFile(s);
			System.out.println(Arrays.toString(ca));
			System.out.println(Adventure.setUpRooms(ca[0]));
			System.out.println(Adventure.setupObjects(ca[1]));
			System.out.println(Adventure.setupSynonyms(ca[2]));
			
			Adventure.setScanner(new Scanner(s));
			println("\n\n\n\n----------------Try to set up the adventure...");
			println(Adventure.getInstance());
		}
		
		public static void testTheSettinnUp()
		{
			testTheSettingUp_part("Small");
			testTheSettingUp_part("Crowther");
		}
		
		
		
		/** 
		 * Test how well it is eading the commands? 
		 */
		public static void testingReadingCommand()
		{
			Adventure.SG_testingmode = true;
			String s = "Small";
			Adventure.setScanner(new Scanner(s));
			Adventure example = Adventure.getInstance();
			Adventure.setScanner(new Scanner(System.in));
			example.CommandAnalyzer("Quit");
			println("The game has been created. ");
			println("Type in the console for more thorough testing...type <q>"
					+ " to quit and enter the next level of testing. ");
			
			Scanner sc = new Scanner(System.in);
			String lalal;
			while(sc.hasNextLine())
			{  	
				lalal=sc.nextLine();
				if(lalal.equals("q"))break;
				example.CommandAnalyzer(lalal);
			}
			
			sc = new Scanner(
					"take\n"
					+"w\n"
					+ "drop\n"
					+ "help\n"
					+ "inventory\n"
					+ "look\n"
					+ "quit\n");
		
			example.SG_testingmode = true;
			String ww;
			while(sc.hasNextLine()&&!(ww=sc.nextLine()).equals("q"))
			{
				example.CommandAnalyzer(ww);
			}
			
			
			
			
		}
		/**
		 *Run the testing verstion of the game. 
		 */
		public static void finalTesting()
		{
			println("Running the game in testing mode!!!! ");
			Adventure.setScanner(new Scanner(System.in));
			Adventure.SG_testingmode = true;
			Adventure.main_();
		}
		
		
		
		
		
	}
	
	
	
	
}
