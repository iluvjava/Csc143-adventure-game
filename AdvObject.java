/*
 * File: AdvObject.java
 * --------------------
 * This file defines a class that models an object in the
 * Adventure game.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/* Class: AdvObject */
/**
 * This class defines an object in the Adventure game.  An object is
 * characterized by the following properties:
 *
 * <ul>
 * <li>Its name, which is the noun used to refer to the object
 * <li>Its description, which is a string giving a short description
 * <li>The room number in which the object initially lives
 * </li>
 *
 * The external format of the objects file is described in the
 * assignment handout.  The comments on the methods exported by
 * this class show how to use the initialized data structure.
 */

public class AdvObject  {
	
	
	
	private String G_name; private String G_describe;
	private int G_inititial;
	
	
	/** 
	 * 
	 */
	private AdvObject()
	{
		
	}


/**
 * Returns the object name, which is the word used to refer to it.
 *
 * @usage String name = obj.getName();
 * @return The name of the object
 */
	public String getName() {
		return this.G_name; // Replace with your code
	}

/* Method: getDescription() */
/**
 * Returns the one-line description of the object.  This description
 * should start with an article, as in "a set of keys" or "an emerald
 * the size of a plover's egg."
 *
 * @usage String name = obj.getDescription();
 * @return The description of the object
 */
	public String getDescription() {
		return this.G_describe; // Replace with your code
	}


/* Method: getInitialLocation() */
/**
 * Returns the initial location of the object.
 *
 * @usage int roomNumber = obj.getInitialLocation();
 * @return The room number in which the object initially resides
 */
	public int getInitialLocation() {
		return this.G_inititial; // Replace with your code
	}

	
	
	/* Method: readFromFile(scan) */
	/**
	 * Reads the data for this object from the Scanner scan, which must have
	 * been opened by the caller. This method returns the object if the object
	 * initialization is successful; if there are no more objects to read,
	 * readFromFile returns null
	 * 
	 * @usage AdvObject object = AdvObject.readFromFile(scan);
	 * @param scan
	 *            A Scanner open on the objects data file
	 * @return the object if an object is successfully read; null at end of file
	 */
	public static AdvObject readFromFile(Scanner scan) {
		ArrayList<String> strlist = new ArrayList();
		AdvObject result = new AdvObject();
		
		boolean nametaken = false;
		while(scan.hasNextLine())
		{
			String s = scan.nextLine();
			if(s.matches("[A-Z]+\\s*$"))
			{
				result.G_name = s; 
				nametaken =true;
			}
			else if(nametaken)
			{
				if(s.matches("\\d+\\s*$"))
				{
					result.G_inititial = Integer.parseInt(s);
					break;
				}
				result.G_describe = s;
			}
			
		}
		if(result.G_inititial==0||result.G_name==null||result.G_name==null)
		{
			
			return null;
			
		}
		//System.out.println(result);
		return result;
		
		//return AdvObjectStub.readFromFile(scan); // Replace with your code
	}

/* Private instance variables */
	// Add your own instance variables here
	
	
	public String toString()
	{
		String s = "\n-------Type: " + this.getClass();
		s+="\n";
		s+="getname(): "+"\"\""+this.getName()+ "\"\""+" getDescription "+ "\"\""+this.getDescription()+"\"\""+" Initial location "+this.getInitialLocation();
		return s; 
	}

}

