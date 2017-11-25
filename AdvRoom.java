/*
 * File: AdvRoom.java
 * ------------------
 * This file defines a class that models a single room in the
 * Adventure game.
 */

import java.io.*;
import java.util.*;

/* Class: AdvRoom */
/**
 * This class defines a single room in the Adventure game. A room is
 * characterized by the following properties:
 * 
 * <ul>
 * <li>A room number, which must be greater than zero
 * <li>Its name, which is a one-line string identifying the room
 * <li>Its description, which is a multiline array describing the room
 * <li>A list of objects contained in the room
 * <li>A flag indicating whether the room has been visited
 * <li>A motion table specifying the exits and where they lead </li>
 * 
 * The external format of the room data file is described in the assignment
 * handout. The comments on the methods exported by this class show how to use
 * the initialized data structure.
 * 
 * 		This class of objects are created in the factory method, the method
 * will gives a scanner of object that take the object txt file as input
 * and we are expect to contain all the information of the object read from the 
 * file into the object file we want. 
 * 
 * 
 * All the information in this class are setted up indirectly by the caller 
 * of this class instance, 
 * 		A class contains: 
 * 			The room number, greater than zero, 
 * 			the name of the room, 
 * 			Adventure objects, which I believe is given by a certain method 
 * 			in this class.  
 * 			
 */

public class AdvRoom{

	
	/**
	 * Returns the room number.
	 * 
	 * @usage int roomNumber = room.getRoomNumber();
	 * @return The room number
	 */
	public int getRoomNumber() {
		
		return this.G_roomnumber;
		//return super.getRoomNumber(); // Replace with your code
	}

	
	/**
	 * Returns the room name, which is its one-line description.
	 * 
	 * @usage String name = room.getName();
	 * @return The room name
	 */
	public String getName() 
	{
		return this.G_roomtitle;
		//return super.getName(); // Replace with your code
	}

	
	/**
	 * Returns an array of strings that correspond to the long description of
	 * the room (including the list of the objects in the room).
	 * 
	 * @usage String[] description = room.getDescription();
	 * @return An array of strings giving the long description of the room
	 */
	public String[] getDescription() 
	{
		return this.G_description.toArray(new String[this.G_description.size()]);
	}

	
	/**
	 * Adds an object to the list of objects in the room.
	 * 
	 * @usage room.addObject(obj);
	 * @param The
	 *            AdvObject to be added
	 */
	public void addObject(AdvObject obj)
	{
		
		this.G_objlist.add(obj);
	}

	
	/**
	 * Removes an object from the list of objects in the room.
	 * 
	 * @usage room.removeObject(obj);
	 * @param The
	 *            AdvObject to be removed
	 */
	public void removeObject(AdvObject obj) {
		this.G_objlist.remove(obj);
	}

	
	/**
	 * Checks whether the specified object is in the room.
	 * 
	 * @usage if (room.containsObject(obj)) . . .
	 * @param The
	 *            AdvObject being tested
	 * @return true if the object is in the room, and false otherwise
	 */
	public boolean containsObject(AdvObject obj) {
		return this.G_objlist.contains(obj);
	}

	/**
	 * Returns the number of objects in the room.
	 * 
	 * @usage int nObjects = room.getObjectCount();
	 * @return The number of objects in the room
	 */
	public int getObjectCount() {
		return this.G_objlist.size();
	}

	/**
	 * Returns the specified element from the list of objects in the room.
	 * 
	 * @usage AdvObject obj = room.getObject(index);
	 * @return The AdvObject at the specified index position
	 */
	public AdvObject getObject(int index) {
		return this.G_objlist.get(index);
		//return super.getObject(index);
	}

	/**
	 * Sets the flag indicating that this room has been visited according to the
	 * value of the parameter. Calling setVisited(true) means that the room has
	 * been visited; calling setVisited(false) restores its initial unvisited
	 * state.
	 * 
	 * @usage room.setVisited(flag);
	 * @param flag
	 *            The new state of the "visited" flag
	 */
	public void setVisited(boolean flag) {
		
		this.G_visited = flag;
	}

	/**
	 * Returns true if the room has previously been visited.
	 * 
	 * @usage if (room.hasBeenVisited()) . . .
	 * @return true if the room has been visited; false otherwise
	 */
	public boolean hasBeenVisited() {
		return this.G_visited;
		//return super.hasBeenVisited(); // Replace with your code
	}

	/**
	 * Returns the motion table associated with this room, which is an array of
	 * directions, room numbers, and enabling objects stored in a
	 * AdvMotionTableEntry.
	 * 
	 * @usage AdvMotionTableEntry[] motionTable = room.getMotionTable();
	 * @return The array of motion table entries associated with this room
	 */
	public AdvMotionTableEntry[] getMotionTable() {
		return this.G_motiontable.toArray(new AdvMotionTableEntry[this.G_motiontable.size()]);
	}

	private AdvRoom ()
	{
		
	}
	
