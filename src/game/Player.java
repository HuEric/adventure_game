package game;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Collections;

public class Player
{
	// Name of the player
	protected String			name;
	// Room the player is in
	protected Room				room;
	// Items the player is carrying
	protected ArrayList<Object>	items = new ArrayList<Object>();
	
	//  Returns an enumeration of the items the player is carrying
	public Enumeration<Object> items()
	{
		return Collections.enumeration(items);
	}
	
	//  Returns true if the player has the item i
	public boolean hasItem(Object i)
	{
		return (items.contains(i));
	}
	
	//  Adds item i to player's inventory if item i is in the room
	public void takeItem(Object i)
	{
		items.add(i);
	}
	
	//  Removes item i from player's inventory and drops it in the room
	public void dropItem(Object i)
	{
		items.remove(i);
	}
	
	//  Moves player to room r; returns room exited
	public Room moveToRoom(Room r)
	{
		// Save the last room to return it later
		Room lastRoom = room;
		
		// Exit current room
		lastRoom.exit(this);
		// Enter new room
		r.enter(this);
		// Save the new room in Player object
		room = r;
		
		// Return the last room
		return lastRoom;
	}
}
