package game;

public class DoorWithLook extends Door
{

	protected boolean	isLocked = false;

	public DoorWithLook()
	{
		
	}

	public DoorWithLook(boolean lock)
	{
		isLocked = lock;
	}
	
	@Override
	// Can open if unlocked
	public void open()
	{
		if (!isLocked)
			isClosed = false;
	}

	// Lock the Door
	public void lock()
	{
		isLocked = true;
	}
	
	// Unlock the Door
	public void unlock()
	{
		isLocked = false;
	}
}