	/**
	 * Reads the data for this room from the Scanner scan, which must have been
	 * opened by the caller. This method returns a room if the room
	 * initialization is successful; if there are no more rooms to read,
	 * readFromFile returns null.
	 * 
	 * @usage AdvRoom room = AdvRoom.readFromFile(scan);
	 * @param scan
	 *            A scanner open on the rooms data file
	 * @return a room if successfully read; null if at end of file
	 */
	public static AdvRoom readFromFile(Scanner sc) 
	{
		
		AdvRoom advr = new AdvRoom();
		byte steps = 0 ;
		String s;
		while(sc.hasNextLine()&&!(s=sc.nextLine()).matches("\\s*"))
		{
			switch(steps)
			{
			case 0:
				if(AdvRoom.isRoomNUmber(s))
				{
					advr.G_roomnumber = Integer.parseInt(s.trim());
					if(advr.G_roomnumber<=0)throw new AssertionError();
					steps++;
				}
				break;
			case 1:
				advr.G_roomtitle = s;
				steps++;
				break;
			case 2:
				if(!AdvRoom.isSeperator(s))
				{
					advr.G_description.add(s);
				}
				else
				{
					steps++;
				}
				break;
			case 3:
				AdvMotionTableEntry AdMTE;
				// probably a motion entry
				if((AdMTE = AdvRoom.parseIntoMotiontable(s))!=null)
				advr.G_motiontable.add(AdMTE);
				break;
			}
		}
		if(steps==3&&advr.G_motiontable.size()>0)
		{
			return advr;
		}
		else if(advr.G_motiontable.size()==0)
		{
			throw new AssertionError();
		}
		return null;
	}

	
	
	
	
	/* Private instance variables */
	// Add your own instance variables here
	
	
	private int G_roomnumber;
	private String G_roomtitle;
	private List<String> G_description = new ArrayList<String>();
	private boolean G_visited =false;
	private List<AdvObject> G_objlist = new ArrayList<AdvObject>();
	private List<AdvMotionTableEntry> G_motiontable = new ArrayList<AdvMotionTableEntry>();
	
	
	
	//----------------------You are about to see all the static method that 
	//----------------------helps us to find info in the file to fill 
	//----------------------up the room information.
	
	/**
	 * Test if the given piece of string satisfies the conditions for a 
	 * title of the room. 
	 * @return
	 * indicate whether is is true;
	 */
	public static boolean isRoomNUmber(String s)
	{
		return s.matches("\\s*\\d+\\s*$");
	}
	
	
	/**
	 * Is there a separator a that seperate the description and the 
	 * all the directions? 
	 * @return
	 */
	public static boolean isSeperator(String s)
	{
		return s.matches("\\s*-{5,}\\s*$");
	}
	
	
	/**
	 * This method will give a line of string  and this method will 
	 * extract all the information in it and return a advtableentry 
	 * if the line of string contains all the information. 
	 * 
	 * @return
	 *  the advobeject or null if the given string can be turn into it. 
	 */
	public static AdvMotionTableEntry parseIntoMotiontable(String s)
	{
		String[] stuff = motionTableSplit(s);
		AdvMotionTableEntry AdvMTE = null;
		if(stuff == null)return AdvMTE;
		if(stuff.length == 3)
		{
			AdvMTE=new AdvMotionTableEntry(stuff[0],Integer.parseInt(stuff[1]),stuff[2]);
		}
		else if(stuff.length == 2)
		{
			AdvMTE= new AdvMotionTableEntry(stuff[0],Integer.parseInt(stuff[1]),null);
		}
		return AdvMTE;
	}
	
	/**
	 * Split the given string in to array of string for the 
	 * moton table. 
	 * @param s
	 * @return
	 * null if the string doesn't satisfy the conditions for the motion 
	 * table. 
	 */
	public static String[] motionTableSplit(String s)
	{
		return isMotionTable(s)?s.split("\\s+|/",3):null;
	}
	
	/**
	 * tells if the string is a motion table. 
	 * @param s
	 * @return
	 */
	public static boolean isMotionTable(String s)
	{
		if( s.matches("\\s*[A-Z]+\\s*\\d+\\s*/?[A-Z]+\\s*$")) return true;
		else
		{
			return s.matches("\\s*[A-Z]+\\s*\\d+\\s*/?[A-Z]*\\s*$");
		}
	}
	
	public String toString()
	{
		String s = "------------"+this.getClass()+"-----------\n";
		
		s+= "title: "+this.G_roomtitle+" Room bumber: "+this.G_roomnumber+"\n";
		s+="Description: "+ this.G_description.toString()+"\n";
		if(this.G_objlist.size()>0)s+="Objects: "+ this.G_objlist.toString()+"\n";
		s+="Motiontable: "+this.G_motiontable.toString()+"\n";
		return s ;
		
	}

	/**
	 * The factory method when you put into the scanner and 
	 * it will return the advRoom object.
	 * 
	 * This is method is only for testing. 
	 * @return
	 * null if the object can not be created. 
	 */
	@Deprecated
	public static AdvRoom readFromFile_(Scanner sc)
	{
		AdvRoom advr = new AdvRoom();
		boolean numbertaken = false;
		boolean titletaken = false;
		boolean descriptiontaken = false;
		String s;
		while(sc.hasNextLine()&&!(s=sc.nextLine()).matches("\\s*"))
		{
			if(!numbertaken)
			{
				if(AdvRoom.isRoomNUmber(s))
				{
					advr.G_roomnumber = Integer.parseInt(s.trim());
					if(advr.G_roomnumber<=0)throw new AssertionError();
					numbertaken = true;
				}
			}
			else if(!titletaken)
			{
				advr.G_roomtitle = s;
				titletaken = true;
			}
			else
			{
				
				if(!descriptiontaken)
				{
					if(!AdvRoom.isSeperator(s))
					{
						advr.G_description.add(s);
					}
					else
					{
						descriptiontaken = true;
					}
				}
				else
				{
					AdvMotionTableEntry AdMTE;
					// probably a motionentry
					if((AdMTE = AdvRoom.parseIntoMotiontable(s))!=null)
					advr.G_motiontable.add(AdMTE);
				}
				
			}
		}
		if(numbertaken&&titletaken&&advr.G_motiontable.size()>0)
		{
			return advr;
		}
		return null;
		
	}
	
	
	
	
}
