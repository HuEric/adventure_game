package game.framework;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;

import game.GameManager;
import game.GameObject;

public class Door extends GameObject
{
	protected Room		_room1		= null;
	protected Room		_room2		= null;
	protected boolean	_isClosed	= true;
	protected String	_name		= null;

	public Door()
	{

	}

	public Door(Room r1, Room r2, boolean isClosed, String name)
	{
		_room1 = r1;
		_room2 = r2;
		_isClosed = isClosed;
		_name = name;
	}

	// Returns room on other side of door from room r
	public Room getOtherRoom(Room r)
	{
		System.out.println("Door Status: " + _isClosed);
		System.out.println(r.getName());
		if (_isClosed)
			return (null);

		return (r == _room1 ? _room2 : _room1);
	}

	// Open the door
	public void open()
	{
		_isClosed = false;
		this.getGeometry().getMaterial().setColor("Color", ColorRGBA.Green);
	}

	// Close the door
	public void close()
	{
		_isClosed = true;
		this.getGeometry().getMaterial().setColor("Color", ColorRGBA.Red);
	}
	
	public boolean isOpen()
	{
		return (!_isClosed);
	}

	@Override
	public void initializeGraphic()
	{
		// If the door is closed set the Color to Red
		ColorRGBA wallColor;
		if (_isClosed)
			wallColor = ColorRGBA.Red;
		else
			wallColor = ColorRGBA.Green;
		Mesh wallMesh = new Box(1, 1, 1);
		this.initializeGameObject(GameManager.getInstance().getAssetManager(), wallColor, wallMesh, _name);
		this.getGeometry().scale(0.1f, 1, 5);
	}
}
