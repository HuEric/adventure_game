package game.framework.factories;

import game.framework.Door;
import game.framework.Room;

public class DoorFactory
{
	/**
	 * Singleton Factory pattern
	 */
	protected static DoorFactory _instance = null;

	protected DoorFactory()
	{
	}

	public static synchronized DoorFactory getInstance()
	{
		if (_instance == null)
			_instance = new DoorFactory();
		return _instance;
	}

	public Door createDoor(Room r1, Room r2, boolean isClosed, String doorName)
	{
		Door newDoor = new Door(r1, r2, isClosed, doorName);

		return (newDoor);
	}
}
