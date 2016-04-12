package game.framework;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;

import game.GameManager;
import game.GameObject;

public class Door extends GameObject
{
	/**
	 * Door attributes
	 */
	protected Room		_room1		= null;
	protected Room		_room2		= null;
	protected boolean	_isClosed	= true;
	protected String	_name		= null;

	public Door()
	{
	}

	/**
	 * Door Constructor
	 * @param r1
	 * @param r2
	 * @param isClosed
	 * @param name
	 */
	public Door(Room r1, Room r2, boolean isClosed, String name)
	{
		_room1 = r1;
		_room2 = r2;
		_isClosed = isClosed;
		_name = name;
	}

	/**
	 * Returns room on other side of door from room r
	 * @param r	From Room
	 * @return	Other linked Room
	 */
	public Room getOtherRoom(Room r)
	{
		System.out.println("Door is closed: " + _isClosed);
		System.out.println("Door to " + r.getName());
		
		// Return null if closed
		if (_isClosed)
			return (null);

		// Return the room depending on r
		return (r == _room1 ? _room2 : _room1);
	}

	/**
	 *  Open the door
	 */
	public void open()
	{
		_isClosed = false;
	}

	/**
	 *  Close the door
	 */
	public void close()
	{
		_isClosed = true;
	}
	
	/**
	 * Getters
	 */
	public boolean isClosed()
	{
		return (_isClosed);
	}

	
	/**
	 * Initialize Door Graphics
	 */
	@Override
	public void initializeGraphic()
	{
		// If the door is closed set the Color to Red else Green
		ColorRGBA doorColor;
		if (_isClosed)
			doorColor = ColorRGBA.Red;
		else
			doorColor = ColorRGBA.Green;

		// Create Door Graphics
		Mesh doorMesh = new Box(1, 1, 1);
		this.initializeGameObject(GameManager.getInstance().getAssetManager(), doorColor, doorMesh, _name);
		this.getGeometry().scale(0.1f, 1, 5);
	}
}
