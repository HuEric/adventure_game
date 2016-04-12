package game.framework.factories;

import game.framework.Door;
import game.framework.DoorWithLock;
import game.framework.Room;

public class DoorWithLockFactory
{
	private static DoorWithLockFactory _instance = null;

	private DoorWithLockFactory()
	{
	}

	public static synchronized DoorWithLockFactory getInstance()
	{
		if (_instance == null)
			_instance = new DoorWithLockFactory();
		return _instance;
	}

	public Door createDoor(Room r1, Room r2, boolean isClosed, String doorName, boolean isLocked)
	{
		Door newDoor = new DoorWithLock(r1, r2, isClosed, doorName, isLocked);

		return (newDoor);
	}

	public Door createDoor()
	{
		Door newDoorWithLock = new DoorWithLock();

		return (newDoorWithLock);
	}
}
