/*
 * File: Adventure.java
 * --------------------
 * This program plays the Adventure game from Assignment #4.
 */

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
/**
 * This class is the main program class for the Adventure game.
 * 
 * The player has an array of object, it can get moved to different room. 
 * read the file ,create the room, read the objects and add it to the room!!!!!!!!!
 * 
 * The command will make the objects to move. 
 * 
 * Use a switch command. 
 * 
 * don't do cases for direction. If the command is not a usual command, it might the direction. 
 * 		please check direction after checking all the other commands. 
 * 		create the command, replace all the words given by the user with a synonyms. 
 * 	The adventure motion command will be created while we are scanning all the rooms in the file. 
 */
public class Adventure{

	// Use this scanner for any console input
	private static Scanner scan = new Scanner(System.in);
	public static boolean SG_testingmode = false;
	public Map<String, AdvObject> G_inventory;
	public Map<Integer, AdvRoom> G_rooms;
	public Map<String, AdvCommand> G_commands;
	public Map<String, String> G_symnonymes;
	public Map<String, AdvObject> G_allobject;
	public AdvRoom G_currentroom;
	/**
	 * Take in a string of command and choose the action the
	 * game should take. 
	 * And it will make a call to exceute the command. 
	 * 
	 * 	1. take in the command
	 * 	2. raise all of them to the upper case, 
	 * 	3. Try to parse it into two parts
	 * 	4. get the corresponding command and adv object if possible. 
	 * 
	 * !!!This is the method that will actually run the
	 * game! important. 
	 * 
	 * 
	 * @param arg
	 */
	void CommandAnalyzer(String arg)
	{
		if(arg == null || arg.isEmpty())return;
		String[] parsedcommand = arg.split("[\\s*]\\.*,*");
		for(int i = 0; i <parsedcommand.length; i++)
		{
			parsedcommand[i] = parsedcommand[i].toUpperCase();
		}
		// Synonyms substitute...
		if(this.G_symnonymes.containsKey(parsedcommand[0]))
		{
			parsedcommand[0] = this.G_symnonymes.get(parsedcommand[0]);
		}
		println("Command analyzer get: ");
		println(Arrays.toString(parsedcommand));
		// check if it is not in the case of any special commands. (motion commands)
		// check if it is any of the spcial commands(commands with some objects)
		AdvCommand advc;
		if((advc=SpecialCommand.getAdvCommand(parsedcommand[0]))!=null)
		{
			println("The command is a valid special command: ");
			println("We have getten the adventure command for it:"+ advc.toString());
		}
		else
		{
			if(this.G_commands.containsKey(parsedcommand[0]))
			{
				advc = this.G_commands.get(parsedcommand[0]);
				println("This command is a valid adventure motion command: "+advc);
			}
			else
			{
				println("It seems to be a invalide command. ");
				System.out.println("This command seems invalide.");
			}
		}
		// We are going to prepare the objects for execution 
		AdvObject exeobj= null;
		if(parsedcommand.length>1)
		{
			String temp ; 
			for(int i =1 ; i<parsedcommand.length; i++)
			{
				temp = parsedcommand[i];
				if(this.G_allobject.containsKey(temp))
				{
					exeobj = this.G_allobject.get(temp);
					break;
				}
			}
			println("I think we have gotton the key: "+ exeobj);
		}
		if(advc!=null)advc.execute(this,exeobj);
	}
	/**
	 * There will be a string as the input parameter
	 * 
	 * 1. is the direction not null or some sort of nonsense
	 * 2. is there such a direction in the current room?
	 * 
	 * 
	 * @param direction
	 *            The string indicating the direction of motion
	 */
	public void executeMotionCommand(String direction) 
	{
		println("ExecuteMotionCommand....");
		
		if(direction ==null || direction.isEmpty())throw new AssertionError(); // what the fuck has happened?
		//check the the room has this diretion
		AdvMotionTableEntry result;
		AdvMotionTableEntry[] compass =this.G_currentroom.getMotionTable();
		// iterate through all possibilities. 
		Boolean alldoesnotmatch = true;
		for(AdvMotionTableEntry s : compass)
		{
			if(s.getDirection().equals(direction))
			{
				if(s.getKeyName()!=null&&this.G_inventory.containsKey(s.getKeyName()))
				{
						int destiny = s.getDestinationRoom();
						this.transportToRoom(this.G_rooms.get(s.getDestinationRoom()));
						alldoesnotmatch= false;
						break;
				}
				if(s.getKeyName()==null)
				{	
					this.transportToRoom(this.G_rooms.get(s.getDestinationRoom()));
					alldoesnotmatch= false;
					break;
				}
			}
		}
		if(alldoesnotmatch)System.out.println("You cannot go there. ");
		
	}
	/**
	 * Implements the QUIT command. This command should ask the user to confirm
	 * the quit request and, if so, should exit from the play method. If not.
	 * the program should continue as usual.
	 */
	public void executeQuitCommand() 
	{
		println("ExecueQuitCommand...");
		//if(!Adventure.SG_testingmode)super.executeQuitCommand(); // Replace with your code
		
		System.out.println("Hard right? Considering quiting? Y/<Any keys>");
		char temp ;
		if(Adventure.scan.hasNextLine()&& !((temp = Adventure.scan.nextLine().toUpperCase().charAt(0))=='Y'))
		{
			println("You decide not to quit");
			return;
		}
		//System.exit(0);
		try {
			System.out.println("Try to exist the game. ");
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	/**
	 * Implements the HELP command. Your code must include some help text for
	 * the user.
	 */
	public void executeHelpCommand() {
		println("ExecutingHelpCommand...");
		println("This is the output of the testing: ");
		System.out.println("It a hard game, but I will illuminate the road for you:");
		for(AdvMotionTableEntry s :this.G_currentroom.getMotionTable())
		{
			String key = s.getKeyName()!=null?": "+s.getKeyName():"";
			System.out.println(s.getDirection()+key);
		}
		
		if(this.G_currentroom.getObjectCount()>0)
		{
			System.out.println("There is something in here:");
			for(int i = 0 ; i<this.G_currentroom.getObjectCount();i++)
			{
				System.out.println(this.G_currentroom.getObject(i).getDescription());
			}
		}
	}
	/**
	 *This method will give the full description of the room when called. 
	 *The description of the current room. 
	 *After looking at the room, the room will be changed to 
	 *a visited room. 
	 */
	public void executeLookCommand() {
		
		println("ExecutingLookCommand...");
		System.out.println(">>>"+this.G_currentroom.getName());
		for(String s : this.G_currentroom.getDescription())
		{
			System.out.println("   "+s);
		}
		this.G_currentroom.setVisited(true);
		this.handleForcedMotion();
	
		
	}
	/**
	 * Print out all the objects in the inventory
	 * 
	 */
	public void executeInventoryCommand() {
		println("ExecutingInventoryCommand...");
		println("This is the testing running result: ");
		System.out.println("Here are all the objects you have: ");
		for(Entry<String ,AdvObject> s : this.G_inventory.entrySet())
		{
			AdvObject temp= s.getValue();
			System.out.println(temp.getName()+": "+temp.getDescription());
		}
	}
	/**
	 * It is possible that it will pass a null object;
	 * or the object is just pain weird....
	 * Or the object is actually in the current room
	 * 
	 * There are three cases or more, let's do each 
	 * of them. 
	 * @param obj
	 *            The AdvObject you want to take
	 */
	public void executeTakeCommand(AdvObject obj) {
		println("ExecutingTakeCommand...");
		println("This is the testing runningresult: ");
		if(obj==null||!this.G_currentroom.containsObject(obj))
		{
			System.out.println("I cannot find the object... ");
		}
		else
		{
			// the object is in the room, remove the object in the 
			// room and add it to the inventory of the player.;
			this.G_currentroom.removeObject(obj);
			this.G_inventory.put(obj.getName(), obj);
			System.out.println(obj.getName()+" is added to inventory. ");
		}
		
	}
	/**
	 * An object that is null or isn't in the inventory will 
	 * be passed to it
	 * @param obj
	 *            The AdvObject you want to drop
	 */
	public void executeDropCommand(AdvObject obj) {
		println("ExecutingDropCommand...");
		println("This is the result from the test running.");
		if(obj==null || !this.G_inventory.containsValue(obj))
		{
			System.out.println
			(obj==null?"I cannot analyze the object you typed":"You don't have this object...");
		}
		else
		{
			this.G_currentroom.addObject(this.G_inventory.remove(obj.getName()));
			System.out.println("The object is dropped to the current location...");
		}
	}
	public void executeForcedCommand() 
	{
		this.handleForcedMotion();
	}

	public String toString()
	{
		boolean ori = Adventure.SG_testingmode;
		Adventure.SG_testingmode |= true;
		String s ="\n---------"+this.getClass()+"--------\n";
		s+= this.G_rooms.toString()+"\n";
		s+= (this.G_commands==null||this.G_commands.isEmpty())?
				"":"Commands Map: "+this.G_commands.toString()+"\n";
		s+= (this.G_symnonymes==null||this.G_symnonymes.isEmpty())?
				"":"Synonyms Map:"+this.G_symnonymes.toString()+"\n";
		s+=(this.G_allobject==null||this.G_allobject.isEmpty())
				?"":"Map of all objects: "+this.G_allobject.toString()+"\n";
		Adventure.SG_testingmode =ori;
		return s;
	}
	/**This non static method will try to setup the
	 * commands in class field if possible
	 *  
	 * 
	 * @return
	 * true or false to indicate the result; 
	 */
	boolean setupCommands()
	{
		if(this.G_rooms==null)return false;
		for(Entry<Integer, AdvRoom> e: this.G_rooms.entrySet())
		{
			AdvMotionTableEntry[] temp = e.getValue().getMotionTable();
			for(AdvMotionTableEntry v : temp)
			{
				this.G_commands.put(v.getDirection().toUpperCase(),new AdvMotionCommand(v.getDirection()));
			}
		}
		return true;
	}
	/**
	 * initiated all the fields to prevent null pointer. 
	 */
	public Adventure()
	{
		this.G_commands = new HashMap<String, AdvCommand>();
		this.G_inventory = new HashMap<String, AdvObject>();
		this.G_symnonymes = new HashMap<String, String>();
		this.G_allobject = new TreeMap<String, AdvObject>();
		
	}
	/**
	 * Does as the name of the function suggest. 
	 * this will take in an object of an advroom 
	 * and try to transport to. 
	 * @param destiny
	 * if the input parameter is null
	 * this method will triggeer the execution of gamever. 
	 */
	private void transportToRoom(AdvRoom destiny)
	{
		
		if(destiny==null){this.executeGameover();return;};
		
		this.G_currentroom = destiny;
		if(this.G_currentroom.hasBeenVisited())
		{System.out.println(this.G_currentroom.getName());this.handleForcedMotion();}
		else
		{this.executeLookCommand();}
		
	}
	/**
	 * Check for the current room and execute forced motion depends on the condition
	 * do nothing if there is not forced motion entry found here. 
	 */
	private void handleForcedMotion()
	{
		for(AdvMotionTableEntry s : this.G_currentroom.getMotionTable())
		{
			if(s.getDirection().equals(SpecialCommand.FORCE.Command))
			{
				// Does this action requires a key?
				if(s.getKeyName()!=null&&this.G_inventory.containsKey(s.getKeyName()))
				{
					this.transportToRoom(this.G_rooms.get(s.getDestinationRoom()));
					break;
				}
				else
				{
					//does need  key to transport 
					this.transportToRoom(this.G_rooms.get(s.getDestinationRoom()));
				}
				break;
			}
		}
	}
	private void executeGameover() 
	{
		System.out.println("Game Completed.");
	}
	private static void println(Object s)
	{
		if(Adventure.SG_testingmode)System.out.println(s);
	}
	/**
	 * 	WHAT WE NEED FOR HOMEWORK IN THIS METHOD:
	 * 1.Ask the user for the name of an adventure game
	 * 2.Read in the data files for the game into an internal data structure
	 * 3.Play the game by reading and executing commands entered by the user.
	 * 
	 * 		My guest of the mechanism: 
	 * 		This main method will create a new instance of itself and it would 
	 * 		somehow control the game through what the game has gotten from
	 *		the console. 
	 *		
	 *		The first thing for this class is to read the file and set up all 
	 *		the date structure; 
	 *
	 *	1. look in the command words-> a hash map. 
	 *@author victo
	 */
	public static void main(String[] args) {
		if(args!=null)for(String s : args)System.out.println(s);
		Adventure.main_();
	}
	@Deprecated
	/**
	 * This is the method that is used under testing condition. 
	 * Don't call it else way, replaced by the main methods. 
	 */
	public static void main_()
	{
		Adventure game ;
		System.out.println("Type in the name of the game to run. ");
		while((game = Adventure.getInstance())==null)System.out.println("loading game failed...");
		game.executeLookCommand();
		while(Adventure.scan.hasNextLine())
		{
			
			game.CommandAnalyzer(Adventure.scan.nextLine());
		}
	}
	/**
	 * Use the input from the user to check whether the files 
	 * for the games has been setted up. 
	 * @return
	 * The scanner that scan the files that exist, null if the file 
	 * doesn't exist. 
	 * null if there are non of all the files have been found. 
	 * @param 
	 * The name or the directory of the file. 
	 * 
	 */
	static Scanner[] checkFile(String filename)
	{
		Scanner [] result = new Scanner[3];
		String[] temp={"Rooms.txt","Objects.txt","Synonyms.txt"}; 	
		byte count = 0 ;
		for(String s : temp)
		{
			File tempfile = new File(filename+s);
			if(tempfile.exists())
			{
				try 
				{
					result[count] = new Scanner(tempfile);
				} catch (FileNotFoundException e) {}
			}
			count++;
		}
		boolean allarenull = true;
		for(Object o : result)
		{
			allarenull&=(o==null);
		}
		if(allarenull)return null;
		return result;
	}
	/**
	 * Ask for the name of the game and return an instance of adventure; 
	 * @return
	 * null if there is something wrong. 
	 */
	public static Adventure getInstance()
	{
		Adventure result = new Adventure();
		while(Adventure.scan.hasNextLine())
		{	
			Scanner[] scanlist;
				if((scanlist = Adventure.checkFile(scan.nextLine()))!=null)
				{
					// start the setting up. 
					byte steps = 0; 
					for(Scanner sc: scanlist)
					{
						switch(steps)
						{
						case 0:
							if((result.G_rooms = Adventure.setUpRooms(sc))==null)return null; // the file is empty. 
							break;
						case 1:
							List<AdvObject>  something;
							if((something=Adventure.setupObjects(sc))!=null)
							{
								for(AdvObject adv : something)
								{
									result.G_rooms.get(adv.getInitialLocation()).addObject(adv);
									result.G_allobject.put(adv.getName().toUpperCase(), adv); // put all objects in a map! 
								}
							}
							break;
						case 2:
							if((result.G_symnonymes = Adventure.setupSynonyms(sc))==null)
								result.G_symnonymes = new TreeMap<String, String>();
							break;
						}
						steps++;
					}
					// The files are checked and we are ready to escape the loop. 
					break;
				};
		}
		if(Adventure.SG_testingmode)/*print something out to indicate the result status. */ ; 
		result.G_currentroom = result.G_rooms.get(1);// set up current room. 
		result.setupCommands();
		return result;
	}
	/**
	 * This method is used only to test the program
	 */
	public static void setScanner(Scanner theScanner) {
		scan = theScanner;
	}
	/**
	 * This method takes in a scanner that has been setted to the room file
	 * 
	 * @param scan
	 * @return
	 * A map of the room number and the room. 
	 * null if there is something wrong. 
	 * It will never return an empty map. 
	 */
	static Map<Integer,AdvRoom> setUpRooms (Scanner scan)
	{
		if(scan==null)return null;
		TreeMap<Integer, AdvRoom> result = new TreeMap<Integer, AdvRoom>();
		while(scan.hasNextLine())
		{
			AdvRoom temp = AdvRoom.readFromFile(scan);
			if(temp!=null)result.put(temp.getRoomNumber(), temp);
		};
		return result.isEmpty()?null:result;
	}
	/**
	 * Input the scanner and the method will return the list 
	 * of AdvObjects if possible;
	 * @return
	 * null if there is something wrong. 
	 */
	static List<AdvObject> setupObjects(Scanner scan)
	{
		if(scan == null)return null; 
		List<AdvObject> result = new ArrayList<AdvObject>();
		while(scan.hasNextLine())result.add(AdvObject.readFromFile(scan));
		while(result.remove(null));
		return result.isEmpty()?null:result;
	}
	/**
	 * Input the scanner and this method will return a map of 
	 * Synonyms if possible
	 * @param sc
	 * @return
	 * null if there is something wrong.
	 */
	static Map<String, String> setupSynonyms(Scanner sc)
	{
		if(sc==null)return null;
		TreeMap<String, String> result = new TreeMap<String, String>();
		String looptemp;
		while(sc.hasNextLine()&&!(looptemp=sc.nextLine()).matches("\\s*"))
		{
			String[] temp = looptemp.split("[\\s*=+]");
			result.put(temp[0].toUpperCase(), temp[1].toUpperCase());
		}
		return result.isEmpty()?null:result;
	}
	/**
	 * Here are all the commands that might not be in the script file. 
	 * Thus they are called the special commands. 
	 * @author victo
	 *
	 */
	public enum SpecialCommand
	{
		QUIT("QUIT", AdvCommand.QUIT),
		INVENTORY("INVENTORY", AdvCommand.INVENTORY),
		TAKE("TAKE", AdvCommand.TAKE),
		DROP("DROP", AdvCommand.DROP),
		LOOK("LOOK",AdvCommand.LOOK),
		HELP("HELP",AdvCommand.HELP),
		FORCE("FORCED", AdvCommand.FORCED);
		SpecialCommand(String s, AdvCommand arg)
		{
			this.G_com = arg;
			this.Command = s; 
		}
		AdvCommand G_com;
		String Command;
		/**
		 * Use this method to get the object you want. 
		 * @return
		 */
		public AdvCommand getAdvCommand()
		{
			return this.G_com;
		}
		/**
		 * If there is a match to the special command, the 
		 * method will return corresponding adventurecommand. 
		 * @param arg
		 * @return
		 * null if there is no match.
		 */
		public static AdvCommand getAdvCommand(String arg)
		{
			for(SpecialCommand sc : SpecialCommand.values())
			{
				if(sc.Command.equals(arg.toUpperCase()))
				{
					return sc.getAdvCommand();
				}
			}
			return null;
		}
	}
}
