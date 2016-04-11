package game;

public class Door
{
	protected Room room1 = null;
	protected Room room2 = null;
	protected boolean isClosed = false;
	
	public Door()
	{
		
	}
	
	public Door(Room r1, Room r2, boolean close)
	{
		room1 = r1;
		room2 = r2;
		isClosed = close;
	}
	
	// Returns room on other side of door from room r
	public Room getOtherRoom(Room r)
	{
		if (isClosed)
			return (null);
		
		return (r == room1 ? room2 : room1);
	}
	
	// Open the door
	public void open()
	{
		isClosed = false;
	}
	
	// Close the door
	public void close()
	{
		isClosed = true;
	}
}
