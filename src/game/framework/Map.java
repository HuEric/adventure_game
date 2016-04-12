package game.framework;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Collections;

import game.GameManager;
import game.framework.factories.RoomFactory;

public class Map
{
	protected ArrayList<MapSite> _locations = null;

	public Map()
	{
		System.out.println("Map Created");

		_locations = new ArrayList<MapSite>();
	}

	public void initialize()
	{
		int totalRoom = 13;

		// Create 13 Rooms
		for (int roomNumber = 0; roomNumber < totalRoom; roomNumber++)
		{
			Room currentRoom = RoomFactory.getInstance().createRoom(roomNumber);
			currentRoom.initialize();
			_locations.add(currentRoom);
		}

		// Link Room 0 to Room 1
		Room firstRoom = ((Room) _locations.get(0));
		Room secondRoom = ((Room) _locations.get(1));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Right, true);

		// Link Room 1 to Room 2
		firstRoom = ((Room) _locations.get(1));
		secondRoom = ((Room) _locations.get(2));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Up, false);

		// Link Room 1 to Room 12
		firstRoom = ((Room) _locations.get(1));
		secondRoom = ((Room) _locations.get(12));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Right, false);

		// Link Room 2 to Room 3
		firstRoom = ((Room) _locations.get(2));
		secondRoom = ((Room) _locations.get(3));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Up, false);

		// Link Room 3 to Room 4
		firstRoom = ((Room) _locations.get(3));
		secondRoom = ((Room) _locations.get(4));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Right, true);

		// Link Room 4 to Room 5
		firstRoom = ((Room) _locations.get(4));
		secondRoom = ((Room) _locations.get(5));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Right, false);

		// Link Room 5 to Room 6
		firstRoom = ((Room) _locations.get(5));
		secondRoom = ((Room) _locations.get(6));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Right, true);

		// Link Room 6 to Room 7
		firstRoom = ((Room) _locations.get(6));
		secondRoom = ((Room) _locations.get(7));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Down, false);

		// Link Room 7 to Room 8
		firstRoom = ((Room) _locations.get(7));
		secondRoom = ((Room) _locations.get(8));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Down, true);

		// Link Room 8 to Room 9
		firstRoom = ((Room) _locations.get(8));
		secondRoom = ((Room) _locations.get(9));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Down, true);

		// Link Room 9 to Room 10
		firstRoom = ((Room) _locations.get(9));
		secondRoom = ((Room) _locations.get(10));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Left, true);

		// Link Room 10 to Room 11
		firstRoom = ((Room) _locations.get(10));
		secondRoom = ((Room) _locations.get(11));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Left, false);

		// Link Room 11 to Room 12
		firstRoom = ((Room) _locations.get(11));
		secondRoom = ((Room) _locations.get(12));
		firstRoom.roomToLinkWith(secondRoom, GameManager.Direction.Up, true);

	}

	public void initializeGraphic()
	{
		// Initialize each rooms
		Enumeration<MapSite> rooms = this.getLocations();

		while (rooms.hasMoreElements())
		{
			((Room)rooms.nextElement()).initializeGraphic();
		}
	}

	public void update()
	{

	}

	/**
	 * Get Rooms
	 * 
	 * @return Enumeration of Rooms
	 */
	public Enumeration<MapSite> getLocations()
	{
		return Collections.enumeration(_locations);
	}
}
