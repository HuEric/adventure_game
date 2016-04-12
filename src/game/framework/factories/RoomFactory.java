package game.framework.factories;

import game.framework.Room;

public class RoomFactory
{
	protected static RoomFactory _instance = null;

	protected RoomFactory()
	{
	}

	public static synchronized RoomFactory getInstance()
	{
		if (_instance == null)
			_instance = new RoomFactory();
		return _instance;
	}

	public Room createRoom(int number)
	{
		Room newRoom = new Room(number);

		return newRoom;
	}
}
