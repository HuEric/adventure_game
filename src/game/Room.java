package game;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Collections;

public class Room extends MapSite
{
	protected int				number;
	protected String			name;
	protected Wall				walls;
	protected ArrayList<Player>	players = new ArrayList<Player>();
	protected ArrayList<Object>	things = new ArrayList<Object>();
	
	public Room()
	{
		
	}
	
	@Override
	void enter(Player p)
	{
		if (players.contains(p) == false)
			players.add(p);
	}

	@Override
	void exit(Player p)
	{
		players.remove(p);
	}

	/** Returns an enumeration of the players in the room */
	public Enumeration<Player> players()
	{
		return Collections.enumeration(players);
	}
	
	/** Returns true if player p is in the room */
	public boolean hasPlayer(Player p)
	{
		return players.contains(p);
	}

	/** Returns an enumeration of the movable things in the room */
	public Enumeration<Object> things()
	{
		return Collections.enumeration(things);
	}

	/** Returns true if the movable thing t is in the room */
	public boolean hasThing(Object t)
	{
		return things.contains(t);
	}
	
	/** Adds the movable thing t to the room */
	public void addThing(Object t)
	{
		things.add(t);
	}
	
	/** Removes the movable thing t from the room */
	public void removeThing(Object t) 
	{
		things.remove(t);
	}
}
